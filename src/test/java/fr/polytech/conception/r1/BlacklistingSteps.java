package fr.polytech.conception.r1;

import org.junit.Assert;

import java.time.ZonedDateTime;

import fr.polytech.conception.r1.profile.InvalidProfileDataException;
import fr.polytech.conception.r1.profile.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BlacklistingSteps
{
    private User theo;
    private User enzo;
    private SessionsList sessionsList;

    @Given("No session initially")
    public void noSessionInitially()
    {
        sessionsList = SessionsList.getInstance();
        sessionsList.cleanAllSessions();
    }

    @Given("Users,Theo and Enzo")
    public void usersTheoAndEnzo() throws InvalidProfileDataException
    {
        theo = new User("Theo", "qwijibo1", "theo@mail.com");
        enzo = new User("Enzo", "qwijibo1", "enzo@mail.com");
    }

    @When("Theo creates some sessions")
    public void enzoCreatesSomeSessions() throws InvalidSessionDataException
    {
        sessionsList.addSession(new Session(ZonedDateTime.parse("2030-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2030-01-01T13:00:00.000+01:00[Europe/Paris]"), null, Sport.ACROSPORT, theo));
        sessionsList.addSession(new Session(ZonedDateTime.parse("2031-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2031-01-01T13:00:00.000+01:00[Europe/Paris]"), null, Sport.TENNIS, theo));
        sessionsList.addSession(new Session(ZonedDateTime.parse("2032-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2032-01-01T13:00:00.000+01:00[Europe/Paris]"), null, Sport.KAYAK, theo));
        sessionsList.addSession(new Session(ZonedDateTime.parse("2033-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2033-01-01T13:00:00.000+01:00[Europe/Paris]"), null, Sport.SKI, theo));
    }

    @When("Theo blacklists Enzo")
    public void theoBlacklistsEnzo()
    {
        theo.blacklist(enzo);
    }

    @Then("Enzo cannot see sessions created by Theo")
    public void enzoCannotSeeSessionsCreatedByEnzo()
    {
        Assert.assertEquals(0, sessionsList.chercherSession(enzo, null, null, null, null, "Theo").size());
    }

    @Then("Enzo can see sessions created by Theo")
    public void enzoCanSeeSessionsCreatedByTheo()
    {
        Assert.assertEquals(4, sessionsList.chercherSession(enzo, null, null, null, null, "Theo").size());
    }
}
