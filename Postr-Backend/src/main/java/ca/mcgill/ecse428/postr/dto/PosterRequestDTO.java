package ca.mcgill.ecse428.postr.dto;

public class PosterRequestDTO {
    private String title;
    private String description;
    private float price;
    private String imageData;
    private String userEmail;
    private int numPurchases;

    public PosterRequestDTO(String title, String description, float price, String imageData, String userEmail, int numPurchases) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.imageData = imageData;
        this.userEmail = userEmail;
        this.numPurchases =numPurchases;
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
