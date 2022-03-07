package com.example.ordersservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@AllArgsConstructor
public class SaleOfferService {

    private RestTemplate restTemplate;

    public void updateItemsQuantityInOffer(Long saleOfferId, Long quantity) {
        log.info("Updating quantity of item in sale offer");
        var uriComponents = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(8081)
                .path("/api/v1/offers/sales/{saleOfferId}")
                .queryParam("quantityToReduce", quantity)
                .buildAndExpand(saleOfferId)
                .encode();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var httpEntity = new HttpEntity(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(uriComponents.toUriString(), HttpMethod.PATCH,
                httpEntity, String.class);

        if (responseEntity.getStatusCode()
                .is2xxSuccessful()) {
            log.info("Reducing quantity of items in sale offer succeed");
        } else {
            log.error("Reducing quantity of items in sale offer failed. Status code is {}",
                    responseEntity.getStatusCode());
        }
    }
}
