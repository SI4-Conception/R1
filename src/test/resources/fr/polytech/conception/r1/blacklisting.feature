Feature: A user can blacklist another user

  Scenario: Cannot see sessions if I'm blacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo blacklists Enzo
    Then Enzo cannot see sessions created by Theo

  Scenario: Cannot see sessions if I'm blacklisted multiple times
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates some sessions
    When Theo blacklists Enzo
    When Theo blacklists Enzo once more
    Then Enzo cannot see sessions created by Theo

  Scenario: Can see sessions if I'm not blacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates some sessions
    Then Enzo can see sessions created by Theo

  Scenario: Can see sessions if I'm blacklisted and then unblacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates some sessions
    When Theo blacklists Enzo
    When Theo unblacklists Enzo
    Then Enzo can see sessions created by Theo

  Scenario: Can see sessions if I'm not blacklisted and unblacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates some sessions
    When Theo unblacklists Enzo
    Then Enzo can see sessions created by Theo

  Scenario: Cannot participate in a session if I'm blacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates one session
    When Theo blacklists Enzo
    Then Enzo cannot participate to the session created by Theo
    Then Enzo doesn't participate to the session

  Scenario: Cannot participate in a session if I'm blacklisted multiple times
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates one session
    When Theo blacklists Enzo
    When Theo blacklists Enzo once more
    Then Enzo cannot participate to the session created by Theo
    Then Enzo doesn't participate to the session

  Scenario: Can participate in a session if I'm not blacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates one session
    Then Enzo can participate to the session created by Theo
    And Enzo participates to the session

  Scenario: Can participate in a session if I'm blacklisted and then unblacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates one session
    When Theo blacklists Enzo
    When Theo unblacklists Enzo
    Then Enzo can participate to the session created by Theo
    And Enzo participates to the session

  Scenario: Can participate in a session if I'm not blacklisted and unblacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates one session
    When Theo unblacklists Enzo
    Then Enzo can participate to the session created by Theo
    And Enzo participates to the session

  Scenario: Cannot participate in a session if I'm blacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates one sessions
    When Enzo blacklists Theo
    Then Enzo can participate to the session created by Theo
    And Enzo participates to the session