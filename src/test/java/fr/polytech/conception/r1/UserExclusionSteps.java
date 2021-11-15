package fr.polytech.conception.r1;

import org.junit.Assert;

import java.time.ZonedDateTime;

import fr.polytech.conception.r1.profile.InvalidProfileDataException;
import fr.polytech.conception.r1.profile.User;
import fr.polytech.conception.r1.session.SessionOneshot;
import fr.polytech.conception.r1.session.SessionsList;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserExclusionSteps
{
    private SessionsList sessionsList;
    private SessionOneshot session;
    private final User helene = new User("Helene", "helene06", "helene@mail.com");
    private final User zhang = new User("Zhang", "zhangFromBolivia", "zhang@mail.com");

    public UserExclusionSteps() throws InvalidProfileDataException
    {
    }

    @Given("A session created by Helene")
    public void aSessionCreatedByHelene() throws InvalidSessionDataException
    {
        sessionsList = SessionsList.getInstance();
        session = new SessionOneshot(ZonedDateTime.parse("2030-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2030-01-01T13:00:00.000+01:00[Europe/Paris]"), null, Sport.ACROSPORT, helene, false);
        sessionsList.addSession(session);
    }

    @When("Zhang participates to the session")
    public void zhangParticipatesToTheSession()
    {
        zhang.participer(session);
    }

    @And("Helene exclude Zhang from the session")
    public void heleneExcludeZhangFromTheSession()
    {
        Assert.assertTrue(session.excludeUser(zhang));
    }

    @Then("Zhang doesn't participate to the session anymore")
    public void zhangDoesnTParticipateToTheSessionAnymore()
    {
        Assert.assertFalse(zhang.getListSessions().contains(session));
        Assert.assertFalse(session.getParticipants().contains(zhang));
    }

    @Then("Zhang can participate to the session again")
    public void zhangCanParticipateToTheSessionAgain()
    {
        Assert.assertTrue(zhang.getListSessions().contains(session));
        Assert.assertTrue(session.getParticipants().contains(zhang));
    }
}
