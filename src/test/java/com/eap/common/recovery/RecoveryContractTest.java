package com.eap.common.recovery;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RecoveryContractTest {

    @Test
    void caseIdRoundTripsOpaqueSourceIdentity() {
        String encoded = RecoveryCaseId.encode(
                "eap-wallet",
                RecoveryDebtType.INBOX_TERMINAL,
                "trade/execution.inbox",
                "TRADE_EXECUTED:abc/123");

        assertEquals(new RecoveryCaseId.Parts(
                        "eap-wallet",
                        RecoveryDebtType.INBOX_TERMINAL,
                        "trade/execution.inbox",
                        "TRADE_EXECUTED:abc/123"),
                RecoveryCaseId.decode(encoded));
    }

    @Test
    void summaryDefensivelyCopiesIdentityAndActions() {
        Map<String, String> identity = new java.util.HashMap<>();
        identity.put("orderId", "order-1");
        Set<RecoveryActionType> actions = new java.util.HashSet<>();
        actions.add(RecoveryActionType.PARK);

        RecoveryCaseSummary summary = new RecoveryCaseSummary(
                "case-1",
                "eap-order",
                RecoveryDebtType.SAGA_TIMEOUT,
                "order_saga",
                "order-1",
                "PENDING_ASSET_CHECK",
                RecoveryFailureClass.PREREQUISITE,
                0,
                Instant.parse("2026-09-17T00:00:00Z"),
                Instant.parse("2026-09-17T00:10:00Z"),
                null,
                null,
                identity,
                actions,
                RecoveryFingerprint.sha256("case-1"));
        identity.clear();
        actions.clear();

        assertEquals(Map.of("orderId", "order-1"), summary.identity());
        assertEquals(Set.of(RecoveryActionType.PARK), summary.allowedActions());
    }

    @Test
    void rejectsTimeTravelInCaseSummary() {
        assertThrows(IllegalArgumentException.class, () -> new RecoveryCaseSummary(
                "case-1",
                "eap-order",
                RecoveryDebtType.SAGA_TIMEOUT,
                "order_saga",
                "order-1",
                "PENDING_ASSET_CHECK",
                RecoveryFailureClass.PREREQUISITE,
                0,
                Instant.parse("2026-09-17T00:10:00Z"),
                Instant.parse("2026-09-17T00:00:00Z"),
                null,
                null,
                Map.of(),
                Set.of(),
                RecoveryFingerprint.sha256("case-1")));
    }
}
