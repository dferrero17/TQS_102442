Feature: Air quality stats

  Scenario: User can view air quality stats for a specific location
    Given the user navigates to the air quality stats page
    When the user selects a location
    Then the air quality stats for that location should be displayed
