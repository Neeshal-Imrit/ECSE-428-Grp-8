package ca.mcgill.ecse428.postr.dto;

import ca.mcgill.ecse428.postr.model.Poster;

public class PosterDTO {

    private Long id;
    private String url;
    private Long userId;


    public PosterDTO() {
    }
    
    public PosterDTO(Long id, String url, Long userId) {
        this.id = id;
        this.url = url;
        this.userId = userId;
    }

    // Optional: Constructor to easily convert a Poster entity to a DTO.
    // Note: This requires that your User entity has a getId() method.
    public PosterDTO(Poster poster) {
        if (poster != null) {
            this.id = poster.getId();
            this.url = poster.getUrl();
            // Check that the associated user is not null before accessing its id
            this.userId = poster.getUser() != null ? poster.getUser().getId() : null;
        }
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
