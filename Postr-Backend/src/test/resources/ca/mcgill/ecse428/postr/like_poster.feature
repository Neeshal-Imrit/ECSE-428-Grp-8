Feature: Like Poster
As a user
I want to like a poster
So that I can support and promote my favorite designs

Background:
Given the following users exist in the system
| email               | password      | posters          |
| jeff@ap.com         | password1     | CoolPoster       |
| smith@ap.com        | password2     | VeryCoolPoster   |

Given the following posters exist in the system
| title              | likes  |
| CoolPoster         | 5      |
| VeryCoolPoster     | 10     |

Scenario: Successfully like a poster (Normal Flow)
Given the user is logged in as "smith@ap.com"
When they like the "CoolPoster"
Then the "CoolPoster" should have "6" likes

Scenario: Successfully unlike a poster (Alternate Flow)
Given the user is logged in as "smith@ap.com"
Given they already liked the "CoolPoster"
Then the "CoolPoster" should have "4" likes

Scenario: Attempt to like a poster without logging in (Error Flow)
Given the user is not logged in
When they attempt to like "CoolPoster"
Then they should see an error message "Please sign in to like posters"

Scenario: Attempt to like a poster without logging in (Error Flow)
Given the user is logged in as "jeff@ap.com"
When they attempt to like "CoolPoster"
Then they should see an error message "You cannot like your own poster"