Feature: Recurring sessions

  Scenario: Participation to recurring session
    Given An empty list of sessions for searching recurring sessions
    Given A recurring session of "Snorkeling" at "2022-05-05T12:00:00.000+01:00[Europe/Paris]" created by theo
    When Paul participates to the recurring session of "Snorkeling" at "2022-05-07T12:00:00.000+01:00[Europe/Paris]"
    Then Paul should participate to the recurring session of "Snorkeling" at "2022-05-07T12:00:00.000+01:00[Europe/Paris]"

  Scenario: Participation to recurring session at specific date doesn't involve participation to next session
    Given An empty list of sessions for searching recurring sessions
    Given A recurring session of "Snorkeling" at "2022-05-05T12:00:00.000+01:00[Europe/Paris]" created by theo
    When Paul participates to the recurring session of "Snorkeling" at "2022-05-07T12:00:00.000+01:00[Europe/Paris]"
    Then Paul should not participate to the recurring session of "Snorkeling" at "2022-05-09T12:00:00.000+01:00[Europe/Paris]"

  Scenario: Invitation to recurring session at specific date doesn't involve invitation to next session
    Given An empty list of sessions for searching recurring sessions
    Given A recurring session of "Snorkeling" at "2022-05-05T12:00:00.000+01:00[Europe/Paris]" created by theo
    When Theo invites Paul to the recurring session of "Snorkeling" at "2022-05-07T12:00:00.000+01:00[Europe/Paris]"
    Then Paul should be invited to the session of "Snorkeling" at "2022-05-07T12:00:00.000+01:00[Europe/Paris]"
    And Paul should not be invited to the session of "Snorkeling" at "2022-05-09T12:00:00.000+01:00[Europe/Paris]"

  Scenario: Cancelling a recurring session doesn't cancel the next recurring sessions
    Given An empty list of sessions for searching recurring sessions
    Given A recurring session of "Snorkeling" at "2022-05-05T12:00:00.000+01:00[Europe/Paris]" created by theo
    When Theo cancels the recurring session of "Snorkeling" at "2022-05-07T12:00:00.000+01:00[Europe/Paris]"
    Then The session of "Snorkeling" at "2022-05-07T12:00:00.000+01:00[Europe/Paris]" sould be canceled
    And The session of "Snorkeling" at "2022-05-09T12:00:00.000+01:00[Europe/Paris]" sould be not canceled