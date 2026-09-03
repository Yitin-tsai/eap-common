package com.eap.common.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Wallet integration fact emitted after the remaining reservation for a
 * cancelled order and the corresponding outbox row commit atomically.
 *
 * <p>The contract intentionally exposes only the Saga milestone. Wallet
 * balances remain private to the Wallet bounded context.</p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAssetReservationReleasedEvent {
    private UUID eventId;
    private UUID cancellationId;
    private UUID orderId;
    private UUID userId;
    private String orderType;
    private Integer releasedQuantity;
    private LocalDateTime releasedAt;
}
