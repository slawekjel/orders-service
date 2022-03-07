package com.example.ordersservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShipmentDetails {

    private Long id;
    private String companyName;
    private Double price;
    private String trackingUrl;
    private LocalDate deliveryDate;
}
