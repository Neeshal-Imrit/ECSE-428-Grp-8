package ca.mcgill.ecse428.postr.model;

import java.util.*;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String password;
  private List<Poster>postersPurchased;

  //User Associations
  @OneToMany(mappedBy = "user")
  public List<Poster> posters;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User() {
  }

  public User(String aEmail, String aPassword)
  {
    email = aEmail;
    password = aPassword;
    posters = new ArrayList<Poster>();
    postersPurchased = new ArrayList<Poster>();

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public Long getId()
  {
    return id;
  }


  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getEmail()
  {
    return email;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetMany */
  public Poster getPoster(int index)
  {
    Poster aPoster = posters.get(index);
    return aPoster;
  }

  public List<Poster> getPosters()
  {
    List<Poster> newPosters = Collections.unmodifiableList(posters);
    return newPosters;
  }

  public int numberOfPosters()
  {
    int number = posters.size();
    return number;
  }

  public boolean hasPosters()
  {
    boolean has = posters.size() > 0;
    return has;
  }

  public int indexOfPoster(Poster aPoster)
  {
    int index = posters.indexOf(aPoster);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPosters()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Poster addPoster(Long aId, String aUrl)
  {
    return new Poster(aId, aUrl, this);
  }

  public boolean addPoster(Poster aPoster)
  {
    boolean wasAdded = false;
    if (posters.contains(aPoster)) { return false; }
    User existingUser = aPoster.getUser();
    boolean isNewUser = existingUser != null && !this.equals(existingUser);
    if (isNewUser)
    {
      aPoster.setUser(this);
    }
    else
    {
      posters.add(aPoster);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean addPosterPurchase(Poster aPoster){
    boolean wasAdded = false;
    if (postersPurchased.contains(aPoster)) { return false; }
    User existingUser = aPoster.getUser();
    boolean isNewUser = existingUser != null && !this.equals(existingUser);
    if (isNewUser)
    {
      aPoster.setUser(this);
    }
    else
    {
      postersPurchased.add(aPoster);
    }
    wasAdded = true;
    return wasAdded;
  }

  public List<Poster> getPostersPurchased(){
    List<Poster> newPosters = Collections.unmodifiableList(postersPurchased);
    return newPosters;
  }



  public boolean removePoster(Poster aPoster)
  {
    boolean wasRemoved = false;
    //Unable to remove aPoster, as it must always have a user
    if (!this.equals(aPoster.getUser()))
    {
      posters.remove(aPoster);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPosterAt(Poster aPoster, int index)
  {  
    boolean wasAdded = false;
    if(addPoster(aPoster))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPosters()) { index = numberOfPosters() - 1; }
      posters.remove(aPoster);
      posters.add(index, aPoster);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePosterAt(Poster aPoster, int index)
  {
    boolean wasAdded = false;
    if(posters.contains(aPoster))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPosters()) { index = numberOfPosters() - 1; }
      posters.remove(aPoster);
      posters.add(index, aPoster);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPosterAt(aPoster, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=posters.size(); i > 0; i--)
    {
      Poster aPoster = posters.get(i - 1);
      aPoster.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "email" + "=" + (getEmail() != null ? !getEmail().equals(this)  ? getEmail().toString().replaceAll("  ","    ") : "this" : "null");
  }
}