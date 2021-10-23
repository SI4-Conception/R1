Feature: Conversation handling

  Scenario: Talk with someone
    Given 2 Users who want to talk
    When User 1 sends starts a conversation with user 2
    Then I should find one message in the conversation