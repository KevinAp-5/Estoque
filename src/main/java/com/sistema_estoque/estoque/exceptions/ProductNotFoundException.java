package com.sistema_estoque.estoque.exceptions;

public class ProductNotFoundException extends Exception{

    public ProductNotFoundException(String message, Throwable e) {
        super(message, e);
    }

    public ProductNotFoundException(String message) {
        super(message);
    }

}
