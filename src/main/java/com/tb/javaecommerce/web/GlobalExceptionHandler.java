package com.tb.javaecommerce.web;

import com.tb.javaecommerce.featuretoggle.exception.FeatureNotEnabledException;
import com.tb.javaecommerce.service.exception.CategoryNotFoundException;
import com.tb.javaecommerce.service.exception.OrderNotFoundException;
import com.tb.javaecommerce.service.exception.ProductNotFoundException;
import com.tb.javaecommerce.service.exception.ProductStatusIsInCorrectException;
import com.tb.javaecommerce.web.exception.FieldsAndReason;
import jakarta.persistence.PersistenceException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

import static com.tb.javaecommerce.utils.PaymentDetailsUtils.getValidationErrors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleProductNotFoundException(ProductNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("product-not-found"));
        problemDetail.setTitle("Product Not Found");
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFoundException.class)
    public ProblemDetail handleCategoryNotFoundException(CategoryNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("category-not-found"));
        problemDetail.setTitle("Category Not Found");
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public ProblemDetail handleOrderNotFoundException(OrderNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("order-not-found"));
        problemDetail.setTitle("Order Not Found");
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProductStatusIsInCorrectException.class)
    public ProblemDetail handleProductStatusException(ProductStatusIsInCorrectException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setType(URI.create("product-status-incorrect"));
        problemDetail.setTitle("Product Status Incorrect");
        return problemDetail;
    }

    @ExceptionHandler(FeatureNotEnabledException.class)
    public ProblemDetail handleFeatureToggleNotEnabledException(FeatureNotEnabledException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("feature-disabled"));
        problemDetail.setTitle("Feature Is Disabled");
        return problemDetail;
    }

    @ExceptionHandler(PersistenceException.class)
    public ProblemDetail handlePersistenceException(PersistenceException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setType(URI.create("persistence-error"));
        problemDetail.setTitle("Persistence Error");
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<FieldsAndReason> validationResponse = fieldErrors.stream().map(error -> FieldsAndReason.builder()
                        .fieldName(error.getField())
                        .reason(error.getDefaultMessage())
                        .build())
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getValidationErrors(validationResponse));
    }
}
