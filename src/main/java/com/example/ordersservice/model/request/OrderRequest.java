package com.example.ordersservice.model.request;

import lombok.Data;

@Data
public class OrderRequest {

    private Long saleOfferId;
    private Long quantity;
    private Double pricePerItem;
    private String shipmentMethod;
    private String paymentMethod;
}
