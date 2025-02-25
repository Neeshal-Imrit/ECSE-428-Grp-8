Feature: User Sign Up
  As a new user
  I want to create an account
  So that I can sign in and use the platform

 Background: 
    Given the following users exist in the system
      | email        | password  | 
      | jeff@ap.com  | password1 | 
      | smith@ap.com | password2 | 

  Scenario: Successful user sign up
    Given the user is on the sign-up page
    When he enters a valid email "newuser@example.com"
    And he enters a valid password "SecurePass123"
    Then the following users shall exist in the system
      | email               | password      | 
      | jeff@ap.com         | password1     | 
      | smith@ap.com        | password2     | 
      | newuser@example.com | SecurePass123 | 

  Scenario: Sign up with an already registered email (Error Flow)
    Given the user is on the sign-up page
    When he enters an email "jeff@ap.com" that is already registered
    And he enters a valid password "SecurePass123"
    Then he should see an error message "Email already exists"
    And the following users shall exist in the system
      | email               | password      | 
      | jeff@ap.com         | password1     | 
      | smith@ap.com        | password2     | 
      | newuser@example.com | SecurePass123 | 

  Scenario: Sign up with invalid password (Error Flow)
    Given the user is on the sign-up page
    When he enters a valid email "Johnuser@example.com"
    And he enters a password "pass1"
    Then he should see an error message "Password must be at least 8 characters long"
    And the following users shall exist in the system
      | email               | password      | 
      | jeff@ap.com         | password1     | 
      | smith@ap.com        | password2     | 
      | newuser@example.com | SecurePass123 |

Scenario: Sign up with invalid email (Error Flow)
    Given the user is on the sign-up page
    When he enters an email "JohnDoe.com"
    And he enters a valid password "SecurePass123"
    Then he should see an error message "Invalid Email"
    And the following users shall exist in the system
      | email               | password      | 
      | jeff@ap.com         | password1     | 
      | smith@ap.com        | password2     | 
      | newuser@example.com | SecurePass123 |
