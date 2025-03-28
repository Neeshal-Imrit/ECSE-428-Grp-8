Feature: Show Purchase History
As a user
I want to view my purchase history
So that I can keep track of my past orders

Background:
Given the following users exist in the system purchase history
| email        | password  | posters                    |
| jeff@ap.com  | password1 | AmazingArt                 |
| smith@ap.com | password2 | CoolPoster, VeryCoolPoster |
Given the following purchases exists purchase history
| email        | poster         | price |
| jeff@ap.com  | CoolPoster     | 10.00 |
| jeff@ap.com  | VeryCoolPoster | 15.00 |

Scenario: View purchase history successfully (Normal Flow)
Given the  user is logged in as "jeff@ap.com" purchase history
When they  view their purchase history
Then they should see the following entries
| poster         | date        | price |
| CoolPoster     | 2025-03-01  | 10.00 |
| VeryCoolPoster | 2025-03-02  | 15.00 |

Scenario: No purchases found (Alternate Flow)
Given the user is logged in as "smith@ap.com" purchase history
When they view their purchase history
Then they  should see a message "You have not made any purchases"

