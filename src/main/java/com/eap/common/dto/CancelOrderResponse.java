package com.eap.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelOrderResponse {
    
    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("cancellationId")
    private String cancellationId;
    
    @JsonProperty("status")
    private String status;
    
    @JsonProperty("cancelledAt")
    private LocalDateTime cancelledAt;
    
    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("message")
    private String message;
    
    public static CancelOrderResponse accepted(String orderId, String cancellationId) {
        CancelOrderResponse response = new CancelOrderResponse();
        response.setOrderId(orderId);
        response.setCancellationId(cancellationId);
        response.setStatus("CANCELLATION_PENDING");
        response.setSuccess(true);
        response.setMessage("取消請求已受理，等待撮合服務確認");
        return response;
    }
    
    public static CancelOrderResponse failure(String orderId, String message) {
        CancelOrderResponse response = new CancelOrderResponse();
        response.setOrderId(orderId);
        response.setSuccess(false);
        response.setMessage(message);
        return response;
    }
}
