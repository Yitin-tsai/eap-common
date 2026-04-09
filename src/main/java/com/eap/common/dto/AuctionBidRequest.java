package com.eap.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionBidRequest {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("auctionId")
    private String auctionId;

    @JsonProperty("side")
    private String side;

    @JsonProperty("steps")
    private List<BidStep> steps;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BidStep {
        @JsonProperty("price")
        private Integer price;

        @JsonProperty("amount")
        private Integer amount;
    }

    public boolean isBuy() {
        return "BUY".equalsIgnoreCase(side);
    }

    public boolean isSell() {
        return "SELL".equalsIgnoreCase(side);
    }

    public boolean isValid() {
        if (userId == null || userId.trim().isEmpty()) return false;
        if (auctionId == null || auctionId.trim().isEmpty()) return false;
        if (side == null || (!isBuy() && !isSell())) return false;
        if (steps == null || steps.isEmpty()) return false;
        for (BidStep step : steps) {
            if (step.getPrice() == null || step.getPrice() < 0) return false;
            if (step.getAmount() == null || step.getAmount() <= 0) return false;
        }
        return true;
    }

    public String getValidationError() {
        if (userId == null || userId.trim().isEmpty()) {
            return "用戶ID不能為空";
        }
        if (auctionId == null || auctionId.trim().isEmpty()) {
            return "競標ID不能為空";
        }
        if (side == null || (!isBuy() && !isSell())) {
            return "投標方向必須是 BUY 或 SELL";
        }
        if (steps == null || steps.isEmpty()) {
            return "投標階梯不能為空";
        }
        for (int i = 0; i < steps.size(); i++) {
            BidStep step = steps.get(i);
            if (step.getPrice() == null || step.getPrice() < 0) {
                return "階梯 " + (i + 1) + " 的價格不能為負數";
            }
            if (step.getAmount() == null || step.getAmount() <= 0) {
                return "階梯 " + (i + 1) + " 的數量必須大於0";
            }
        }
        return null;
    }
}
