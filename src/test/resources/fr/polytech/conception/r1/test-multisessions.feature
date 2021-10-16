Feature: multi-sessions operations

  Scenario: Search session between dates
    Given Lists of valid sessions and users
    When I search a session between "2030-01-01" and "2030-01-02"
    Then I should have 1 sessions

  Scenario: Search session between invalid dates
    Given Lists of valid sessions and users
    When I search a session between "2037-01-01" and "2036-01-02"
    Then I should have 0 sessions

  Scenario: Search session by sport
    Given Lists of valid sessions and users
    When I search a "Tennis" session
    Then I should have 3 sessions

  Scenario: Search session by invalid sport name
    Given Lists of valid sessions and users
    When I search a "ftichti" session
    Then I should have 0 sessions

  Scenario: Search session created by user
    Given Lists of valid sessions and users
    When I search a session created by "Pikachu"
    Then I should have 2 sessions

  Scenario: Search session non existent by user
    Given Lists of valid sessions and users
    When I search a session created by "Sangoku"
    Then I should have 0 sessions