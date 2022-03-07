package com.example.ordersservice.model.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentDetails {

    private Long id;
    private String method;
    private String provider;
    private Double sum;
    private String url;
}
