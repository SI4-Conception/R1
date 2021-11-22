Feature: Recurring sessions

  Scenario: Participation to recurring session
    Given An empty list of sessions for searching recurring sessions
    Given A recurring session of "Snorkeling" at "2022-05-05T12:00:00.000+01:00[Europe/Paris]" created by theo
    When Paul participates to the recurring session of "Snorkeling" at "2022-05-06T12:00:00.000+01:00[Europe/Paris]"
