package com.sistema_estoque.estoque.exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message, Throwable e) {
        super(message, e);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

}
