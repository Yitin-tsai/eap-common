# EAP Common Contracts

`eap-common` is a shared Java library, not a service and not a source of business state. It contains the DTOs, event payloads, enums, and RabbitMQ names compiled by the EAP repositories.

## Current Core Events

| Event | Producer | Consumers | Meaning |
| --- | --- | --- | --- |
| `OrderSubmittedEvent` | Order | Wallet | request asset reservation for an accepted order |
| `OrderAssetReservationSucceededEvent` | Wallet outbox | Order, MatchEngine | reservation succeeded; preserve `marketId` and `marketSequence` |
| `OrderFailedEvent` | Wallet outbox | Order | reservation failed |
| `TradeExecutedEvent` | MatchEngine outbox | Order, Wallet | authoritative durable trade fact |
| `OrderCancellationRequestedEvent` | Order outbox | MatchEngine | durable request for MatchEngine to arbitrate cancellation against matching |
| `OrderCancellationResultEvent` | MatchEngine outbox | Order, Wallet | durable decision and exact cancelled remainder; rejected outcomes do not release assets |

`OrderTradeAppliedEvent` and `WalletTradeSettledEvent` are retired. Order and Wallet persist their local results without reporting a completion marker to MatchEngine.

Timed-auction contracts remain separate under `auction.exchange`:

| Event | Producer | Consumers | Current publication boundary |
| --- | --- | --- | --- |
| `AuctionCreatedEvent` | MatchEngine scheduler | Order | direct RabbitMQ publication |
| `AuctionBidSubmittedEvent` | Order | Wallet | direct RabbitMQ publication |
| `AuctionBidConfirmedEvent` | Wallet outbox | MatchEngine | Wallet state + outbox transaction |
| `AuctionClearedEvent` | MatchEngine scheduler | Order, Wallet | direct RabbitMQ publication |

These contracts prove that TDA is implemented; they do not give it the CDA path's end-to-end outbox or capacity guarantees.

## Change Rules

1. Treat event fields and routing keys as cross-repository contracts.
2. Prefer additive, backward-compatible changes.
3. Update producers, consumers, tests, and public architecture documentation together.
4. Use a new event version for a breaking change; do not rely on every service being deployed atomically.
5. Do not place service-owned business logic or persistence entities in this module.

## Build and Test

`CoreEventJsonContractTest` pins the JSON field names and round-trip behavior of the current CDA order, trade, and cancellation events. Producer outbox tests, consumer behavior tests, and full lifecycle tests remain responsible for routing and business correctness.

```bash
./gradlew test
```

See [EAP system architecture](https://github.com/Yitin-tsai/eap-infra/blob/main/docs/architecture.md) for ownership and event flow.
