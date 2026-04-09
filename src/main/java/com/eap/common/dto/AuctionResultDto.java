package com.eap.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionResultDto {

    @JsonProperty("auctionId")
    private String auctionId;

    @JsonProperty("clearingPrice")
    private Integer clearingPrice;

    @JsonProperty("clearingVolume")
    private Integer clearingVolume;

    @JsonProperty("participantCount")
    private Integer participantCount;

    @JsonProperty("results")
    private List<UserResult> results;

    @JsonProperty("clearedAt")
    private LocalDateTime clearedAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserResult {
        @JsonProperty("userId")
        private String userId;

        @JsonProperty("side")
        private String side;

        @JsonProperty("bidAmount")
        private Integer bidAmount;

        @JsonProperty("clearedAmount")
        private Integer clearedAmount;

        @JsonProperty("settlementAmount")
        private Integer settlementAmount;

        @JsonProperty("status")
        private String status;
    }
}
