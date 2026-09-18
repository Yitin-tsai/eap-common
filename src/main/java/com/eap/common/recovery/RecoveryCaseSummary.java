package com.eap.common.recovery;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

public record RecoveryCaseSummary(
        String caseId,
        String service,
        RecoveryDebtType debtType,
        String work,
        String sourceId,
        String status,
        RecoveryFailureClass failureClass,
        int attemptCount,
        Instant firstSeenAt,
        Instant lastUpdatedAt,
        String errorType,
        String errorSummary,
        Map<String, String> identity,
        Set<RecoveryActionType> allowedActions,
        String fingerprint) {

    public RecoveryCaseSummary {
        caseId = requireText(caseId, "caseId");
        service = requireText(service, "service");
        debtType = java.util.Objects.requireNonNull(debtType, "debtType");
        work = requireText(work, "work");
        sourceId = requireText(sourceId, "sourceId");
        status = requireText(status, "status");
        failureClass = java.util.Objects.requireNonNull(failureClass, "failureClass");
        if (attemptCount < 0) {
            throw new IllegalArgumentException("attemptCount must be non-negative");
        }
        firstSeenAt = java.util.Objects.requireNonNull(firstSeenAt, "firstSeenAt");
        lastUpdatedAt = java.util.Objects.requireNonNull(lastUpdatedAt, "lastUpdatedAt");
        if (lastUpdatedAt.isBefore(firstSeenAt)) {
            throw new IllegalArgumentException("lastUpdatedAt must not precede firstSeenAt");
        }
        identity = Map.copyOf(identity == null ? Map.of() : identity);
        allowedActions = Set.copyOf(allowedActions == null ? Set.of() : allowedActions);
        fingerprint = requireText(fingerprint, "fingerprint");
    }

    private static String requireText(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
