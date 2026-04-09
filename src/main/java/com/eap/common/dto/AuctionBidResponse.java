package com.eap.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionBidResponse {

    @JsonProperty("auctionId")
    private String auctionId;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("side")
    private String side;

    @JsonProperty("totalLocked")
    private Integer totalLocked;

    @JsonProperty("success")
    private boolean success;

    @JsonProperty("message")
    private String message;

    @JsonProperty("submittedAt")
    private LocalDateTime submittedAt;

    public static AuctionBidResponse success(String auctionId, String userId, String side, Integer totalLocked) {
        AuctionBidResponse response = new AuctionBidResponse();
        response.setAuctionId(auctionId);
        response.setUserId(userId);
        response.setSide(side);
        response.setTotalLocked(totalLocked);
        response.setSuccess(true);
        response.setMessage("投標提交成功");
        response.setSubmittedAt(LocalDateTime.now());
        return response;
    }

    public static AuctionBidResponse failure(String message) {
        AuctionBidResponse response = new AuctionBidResponse();
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
}
