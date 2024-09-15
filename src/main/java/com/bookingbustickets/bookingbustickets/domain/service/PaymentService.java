package com.bookingbustickets.bookingbustickets.domain.service;

import com.bookingbustickets.bookingbustickets.controller.request.PaymentRequest;
import com.bookingbustickets.bookingbustickets.controller.response.PaymentResponse;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

  private static final String CLIENT_BASE_URL = "http://localhost:3000";
  private static final String CURRENCY = "EUR";

  @Value("${stripe.api-key}")
  private String stripeApiKey;

  public PaymentResponse initCheckoutSession(PaymentRequest paymentRequest) throws StripeException {
    Stripe.apiKey = stripeApiKey;
    SessionCreateParams params =
        SessionCreateParams.builder()
            .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            .setSuccessUrl(CLIENT_BASE_URL + "/payment/success/" + paymentRequest.reservationId())
            .setCancelUrl(CLIENT_BASE_URL + "/payment/fail")
            .addAllLineItem(paymentRequest.orderData().stream().map(this::mapToLineItem).toList())
            .build();
    Session session = Session.create(params);
    return new PaymentResponse(session.getUrl());
  }

  private SessionCreateParams.LineItem mapToLineItem(PaymentRequest.OrderData orderData) {
    return SessionCreateParams.LineItem.builder()
        .setQuantity((long) orderData.quantity())
        .setPriceData(
            SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(CURRENCY)
                .setProductData(
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(orderData.productName())
                        .build())
                .setUnitAmount((long) Math.round(orderData.price() * 100))
                .build())
        .build();
  }
}
