package com.eap.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionStatusDto {

    @JsonProperty("auctionId")
    private String auctionId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("deliveryHour")
    private LocalDateTime deliveryHour;

    @JsonProperty("openTime")
    private LocalDateTime openTime;

    @JsonProperty("closeTime")
    private LocalDateTime closeTime;

    @JsonProperty("participantCount")
    private Integer participantCount;

    @JsonProperty("clearingPrice")
    private Integer clearingPrice;

    @JsonProperty("clearingVolume")
    private Integer clearingVolume;

    @JsonProperty("priceFloor")
    private Integer priceFloor;

    @JsonProperty("priceCeiling")
    private Integer priceCeiling;
}
