Feature: View Website Home Page  
  As a user  
  I want to view the home page  
  So that I can explore different categories of posters and view some of the most popular designs  

  Background:
    Given the following users exist in the system
      | email        | password  | 
      | jeff@ap.com  | password1 | 
      | smith@ap.com | password2 | 
    Given the user is logged in as "jeff@ap.com"
    
  Scenario: User successfully sees categories and posters (Normal Flow)  
    Given the user is on the home page
    And the following posters exist in the system
      | title          | description                     | price | imageData   |  
      | CoolPoster     | It's a cool poster!             | 1.00  | 1, 2, 3     |  
      | VeryCoolPoster | It's a very cool poster!        | 1.55  | 1, 2, 4     |  
      | Amazing Art    | A stunning piece of digital art | 5.00  | artwork.jpg |     
    Then they should see the following posters displayed in the featured section  
      | title          | description                     | price | imageData   |  
      | CoolPoster     | It's a cool poster!             | 1.00  | 1, 2, 3     |  
      | VeryCoolPoster | It's a very cool poster!        | 1.55  | 1, 2, 4     |  
      | Amazing Art    | A stunning piece of digital art | 5.00  | artwork.jpg |  

  Scenario: No Posters Exist (Error Flow)  
    Given there are no posters available in the system  
    When the user is on the home page  
    Then they should see a message "No posters available at the moment"  