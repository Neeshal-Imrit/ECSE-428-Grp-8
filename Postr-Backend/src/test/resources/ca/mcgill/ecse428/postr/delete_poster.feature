Feature: Delete Poster
As an artist
I want to delete a poster
So that I can remove content I no longer wish to display

Background:
Given the following posters exist in the system
| title          | description                     | price |
| CoolPoster     | It's a cool poster!             | 1.00  |

Given the following users exist in the system  
| email          | password  | posters    |
| artist@ap.com  | password1 | CoolPoster | 
| smith@ap.com   | password2 |            |

Scenario: Successfully delete a poster (Normal Flow)
Given the artist is logged in as "artist@ap.com"
When they delete the "CoolPoster"
Then they should see a confirmation message "Poster deleted successfully"

