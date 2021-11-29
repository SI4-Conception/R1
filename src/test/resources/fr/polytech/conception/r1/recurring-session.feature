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

  Scenario: Changing difficulty for a recurring session changes difficulty only for futures sessions
    Given An empty list of sessions for searching recurring sessions
    Given A recurring session of "Snorkeling" beginning today + 1 days created by theo
    Given Theo level for "Snorkeling" set to "ADVANCED"
    When Theo sets the difficulty of the session of "Snorkeling" at today + 8 days to "ADVANCED"
    Then All the sessions of "Snorkeling" from today + 8 should be set to "ADVANCED" level
    Then All the sessions of "Snorkeling" before today + 8 should be set to "BEGINNER" level
    And We cannot see sessions that already occured

  Scenario: Changing min users for a recurring session changes difficulty only for futures sessions
    Given An empty list of sessions for searching recurring sessions
    Given A recurring session of "Snorkeling" beginning today + 1 days created by theo
    When Theo sets min participants of the session of "Snorkeling" at today + 8 days to 5
    Then All the sessions of "Snorkeling" from today + 8 should have 5 min participants
    Then All the sessions of "Snorkeling" before today + 8 should have 1 min participants
    And We cannot see sessions that already occured

  Scenario: Can participate to recurring session if today is between the session limit registration time and the session begin
    Given An empty list of sessions for searching recurring sessions
    Given A recurring session of "Snorkeling" beginning today + 1 days created by theo with a limit registration time of 10 days
    When Paul tries to participate to the "Snorkeling" session of today + 5 days
    Then Paul can participate to the session of "Snorkeling"

  Scenario: Cannot participate to recurring session if today is between the session limit registration time and the session begin
    Given An empty list of sessions for searching recurring sessions
    Given A recurring session of "Snorkeling" beginning today + 1 days created by theo with a limit registration time of 10 days
    When Paul tries to participate to the "Snorkeling" session of today + 50 days
    Then Paul cannot participate to the session of "Snorkeling"

  Scenario: Cannot create a recurring session from the past
    Given An empty list of sessions for searching recurring sessions
    When Theo tries to create a recurring session of "Snorkeling" beginning today - 1 days
    Then The recurring session of "Snorkeling" doesn't exist

  Scenario Outline: Creating a new recurring session
    Given Valid sport for this recurring session: <arg0>
    When I create the recurring session with these valid data beginning today + 40 days
    Then I should have a valid recurring session
    Examples:
      | arg0     |
      | "Tennis" |