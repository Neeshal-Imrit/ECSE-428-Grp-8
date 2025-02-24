package ca.mcgill.ecse428.postr.dto;

import java.util.List;

public class ErrorDTO {

    private List<String> errors;

    private String error;

    @SuppressWarnings("unused")
    private ErrorDTO(){}

    public ErrorDTO(List<String> errors) {
        this.errors = errors;
    }
    public ErrorDTO(String error) {
        this.errors = List.of(error);
        this.error = error;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getError() {
        return error;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void setError(String error) {
        this.error = error;
    }
}