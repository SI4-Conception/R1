Feature: Features of my session

  Scenario: Creating a new session
    Given valid datas for the session
    When I create the session
    Then it should have been a success