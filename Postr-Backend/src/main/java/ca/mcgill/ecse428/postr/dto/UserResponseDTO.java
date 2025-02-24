package ca.mcgill.ecse428.postr.dto;
import ca.mcgill.ecse428.postr.model.User;

public class UserResponseDTO {
    private String email;
    private String password;

    private UserResponseDTO() {
    }

    public UserResponseDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserResponseDTO(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
    
}
