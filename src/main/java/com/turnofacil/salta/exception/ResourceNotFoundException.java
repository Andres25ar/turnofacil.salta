package com.turnofacil.salta.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
* cuando se lanza esta exception debe responder con el codigo
* HTTP 404 NOT FOUND
*/
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue){
        super(String.format("No se encontró " + resourceName + " con " + fieldName + " = " + fieldValue));
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
