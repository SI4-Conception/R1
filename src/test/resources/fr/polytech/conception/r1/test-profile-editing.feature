Feature: profile editing features

  Scenario: valid profile editing
    Given valid profile data
    When I update my profile
    Then My profile should be updated