package com.eap.common.observability;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DurableDebtSnapshotTest {

    @Test
    void copiesComponentsAndPreservesSubsetSemantics() {
        var components = new java.util.ArrayList<DurableDebtSnapshot.ComponentDebt>();
        components.add(new DurableDebtSnapshot.ComponentDebt("outbox", 3, 1, 1, 9));

        DurableDebtSnapshot snapshot = new DurableDebtSnapshot(
                DurableDebtSnapshot.CONTRACT_VERSION,
                "eap-order",
                Instant.EPOCH,
                true,
                0,
                components);
        components.clear();

        assertEquals(1, snapshot.components().size());
        assertThrows(UnsupportedOperationException.class, () -> snapshot.components().clear());
    }

    @Test
    void rejectsRetryOrTerminalCountsOutsideTotal() {
        assertThrows(IllegalArgumentException.class,
                () -> new DurableDebtSnapshot.ComponentDebt("inbox", 1, 2, 0, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new DurableDebtSnapshot.ComponentDebt("inbox", 1, 0, 2, 1));
    }

    @Test
    void rejectsAgeForEmptyDebt() {
        assertThrows(IllegalArgumentException.class,
                () -> new DurableDebtSnapshot.ComponentDebt("inbox", 0, 0, 0, 1));
    }
}
