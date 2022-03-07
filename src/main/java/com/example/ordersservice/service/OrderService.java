package com.example.ordersservice.service;

import com.example.ordersservice.model.Order;
import com.example.ordersservice.model.request.OrderRequest;
import com.example.ordersservice.model.response.OrderDetails;
import com.example.ordersservice.model.response.PaymentDetails;
import com.example.ordersservice.model.response.ShipmentDetails;
import com.example.ordersservice.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PaymentService paymentService;
    private final ShipmentService shipmentService;
    private final SaleOfferService saleOfferService;

    public OrderDetails createOrder(OrderRequest orderRequest) {
        log.info("Preparing and creating order");
        var orderBuilder = Order.builder()
                .saleOfferId(orderRequest.getSaleOfferId())
                .quantity(orderRequest.getQuantity())
                .pricePerItem(orderRequest.getPricePerItem())
                .shipmentMethod(orderRequest.getShipmentMethod())
                .paymentMethod(orderRequest.getPaymentMethod());
        var orderDetailsBuilder = OrderDetails.builder();

        ResponseEntity<ShipmentDetails> shipmentDetails = shipmentService.orderShipmentAndGetDetails(
                orderRequest.getShipmentMethod());

        if (shipmentDetails.getStatusCode()
                .is2xxSuccessful() && Objects.nonNull(shipmentDetails.getBody())) {
            var finalPrice = calculateFinalPrice(orderRequest.getPricePerItem(), orderRequest.getQuantity(),
                    shipmentDetails.getBody()
                            .getPrice());
            orderBuilder.shipmentId(shipmentDetails.getBody()
                            .getId())
                    .finalPrice(finalPrice);
            orderDetailsBuilder.shipmentDetails(shipmentDetails.getBody());

            ResponseEntity<PaymentDetails> paymentDetails = paymentService.orderPaymentAndGetDetails(
                    orderRequest.getPaymentMethod(),
                    finalPrice);
            if (paymentDetails.getStatusCode()
                    .is2xxSuccessful() && Objects.nonNull(paymentDetails.getBody())) {
                orderBuilder.paymentId(paymentDetails.getBody()
                        .getId());
                orderDetailsBuilder.paymentDetails(paymentDetails.getBody());
            }
        }

        var order = createOrder(orderBuilder.build());

        saleOfferService.updateItemsQuantityInOffer(orderRequest.getSaleOfferId(), orderRequest.getQuantity());

        return orderDetailsBuilder
                .orderId(order.getId())
                .saleOfferId(order.getSaleOfferId())
                .shipmentId(order.getShipmentId())
                .paymentId(order.getPaymentId())
                .quantity(order.getQuantity())
                .pricePerItem(order.getPricePerItem())
                .finalPrice(order.getFinalPrice())
                .build();
    }

    private Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    private Double calculateFinalPrice(Double itemPrice, Long quantity, Double shipmentPrice) {
        return itemPrice * quantity + shipmentPrice;
    }
}
