package com.eap.common.recovery;

import java.util.Objects;

public record BrokerReplayPreflightResult(
        BrokerReplayPreflightDecision decision,
        boolean safeToReplay,
        String reason,
        String messageId,
        String inboxStatus) {

    public BrokerReplayPreflightResult {
        decision = Objects.requireNonNull(decision, "decision");
        if (reason == null || reason.isBlank()) {
            throw new IllegalArgumentException("reason must not be blank");
        }
        if (safeToReplay && decision != BrokerReplayPreflightDecision.ELIGIBLE) {
            throw new IllegalArgumentException("Only ELIGIBLE may request a broker publish");
        }
    }

    public boolean alreadyOwnedByConsumer() {
        return decision == BrokerReplayPreflightDecision.ALREADY_DURABLE
                || decision == BrokerReplayPreflightDecision.ALREADY_APPLIED;
    }
}
