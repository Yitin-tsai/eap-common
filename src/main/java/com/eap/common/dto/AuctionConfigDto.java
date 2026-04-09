package com.eap.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionConfigDto {

    @JsonProperty("priceFloor")
    private Integer priceFloor;

    @JsonProperty("priceCeiling")
    private Integer priceCeiling;

    @JsonProperty("durationMinutes")
    private Integer durationMinutes;

    @JsonProperty("auctionEnabled")
    private boolean auctionEnabled;
}
