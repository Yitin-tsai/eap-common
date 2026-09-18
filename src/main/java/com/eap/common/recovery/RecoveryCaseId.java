package com.eap.common.recovery;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class RecoveryCaseId {

    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();
    private static final Base64.Decoder DECODER = Base64.getUrlDecoder();

    private RecoveryCaseId() {
    }

    public static String encode(
            String service,
            RecoveryDebtType debtType,
            String work,
            String sourceId) {
        return "v1."
                + encodePart(service) + "."
                + debtType.name() + "."
                + encodePart(work) + "."
                + encodePart(sourceId);
    }

    public static Parts decode(String caseId) {
        if (caseId == null || caseId.isBlank()) {
            throw new IllegalArgumentException("caseId must not be blank");
        }
        String[] parts = caseId.split("\\.", -1);
        if (parts.length != 5 || !"v1".equals(parts[0])) {
            throw new IllegalArgumentException("Unsupported recovery case ID");
        }
        try {
            return new Parts(
                    decodePart(parts[1]),
                    RecoveryDebtType.valueOf(parts[2]),
                    decodePart(parts[3]),
                    decodePart(parts[4]));
        } catch (IllegalArgumentException failure) {
            throw new IllegalArgumentException("Malformed recovery case ID", failure);
        }
    }

    private static String encodePart(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Recovery case ID parts must not be blank");
        }
        return ENCODER.encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private static String decodePart(String value) {
        String decoded = new String(DECODER.decode(value), StandardCharsets.UTF_8);
        if (decoded.isBlank()) {
            throw new IllegalArgumentException("Recovery case ID parts must not be blank");
        }
        return decoded;
    }

    public record Parts(
            String service,
            RecoveryDebtType debtType,
            String work,
            String sourceId) {
    }
}
