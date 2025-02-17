package ca.mcgill.ecse428.postr.dto;

public class UserDTO {

    private Long id;
    private String email;
    private String password;

    // Constructor for creating a new UserDTO
    public UserDTO(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    // Constructor for when receiving data from a user (typically for POST requests)
    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
