package com.eap.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCancellationResultEvent {
    public static final String CANCELLED = "CANCELLED";
    public static final String ALREADY_MATCHED = "ALREADY_MATCHED";
    public static final String NOT_OPEN = "NOT_OPEN";

    private UUID cancellationId;
    private UUID orderId;
    private UUID userId;
    private String outcome;
    private String reason;
    private String orderType;
    private Integer limitPrice;
    private Integer cancelledAmount;
    private LocalDateTime decidedAt;

    public boolean cancelled() {
        return CANCELLED.equals(outcome);
    }
}
