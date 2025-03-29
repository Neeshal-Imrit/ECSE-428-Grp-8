Feature: Update Poster
As an artist
I want to update a poster's details
So that I can correct or improve its information

Background:
Given the following users exist in the system update poster
| email               | password      | posters          |
| jeff@ap.com         | password1     | CoolPoster       |
| artist@ap.com       | password2     | VeryCoolPoster   |

 Given the following posters exist in the system update poster
| title          | description                     | price | imageData                                                                                             | user         |
| CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | artist@ap.com |

Scenario: Successfully update a poster (Normal Flow)
Given the artist is logged in as "artist@ap.com" update poster
When they update the "CoolPoster" description to "Updated cool poster" update poster
Then the following posters shall exist in the system update poster
| title          | description              | price | imageData                                                                                             | user         |
| CoolPoster     | Updated cool poster      | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster! | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | artist@ap.com |

Scenario: User Updates a Poster Without a Title (Error Flow)
Given the user is on the update poster page
When he  enters a title "" and a description "A stunning piece of digital art" and a price 5.00 and an image file "iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC"
Then he shall  see an error message "The poster must have a title"
Then the  following posters shall exist in the system update poster
| title          | description                     | price | imageData                                                                                             | user         |
| CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | artist@ap.com |
    
Scenario: User updates a Poster Without a Description (Error Flow)
Given the user is on the update poster page
When he enters a title "Amazing Art" and a description "" and a price 5.00 and an image file "iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC" update poster
Then he shall   see an error message "The description cannot be empty"
And the   following posters shall exist in the system update poster
| title          | description                     | price | imageData                                                                                             | user         |
| CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | artist@ap.com |
    

Scenario: User updates a Poster With an invalid price (Error Flow)
Given the user is on the  update poster page
When he enters a  title "Amazing Art" and a description "A stunning piece of digital art" and a price -5.00 and an image file "iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC"
Then he  shall see an error message "The poster's price cannot be negative"
And the following  posters shall exist in the system update poster
| title          | description                     | price | imageData                                                                                             | user         |
| CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | artist@ap.com |


Scenario: User updates a Poster Without an Image (Error Flow)
Given the user is logged in as "jeff@ap.com" update poster
When the user updates a poster without an image
Then the user shall see an error message "There is no image for the poster"
And the following   posters shall exist in the system update poster 
| title          | description                     | price | imageData                                                                                             | user         |
| CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | artist@ap.com |
    

Scenario: User updates a Poster with the same title as an existing one (Error Flow)
Given the  user is  logged in as "jeff@ap.com" update poster
When the user updates a poster with the same title as an existing one
Then the user shall see an error  message "A poster with the same title already exists" 
And the following posters shall exist in the  system update poster
| title          | description                     | price | imageData                                                                                             | user         |
| CoolPoster     | It's a cool poster!             | 1.00  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | jeff@ap.com  |
| VeryCoolPoster | It's a very cool poster!        | 1.55  | iVBORw0KGgoAAAANSUhEUgAAAAIAAAACCAYAAABytg0kAAAAFElEQVR42mP8/5+hP6MggIMAAP9cAv52kBLqAAAAAElFTkSuQmCC  | artist@ap.com |



