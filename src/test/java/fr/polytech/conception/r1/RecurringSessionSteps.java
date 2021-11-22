package fr.polytech.conception.r1;

import org.junit.Assert;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Optional;

import fr.polytech.conception.r1.profile.InvalidProfileDataException;
import fr.polytech.conception.r1.profile.User;
import fr.polytech.conception.r1.session.SessionOneshot;
import fr.polytech.conception.r1.session.SessionRecurring;
import fr.polytech.conception.r1.session.SessionsList;
import io.cucumber.java.en.Given;
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
    }
}
