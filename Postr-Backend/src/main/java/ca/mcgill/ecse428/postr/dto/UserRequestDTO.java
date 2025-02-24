package ca.mcgill.ecse428.postr.dto;

public class UserRequestDTO {
    private String email;
    private String password;

    public UserRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
