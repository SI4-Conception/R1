package fr.polytech.conception.r1;

import org.junit.Assert;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.polytech.conception.r1.profile.User;
import fr.polytech.conception.r1.session.Session;
import fr.polytech.conception.r1.session.SessionOneshot;
import fr.polytech.conception.r1.session.SessionsList;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MultiSessionsSteps
{
    private final List<Session> validSessionsList = new ArrayList<>();
    private final List<Session> foundSessionsList = new ArrayList<>();
    private final List<User> usersList = new ArrayList<>();
    private SessionsList sessionsList;

    @Given("Lists of valid sessions and users")
    public void listsOfValidSessionsAndUsers()
    {
        sessionsList = SessionsList.getInstance();
        sessionsList.cleanAllSessions();
        try
        {
            User pikachu = new User("Pikachu", "P1k4chu06", "pika.chu@mail.com");
            User schtroumpfALunettes = new User("Schtroumpf a lunettes", "schtroumpf", "schtroumpfalunettes@mail.com");
            usersList.add(pikachu);
            usersList.add(schtroumpfALunettes);
            sessionsList.addSession(new SessionOneshot(ZonedDateTime.parse("2030-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2030-01-01T15:00:00.000+01:00[Europe/Paris]"), "", Sport.VOLLEY_BALL, pikachu, false));
            sessionsList.addSession(new SessionOneshot(ZonedDateTime.parse("2030-01-02T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2030-01-02T15:00:00.000+01:00[Europe/Paris]"), "", Sport.TENNIS, pikachu, false));
            sessionsList.addSession(new SessionOneshot(ZonedDateTime.parse("2031-01-02T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2031-01-02T15:00:00.000+01:00[Europe/Paris]"), "", Sport.TENNIS, schtroumpfALunettes, false));
            sessionsList.addSession(new SessionOneshot(ZonedDateTime.parse("2032-01-02T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2032-01-02T15:00:00.000+01:00[Europe/Paris]"), "", Sport.TENNIS, schtroumpfALunettes, false));
        }
        catch (Exception e)
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
        User pika = usersList.get(0);

        foundSessionsList.addAll(sessionsList.chercherSession(pika, null, null, beginDate, endDate, null));
    }

    @Then("I should have {int} sessions")
    public void iShouldHaveAllSessions(int arg0)
    {
        Assert.assertEquals(arg0, foundSessionsList.size());
    }

    @When("I search a {sport} session")
    public void iSearchASession(Sport sport)
    {
        User pika = usersList.get(0);

        foundSessionsList.addAll(sessionsList.chercherSession(pika, sport, null, null, null, null));
    }

    @When("I search a session created by {string}")
    public void iSearchASessionCreatedBy(String arg0)
    {
        User pika = usersList.get(0);

        foundSessionsList.addAll(sessionsList.chercherSession(pika, null, null, null, null, arg0));
    }
}
