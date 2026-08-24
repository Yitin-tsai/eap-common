package com.eap.common.constants;

/**
 * RabbitMQ 相關常量配置
 * 集中管理所有 Queue、Exchange 和 Routing Key 的名稱
 * 
 * 拓撲設計：使用 topic exchange 實現 pub-sub 模式
 * 每個模組有自己的 queue，綁定到相同的 routing key 以接收廣播事件
 */
public class RabbitMQConstants {
    
    // Exchange 名稱
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String TRADE_EXCHANGE = "trade.exchange";

    // Dead Letter Exchange / Queue (ADR-001)
    public static final String DEAD_LETTER_EXCHANGE = "order.dlx";
    public static final String DEAD_LETTER_QUEUE = "order.dlq";
    
    // Routing Keys (canonical - 每個事件類型一個 routing key)
    public static final String ORDER_SUBMITTED_KEY = "order.submitted";
    public static final String ORDER_CONFIRMED_KEY = "order.confirmed";
    public static final String TRADE_EXECUTED_KEY = "trade.executed";
    public static final String ORDER_CANCELLATION_REQUESTED_KEY = "order.cancellation.requested";
    public static final String ORDER_CANCELLATION_RESULT_KEY = "order.cancellation.result";
    public static final String ORDER_FAILED_KEY = "order.failed";
    
    // Wallet Module Queue Names
    public static final String WALLET_ORDER_SUBMITTED_QUEUE = "wallet.orderSubmitted.queue";
    public static final String WALLET_TRADE_EXECUTED_QUEUE = "wallet.tradeExecuted.queue";
    public static final String WALLET_ORDER_CANCELLATION_RESULT_QUEUE = "wallet.orderCancellationResult.queue";
    
    // MatchEngine Module Queue Names
    public static final String MATCH_ENGINE_ORDER_CONFIRMED_QUEUE = "matchEngine.orderConfirmed.queue";
    public static final String MATCH_ENGINE_ORDER_CANCELLATION_REQUESTED_QUEUE = "matchEngine.orderCancellationRequested.queue";
    
    // Order Module Queue Names
    public static final String ORDER_ORDER_CONFIRMED_QUEUE = "order.orderConfirmed.queue";
    public static final String ORDER_TRADE_EXECUTED_QUEUE = "order.tradeExecuted.queue";
    public static final String ORDER_ORDER_FAILED_QUEUE = "order.orderFailed.queue";
    public static final String ORDER_ORDER_CANCELLATION_RESULT_QUEUE = "order.orderCancellationResult.queue";
    
    // Auction Exchange
    public static final String AUCTION_EXCHANGE = "auction.exchange";

    // Auction Routing Keys
    public static final String AUCTION_CREATED_KEY = "auction.created";
    public static final String AUCTION_BID_SUBMITTED_KEY = "auction.bid.submitted";
    public static final String AUCTION_BID_CONFIRMED_KEY = "auction.bid.confirmed";
    public static final String AUCTION_CLEARED_KEY = "auction.cleared";
    public static final String AUCTION_BID_RESULT_KEY = "auction.bid.result";

    // Wallet Module Auction Queue Names
    public static final String WALLET_AUCTION_BID_SUBMITTED_QUEUE = "wallet.auctionBidSubmitted.queue";
    public static final String WALLET_AUCTION_CLEARED_QUEUE = "wallet.auctionCleared.queue";

    // Order Module Auction Queue Names
    public static final String ORDER_AUCTION_CLEARED_QUEUE = "order.auctionCleared.queue";
    public static final String ORDER_AUCTION_CREATED_QUEUE = "order.auctionCreated.queue";

    // MatchEngine Module Auction Queue Names
    public static final String MATCH_ENGINE_AUCTION_BID_CONFIRMED_QUEUE = "matchEngine.auctionBidConfirmed.queue";

    private RabbitMQConstants() {
        // 私有構造函數防止實例化
    }
}
