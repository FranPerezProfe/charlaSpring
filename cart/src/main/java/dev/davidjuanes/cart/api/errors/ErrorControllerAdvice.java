package dev.davidjuanes.cart.api.errors;

import dev.davidjuanes.cart.service.cart.CartNotFoundException;
import dev.davidjuanes.cart.api.dto.ErrorDto;
import dev.davidjuanes.cart.service.product.ProductsNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
@Slf4j
public class ErrorControllerAdvice {

    @ResponseBody
    @ExceptionHandler(CartNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    ErrorDto cartNotFoundHandler(CartNotFoundException ex) {
        log.error("API Exception: {}", ex.getMessage(), ex);
        return new ErrorDto(NOT_FOUND.toString(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ProductsNotFoundException.class)
    @ResponseStatus(BAD_REQUEST)
    ErrorDto productsNotFoundHandler(ProductsNotFoundException ex) {
        log.error("API Exception: {}", ex.getMessage(), ex);
        return new ErrorDto(BAD_REQUEST.toString(), ex.getMessage());
    }
}
