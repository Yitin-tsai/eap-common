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
public class AuctionClearedEvent {
    private String auctionId;
    private Integer clearingPrice;
    private Integer clearingVolume;
    private String status; // "CLEARED" or "FAILED"
    private List<AuctionBidResult> results;
    private LocalDateTime clearedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuctionBidResult {
        private UUID userId;
        private String side;
        private Integer bidAmount;
        private Integer clearedAmount;
        private Integer settlementAmount;
        private Integer originalTotalLocked; // 用於 wallet 結算時計算退款金額
    }
}
