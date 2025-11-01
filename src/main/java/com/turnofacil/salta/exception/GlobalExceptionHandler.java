package com.turnofacil.salta.exception;

import com.turnofacil.salta.dto.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

//anotacion para manejar excepciones globales
@ControllerAdvice
public class GlobalExceptionHandler {
    //inyeccion de las excepciones personalizadas
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageResponse> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
        //almacena la excepcion en forma de mensaje
        MessageResponse message = new MessageResponse(ex.getMessage());
        //retorna 404 NOT FOUND de http
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    //mnejador para excepciones de logica de negocio (ej. "Nombre ya existe")
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<MessageResponse> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request){
        //almacena la excepcion en forma de mensaje
        MessageResponse message = new MessageResponse(ex.getMessage());
        //retorna 400 BAD REQUEST de http
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    //este es el manejador para la anotacion @Valid... Se activa cuando una validacion del DTO falla.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request){
        // Creamos un mapa clave -> valor; para guardar los errores: {campo} -> {mensaje}
        //Instancia el objeto mapa
        Map<String, String> errors = new HashMap<>();
        //Por cadad error va capturando el nombre y el mensaje recibido
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        // Devolvemos un JSON con todos los campos que fallaron y el codigo 400
        //retorna 400 BAD REQUEST de http
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Un manejador generico para cualquier otro error no capturado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> handleGlobalException(Exception ex, WebRequest request){
        // Es importante loguear el error real en la consola del servidor
        ex.printStackTrace();
        //almacena el mensaje del error
        MessageResponse message = new MessageResponse("Ocurrió un error interno: " + ex.getMessage());
        //retorna 500 INTERNAL SERVER ERROR de http
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Manejador para credenciales incorrectas (login fallido).
     * Atrapa el error antes que el manejador genérico de 'Exception.class'.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        // Devolvemos un mensaje amigable y el estado 401
        MessageResponse message = new MessageResponse("Credenciales inválidas. Por favor, verifique su email y contraseña.");
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
}
