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

//TODO Anotate this class to tell Spring this is an Exception Handler (Controller Advice)
@Slf4j
public class ErrorControllerAdvice {

    /*TODO Add three annotations needed here. You need to tell Spring that this method should be used when
     * a CartNotFoundException is fired, that the returned value is the body that you want to give back to the user
     * and that the HTTP code should be 404 NOT FOUND
     */
    ErrorDto cartNotFoundHandler(CartNotFoundException ex) {
        log.error("API Exception: {}", ex.getMessage(), ex);
        return new ErrorDto(NOT_FOUND.toString(), ex.getMessage());
    }

    /*TODO Add three annotations needed here. You need to tell Spring that this method should be used when
     * a ProductsNotFoundException is fired, that the returned value is the body that you want to give back to the user
     * and that the HTTP code should be 400 BAD REQUEST
     */
    ErrorDto productsNotFoundHandler(ProductsNotFoundException ex) {
        log.error("API Exception: {}", ex.getMessage(), ex);
        return new ErrorDto(BAD_REQUEST.toString(), ex.getMessage());
    }
}
