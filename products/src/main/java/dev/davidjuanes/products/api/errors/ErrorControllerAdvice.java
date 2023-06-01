package dev.davidjuanes.products.api.errors;

import dev.davidjuanes.products.api.dto.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ErrorDto productNotFoundHandler(ProductNotFoundException ex) {
        log.error("API Exception: {}", ex.getMessage(), ex);
        return new ErrorDto(NOT_FOUND.toString(), ex.getMessage());
    }
}
