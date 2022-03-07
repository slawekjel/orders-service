package com.example.ordersservice.service;

import com.example.ordersservice.model.request.ShipmentRequest;
import com.example.ordersservice.model.response.ShipmentDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@AllArgsConstructor
public class ShipmentService {

    private final RestTemplate restTemplate;

    public ResponseEntity<ShipmentDetails> orderShipmentAndGetDetails(String methodName) {
        log.info("Preparing and sending REST HTTP request to order a shipment");
        var uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8084)
                .path("/api/v1/shipments/")
                .build()
                .encode();
        var shipmentRequest = new ShipmentRequest(methodName);

        return restTemplate.postForEntity(uriComponents.toUriString(), shipmentRequest, ShipmentDetails.class);
    }
}
