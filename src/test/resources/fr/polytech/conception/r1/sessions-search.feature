Feature: Searching session by relevancy

  Scenario: Displaying unsponsored relevants sessions
    Given A list of sessions for searching
    Given Users Karl and Theo
    And An unsponsored session created by Theo of "Yoseikan Budo" at "2030-01-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Wing chun" at "2030-03-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Voltige en cercle" at "2030-02-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    When Karl do a default search on the sessions
    Then Karl should find the sessions with the following order: "Yoseikan Budo, Voltige en cercle, Wing chun"

  Scenario: Displaying sponsored relevants sessions
    Given A list of sessions for searching
    Given Users Karl and Theo
    And An unsponsored session created by Theo of "Yoseikan Budo" at "2030-01-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    And A sponsored session created by Theo of "Wing chun" at "2030-01-05T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Voltige en cercle" at "2030-01-03T12:00:00.000+01:00[Europe/Paris]" with granted access
    When Karl do a default search on the sessions
    Then Karl should find the sessions with the following order: "Wing chun, Yoseikan Budo, Voltige en cercle"

  Scenario: Do not display sessions for friends only
    Given A list of sessions for searching
    Given Users Karl and Theo
    And An unsponsored session created by Theo of "Yoseikan Budo" at "2030-01-05T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Wing chun" at "2030-01-07T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Voltige en cercle" at "2030-01-03T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Squash" at "2030-01-01T12:00:00.000+01:00[Europe/Paris]" for friends only
    When Karl do a default search on the sessions
    Then Karl should find the sessions with the following order: "Voltige en cercle, Yoseikan Budo, Wing chun"

  Scenario: Display sessions for friends only if users are friends
    Given A list of sessions for searching
    Given Users Karl and Theo
    And Karl and Theo friends
    And An unsponsored session created by Theo of "Yoseikan Budo" at "2030-01-05T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Wing chun" at "2030-01-07T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Voltige en cercle" at "2030-01-03T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Squash" at "2030-01-01T12:00:00.000+01:00[Europe/Paris]" for friends only
    When Karl do a default search on the sessions
    Then Karl should find the sessions with the following order: "Squash, Voltige en cercle, Yoseikan Budo, Wing chun"