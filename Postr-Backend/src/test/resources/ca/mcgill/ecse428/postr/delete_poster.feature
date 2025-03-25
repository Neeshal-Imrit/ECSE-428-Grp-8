Feature: Delete Poster
As an artist
I want to delete a poster
So that I can remove content I no longer wish to display

Background:
Given the following users exist in the system delete poster  
| email               | password      | posters          |
| jeff@ap.com         | password1     | CoolPoster       |
| smith@ap.com        | password2     | VeryCoolPoster   |

Given the following posters exist in the system delete poster
  | title              | description                          | price | imageData                                                                                               | numberOfPurchases | user         |
  | CoolPoster         | It's a cool poster!                  | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC    | 0                 | jeff@ap.com  |
  | VeryCoolPoster     | It's a very cool poster!             | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC    | 0                 | smith@ap.com |


Scenario: Successfully delete a poster (Normal Flow)
Given the artist is logged in as "jeff@ap.com" delete poster
When they delete the "CoolPoster"
Then they should see a confirmation message delete poster "Poster deleted successfully"

