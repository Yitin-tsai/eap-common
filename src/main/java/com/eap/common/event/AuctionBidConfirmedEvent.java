package com.eap.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Event published by wallet after successfully locking funds for an auction bid.
 * Consumed by matchEngine to collect the confirmed bid into Redis.
 *
 * Follows the same wallet-first + outbox pattern as OrderAssetReservationSucceededEvent:
 * eap-order publishes AuctionBidSubmittedEvent
 *   → eap-wallet locks funds + writes AuctionBidConfirmedEvent to outbox (atomic)
 *   → OutboxPoller publishes to AUCTION_EXCHANGE
 *   → eap-matchEngine collects confirmed bid into Redis
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionBidConfirmedEvent {
    private String auctionId;
    private UUID userId;
    private String side; // "BUY" or "SELL"
    private List<AuctionBidSubmittedEvent.BidStep> steps;
    private Integer totalLocked;
    private LocalDateTime createdAt;
}
