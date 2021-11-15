Feature: Searching session by relevancy

  Scenario: Displaying relevants sessions
    Given A list of sessions for searching
    And An unsposored session created by Theo of "Yoseikan Budo" at "2030-01-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsposored session created by Theo of "Wing chun" at "2030-03-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsposored session created by Theo of "Voltige en cercle" at "2030-02-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    When Karl do a default search on the sessions
    Then Karl should find the sessions with the following order: "Yoseikan Budo, Voltige en cercle, Wing chun"