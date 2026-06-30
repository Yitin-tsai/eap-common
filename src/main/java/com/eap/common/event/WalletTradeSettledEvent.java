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
public class WalletTradeSettledEvent {
    private String tradeId;
    private Integer legacyMatchId;
    private UUID buyerId;
    private UUID sellerId;
    private UUID buyerOrderId;
    private UUID sellerOrderId;
    private Integer dealPrice;
    private Integer quantity;
    private Integer buyerLockedCurrency;
    private Integer buyerRefundCurrency;
    private Integer sellerReceivedCurrency;
    private LocalDateTime settledAt;
}
