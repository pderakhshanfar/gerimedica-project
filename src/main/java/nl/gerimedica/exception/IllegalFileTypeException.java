package nl.gerimedica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalFileTypeException extends IllegalArgumentException{
    public IllegalFileTypeException(String s) {
        super(s);
    }
}
