Feature: A user can invite another user to a session they organized

  Scenario: Invite another user to a session
    Given 2 distinct users
    Given a session organized by user 1
    When user 1 invites user 2 to the session
    Then user 1 can see the invitaion he has sent
    And user 1 can see that the invitation is pending
    And user 2 has received the invitation

  Scenario: Accept an invitation
    Given 2 distinct users
    Given a session organized by user 1
    Given an invitation to a session from user 1 to user 2
    When user 2 accepts the invitation
    Then user 2 is participating the session

  Scenario: Accept an invitation of a full session
    Given 2 distinct users
    Given a session organized by user 1
    Given a full session
    Given an invitation to a session from user 1 to user 2
    When user 2 accepts the invalid invitation
    Then user 2 is denied accepting the invitation

  Scenario: Decline an invitation
    Given 2 distinct users
    Given a session organized by user 1
    Given an invitation to a session from user 1 to user 2
    When user 2 declines the invitation
    Then user 1 can see the invitation sent is declined

  Scenario: Cannot see invitation from blacklisted
    Given 2 distinct users
    Given a session organized by user 1
    Given user 2 has blacklisted user 1
    When user 1 invites user 2 to the session
    Then user 2 doesn't see a pending invitation
    And user 1 sees the invitation sent as pending

  Scenario: Cannot see invitation after blacklisted and invitation received before
    Given 2 distinct users
    Given a session organized by user 1
    Given an invitation to a session from user 1 to user 2
    When user 1 blacklists user 2
    Then user 2 has received the invitation
    And user 1 sees the invitation sent as pending

  Scenario: Can see invitation after unblacklisted and invitation received before
    Given 2 distinct users
    Given a session organized by user 1
    Given user 2 has blacklisted user 1
    Given an invitation to a session from user 1 to user 2
    When user 1 unblacklists user 2
    Then user 2 can see that the invitation is pending
    And user 1 sees the invitation sent as pending

  Scenario: Accept an invitation with session passed its subscription date
    Given 2 distinct users
    Given a session organized by user 1
    Given a session passed its subscription date
    Given an invitation to a session from user 1 to user 2
    When user 2 accepts the invitation
    Then user 2 is participating the session

  Scenario: Invite to a passed session
    Given 2 distinct users
    Given a session organized by user 1
    Given a passed session
    When user 1 sends the invalid invitation to user 2
    Then user 1 is denied sending the invitation

  Scenario: Accept an invitation to a passed session
    Given 2 distinct users
    Given a session organized by user 1
    Given an invitation to a session from user 1 to user 2
    Given a passed session
    When user 2 accepts the invalid invitation
    Then user 2 is denied accepting the invitation

  Scenario: Invite twice
    Given 2 distinct users
    Given a session organized by user 1
    Given an invitation to a session from user 1 to user 2
    When user 1 invites user 2 again
    Then user 1 is denied sending the invitation

  Scenario: Invite a participant
    Given 2 distinct users
    Given a session organized by user 1
    Given user 2 participating session
    When user 1 sends the invalid invitation to user 2
    Then user 1 is denied sending the invitation

  Scenario: Not the organizer inviting
    Given 3 distinct users
    Given a session organized by user 1
    When user 3 sends the invalid invitation to user 2
    Then user 3 is denied sending the invitation