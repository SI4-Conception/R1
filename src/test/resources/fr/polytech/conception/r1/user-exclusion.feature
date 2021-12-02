Feature: Exclude an user from a session

  Scenario: An user is excluded from a session
    Given A session created by Helene
    When Zhang participates to the session
    And Helene excludes Zhang from the session
    Then Zhang doesn't participate to the session anymore

    Scenario: An user can participate again in a session if he as been excluded from this one
      Given A session created by Helene
      When Zhang participates to the session
      And Helene excludes Zhang from the session
      And Zhang doesn't participate to the session anymore
      And Zhang participates to the session
      Then Zhang can participate to the session again