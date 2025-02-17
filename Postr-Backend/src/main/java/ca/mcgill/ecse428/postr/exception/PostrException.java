package ca.mcgill.ecse428.postr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

public class PostrException extends RuntimeException {

    @NonNull
    private HttpStatus status;

    public PostrException(@NonNull HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    @NonNull
    public HttpStatus getStatus() {
        return status;
    }
}