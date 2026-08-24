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
public class OrderCancellationRequestedEvent {
    private UUID cancellationId;
    private UUID orderId;
    private UUID userId;
    private Integer originalAmount;
    private LocalDateTime requestedAt;
}
