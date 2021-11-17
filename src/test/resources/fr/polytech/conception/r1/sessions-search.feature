Feature: Searching session by relevancy

  Scenario: Displaying unsponsored relevants sessions
    Given An empty list of sessions for searching
    Given Users Karl and Theo special user
    And An unsponsored session created by Theo of "Yoseikan Budo" at "2030-01-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Wing chun" at "2030-03-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Voltige en cercle" at "2030-02-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    When Karl does a default search on the sessions
    Then Karl should find the sessions with the following order: "Yoseikan Budo, Voltige en cercle, Wing chun"

  Scenario: Displaying sponsored relevants sessions
    Given An empty list of sessions for searching
    Given Users Karl and Theo special user
    And An unsponsored session created by Theo of "Yoseikan Budo" at "2030-01-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    And A sponsored session created by Theo of "Wing chun" at "2030-01-05T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Voltige en cercle" at "2030-01-03T12:00:00.000+01:00[Europe/Paris]" with granted access
    When Karl does a default search on the sessions
    Then Karl should find the sessions with the following order: "Wing chun, Yoseikan Budo, Voltige en cercle"

  Scenario: Do not display sessions for friends only
    Given An empty list of sessions for searching
    Given Users Karl and Theo special user
    And An unsponsored session created by Theo of "Yoseikan Budo" at "2030-01-05T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Wing chun" at "2030-01-07T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Voltige en cercle" at "2030-01-03T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Squash" at "2030-01-01T12:00:00.000+01:00[Europe/Paris]" for friends only
    When Karl does a default search on the sessions
    Then Karl should find the sessions with the following order: "Voltige en cercle, Yoseikan Budo, Wing chun"

  Scenario: Display sessions for friends only if users are friends
    Given An empty list of sessions for searching
    Given Users Karl and Theo special user
    And Karl and Theo friends
    And An unsponsored session created by Theo of "Yoseikan Budo" at "2030-01-05T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Wing chun" at "2030-01-07T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Voltige en cercle" at "2030-01-03T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Squash" at "2030-01-01T12:00:00.000+01:00[Europe/Paris]" for friends only
    When Karl does a default search on the sessions
    Then Karl should find the sessions with the following order: "Squash, Voltige en cercle, Yoseikan Budo, Wing chun"

    # WARNING ! In this scenario the sessions occur at the next year. This is because recurring session are only displayed for the coming year. If the test doesn't work consider changing the dates
  Scenario: Taking care of recuring sessions:
    Given An empty list of sessions for searching
    Given Users Karl and Theo special user
    And An unsponsored session created by Theo of "Yoseikan Budo" at "2022-05-05T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Wing chun" at "2022-05-07T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Voltige en cercle" at "2022-05-03T12:00:00.000+01:00[Europe/Paris]" with granted access
    And A recurring session created by Theo of "Ski nordique" starting at "2022-05-01T12:00:00.000+01:00[Europe/Paris]" with a period of 5 days
    When Karl does a default search on the sessions
    And Karl looks only for the 7 first results of the search
    Then Karl should find the sessions with the following order: "Ski nordique, Voltige en cercle, Yoseikan Budo, Ski nordique, Wing chun, Ski nordique, Ski nordique"

  Scenario: Search for sessions with friends only
    Given An empty list of sessions for searching
    Given Users Karl and Theo special user
    Given Users "Ioni, Emil, Traian" friends with Karl
    And An unsponsored session created by Theo of "Yoseikan Budo" at "2030-01-05T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Wing chun" at "2030-01-07T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Voltige en cercle" at "2030-01-03T12:00:00.000+01:00[Europe/Paris]" with granted access
    And An unsponsored session created by Theo of "Squash" at "2030-01-01T12:00:00.000+01:00[Europe/Paris]" with granted access
    And User "Ioni" participates to the session of "Voltige en cercle"
    And User "Emil" participates to the session of "Yoseikan Budo"
    And User "Traian" participates to the session of "Yoseikan Budo"
    When Karl does a friends-participating search on the sessions
    Then Karl should find the sessions with the following order: "Voltige en cercle, Yoseikan Budo"