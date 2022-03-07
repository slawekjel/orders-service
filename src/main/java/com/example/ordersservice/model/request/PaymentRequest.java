package com.example.ordersservice.model.request;

import lombok.Data;

@Data
public class PaymentRequest {

    private final String methodName;
    private final Double sum;
}
