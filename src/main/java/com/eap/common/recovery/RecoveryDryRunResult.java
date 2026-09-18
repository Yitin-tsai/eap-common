package com.eap.common.recovery;

import java.util.Objects;

public record RecoveryDryRunResult(
        String caseId,
        RecoveryActionType action,
        boolean allowed,
        String reason,
        String currentFingerprint,
        RecoveryCaseSummary current) {

    public RecoveryDryRunResult {
        if (caseId == null || caseId.isBlank()) {
            throw new IllegalArgumentException("caseId must not be blank");
        }
        action = Objects.requireNonNull(action, "action");
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("reason must not be blank");
        }
        if (currentFingerprint == null || currentFingerprint.isBlank()) {
            throw new IllegalArgumentException("currentFingerprint must not be blank");
        }
        current = Objects.requireNonNull(current, "current");
    }
}
