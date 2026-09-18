package com.eap.common.recovery;

public enum RecoveryFailureClass {
    TRANSIENT,
    PERMANENT,
    SCHEMA,
    IDENTITY,
    INVARIANT,
    PREREQUISITE,
    UNKNOWN
}
