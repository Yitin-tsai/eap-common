package com.eap.common.constants;

import lombok.Getter;

@Getter
public enum AuctionStatus {
    OPEN("競標進行中"),
    CLOSED("競標已截止"),
    CLEARING("結算中"),
    CLEARED("結算完成"),
    FAILED("結算失敗");

    private final String description;

    AuctionStatus(String description) {
        this.description = description;
    }
}
