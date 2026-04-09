package com.eap.common.constants;

import lombok.Getter;

@Getter
public enum TradingMode {
    CDA("連續雙向拍賣"),
    AUCTION("定時集合競價");

    private final String description;

    TradingMode(String description) {
        this.description = description;
    }
}
