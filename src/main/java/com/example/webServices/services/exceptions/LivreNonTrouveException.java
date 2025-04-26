package com.example.webServices.services.exceptions;

public class LivreNonTrouveException extends RuntimeException{
    public LivreNonTrouveException() {
        super();
    }

    public LivreNonTrouveException(String message) {
        super(message);
    }
}
