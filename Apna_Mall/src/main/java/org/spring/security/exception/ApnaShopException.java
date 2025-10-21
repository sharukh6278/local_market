package org.spring.security.exception;

public class ApnaShopException extends RuntimeException {
    private int statusCode;
    private String message;
    private String description;

    public ApnaShopException(int statusCode, String message, String description) {
        this.statusCode = statusCode;
        this.message = message;
        this.description = description;
    }

    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}