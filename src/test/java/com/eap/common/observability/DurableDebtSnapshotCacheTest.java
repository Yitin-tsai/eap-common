package com.eap.common.observability;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DurableDebtSnapshotCacheTest {

    @Test
    void startsUnhealthySoInitialZeroesCannotBeTreatedAsCompletion() {
        Clock clock = Clock.fixed(Instant.parse("2026-09-14T00:00:00Z"), ZoneOffset.UTC);
        DurableDebtSnapshotCache cache = new DurableDebtSnapshotCache(
                "eap-order", List.of("inbox"), clock);

        assertFalse(cache.snapshot().observationSuccess());
        assertTrue(cache.snapshot().snapshotAgeSeconds() > 0);
        assertEquals(0, cache.component("inbox").totalCount());
    }

    @Test
    void fillsMissingAllowlistedWorkAndRejectsUnknownWork() {
        Clock clock = Clock.fixed(Instant.parse("2026-09-14T00:00:00Z"), ZoneOffset.UTC);
        DurableDebtSnapshotCache cache = new DurableDebtSnapshotCache(
                "eap-order", List.of("inbox", "outbox"), clock);

        cache.recordSuccess(List.of(new DurableDebtSnapshot.ComponentDebt("inbox", 2, 1, 0, 5)));

        assertTrue(cache.snapshot().observationSuccess());
        assertEquals(0, cache.component("outbox").totalCount());
        assertThrows(IllegalArgumentException.class,
                () -> cache.recordSuccess(List.of(
                        new DurableDebtSnapshot.ComponentDebt("unknown", 1, 0, 0, 1))));
    }

    @Test
    void retainsLastSuccessfulValuesAndMarksFailedAttempt() {
        MutableClock clock = new MutableClock(Instant.parse("2026-09-14T00:00:00Z"));
        DurableDebtSnapshotCache cache = new DurableDebtSnapshotCache("eap-wallet", List.of("inbox"), clock);
        cache.recordSuccess(List.of(new DurableDebtSnapshot.ComponentDebt("inbox", 2, 1, 0, 5)));

        clock.advanceSeconds(17);
        cache.recordFailure();

        assertFalse(cache.snapshot().observationSuccess());
        assertEquals(17, cache.snapshot().snapshotAgeSeconds());
        assertEquals(2, cache.component("inbox").totalCount());

        cache.recordSuccess(List.of(new DurableDebtSnapshot.ComponentDebt("inbox", 0, 0, 0, 0)));

        assertTrue(cache.snapshot().observationSuccess());
        assertEquals(0, cache.snapshot().snapshotAgeSeconds());
        assertEquals(0, cache.component("inbox").totalCount());
    }

    @Test
    void rejectsDuplicateWork() {
        DurableDebtSnapshotCache cache = new DurableDebtSnapshotCache(
                "eap-matchEngine", List.of("cleanup"));

        assertThrows(IllegalArgumentException.class, () -> cache.recordSuccess(List.of(
                new DurableDebtSnapshot.ComponentDebt("cleanup", 1, 0, 0, 1),
                new DurableDebtSnapshot.ComponentDebt("cleanup", 1, 1, 0, 1))));
    }

    private static final class MutableClock extends Clock {
        private final AtomicReference<Instant> now;

        private MutableClock(Instant now) {
            this.now = new AtomicReference<>(now);
        }

        private void advanceSeconds(long seconds) {
            now.updateAndGet(value -> value.plusSeconds(seconds));
        }

        @Override
        public ZoneId getZone() {
            return ZoneOffset.UTC;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            return this;
        }

        @Override
        public Instant instant() {
            return now.get();
        }
    }
}
