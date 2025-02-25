Feature: Sample Cucumber Test

  Scenario: Checking if the application is running
    Given the application is started
    When I check the status
    Then the response should be "Running"
