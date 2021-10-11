Feature: multi-sessions operations

  Scenario: Search session between dates
    Given A list of valid sessions
    When I search a session between "2030-01-01" and "2030-01-02"
    Then I should have all sessions between these dates