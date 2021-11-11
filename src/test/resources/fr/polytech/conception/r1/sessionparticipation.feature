Feature: Participation to a session

  Scenario: Participate in a session
    Given Previously created session without any condition for participants
    When I try to participate in a session found
    Then The registration is taken into account by the session

  Scenario: Trying to participate in a session with too many users
    Given Previously created session with 3 users at max and already 3 participants
    When I try to participate in a session found
    Then The registration is not taken into account by the session

  Scenario: Trying to participate in a session with not too many users
    Given Previously created session with 4 users at max and already 3 participants
    When I try to participate in a session found
    Then The registration is taken into account by the session

  Scenario: Trying to participate twice in a session
    Given Previously created session without any condition for participants
    When I try to participate in a session found
    Then The registration is taken into account by the session
    And I cannot participate a second time to the session