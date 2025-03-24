package ca.mcgill.ecse428.postr.dto;
import ca.mcgill.ecse428.postr.model.User;

public class UserResponseDTO {
    private String email;
    private String password;
    private Long id;

    private UserResponseDTO() {
    }

    public UserResponseDTO(String email, String password, Long id) {
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public UserResponseDTO(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.id = user.getId();
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public Long getId() {
        return this.id;
    }

}
