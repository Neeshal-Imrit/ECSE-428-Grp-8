Feature: Like Poster
As a user
I want to like a poster
So that I can support and promote my favorite designs

Background:
Given the following users exist in the system like poster
| email               | password      | posters          |
| jeff@ap.com         | password1     | CoolPoster       |
| smith@ap.com        | password2     | VeryCoolPoster   |

Given the following posters exist in the system like poster
  | title              | description                          | price | imageData                                                                                               | numberOfPurchases | user         | likes |
  | CoolPoster         | It's a cool poster!                  | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC    | 0                 | jeff@ap.com  | 5     |
  | VeryCoolPoster     | It's a very cool poster!             | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC    | 0                 | smith@ap.com | 0     |

Scenario: Successfully like a poster (Normal Flow)
Given the user is logged in as "smith@ap.com" before liking posters
When they like the "CoolPoster"
Then the "CoolPoster" should have 6 likes

Scenario: Successfully unlike a poster (Alternate Flow)
Given the user is logged in as "smith@ap.com" before liking posters
Given they already liked the "CoolPoster"
Then the "CoolPoster" should have 5 likes

Scenario: Attempt to like a poster without logging in (Error Flow)
Given the user is not logged in to like posters
When they attempt to like "CoolPoster"
Then they should see an error message "Please sign in to like posters"

Scenario: Attempt to like own poster (Error Flow)
Given the user is logged in as "jeff@ap.com" before liking posters
When they attempt to like "CoolPoster"
Then they should see an error message "You cannot like your own poster"