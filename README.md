# EAP Common Module

Shared contracts for the EAP workspace.

This module carries the DTOs, events, enums, and RabbitMQ constants that need to stay consistent across `eap-order`, `eap-wallet`, `eap-matchEngine`, `eap-mcp`, and `eap-ai-client`.

## What lives here

- `constants/` - shared exchange, queue, and routing-key definitions
- `dto/` - request/response models shared across services
- `event/` - event payloads published through RabbitMQ

## Why it exists

- Keep cross-service contracts in one place
- Reduce copy/paste drift between services
- Make event schema changes visible in one module before they break listeners

## Usage

```gradle
dependencies {
    implementation project(':eap-common')
}
```

## Change rule

If you change a shared DTO or event:

1. Update the producer and consumer services in the same branch.
2. Check the corresponding contract tests.
3. Keep backward compatibility unless you are intentionally making a breaking change.

## Test

```bash
./gradlew :eap-common:test
```
