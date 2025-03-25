Feature: User Sign Up
  As a new user
  I want to create an account
  So that I can sign in and use the platform

 Background: 
    Given the following users exist in the system for sign up
      | email        | password  | 
      | jeff@ap.com  | password1 | 
      | smith@ap.com | password2 | 

  Scenario: Successful user sign up
    Given the user is on the sign-up page
    When he enters an email "newuser@example.com" and he enters a password "SecurePass123"
    Then the following users shall exist in the system
      | email               | password      | 
      | jeff@ap.com         | password1     | 
      | smith@ap.com        | password2     | 
      | newuser@example.com | SecurePass123 | 

  Scenario: Sign up with an already registered email (Error Flow)
    Given the user is on the sign-up page
    When he enters an email "jeff@ap.com" that is already registered and he enters a password "SecurePass123"
    Then he should see an error message sign up "Email already in use"
    And the following users shall exist in the system
      | email               | password      | 
      | jeff@ap.com         | password1     |
      | smith@ap.com        | password2     |

  Scenario: Sign up with invalid password (Error Flow)
    Given the user is on the sign-up page
    When he enters an email "Johnuser@example.com" and he enters a password "pass1"
    Then he should see an error message sign up "Password must be at least 8 characters long"
    And the following users shall exist in the system
      | email               | password      | 
      | jeff@ap.com         | password1     | 
      | smith@ap.com        | password2     |

Scenario: Sign up with invalid email (Error Flow)
    Given the user is on the sign-up page
    When he enters an email "JohnDoe.com" and he enters a password "SecurePass123"
    Then he should see an error message sign up "Invalid email"
    And the following users shall exist in the system
      | email               | password      | 
      | jeff@ap.com         | password1     | 
      | smith@ap.com        | password2     |
