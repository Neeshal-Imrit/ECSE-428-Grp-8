package ca.mcgill.ecse428.postr.dto;

import ca.mcgill.ecse428.postr.model.Poster;

public class PosterResponseDTO {

    private String title;
    private String description;
    private float price;
    private String imageData;
    private String userEmail;
    private int numPurchases;

    private PosterResponseDTO() {
    }

    public PosterResponseDTO(String title, String description, float price, String imageData, String userEmail, int numPurchases) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageData = imageData;
        this.userEmail = userEmail;
        this.numPurchases = numPurchases;
    }

    public PosterResponseDTO(Poster poster) {
        this.title = poster.getTitle();
        this.description = poster.getDescription();
        this.price = poster.getPrice();
        this.imageData = poster.getUrl();
        this.userEmail = poster.getUser().getEmail();
        this.numPurchases = poster.getNumPurchases();
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

    public int getNumPurchases() {
        return this.numPurchases;
    }

}
