Feature: Upload a Poster
As a user on Postr
I want to upload my poster designs
So that other users can browse and purchase them

Background:
 Given the following users exist in the system
      | email        | password  | 
      | jeff@ap.com  | password1 | 
      | smith@ap.com | password2 | 
 Given the user is logged in as "jeff@ap.com"

 Given the following posters exist in the system
      | title          | description              | price | imageData | user         |
      | CoolPoster     | It's a cool poster!      | 1.00  | 1, 2, 3   | jeff@ap.com  |
      | VeryCoolPoster | It's a very cool poster! | 1.55  | 1, 2, 4   | smith@ap.com |

Scenario: User Successfully Uploads a Poster (Normal Flow)
Given the user is on the upload poster page
When he enters a title "Amazing Art"
And he enters a description "A stunning piece of digital art"
And he enters a price "5.00"
And he uploads a valid image file "artwork.jpg"
Then the following posters shall exist in the system
| title          | description                     | price | imageData   | user         |
| CoolPoster     | It's a cool poster!             | 1.00  | 1, 2, 3     | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster!        | 1.55  | 1, 2, 4     | smith@ap.com |
| Amazing Art    | A stunning piece of digital art | 5.00  | artwork.jpg | jeff@ap.com  |

Scenario: User Uploads a Poster Without a Title (Error Flow)
Given the user is on the upload poster page
When he leaves the title field empty
And he enters a description "A vibrant poster design"
And he enters a price "3.00"
And he uploads a valid image file "poster.png"
Then he should see an error message "The poster must have a title"
And the following posters shall exist in the system
| title          | description                     | price | imageData   | user         |
| CoolPoster     | It's a cool poster!             | 1.00  | 1, 2, 3     | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster!        | 1.55  | 1, 2, 4     | smith@ap.com |
| Amazing Art    | A stunning piece of digital art | 5.00  | artwork.jpg | jeff@ap.com  |

Scenario: User Uploads a Poster Without a Description (Error Flow)
Given the user is on the upload poster page
When he enters a title "Minimalist Design"
And he leaves the description field empty
And he enters a price "2.50"
And he uploads a valid image file "minimal.png"
Then he should see an error message "The description cannot be empty"
And the following posters shall exist in the system
| title          | description                     | price | imageData   | user         |
| CoolPoster     | It's a cool poster!             | 1.00  | 1, 2, 3     | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster!        | 1.55  | 1, 2, 4     | smith@ap.com |
| Amazing Art    | A stunning piece of digital art | 5.00  | artwork.jpg | jeff@ap.com  |

Scenario: User Uploads a Poster With an invalid price (Error Flow)
Given the user is on the upload poster page
When he enters a title "Abstract Shapes"
And he enters a description "A beautiful abstract composition"
And he enters a price -1.0
And he uploads a valid image file "abstract.jpg"
Then he should see an error message "The poster's price cannot be negative"
And the following posters shall exist in the system
| title          | description                     | price | imageData   | user         |
| CoolPoster     | It's a cool poster!             | 1.00  | 1, 2, 3     | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster!        | 1.55  | 1, 2, 4     | smith@ap.com |
| Amazing Art    | A stunning piece of digital art | 5.00  | artwork.jpg | jeff@ap.com  |

Scenario: User Uploads a Poster Without an Image (Error Flow)
Given the user is on the upload poster page
When he enters a title "City Skyline"
And he enters a description "A high-resolution city skyline poster"
And he enters a price "4.50"
And he does not upload an image
Then he should see an error message "There is no image for the poster"
And the following posters shall exist in the system
| title          | description                     | price | imageData   | user         |
| CoolPoster     | It's a cool poster!             | 1.00  | 1, 2, 3     | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster!        | 1.55  | 1, 2, 4     | smith@ap.com |
| Amazing Art    | A stunning piece of digital art | 5.00  | artwork.jpg | jeff@ap.com  |