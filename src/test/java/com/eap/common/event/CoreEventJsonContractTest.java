package com.eap.common.event;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class CoreEventJsonContractTest {

    private static final UUID ORDER_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID USER_ID = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID BUYER_ID = UUID.fromString("00000000-0000-0000-0000-000000000003");
    private static final UUID SELLER_ID = UUID.fromString("00000000-0000-0000-0000-000000000004");
    private static final UUID SELLER_ORDER_ID = UUID.fromString("00000000-0000-0000-0000-000000000005");
    private static final UUID CANCELLATION_ID = UUID.fromString("00000000-0000-0000-0000-000000000006");
    private static final LocalDateTime OCCURRED_AT = LocalDateTime.parse("2026-08-21T12:34:56");

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    @Test
    void orderSubmittedJsonRemainsCompatible() throws Exception {
        OrderSubmittedEvent event = OrderSubmittedEvent.builder()
                .orderId(ORDER_ID)
                .userId(USER_ID)
                .marketId("CDA-1")
                .marketSequence(101L)
                .price(120)
                .amount(4)
                .orderType("BUY")
                .createdAt(OCCURRED_AT)
                .build();

        assertContract(event, OrderSubmittedEvent.class, """
                {
                  "orderId": "00000000-0000-0000-0000-000000000001",
                  "userId": "00000000-0000-0000-0000-000000000002",
                  "marketId": "CDA-1",
                  "marketSequence": 101,
                  "price": 120,
                  "amount": 4,
                  "orderType": "BUY",
                  "createdAt": "2026-08-21T12:34:56"
                }
                """);
    }

    @Test
    void orderAssetReservationSucceededJsonContractIsStable() throws Exception {
        OrderAssetReservationSucceededEvent event = OrderAssetReservationSucceededEvent.builder()
                .orderId(ORDER_ID)
                .userId(USER_ID)
                .marketId("CDA-1")
                .marketSequence(101L)
                .price(120)
                .amount(4)
                .orderType("BUY")
                .createdAt(OCCURRED_AT)
                .build();

        assertContract(event, OrderAssetReservationSucceededEvent.class, """
                {
                  "orderId": "00000000-0000-0000-0000-000000000001",
                  "userId": "00000000-0000-0000-0000-000000000002",
                  "marketId": "CDA-1",
                  "marketSequence": 101,
                  "price": 120,
                  "amount": 4,
                  "orderType": "BUY",
                  "createdAt": "2026-08-21T12:34:56"
                }
                """);
    }

    @Test
    void tradeExecutedJsonRemainsCompatible() throws Exception {
        TradeExecutedEvent event = TradeExecutedEvent.builder()
                .tradeId("trade-101")
                .sequence(501L)
                .legacyMatchId(42)
                .marketId("CDA-1")
                .buyerId(BUYER_ID)
                .sellerId(SELLER_ID)
                .buyerOrderId(ORDER_ID)
                .sellerOrderId(SELLER_ORDER_ID)
                .buyerMarketSequence(101L)
                .sellerMarketSequence(102L)
                .originBuyerPrice(120)
                .originSellerPrice(110)
                .dealPrice(115)
                .quantity(4)
                .occurredAt(OCCURRED_AT)
                .build();

        assertContract(event, TradeExecutedEvent.class, """
                {
                  "tradeId": "trade-101",
                  "sequence": 501,
                  "legacyMatchId": 42,
                  "marketId": "CDA-1",
                  "buyerId": "00000000-0000-0000-0000-000000000003",
                  "sellerId": "00000000-0000-0000-0000-000000000004",
                  "buyerOrderId": "00000000-0000-0000-0000-000000000001",
                  "sellerOrderId": "00000000-0000-0000-0000-000000000005",
                  "buyerMarketSequence": 101,
                  "sellerMarketSequence": 102,
                  "originBuyerPrice": 120,
                  "originSellerPrice": 110,
                  "dealPrice": 115,
                  "quantity": 4,
                  "occurredAt": "2026-08-21T12:34:56"
                }
                """);
    }

    @Test
    void cancellationEventsRemainCompatible() throws Exception {
        OrderCancellationRequestedEvent requested = OrderCancellationRequestedEvent.builder()
                .cancellationId(CANCELLATION_ID)
                .orderId(ORDER_ID)
                .userId(USER_ID)
                .originalAmount(10)
                .requestedAt(OCCURRED_AT)
                .build();
        OrderCancellationResultEvent result = OrderCancellationResultEvent.builder()
                .cancellationId(CANCELLATION_ID)
                .orderId(ORDER_ID)
                .userId(USER_ID)
                .outcome(OrderCancellationResultEvent.CANCELLED)
                .reason("removed from order book")
                .orderType("BUY")
                .limitPrice(120)
                .cancelledAmount(4)
                .decidedAt(OCCURRED_AT)
                .build();

        assertContract(requested, OrderCancellationRequestedEvent.class, """
                {
                  "cancellationId": "00000000-0000-0000-0000-000000000006",
                  "orderId": "00000000-0000-0000-0000-000000000001",
                  "userId": "00000000-0000-0000-0000-000000000002",
                  "originalAmount": 10,
                  "requestedAt": "2026-08-21T12:34:56"
                }
                """);
        assertContract(result, OrderCancellationResultEvent.class, """
                {
                  "cancellationId": "00000000-0000-0000-0000-000000000006",
                  "orderId": "00000000-0000-0000-0000-000000000001",
                  "userId": "00000000-0000-0000-0000-000000000002",
                  "outcome": "CANCELLED",
                  "reason": "removed from order book",
                  "orderType": "BUY",
                  "limitPrice": 120,
                  "cancelledAmount": 4,
                  "decidedAt": "2026-08-21T12:34:56"
                }
                """);
    }

    private <T> void assertContract(T event, Class<T> eventType, String expectedJson) throws Exception {
        String actualJson = objectMapper.writeValueAsString(event);
        assertEquals(objectMapper.readTree(expectedJson), objectMapper.readTree(actualJson));
        assertEquals(event, objectMapper.readValue(actualJson, eventType));
    }
}
