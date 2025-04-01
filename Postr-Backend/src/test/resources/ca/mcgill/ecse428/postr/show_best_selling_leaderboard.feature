Feature: Show Leaderboard for Best Selling Posters  
  As a user  
  I want to see a leaderboard of best-selling posters  
  So that I can browse popular designs  

Background:  
  Given the following posters have sales data in the system  
    | title          | salesCount  |  
    | CoolPoster     | 15          |  
    | VeryCoolPoster | 10          |  
    | Amazing Art    | 25          |  

Scenario: Display leaderboard successfully (Normal Flow)  
  Given the user is on the Leaderboard page  
  Then they should see the posters sorted by salesCount in descending order  
    | title          | salesCount  |  
    | Amazing Art    | 25          |  
    | CoolPoster     | 15          |  
    | VeryCoolPoster | 10          |  

Scenario: No sales data available (Error Flow)  
  Given there are no sales data in the system  
  When the user is on the Leaderboard page  
  Then they should see a message "No leaderboard data available" leaderboard

Scenario: No sales data available for one of the posters (Alternate Flow)
   Given the following posters have sales data in the system  
    | title          | salesCount  |  
    | CoolPoster     | 15          |  
    | VeryCoolPoster | 10          |  
    | Amazing Art    |             |  
   When the user is on the Leaderboard page  
   Then they should see the posters sorted by salesCount in descending order  
    | title          | salesCount  |   
    | CoolPoster     | 15          |  
    | VeryCoolPoster | 10          | 
