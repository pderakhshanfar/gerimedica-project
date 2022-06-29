package nl.gerimedica.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class ExceptionController {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionController.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleConstraintViolationExceptionException(ConstraintViolationException ex) {
        LOG.debug("ConstraintViolationException: ", ex);
        return new ResponseEntity<>(ex.getLocalizedMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
