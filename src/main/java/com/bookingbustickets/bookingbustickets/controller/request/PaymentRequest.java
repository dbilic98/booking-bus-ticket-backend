package com.bookingbustickets.bookingbustickets.controller.request;

import java.util.List;

public record PaymentRequest(Long reservationId, List<OrderData> orderData) {
  public record OrderData(String productName, int quantity, float price) {}
}
