package fr.polytech.conception.r1;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.ZonedDateTime;

public class SessionSteps
{
    private ZonedDateTime validDateTimeBegin = ZonedDateTime.parse("2030-02-01T12:00:00.000+01:00[Europe/Paris]");
    private ZonedDateTime validDateTimeEnd = ZonedDateTime.parse("2030-02-01T15:00:00.000+01:00[Europe/Paris]");
    private String validAddress = "14 rue Bolchaia Loubianka";
    private Sport validSport = new Sport("Tir aux pigeons");

    private Session session;

    @Given("valid datas for the session")
    public void validDatasForTheSession()
    {
    }

    @When("I create the session")
    public void iCreateTheSession()
    {
        session = new Session(validDateTimeBegin, validDateTimeEnd, validAddress, validSport);
    }

    @Then("I should have a valid session")
    public void iShouldHaveAValidSession()
    {
        Assert.assertNotNull(session);
    }





    @Given("Previously created session")
    public void previouslyCreatedSession()
    {
        session = new Session(validDateTimeBegin, validDateTimeEnd, validAddress, validSport);
    }

    @When("I change the session settings with valid datas")
    public void iChangeTheSessionSettingsWithValidDatas()
    {
        // TODO: change session settings
    }

    @Then("I should have the changed valid session")
    public void iShouldHaveTheChangedValidSession()
    {
        Assert.assertNotNull(session);
    }
}
