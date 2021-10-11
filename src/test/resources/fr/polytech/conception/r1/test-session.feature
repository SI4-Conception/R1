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

  Scenario: not two simultaneous sessions
    Given "Julien" wants to create a session at the same time as another session he has already created
    When I try to create a session at the same time as another session I have already created
    Then the session is not created