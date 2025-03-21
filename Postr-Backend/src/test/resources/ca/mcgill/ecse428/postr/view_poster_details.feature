Feature: View Poster Details  
  As a user  
  I want to view detailed information about a poster  
  So that I can decide whether to purchase it  

Background:  
  Given the following users exist in the system  
    | email               | password      | posters        |
    | jeff@ap.com         | password1     | CoolPoster     |
    | smith@ap.com        | password2     | VeryCoolPoster | 
  Given the following posters exist in the system  
    | title          | description                     | price | imageData                                                                                                |  
    | CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC     |  
    | VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC     |  

Scenario: View poster details successfully (Normal Flow)  
  Given the user is on the "Shop Posters" page  
  When they select the "CoolPoster"  
  Then they should see the details for "CoolPoster" displayed  

Scenario: Attempt to view non-existent poster details (Error Flow)  
  Given the user is on the "Shop Posters" page  
  When they attempt to select a recently deleted poster "NonExistentPoster"  
  Then they should see an error message "Poster not found"  

