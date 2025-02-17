package ca.mcgill.ecse428.postr.exception;

import ca.mcgill.ecse428.postr.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
public class PostrExceptionHandler {

    @ExceptionHandler(PostrException.class)
    public ResponseEntity<ErrorDTO> handleGameOnException(PostrException e) {
        return new ResponseEntity<>(new ErrorDTO(e.getMessage()), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ArrayList<String> errorMessages = new ArrayList<String>();
        for (ObjectError err : ex.getAllErrors()) {
            errorMessages.add(err.getDefaultMessage());
        }
        ErrorDTO responseBody = new ErrorDTO(errorMessages);
        return new ResponseEntity<ErrorDTO>(responseBody, HttpStatus.BAD_REQUEST);
    }
}