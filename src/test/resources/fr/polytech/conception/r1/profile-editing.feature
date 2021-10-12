Feature: profile editing features

  Scenario: account creation
    Given a username "Romain74", a password "mySafePassword" and an email address "romain.baudet@gmail.com"
    When I try to create an account
    Then the account should be created

  Scenario: valid profile editing
    Given an existing account and a valid username "BaudetRomain74"
    When I change my username to the valid username
    Then My username should be updated

  Scenario: username too short
    Given an existing account and a too short username "bob"
    When I change my username to the too short username
    Then An error occurs because the name is too short

  Scenario: username too long
    Given an existing account and a too long username "thisisdefinitelywaytoolongbutIllmakeitlongeroktherewego"
    When I change my username to the too long username
    Then An error occurs because the name is too long