package com.eap.common.recovery;

public record BrokerReplayPreflightRequest(
        String sourceQueue,
        String originalExchange,
        String originalRoutingKey,
        String payload) {

    public BrokerReplayPreflightRequest {
        sourceQueue = requireText(sourceQueue, "sourceQueue");
        originalExchange = requireText(originalExchange, "originalExchange");
        originalRoutingKey = requireText(originalRoutingKey, "originalRoutingKey");
        if (payload == null) {
            throw new IllegalArgumentException("payload must not be null");
        }
    }

    private static String requireText(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value;
    }
}
