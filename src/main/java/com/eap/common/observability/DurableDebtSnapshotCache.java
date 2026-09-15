package com.eap.common.observability;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/** Thread-safe cache that makes failed or stale durable-debt observations explicit. */
public final class DurableDebtSnapshotCache {

    private final String service;
    private final List<String> workAllowlist;
    private final Clock clock;
    private final AtomicReference<SuccessfulObservation> lastSuccess;
    private final AtomicBoolean lastAttemptSucceeded = new AtomicBoolean(false);

    public DurableDebtSnapshotCache(String service, Collection<String> workAllowlist) {
        this(service, workAllowlist, Clock.systemUTC());
    }

    DurableDebtSnapshotCache(String service, Collection<String> workAllowlist, Clock clock) {
        if (service == null || service.isBlank()) {
            throw new IllegalArgumentException("service must not be blank");
        }
        this.service = service;
        this.workAllowlist = List.copyOf(workAllowlist);
        if (this.workAllowlist.isEmpty()
                || this.workAllowlist.stream().anyMatch(work -> work == null || work.isBlank())
                || this.workAllowlist.stream().distinct().count() != this.workAllowlist.size()) {
            throw new IllegalArgumentException("workAllowlist must contain unique non-blank values");
        }
        this.clock = Objects.requireNonNull(clock, "clock");
        this.lastSuccess = new AtomicReference<>(new SuccessfulObservation(
                Instant.EPOCH,
                zeroComponents()));
    }

    public void recordSuccess(Collection<DurableDebtSnapshot.ComponentDebt> observed) {
        Map<String, DurableDebtSnapshot.ComponentDebt> byWork = new LinkedHashMap<>();
        for (DurableDebtSnapshot.ComponentDebt component : observed) {
            if (!workAllowlist.contains(component.work())) {
                throw new IllegalArgumentException("unknown durable-debt work: " + component.work());
            }
            if (byWork.put(component.work(), component) != null) {
                throw new IllegalArgumentException("duplicate durable-debt work: " + component.work());
            }
        }
        List<DurableDebtSnapshot.ComponentDebt> normalized = workAllowlist.stream()
                .map(work -> byWork.getOrDefault(work, zero(work)))
                .toList();
        lastSuccess.set(new SuccessfulObservation(Instant.now(clock), normalized));
        lastAttemptSucceeded.set(true);
    }

    public void recordFailure() {
        lastAttemptSucceeded.set(false);
    }

    public DurableDebtSnapshot snapshot() {
        SuccessfulObservation successful = lastSuccess.get();
        long age = Math.max(0, Duration.between(successful.observedAt(), Instant.now(clock)).toSeconds());
        return new DurableDebtSnapshot(
                DurableDebtSnapshot.CONTRACT_VERSION,
                service,
                successful.observedAt(),
                lastAttemptSucceeded.get(),
                age,
                successful.components());
    }

    public DurableDebtSnapshot.ComponentDebt component(String work) {
        return snapshot().components().stream()
                .filter(component -> component.work().equals(work))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown durable-debt work: " + work));
    }

    private List<DurableDebtSnapshot.ComponentDebt> zeroComponents() {
        return workAllowlist.stream().map(DurableDebtSnapshotCache::zero).toList();
    }

    private static DurableDebtSnapshot.ComponentDebt zero(String work) {
        return new DurableDebtSnapshot.ComponentDebt(work, 0, 0, 0, 0);
    }

    private record SuccessfulObservation(
            Instant observedAt,
            List<DurableDebtSnapshot.ComponentDebt> components) {
    }
}
