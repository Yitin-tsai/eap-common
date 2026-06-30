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
public class TradeExecutedEvent {
    private String tradeId;
    private Long sequence;
    private Integer legacyMatchId;
    private String marketId;
    private UUID buyerId;
    private UUID sellerId;
    private UUID buyerOrderId;
    private UUID sellerOrderId;
    private Long buyerMarketSequence;
    private Long sellerMarketSequence;
    private Integer originBuyerPrice;
    private Integer originSellerPrice;
    private Integer dealPrice;
    private Integer quantity;
    private LocalDateTime occurredAt;
}
