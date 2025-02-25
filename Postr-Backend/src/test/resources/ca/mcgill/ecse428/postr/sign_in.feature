Feature: User Sign In
  As a registered user
  I want to sign into my account
  So that I can access the home page

Background:
   Given the following users exist in the system
      | email               | password      | 
      | jeff@ap.com         | password1     | 
      | smith@ap.com        | password2     | 
      | newuser@example.com | SecurePass123 | 

  Scenario: Successful user sign in
    Given the user is on the sign-in page
    When he enters a valid email "smith@ap.com" and password "password2"
    Then he should be redirected to the home page

  Scenario: Sign in with incorrect password (Error Flow)
    Given the user is on the sign-in page
    When he enters a valid email "smith@ap.com"
    And an incorrect password "WrongPass456"
    Then he should see an error message "Invalid password"

  Scenario: Sign in with unregistered email (Error Flow)
    Given the user is on the sign-in page
    When he enters an invalid email "yeet@ap.com"
    And a password "WrongPass456"
    Then he should see an error message "Invalid email"