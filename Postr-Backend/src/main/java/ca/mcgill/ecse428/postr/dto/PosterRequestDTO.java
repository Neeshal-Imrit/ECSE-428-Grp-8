package ca.mcgill.ecse428.postr.dto;

public class PosterRequestDTO {
    private String title;
    private String description;
    private float price;
    private String imageData;
    private String userEmail;

    public PosterRequestDTO(String title, String description, float price, String imageData, String userEmail) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageData = imageData;
        this.userEmail = userEmail;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public float getPrice() {
        return this.price;
    }

    public String getUrl() {
        return this.imageData;
    }

    public String getUserEmail() {
        return this.userEmail;
    }


}
