Feature: View All Available Poster Designs
  As a user of Postr
  I want to browse all available poster designs
  So that I can explore and purchase my favorite designs

  Scenario: Display All Available Posters (Normal Flow)
    Given the following posters exist in the system
      | title          | description                     | price | imageData   | user         |
      | CoolPoster     | It's a cool poster!             | 1.00  | 1, 2, 3     | jeff@ap.com  |
      | VeryCoolPoster | It's a very cool poster!        | 1.55  | 1, 2, 4     | smith@ap.com |
      | Amazing Art    | A stunning piece of digital art | 5.00  | artwork.jpg | jeff@ap.com  |
    When the user is on the shop page
    Then they should see the following posters displayed
      | title          | description                     | price | imageData   | user         |
      | CoolPoster     | It's a cool poster!             | 1.00  | 1, 2, 3     | jeff@ap.com  |
      | VeryCoolPoster | It's a very cool poster!        | 1.55  | 1, 2, 4     | smith@ap.com |
      | Amazing Art    | A stunning piece of digital art | 5.00  | artwork.jpg | jeff@ap.com  |

  Scenario: No Posters Available (Alternate Flow)
    Given there are no posters available in the system
    When the user navigates to the posters listing page
    Then they should see a message "No posters available at the moment"
