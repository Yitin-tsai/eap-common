package com.eap.common.recovery;

public enum BrokerReplayPreflightDecision {
    ELIGIBLE,
    ALREADY_DURABLE,
    ALREADY_APPLIED,
    INVALID_PAYLOAD,
    IDENTITY_CONFLICT,
    PERMANENT_FAILURE,
    UNSUPPORTED_ROUTE
}
