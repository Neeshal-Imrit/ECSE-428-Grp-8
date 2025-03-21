Feature: Sign Out User
As a user
I want to sign out of my account
So that I can keep my account secure

Background:
  Given the following users exist in the system  
    | email        | password  |  
    | jeff@ap.com  | password1 |  
    | smith@ap.com | password2 |  

Scenario: Successful sign-out (Normal Flow)
Given the user is logged in as "jeff@ap.com"
When they sign out
Then they should be redirected to the sign-in page

Scenario: Sign-out attempt while already logged out (Error Flow)
Given the user is not logged in
When they attempt to sign out
Then they should see a message "You are not signed in"
