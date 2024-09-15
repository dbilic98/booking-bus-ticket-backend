package com.bookingbustickets.bookingbustickets.controller;

import com.bookingbustickets.bookingbustickets.controller.request.PaymentRequest;
import com.bookingbustickets.bookingbustickets.controller.response.PaymentResponse;
import com.bookingbustickets.bookingbustickets.domain.service.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @PostMapping("/create-checkout-session")
  public PaymentResponse getSession(@RequestBody PaymentRequest paymentRequest)
      throws StripeException {
    return paymentService.initCheckoutSession(paymentRequest);
  }
}
