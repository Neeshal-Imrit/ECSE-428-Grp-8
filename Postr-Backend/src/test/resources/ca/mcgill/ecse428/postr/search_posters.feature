Feature: Search Poster Using Filters
As a user
I want to filter posters by category and price range
So that I can find posters that match my preferences

Background:
Given the following user exist in the system search filter
| email               | password      | posters          |
| jeff@ap.com         | password1     | CoolPoster       |
Given the following posters exist in the system search filter
| title          | category       | price | user         |
| CoolPoster     | Digital Art    | 1.00  | jeff@ap.com  |
| Amazing Art    | Digital Art    | 5.00  | jeff@ap.com  |
| Classic Poster | Vintage        | 2.00  | jeff@ap.com  |


Scenario: Search posters by category (Normal Flow)
Given the user is on the "Shop Posters" page search filter
When they filter by "Digital Art" search filter
Then they should see the following posters displayed search filter
| title          |
| CoolPoster     |
| Amazing Art    |

Scenario: Search posters by price range (Normal Flow)
Given the user is on the "Shop Posters" page search filter
When they filter by price range "1.00" to "2.50" search filter
Then they should see the following posters displayed search filter
| title          |
| CoolPoster     |
| Classic Poster |

Scenario: Search posters by price range and there are none matching the criteria (Alternate Flow)
Given the user is on the "Shop Posters" page search filter
When they filter by price range "10.00" to "20.00" search filter
Then they should see a message "No posters match the selected criteria" search filter
