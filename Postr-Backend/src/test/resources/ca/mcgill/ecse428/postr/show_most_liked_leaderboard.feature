Feature: Show Leaderboard of Most Popular Posters
As a user
I want to see a leaderboard of the most popular posters
So that I can browse designs that are highly rated

Background:
Given the following posters have popularity data in the system
| title          | likes |
| CoolPoster     | 100   |
| Amazing Art    | 200   |

Scenario: Display leaderboard successfully (Normal Flow)
Given the user is on the "Popular Posters" page
Then they should see the posters sorted by likes in descending order
| title         | likes |
| Amazing Art   | 200   |
| CoolPoster    | 100   |

Scenario: No popular posters available (Error Flow)
Given there are no posters in the system
When the user is on the "Popular Posters" page
Then they should see a message "No posters available"

Scenario: No like data available for one of the posters (Alternate Flow)
   Given the following posters have sales data in the system  
    | title          | likes       |  
    | CoolPoster     | 15          |  
    | VeryCoolPoster | 10          |  
    | Amazing Art    |             |  
   When the user is on the "Leaderboard" page  
   Then they should see the posters sorted by salesCount in descending order  
    | title          | salesCount  |   
    | CoolPoster     | 15          |  
    | VeryCoolPoster | 10          | 