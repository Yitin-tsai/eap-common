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
public class ClearingResultDto {

    @JsonProperty("auctionId")
    private String auctionId;

    @JsonProperty("clearingPrice")
    private Integer clearingPrice;

    @JsonProperty("clearingVolume")
    private Integer clearingVolume;

    @JsonProperty("totalBuyVolume")
    private Integer totalBuyVolume;

    @JsonProperty("totalSellVolume")
    private Integer totalSellVolume;

    @JsonProperty("matchedPairs")
    private Integer matchedPairs;

    @JsonProperty("status")
    private String status;
}
