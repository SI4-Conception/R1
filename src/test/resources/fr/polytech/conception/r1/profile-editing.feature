Feature: profile editing features

  Scenario: account creation
    Given a username "Romain74", a password "mySafePassword" and an email address "romain.baudet@gmail.com"
    When I try to create an account
    Then the account should be created

  Scenario: valid username change
    Given an existing account and a valid username "BaudetRomain74"
    When I change my username to the valid username
    Then My username should be updated

  Scenario: username too short
    Given an existing account and a too short username "bob"
    When I change my username to the too short username
    Then An error should occur because the name is too short

  Scenario: username too long
    Given an existing account and a too long username "thisisdefinitelywaytoolongbutIllmakeitlongeroktherewego"
    When I change my username to the too long username
    Then An error occurs because the name is too long

  Scenario: valid password change
    Given an existing account and a valid passsword "thisPasswordIsGood"
    When I change my password to the valid password
    Then My password should be updated

  Scenario: password too short
    Given an existing account and a too short passsword "sh0rt"
    When I change my password to the too short password
    Then An errors should occur because the password is too short

  Scenario: valid profile picture change
    Given an existing account and a valid profile picture path "thisPasswordIsGood"
    When I change my profile picture to the valid profile picture
    Then My profile picture should be updated

  Scenario: valid email change
    Given an existing account and a valid email address "romainbaudet74@gmail.com"
    When I change my password to the valid email address
    Then My email address should be updated

  Scenario: wrong email
    Given an existing account and a wrong email "thisisnotgood"
    When I change my email to the wrond email
    Then An error should occur because the email is wrong

  Scenario: valid address change
    Given an existing account and a valid address "85 rue Henri Poincaré"
    When I change my address to the valid address
    Then My address should be updated

  Scenario: valid first name change
    Given an existing account and a valid first name "Serge"
    When I change my address to the valid first name
    Then My first name should be updated

  Scenario: valid last name change
    Given an existing account and a valid last name "Mongne"
    When I change my address to the valid last name
    Then My last name should be updated
    
  Scenario: adding a valid sport to favourites
    Given an existing account and a sport Tennis
    When I add this sport to my favourites sports
    Then The sport should appear as favourite in my profile

  Scenario: adding multiple valid sports to favourites
    Given an existing account and some sports Tennis Badminton Ski
    When I add these sports to my favourites sports
    Then The sports should appear as favourites in my profile