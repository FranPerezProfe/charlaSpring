package dev.davidjuanes.products.api.errors;

import dev.davidjuanes.products.api.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

//TODO Anotate this class to tell Spring this is an Exception Handler (Controller Advice)
@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

    /*TODO Add three annotations needed here. You need to tell Spring that this method should be used when
     * a ProductNotFoundException is fired, that the returned value is the body that you want to give back to the user
     * and that the HTTP code should be 404 NOT FOUND
     */
    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus
    ErrorDto productNotFoundHandler(ProductNotFoundException ex) {
        log.error("API Exception: {}", ex.getMessage(), ex);
        return new ErrorDto(NOT_FOUND.toString(), ex.getMessage());
    }
}
