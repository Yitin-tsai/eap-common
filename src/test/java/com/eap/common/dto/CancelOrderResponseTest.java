package com.eap.common.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CancelOrderResponseTest {

    @Test
    void accepted_shouldExposeTheAsynchronousCancellationIdentity() {
        CancelOrderResponse response = CancelOrderResponse.accepted("order-1", "cancellation-1");

        assertEquals("order-1", response.getOrderId());
        assertEquals("cancellation-1", response.getCancellationId());
        assertEquals("CANCELLATION_PENDING", response.getStatus());
        assertTrue(response.isSuccess());
    }
}
