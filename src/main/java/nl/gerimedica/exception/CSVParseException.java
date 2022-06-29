package nl.gerimedica.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class CSVParseException extends RuntimeException{

    public CSVParseException(String s) {
        super(s);
    }
}