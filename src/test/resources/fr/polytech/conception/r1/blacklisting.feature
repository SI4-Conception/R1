Feature: A user can blacklist another user

  Scenario: Cannot see sessions if I'm blacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates some sessions
    When Theo blacklists Enzo
    Then Enzo cannot see sessions created by Theo

  Scenario: Can see sessions if I'm not blacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates some sessions
    Then Enzo can see sessions created by Theo

  Scenario: Cannot participate in a session if I'm blacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates one sessions
    When Theo blacklists Enzo
    Then Enzo cannot participate to the session created by Theo
    Then Enzo doesn't participate to the session

  Scenario: Can participate in a session if I'm not blacklisted
    Given No session initially
    Given Users,Theo and Enzo
    When Theo creates one sessions
    Then Enzo can participate to the session created by Theo
    And Enzo participates to the session