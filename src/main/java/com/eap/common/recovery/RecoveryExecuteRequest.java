package com.eap.common.recovery;

import java.util.Objects;
import java.util.UUID;

public record RecoveryExecuteRequest(
        UUID actionId,
        RecoveryActionType action,
        String expectedFingerprint) {

    public RecoveryExecuteRequest {
        actionId = Objects.requireNonNull(actionId, "actionId");
        action = Objects.requireNonNull(action, "action");
        if (expectedFingerprint == null || expectedFingerprint.isBlank()) {
            throw new IllegalArgumentException("expectedFingerprint must not be blank");
        }
    }
}
