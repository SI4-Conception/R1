package fr.polytech.conception.r1;

import org.junit.Assert;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MultiSessionsSteps
{
    private final List<Session> validSessionsList = new ArrayList<>();
    private final List<Session> foundSessionsList = new ArrayList<>();

    @Given("A list of valid sessions")
    public void aListOfValidSessions()
    {
        try
        {
            validSessionsList.add(new Session(ZonedDateTime.parse("2030-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2030-01-01T15:00:00.000+01:00[Europe/Paris]"), "", new Sport("Beach volley"), new Utilisateur()));
        }
        catch (InvalidSessionDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @When("I search a session between {string} and {string}")
    public void iSearchASessionBetweenAnd(String arg0, String arg1)
    {
        ZonedDateTime beginDate = ZonedDateTime.parse(arg0 + "T00:00:00.000+01:00[Europe/Paris]");
        ZonedDateTime endDate = ZonedDateTime.parse(arg1 + "T00:00:00.000+01:00[Europe/Paris]");
        validSessionsList.forEach(session -> {
            if(session.getDebut().isAfter(beginDate) && session.getFin().isBefore(endDate)) {
                foundSessionsList.add(session);
            }
        });
    }

    @Then("I should have all sessions between these dates")
    public void iShouldHaveAllSessionsBetweenTheseDates()
    {
        Assert.assertTrue(foundSessionsList.size() > 0);
    }
}
