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

  Scenario: Trying to change session with more min users than max users
    Given Previously created session with correct 2 min and 6 max users
    When I try to set 7 min users
    Then I should have a session with 2 min and 6 max users

  Scenario: participate in a session
    Given Previously created a correct session
    When I try to participate in a session found
    Then the registration is taken into account by the session