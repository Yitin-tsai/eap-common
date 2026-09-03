package com.eap.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAssetReservationSucceededEvent {
    private UUID orderId;
    private UUID userId;
    private String marketId;
    private Long marketSequence;
    private Integer price;
    private Integer amount;
    private String orderType;

    private LocalDateTime createdAt;
}
