Feature: Features of my session

  Scenario: Creating a new session
    Given Valid begin time for the session: "2030-02-01T12:00:00.000+01:00[Europe/Paris]"
    Given Valid end time for the session: "2030-02-01T15:00:00.000+01:00[Europe/Paris]"
    Given Valid address for the session "14 rue Bolchaia Loubianka"
    Given Valid sport for the session "Kayak"
    When I create the session with these valid data
    Then I should have a valid session

  Scenario: Changing settings of an existing session
    Given Previously created session without any condition for participants
    Given Valid begin time for the session: "2031-02-01T12:00:00.000+01:00[Europe/Paris]"
    Given Valid end time for the session: "2031-02-01T15:00:00.000+01:00[Europe/Paris]"
    Given Valid number of min participants: 4
    Given Valid number of max participants: 55
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
    Given Previously created session without any condition for participants
    When I try to set a valid end subscription date
    Then I should have a session with this new end subscription date

  Scenario: Trying to change session with incorrect limit subscription date
    Given Previously created session with valid end subscription date
    When I try to set a invalid end subscription date
    Then I should have a session with old end subscription date

  Scenario: Trying to set less max users than the current number of participants
    Given Previously created session with valid end subscription date
    When 5 users participate to the session
    Then I cannot set the max number of participants to 4