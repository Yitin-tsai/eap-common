package com.eap.common.recovery;

import java.util.Map;
import java.util.Objects;

public record RecoveryCaseDetail(
        RecoveryCaseSummary summary,
        String payload,
        Map<String, String> sourceMetadata) {

    public RecoveryCaseDetail {
        summary = Objects.requireNonNull(summary, "summary");
        sourceMetadata = Map.copyOf(sourceMetadata == null ? Map.of() : sourceMetadata);
    }
}
