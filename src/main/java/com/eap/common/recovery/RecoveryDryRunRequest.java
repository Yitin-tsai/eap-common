package com.eap.common.recovery;

import java.util.Objects;

public record RecoveryDryRunRequest(
        RecoveryActionType action,
        String expectedFingerprint) {

    public RecoveryDryRunRequest {
        action = Objects.requireNonNull(action, "action");
        if (expectedFingerprint == null || expectedFingerprint.isBlank()) {
            throw new IllegalArgumentException("expectedFingerprint must not be blank");
        }
    }
}
