package com.example.ordersservice.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseEntity {

    private Long saleOfferId;
    private Long shipmentId;
    private Long paymentId;
    private Long quantity;
    private Double pricePerItem;
    private Double finalPrice;
    private String shipmentMethod;
    private String paymentMethod;
}
