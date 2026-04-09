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
public class AuctionBidResultEvent {
    private String auctionId;
    private UUID userId;
    private String side;
    private Integer clearingPrice;
    private Integer bidAmount;
    private Integer clearedAmount;
    private Integer settlementAmount;
    private String status; // "CLEARED", "PARTIAL", "NOT_CLEARED"
    private LocalDateTime clearedAt;
}
