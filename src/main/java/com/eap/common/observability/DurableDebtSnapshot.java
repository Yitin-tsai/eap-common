package com.eap.common.observability;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * Versioned, low-cardinality view of unfinished durable work owned by one service.
 *
 * <p>{@code retryCount} and {@code terminalCount} are subsets of {@code totalCount}.
 * A failed observation retains the last successful component values, while
 * {@code observationSuccess=false} and {@code snapshotAgeSeconds} make the stale
 * data explicit to callers.</p>
 */
public record DurableDebtSnapshot(
        int contractVersion,
        String service,
        Instant observedAt,
        boolean observationSuccess,
        long snapshotAgeSeconds,
        List<ComponentDebt> components) {

    public static final int CONTRACT_VERSION = 1;

    public DurableDebtSnapshot {
        if (contractVersion <= 0) {
            throw new IllegalArgumentException("contractVersion must be positive");
        }
        service = requireText(service, "service");
        Objects.requireNonNull(observedAt, "observedAt");
        if (snapshotAgeSeconds < 0) {
            throw new IllegalArgumentException("snapshotAgeSeconds must be non-negative");
        }
        components = List.copyOf(components);
    }

    public record ComponentDebt(
            String work,
            long totalCount,
            long retryCount,
            long terminalCount,
            long oldestUnresolvedAgeSeconds) {

        public ComponentDebt {
            work = requireText(work, "work");
            if (totalCount < 0 || retryCount < 0 || terminalCount < 0
                    || oldestUnresolvedAgeSeconds < 0) {
                throw new IllegalArgumentException("durable debt values must be non-negative");
            }
            if (retryCount > totalCount || terminalCount > totalCount) {
                throw new IllegalArgumentException("retry and terminal debt must be subsets of total debt");
            }
            if (totalCount == 0 && oldestUnresolvedAgeSeconds != 0) {
                throw new IllegalArgumentException("empty durable debt cannot have a non-zero age");
            }
        }
    }

    private static String requireText(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
