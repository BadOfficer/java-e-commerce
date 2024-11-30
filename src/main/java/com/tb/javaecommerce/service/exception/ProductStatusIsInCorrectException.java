package com.tb.javaecommerce.service.exception;

import jakarta.persistence.PersistenceException;

public class ProductStatusIsInCorrectException extends PersistenceException {
    private static final String PRODUCT_STATUS_IS_INCORRECT_MESSAGE = "Product status cannot be %s";

    public ProductStatusIsInCorrectException(String status) {
        super(String.format(PRODUCT_STATUS_IS_INCORRECT_MESSAGE, status));
    }
}
