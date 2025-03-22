Feature: Search Poster Using Filters
As a user
I want to filter posters by category and price range
So that I can find posters that match my preferences

Background:
Given the following posters exist in the system
| title          | category       | price |
| CoolPoster     | Digital Art    | 1.00  |
| Amazing Art    | Digital Art    | 5.00  |
| Classic Poster | Vintage        | 2.00  |

Scenario: Search posters by category (Normal Flow)
Given the user is on the "Shop Posters" page
When they filter by "Digital Art"
Then they should see the following posters displayed
| title          |
| CoolPoster     |
| Amazing Art    |

Scenario: Search posters by price range (Normal Flow)
Given the user is on the "Shop Posters" page
When they filter by price range "1.00" to "2.50"
Then they should see the following posters displayed
| title          |
| CoolPoster     |
| Classic Poster |

Scenario: Search posters by price range and there are none matching the criteria (Alternate Flow)
Given the user is on the "Shop Posters" page
When they filter by price range "10.00" to "20.00"
Then they should see a message "No posters match the selected criteria"
