package fr.polytech.conception.r1;

import org.junit.Assert;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import fr.polytech.conception.r1.profile.InvalidProfileDataException;
import fr.polytech.conception.r1.profile.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MultiSessionsSteps
{
    private final List<Session> validSessionsList = new ArrayList<>();
    private final List<Session> foundSessionsList = new ArrayList<>();
    private final List<User> usersList = new ArrayList<>();

    @Given("Lists of valid sessions and users")
    public void listsOfValidSessionsAndUsers()
    {
        try
        {
            User pikachu = new User("Pikachu", "P1k4chu06", "pika.chu@mail.com");
            User schtroumpfALunettes = new User("Schtroumpf a lunettes", "schtroumpf", "schtroumpfalunettes@mail.com");
            usersList.add(pikachu);
            usersList.add(schtroumpfALunettes);
            validSessionsList.add(new Session(ZonedDateTime.parse("2030-01-01T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2030-01-01T15:00:00.000+01:00[Europe/Paris]"), "", new Sport("Beach volley"), pikachu));
            validSessionsList.add(new Session(ZonedDateTime.parse("2030-01-02T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2030-01-02T15:00:00.000+01:00[Europe/Paris]"), "", new Sport("Tennis"), pikachu));
            validSessionsList.add(new Session(ZonedDateTime.parse("2031-01-02T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2031-01-02T15:00:00.000+01:00[Europe/Paris]"), "", new Sport("Tennis"), schtroumpfALunettes));
            validSessionsList.add(new Session(ZonedDateTime.parse("2032-01-02T12:00:00.000+01:00[Europe/Paris]"), ZonedDateTime.parse("2032-01-02T15:00:00.000+01:00[Europe/Paris]"), "", new Sport("Tennis"), schtroumpfALunettes));
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
        validSessionsList.forEach(session -> {
            if(session.getDebut().isAfter(beginDate) && session.getFin().isBefore(endDate)) {
                foundSessionsList.add(session);
            }
        });
    }

    @Then("I should have {int} sessions")
    public void iShouldHaveAllSessions(int arg0)
    {
        Assert.assertEquals(arg0, foundSessionsList.size());
    }

    @When("I search a {string} session")
    public void iSearchASession(String arg0)
    {
        validSessionsList.forEach(session -> {
            if(session.getSport().getNom().equals(arg0)) {
                foundSessionsList.add(session);
            }
        });
    }

    @When("I search a session created by {string}")
    public void iSearchASessionCreatedBy(String arg0)
    {
        validSessionsList.forEach(session -> {
            if(session.getOrganisateur().getPseudo().equals(arg0)) {
                foundSessionsList.add(session);
            }
        });
    }
}
