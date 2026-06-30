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
public class OrderTradeAppliedEvent {
    private String tradeId;
    private UUID orderId;
    private String side;
    private Integer legacyMatchId;
    private Integer dealPrice;
    private Integer quantity;
    private LocalDateTime appliedAt;
}
