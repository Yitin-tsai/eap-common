package com.eap.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionBidSubmittedEvent {
    private String auctionId;
    private UUID userId;
    private String side; // "BUY" or "SELL"
    private List<BidStep> steps;
    private Integer totalLocked; // BUY: sum(price * amount), SELL: sum(amount)
    private LocalDateTime createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BidStep {
        private Integer price;
        private Integer amount;
    }
}
