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