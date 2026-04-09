package com.eap.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionCreatedEvent {
    private String auctionId;
    private LocalDateTime deliveryHour;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private Integer priceFloor;
    private Integer priceCeiling;
    private LocalDateTime createdAt;
}
