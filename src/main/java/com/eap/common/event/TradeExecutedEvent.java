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
    /**
     * Cross-service storage contract for a trade ID. MatchEngine currently generates
     * {@code <marketId>-<Redis match sequence>}; it is not a UUID. All participating schemas
     * reserve this capacity and consumers validate the boundary defensively. If market IDs
     * become externally configurable, their length must be constrained before matching begins.
     */
    public static final int MAX_TRADE_ID_LENGTH = 80;

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
