package by.fpmi.rest.task.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class NonExistedContactException extends HttpClientErrorException {

    public NonExistedContactException(HttpStatus statusCode) {
        super(statusCode);
    }

    public NonExistedContactException(HttpStatus statusCode, String statusText) {
        super(statusCode, statusText);
    }
}
