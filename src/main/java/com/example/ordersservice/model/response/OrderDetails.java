package com.example.ordersservice.model.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDetails {

    private Long orderId;
    private Long saleOfferId;
    private Long shipmentId;
    private Long paymentId;
    private Long quantity;
    private Double pricePerItem;
    private Double finalPrice;
    private PaymentDetails paymentDetails;
    private ShipmentDetails shipmentDetails;
}
