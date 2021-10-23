Feature: Friendship handling

  Scenario: Accept a friend request
    Given 2 distincts users
    When User 1 sends a friend request to user 2
    When User 2 accepts the friend request of the user 1
    Then User 1 and 2 should be friend

  Scenario: Decline a friend request
    Given 2 distincts users
    When User 1 sends a friend request to user 2
    When User 2 declines the friend request of the user 1
    Then User 1 and 2 should not be friend

  Scenario: Send a friend request to one of your friends
    Given 2 distincts users
    When User 1 sends a friend request to user 2
    When User 2 accepts the friend request of the user 1
    When User 1 resends a friend request to user 2
    Then User 1 and 2 should be friend

  Scenario: Send a friend request twice
    Given 2 distincts users
    When User 1 sends a friend request to user 2
    When User 1 resends a friend request to user 2
    Then User 1 and 2 should not be friend