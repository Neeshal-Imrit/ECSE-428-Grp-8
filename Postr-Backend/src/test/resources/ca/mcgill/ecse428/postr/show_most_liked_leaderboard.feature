Feature: Show Leaderboard of Most Popular Posters
As a user
I want to see a leaderboard of the most popular posters
So that I can browse designs that are highly rated

Background:
Given the following posters have popularity data in the system
| title          | likes |
| CoolPoster     | 1   |
| Amazing Art    | 2   |

Scenario: Display leaderboard successfully (Normal Flow)
Given the user is now on the "Popular Posters" page
Then they should see the posters sorted by likes in descending order
| title         | likes |
| Amazing Art   | 2   |
| CoolPoster    | 1   |

Scenario: No popular posters available (Error Flow)
Given there are no posters in the system
When the user navigates to the "Popular Posters" page
Then they should see a message with text "No posters available"

Scenario: Multiple posters with same number of likes (Alternate Flow)
Given the following posters have popularity data in the system
  | title         | likes |
  | CoolPoster    | 10    |
  | AwesomeDesign | 10    |
  | BrilliantArt  | 10    |
When the user is now on the "Popular Posters" page
Then they should see posters with equal likes sorted alphabetically by title
  | title         | likes |
  | AwesomeDesign | 10    |
  | BrilliantArt  | 10    |
  | CoolPoster    | 10    |