Feature: Purchase a Poster
  As a user
  I want to purchase a poster
  So that I can add it to my collection

Background:
  Given the following users exist in the system buy poster
| email               | password      | posters          |
| jeff@ap.com         | password1     | CoolPoster       |
| smith@ap.com        | password2     | VeryCoolPoster   |

Given the following posters exist in the system buy poster
  | title              | description                          | price | imageData                                                                                               | numberOfPurchases | user         |
  | CoolPoster         | It's a cool poster!                  | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC    | 0                 | jeff@ap.com  |
  | VeryCoolPoster     | It's a very cool poster!             | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC    | 0                 | smith@ap.com |

Scenario: Successful purchase (Normal Flow)
  Given the user is logged in as "jeff@ap.com" buy poster
  When they purchase the "CoolPoster"
  Then they should see a confirmation message "Purchase successful"
  And the poster "CoolPoster" should have 1 purchase

Scenario: Attempt to purchase the user's own poster (Error Flow)
Given the user is logged in as "jeff@ap.com"
When they purchase the poster "CoolPoster"
Then they should see an error message "You cannot buy a poster you own"

Scenario: Attempt to purchase a poster that is no longer available (Error Flow)
  Given the user is logged in as "jeff@ap.com"
  When they purchase the deleted poster "VeryVeryCoolPoster"
  Then they should see an error message "This poster is no longer available"

