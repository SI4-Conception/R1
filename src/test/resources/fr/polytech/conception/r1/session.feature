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
    Given Invalid time data for the session
    When I try to create the session with invalid dates
    Then I should fail and should't have a session

  Scenario: not two simultaneous sessions
    Given "Julien" wants to create a session at the same time as another session he has already created
    When I try to create a session at the same time as another session I have already created
    Then the session is not created

  Scenario: Trying to change session with more min users than max users
    Given Previously created session with correct 2 min and 6 max users
    When I try to set 7 min users
    Then I should have a session with 2 min and 6 max users

  Scenario: Trying to change session with correct number of min users
    Given Previously created session with correct 2 min and 6 max users
    When I try to set 3 min users
    Then I should have a session with 3 min and 6 max users

  Scenario: Trying to change session with less max users than min users
    Given Previously created session with correct 5 min and 6 max users
    When I try to set 2 max users
    Then I should have a session with 5 min and 6 max users

  Scenario: Trying to change session with correct number of min users
    Given Previously created session with correct 2 min and 6 max users
    When I try to set 3 max users
    Then I should have a session with 2 min and 3 max users

  Scenario: Trying to change session with correct limit subscription date
    Given Previously created session
    When I try to set a valid end subscription date
    Then I should have a session with this new end subscription date

  Scenario: Trying to change session with incorrect limit subscription date
    Given Previously created session with valid end subscription date
    When I try to set a invalid end subscription date
    Then I should have a session with old end subscription date

  Scenario: Participate in a session
    Given Previously created session
    When I try to participate in a session found
    Then the registration is taken into account by the session

  Scenario: Trying to participate in a session with too many users
    Given Previously created session with 3 users at max and already 3 participants
    When I try to participate in a session found
    Then The registration is not taken into account by the session

  Scenario: Trying to participate twice in a session
    Given Previously created session
    When I try to participate in a session found
    Then I cannot participate a second time to the session