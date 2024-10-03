package com.bookingbustickets.bookingbustickets.exception;

import com.bookingbustickets.bookingbustickets.exception.response.ErrorResponse;
import lombok.NonNull;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
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
            TicketNotFoundException.class,
            InvalidReservationException.class,
            ReservationStatusException.class,
            ExpiredReservationException.class,
            BusNotFoundException.class,
            CompanyNotFoundException.class,
            ScheduleDateException.class})

    public ResponseEntity<Object> handleNotFoundException(Exception e, WebRequest request) {
        List<String> reasons = new ArrayList<>();
        reasons.add(e.getMessage());
        ErrorResponse body = new ErrorResponse(reasons);
        return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(RuntimeException ex) {
        List<String> reasons = new ArrayList<>();
        reasons.add("You cannot delete this record because it is linked to other records in the system.");
        ErrorResponse errorResponse = new ErrorResponse(reasons);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getDefaultMessage());
        }
        ErrorResponse errorResponse = new ErrorResponse(errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}