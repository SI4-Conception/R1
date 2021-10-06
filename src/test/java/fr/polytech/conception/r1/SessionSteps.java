package fr.polytech.conception.r1;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.ZonedDateTime;

public class SessionSteps
{
    private final ZonedDateTime validDateTimeBegin = ZonedDateTime.parse("2030-02-01T12:00:00.000+01:00[Europe/Paris]");
    private final ZonedDateTime validDateTimeEnd = ZonedDateTime.parse("2030-02-01T15:00:00.000+01:00[Europe/Paris]");
    private final String validAddress = "14 rue Bolchaia Loubianka";
    private final Sport validSport = new Sport("Tir aux pigeons");

    private Session session = null;

    @Given("valid datas for the session")
    public void validDatasForTheSession()
    {
    }

    @When("I create the session")
    public void iCreateTheSession()
    {
        try {
            session = new Session(validDateTimeBegin, validDateTimeEnd, validAddress, validSport);
        }
        catch (InvalidSessionDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("I should have a valid session")
    public void iShouldHaveAValidSession()
    {
        Assert.assertNotNull(session);
    }





    @Given("Previously created session")
    public void previouslyCreatedSession()
    {
        try {
            session = new Session(validDateTimeBegin, validDateTimeEnd, validAddress, validSport);
        }
        catch (InvalidSessionDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @When("I change the session settings with valid datas")
    public void iChangeTheSessionSettingsWithValidDatas()
    {
        // TODO: change session settings
        try {
            session.setMaxParticipants(55);
            session.setMinParticipants(4);
        } catch (InvalidSessionDataException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("I should have the changed valid session")
    public void iShouldHaveTheChangedValidSession()
    {
        Assert.assertNotNull(session);
        Assert.assertEquals(4, session.getMinParticipants());
        Assert.assertEquals(55, session.getMaxParticipants());
    }
}
