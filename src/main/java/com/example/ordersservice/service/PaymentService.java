package com.example.ordersservice.service;

import com.example.ordersservice.model.request.PaymentRequest;
import com.example.ordersservice.model.response.PaymentDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@AllArgsConstructor
public class PaymentService {

    private final RestTemplate restTemplate;

    public ResponseEntity<PaymentDetails> orderPaymentAndGetDetails(String methodName, Double sum) {
        log.info("Preparing and sending REST HTTP request to order a payment");
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8085)
                .path("/api/v1/payments/")
                .build()
                .encode();
        PaymentRequest paymentRequest = createPaymentRequest(methodName, sum);

        return restTemplate.postForEntity(
                uriComponents.toUriString(), paymentRequest, PaymentDetails.class);
    }

    private PaymentRequest createPaymentRequest(String methodName, Double sum) {
        return new PaymentRequest(methodName, sum);
    }
}
