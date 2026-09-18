package com.eap.common.recovery;

import java.util.Objects;
import java.util.UUID;

public record RecoveryExecuteResult(
        UUID actionId,
        String caseId,
        RecoveryActionType action,
        RecoveryExecutionStatus status,
        String message,
        RecoveryCaseSummary before,
        RecoveryCaseSummary after) {

    public RecoveryExecuteResult {
        actionId = Objects.requireNonNull(actionId, "actionId");
        if (caseId == null || caseId.isBlank()) {
            throw new IllegalArgumentException("caseId must not be blank");
        }
        action = Objects.requireNonNull(action, "action");
        status = Objects.requireNonNull(status, "status");
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("message must not be blank");
        }
        before = Objects.requireNonNull(before, "before");
    }
}
