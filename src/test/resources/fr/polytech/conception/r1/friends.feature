Feature: Friendship handling

  Scenario: Accept a friend request
    Given 2 distincts users
    When User 1 sends a friend request to user 2
    And User 2 accepts the friend request of the user 1
    Then User 1 and 2 should be friend

  Scenario: Decline a friend request
    Given 2 distincts users
    When User 1 sends a friend request to user 2
    And User 2 declines the friend request of the user 1
    Then User 1 and 2 should not be friend

  Scenario: Send a friend request to one of your friends
    Given 2 friends users
    Then User 1 cannot resends a friend request to user 2

  Scenario: Send a friend request twice
    Given 2 distincts users
    When User 1 sends a friend request to user 2
    Then User 1 cannot resends a friend request to user 2
    And User 1 and 2 should not be friend

  Scenario: A friend tries to participate in a session friends only
    Given 2 friends users
    Given A session created by user 1 for friends only
    When User 2 try to participate to the session
    Then User 2 registration should be ok

  Scenario: A non-friend tries to participate in a session friends only
    Given 2 distincts users
    Given A session created by user 1 for friends only
    When User 2 try to participate to the session
    Then User 2 registration should not be ok

  Scenario: A friend tries to find a session friends only
    Given 2 friends users
    Given A session created by user 1 for friends only
    When User 2 searches a session
    Then User 2 should find 1 sessions

  Scenario: A non-friend tries to find a session friends only
    Given 2 distincts users
    Given A session created by user 1 for friends only
    When User 2 searches a session
    Then User 2 should find 0 sessions
