package com.eap.common.recovery;

public enum RecoveryDebtType {
    BROKER_DEAD_LETTER,
    INBOX_TERMINAL,
    OUTBOX_TERMINAL,
    CLEANUP_TERMINAL,
    SAGA_TIMEOUT
}
