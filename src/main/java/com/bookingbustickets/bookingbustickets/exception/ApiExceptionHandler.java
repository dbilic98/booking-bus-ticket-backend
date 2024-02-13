package com.bookingbustickets.bookingbustickets.exception;

import com.bookingbustickets.bookingbustickets.exception.response.ErrorResponse;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            PassengerCategoryNotFoundException.class,
            PlaceNotFoundException.class,
            ReservationNotFoundException.class,
            RouteNotFoundException.class,
            ScheduleNotFoundException.class,
            SeatNotFoundException.class,
            TicketNotFoundException.class})

    public ResponseEntity<Object> handleNotFoundException(Exception e, WebRequest request) {
        List<String> reasons = new ArrayList<>();
        reasons.add(e.getMessage());
        ErrorResponse body = new ErrorResponse(reasons);
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> errors.add(fieldError.getDefaultMessage()));
        var errorResponse = new ErrorResponse(errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
