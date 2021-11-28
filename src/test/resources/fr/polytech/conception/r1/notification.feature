Feature: A user receives notifications

  Scenario: Sends invitation
    Given 2 distinct users to notify
    Given a session organized by user 1 in notify
    When user 1 invites user 2 to the session in notify
    Then user 2 can see the invitation has been received through a notification

  Scenario: Accepts invitation
    Given 2 distinct users to notify
    Given a session organized by user 1 in notify
    Given user 1 invites user 2 to the session in notify
    When user 2 accepts the invitation in notify
    Then user 1 can see the invitaion has been accepted through a notification

  Scenario: Declines invitation
    Given 2 distinct users to notify
    Given a session organized by user 1 in notify
    Given user 1 invites user 2 to the session in notify
    When user 2 declines the invitation in notify
    Then user 1 can see the invitaion has been declined through a notification

  Scenario: Sends invitation to blacklister
    Given 2 distinct users to notify
    Given user 2 has previoulsy blacklisted user 1
    Given a session organized by user 1 in notify
    When user 1 invites user 2 to the session in notify
    Then user 2 cannot see the invitation has been received through a notification

  Scenario: Notification for unparticipation
    Given 2 distinct users to notify
    Given a session organized by user 1 in notify
    Given user 2 participating the session
    When user 2 unparticipates the session
    Then user 1 can see the participation has been undone

  Scenario: Notification for exclusion
    Given 2 distinct users to notify
    Given a session organized by user 1 in notify
    Given user 2 participating the session
    When user 1 excludes user 2 from the session
    Then user 2 can see they have been excluded

  Scenario: Notification for session modification
    Given 2 distinct users to notify
    Given a session organized by user 1 in notify
    Given user 2 participating the session
    When user 1 changes the location of the session
    Then user 2 can see a modification has occurred

  Scenario: Notification for session modification
    Given 2 distinct users to notify
    Given a session organized by user 1 in notify
    Given user 2 participating the session
    When user 1 changes the start time of the session
    Then user 2 can see a modification has occurred

  Scenario: Notification for session modification
    Given 2 distinct users to notify
    Given a session organized by user 1 in notify
    Given user 2 participating the session
    When user 1 changes the end time of the session
    Then user 2 can see a modification has occurred

  Scenario: Notification for session modification
    Given 2 distinct users to notify
    Given a session organized by user 1 in notify
    Given user 2 participating the session
    When user 1 changes the maximum number of participants of the session
    Then user 2 can see a modification has occurred

  Scenario: Notification for session cancelation
    Given 2 distinct users to notify
    Given a session organized by user 1 in notify
    Given user 2 participating the session
    When user 1 cancels the session
    Then user 2 can see the session has been canceled
