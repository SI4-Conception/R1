package fr.polytech.conception.r1;

import org.junit.Assert;

import java.time.ZonedDateTime;

import fr.polytech.conception.r1.profile.InvalidProfileDataException;
import fr.polytech.conception.r1.profile.User;
import fr.polytech.conception.r1.session.SessionOneshot;
import fr.polytech.conception.r1.session.SessionsList;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BlacklistingSteps
{
    private User theo;
    private User enzo;
    private SessionsList sessionsList;
    private SessionOneshot session;

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
        sessionsList.addSession(new SessionOneshot(ZonedDateTime.parse("2030-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2030-01-01T13:00:00.000+01:00[Europe/Paris]"), null, Sport.ACROSPORT, theo, false));
        sessionsList.addSession(new SessionOneshot(ZonedDateTime.parse("2031-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2031-01-01T13:00:00.000+01:00[Europe/Paris]"), null, Sport.TENNIS, theo, false));
        sessionsList.addSession(new SessionOneshot(ZonedDateTime.parse("2032-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2032-01-01T13:00:00.000+01:00[Europe/Paris]"), null, Sport.KAYAK, theo, false));
        sessionsList.addSession(new SessionOneshot(ZonedDateTime.parse("2033-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2033-01-01T13:00:00.000+01:00[Europe/Paris]"), null, Sport.SKI, theo, false));
    }

    @When("Theo blacklists Enzo")
    public void theoBlacklistsEnzo()
    {
        theo.blacklist(enzo);
    }

    @Then("Enzo cannot see sessions created by Theo")
    public void enzoCannotSeeSessionsCreatedByEnzo()
    {
        Assert.assertEquals(0, sessionsList.searchSession(enzo, null, null, null, null, "Theo").count());
    }

    @Then("Enzo can see sessions created by Theo")
    public void enzoCanSeeSessionsCreatedByTheo()
    {
        Assert.assertEquals(4, sessionsList.searchSession(enzo, null, null, null, null, "Theo").count());
    }

    @When("Theo creates one session")
    public void theoCreatesOneSessions() throws InvalidSessionDataException
    {
        session = new SessionOneshot(ZonedDateTime.parse("2030-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2030-01-01T13:00:00.000+01:00[Europe/Paris]"), null, Sport.ACROSPORT, theo, false);
    }

    @Then("Enzo cannot participate to the session created by Theo")
    public void enzoCannotParticipateToTheSessionCreatedByTheo()
    {
        Assert.assertFalse(enzo.participer(session));
    }

    @Then("Enzo doesn't participate to the session")
    public void theoDoesnTParticipateToTheSession()
    {
        Assert.assertFalse(session.getParticipants().contains(theo));
        Assert.assertFalse(theo.getAttendedSessions().contains(session));
    }

    @Then("Enzo can participate to the session created by Theo")
    public void enzoCanParticipateToTheSessionCreatedByTheo()
    {
        Assert.assertTrue(enzo.participer(session));
    }

    @Then("Enzo participates to the session")
    public void theoParticipatesToTheSession()
    {
        Assert.assertTrue(session.getParticipants().contains(enzo));
        Assert.assertTrue(enzo.getAttendedSessions().contains(session));
    }

    @When("Theo blacklists Enzo once more")
    public void theoBlacklistsEnzoOnceMore()
    {
        theo.blacklist(enzo);
    }

    @When("Theo unblacklists Enzo")
    public void theoUnblacklistsEnzo()
    {
       theo.unblacklist(enzo);
    }
}
