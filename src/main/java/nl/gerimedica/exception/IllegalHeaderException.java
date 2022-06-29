package nl.gerimedica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalHeaderException extends IllegalArgumentException{
    public IllegalHeaderException(String s) {
        super(s);
    }
}