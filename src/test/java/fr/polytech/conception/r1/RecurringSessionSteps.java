package fr.polytech.conception.r1;

import org.junit.Assert;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Optional;

import fr.polytech.conception.r1.profile.InvalidProfileDataException;
import fr.polytech.conception.r1.profile.User;
import fr.polytech.conception.r1.session.Session;
import fr.polytech.conception.r1.session.SessionOneshot;
import fr.polytech.conception.r1.session.SessionRecurring;
import fr.polytech.conception.r1.session.SessionsList;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RecurringSessionSteps
{
    private SessionsList sessionsList;
    private final User theo = new User("Theo", "The0Pa55w0rd", "theo@mail.com");
    private final User paul = new User("Paul", "PaulPa55w0rd", "paul@mail.com");

    public RecurringSessionSteps() throws InvalidProfileDataException
    {
    }

    @Given("An empty list of sessions for searching recurring sessions")
    public void anEmptyListOfSessionsForSearchingRecurringSessions()
    {
        sessionsList=SessionsList.getInstance();
        sessionsList.cleanAllSessions();
    }

    @Given("A recurring session of {string} at {string} created by theo")
    public void aRecurringSessionOfAtCreatedByTheo(String arg0, String arg1) throws InvalidSessionDataException
    {
        SessionRecurring sessionRecurring = new SessionRecurring(ZonedDateTime.parse(arg1), Period.ofDays(2), Duration.ofHours(12), "", Sport.getByName(arg0), theo);
        sessionsList.addSession(sessionRecurring);
    }

    @When("Paul participates to the recurring session of {string} at {string}")
    public void paulParticipatesToTheRecurringSessionOfSnorkelingAt(String arg0, String arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(paul).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isEqual(ZonedDateTime.parse(arg1))).findFirst();
        Assert.assertTrue(foundSession.isPresent());
        paul.participer(foundSession.get());
    }

    @Then("Paul should participate to the recurring session of {string} at {string}")
    public void paulShouldParticipateToTheRecurringSessionOfAt(String arg0, String arg1)
    {
        for(int i = 0; i < paul.getAttendedSessions().size(); i++)
        {
            Session session = paul.getAttendedSessions().get(i);
            if(session.getOneshots(ZonedDateTime.parse(arg1).plusDays(1)).anyMatch(sessionOneshot -> sessionOneshot.getStart().isEqual(ZonedDateTime.parse(arg1)) && sessionOneshot.getSport().getName().equals(arg0)))
            {
                return;
            }
        }
        Assert.fail();
    }
}
