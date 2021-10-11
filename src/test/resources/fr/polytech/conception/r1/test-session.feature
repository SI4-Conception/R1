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

  Scenario: Trying to change session with more min users than max users
    Given Previously created session with correct 2 min and 6 max users
    When I try to set 7 min users
    Then I should have a session with 2 min and 6 max users