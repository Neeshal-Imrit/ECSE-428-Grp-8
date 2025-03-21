Feature: Upload a Poster
As a user on Postr
I want to upload my poster designs
So that other users can browse and purchase them

Background:
 Given the following users exist in the system upload posters
      | email        | password  | 
      | jeff@ap.com  | password1 | 
      | smith@ap.com | password2 | 
 Given the user is logged in as "jeff@ap.com" upload posters

 Given the following posters exist in the system upload posters
      | title          | description              | price | imageData                                                                                             | user         |
      | CoolPoster     | It's a cool poster!      | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
      | VeryCoolPoster | It's a very cool poster! | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | smith@ap.com |

Scenario: User Successfully Uploads a Poster (Normal Flow)
Given the user is on the upload poster page upload posters
When he enters a title "Amazing Art" and a description "A stunning piece of digital art" and a price 5.00 and an image file "iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC"
Then the following posters shall exist in the system upload poster
      | title          | description                     | price | imageData                                                                                             | user         |
      | CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
      | VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | smith@ap.com |
      | Amazing Art    | A stunning piece of digital art | 5.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |

Scenario: User Uploads a Poster Without a Title (Error Flow)
Given the user is on the upload poster page upload posters
When he enters a title "" and a description "A stunning piece of digital art" and a price 5.00 and an image file "iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC"
Then he should see an error message "The poster must have a title" upload poster
Then the following posters shall exist in the system upload poster
      | title          | description                     | price | imageData                                                                                             | user         |
      | CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
      | VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | smith@ap.com |
      

Scenario: User Uploads a Poster Without a Description (Error Flow)
Given the user is on the upload poster page upload posters
When he enters a title "Amazing Art" and a description "" and a price 5.00 and an image file "iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC"
Then he should see an error message "The description cannot be empty" upload poster
And the following posters shall exist in the system upload poster
      | title          | description                     | price | imageData                                                                                             | user         |
      | CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
      | VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | smith@ap.com |
      

Scenario: User Uploads a Poster With an invalid price (Error Flow)
Given the user is on the upload poster page upload posters
When he enters a title "Amazing Art" and a description "A stunning piece of digital art" and a price -5.00 and an image file "iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC"
Then he should see an error message "The poster's price cannot be negative" upload poster
And the following posters shall exist in the system upload poster
      | title          | description                     | price | imageData                                                                                             | user         |
      | CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
      | VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | smith@ap.com |


Scenario: User Uploads a Poster Without an Image (Error Flow)
Given the user is logged in as "jeff@ap.com"
When the user uploads a poster without an image upload poster
Then the user should see an error message "There is no image for the poster" upload poster
And the following posters shall exist in the system upload poster
      | title          | description                     | price | imageData                                                                                             | user         |
      | CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
      | VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | smith@ap.com |
      

Scenario: User Uploads a Poster with the same title as an existing one (Error Flow)
Given the user is logged in as "jeff@ap.com"
When the user uploads a poster with the same title as an existing one
Then the user should see an error message "A poster with the same title already exists" upload poster
And the following posters shall exist in the system upload poster
      | title          | description                     | price | imageData                                                                                             | user         |
      | CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
      | VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | smith@ap.com |




  