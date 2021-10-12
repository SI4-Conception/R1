Feature: Features of my session

  Scenario: Creating a new session
    Given valid datas for the session
    When I create the session
    Then I should have a valid session

  Scenario: Changing settings of an existing session
    Given Previously created session
    When I change the session settings with valid datas
    Then I should have the changed valid session

  Scenario: Trying to create session with invalid params
    Given Invalid datas for the session
    When I try to create the session
    Then I should fail and should't have a session