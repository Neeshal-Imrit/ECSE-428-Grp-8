package ca.mcgill.ecse428.postr.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "poster")
public class Poster {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Poster Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String url;

  // Poster Associations
  @ManyToOne
  private User user;

  private String title;

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public float getPrice() {
    return price;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public void setImageData(byte[] imageData) {
    this.imageData = imageData;
  }

  public byte[] getImageData() {
    return imageData;
  }

  private String description;

  private float price;

  private int numPurchases;


  @Column(columnDefinition = "BYTEA")
  private byte[] imageData; 

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Poster() {
  }

  public Poster(Long aId, String aUrl, User aUser) {
    id = aId;
    url = aUrl;
    boolean didAddUser = setUser(aUser);
    if (!didAddUser) {
      throw new RuntimeException(
          "Unable to create poster due to user. See https://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    numPurchases = 0;
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setId(Long aId) {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setUrl(String aUrl) {
    boolean wasSet = false;
    url = aUrl;
    wasSet = true;
    return wasSet;
  }

  public Long getId() {
    return id;
  }

  public String getUrl() {
    return url;
  }

  /* Code from template association_GetOne */
  public User getUser() {
    return user;
  }

  /* Code from template association_SetOneToMany */
  public boolean setUser(User aUser) {
    boolean wasSet = false;
    if (aUser == null) {
      return wasSet;
    }

    User existingUser = user;
    user = aUser;
    if (existingUser != null && !existingUser.equals(aUser)) {
      existingUser.removePoster(this);
    }
    user.addPoster(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    User placeholderUser = user;
    this.user = null;
    if (placeholderUser != null) {
      placeholderUser.removePoster(this);
    }
  }

  public int getNumPurchases(){
    return numPurchases;
  }

  public boolean addPurchase(){
    boolean wasAdded = false;
    numPurchases += 1;
    wasAdded = true;
    return wasAdded;
  }

  public String toString() {
    return super.toString() + "[" +
        "url" + ":" + getUrl() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "id" + "="
        + (getId() != null ? !getId().equals(this) ? getId().toString().replaceAll("  ", "    ") : "this" : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "user = " + (getUser() != null ? Integer.toHexString(System.identityHashCode(getUser())) : "null");
  }

}