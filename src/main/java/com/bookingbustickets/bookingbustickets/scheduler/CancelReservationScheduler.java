package com.bookingbustickets.bookingbustickets.scheduler;

import com.bookingbustickets.bookingbustickets.domain.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CancelReservationScheduler {

    private final ReservationService reservationService;

    public CancelReservationScheduler(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Scheduled(fixedDelay = 30_000)
    public void cancelReservations() {
        reservationService.cancelPendingReservations();
    }
}
