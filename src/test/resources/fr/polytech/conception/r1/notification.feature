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

