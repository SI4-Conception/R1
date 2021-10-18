Feature: multi-sessions operations
  We begin with the following configuration:
  User pikachu = new User("Pikachu", "P1k4chu06", "pika.chu@mail.com");
  User schtroumpfALunettes = new User("Schtroumpf a lunettes", "schtroumpf", "schtroumpfalunettes@mail.com");
  Session(ZonedDateTime.parse("2030-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2030-01-01T15:00:00.000+01:00[Europe/Paris]"), "", new Sport("Beach volley"), pikachu);
  Session(ZonedDateTime.parse("2030-01-02T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2030-01-02T15:00:00.000+01:00[Europe/Paris]"), "", new Sport("Tennis"), pikachu);
  Session(ZonedDateTime.parse("2031-01-02T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2031-01-02T15:00:00.000+01:00[Europe/Paris]"), "", new Sport("Tennis"), schtroumpfALunettes);
  Session(ZonedDateTime.parse("2032-01-02T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2032-01-02T15:00:00.000+01:00[Europe/Paris]"), "", new Sport("Tennis"), schtroumpfALunettes);

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