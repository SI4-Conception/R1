Feature: Conversation handling

  Scenario: Talk with someone
    Given 2 Users who wants to talk
    When User 1 starts a conversation with user 2
    Then There is 1 message in the whole conversation