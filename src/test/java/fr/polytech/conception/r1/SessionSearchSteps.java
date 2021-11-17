package fr.polytech.conception.r1;

import com.google.common.collect.Streams;

import org.junit.Assert;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

import fr.polytech.conception.r1.profile.InvalidFriendshipException;
import fr.polytech.conception.r1.profile.InvalidProfileDataException;
import fr.polytech.conception.r1.profile.User;
import fr.polytech.conception.r1.session.SessionOneshot;
import fr.polytech.conception.r1.session.SessionRecurring;
import fr.polytech.conception.r1.session.SessionsList;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SessionSearchSteps
{
    private SessionsList sessionsList;
    private User theo;
    private User karl;
    private Stream<SessionOneshot> resultSessionSearch;

    @Given("Users Karl and Theo special user")
    public void usersKarlAndTheoSpecialUser() throws InvalidProfileDataException
    {
        theo = new User("theo", "Mdp2Theo", "theo@mail.com");
        theo.setSpecialUser(true);
        karl = new User("karl", "1L0v3C4p1t4l", "karl@mail.com");
    }

    @Given("An empty list of sessions for searching")
    public void anEmptyListOfSessionsForSearching()
    {
        sessionsList = SessionsList.getInstance();
        sessionsList.cleanAllSessions();
    }

    @And("An unsponsored session created by Theo of {string} at {string} with granted access")
    public void anUnsponsoredSessionOfAtWithGrantedAccess(String arg0, String arg1) throws InvalidSessionDataException
    {
        ZonedDateTime sessionBegin = ZonedDateTime.parse(arg1);
        Sport sport = Sport.getByName(arg0);
        sessionsList.addSession(new SessionOneshot(sessionBegin, sessionBegin.plusHours(1), "", sport, theo, false));
    }

    @And("A sponsored session created by Theo of {string} at {string} with granted access")
    public void aSponsoredSessionOfAtWithGrantedAccess(String arg0, String arg1) throws InvalidSessionDataException
    {
        ZonedDateTime sessionBegin = ZonedDateTime.parse(arg1);
        Sport sport = Sport.getByName(arg0);
        sessionsList.addSession(new SessionOneshot(sessionBegin, sessionBegin.plusHours(1), "", sport, theo, true));
    }

    @When("Karl does a default search on the sessions")
    public void karlDoesADefaultSearchOnTheSessions()
    {
        resultSessionSearch = sessionsList.defaultSessionSearch(karl);
    }

    @Then("Karl should find the sessions with the following order: {string}")
    @SuppressWarnings("UnstableApiUsage")
    public void karlShouldFindTheSessionsWithTheFollowingOrder(String arg0)
    {
        var expectedSessionList = Arrays.stream(arg0.split(", "));
        Assert.assertTrue(Streams.zip(expectedSessionList, resultSessionSearch,
                        (exp, rec) -> exp.equals(rec.getSport().getName()))
                .allMatch(x -> x));
    }

    @And("An unsponsored session created by Theo of {string} at {string} for friends only")
    public void anUnsponsoredSessionCreatedByTheoOfAtForFriendsOnly(String arg0, String arg1) throws InvalidSessionDataException
    {
        ZonedDateTime sessionBegin = ZonedDateTime.parse(arg1);
        Sport sport = Sport.getByName(arg0);
        SessionOneshot sessionOneshot = new SessionOneshot(sessionBegin, sessionBegin.plusDays(1), "", sport, theo, true);
        sessionOneshot.setReserveAuxAmis(true);
        sessionsList.addSession(sessionOneshot);
    }

    @And("Karl and Theo friends")
    public void karlAndTheoFriends() throws InvalidFriendshipException
    {
        karl.sendFriendRequest(theo);
        theo.acceptFriendRequest(karl);
    }

    @And("A recurring session created by Theo of {string} starting at {string} with a period of {int} days")
    public void aRecurringSessionCreatedByTheoOfStartingAtWithAPeriodOfDays(String arg0, String arg1, int arg2) throws InvalidSessionDataException
    {
        Sport sport = Sport.getByName(arg0);
        ZonedDateTime premiere = ZonedDateTime.parse(arg1);
        Duration duration = Duration.ofHours(1);
        SessionRecurring sessionRecurring = new SessionRecurring(premiere, Period.ofDays(arg2), duration, "", sport, theo);
        sessionsList.addSession(sessionRecurring);
    }

    @And("Karl looks only for the {int} first results of the search")
    public void karlLooksOnlyForTheFirstResultsOfTheSearch(int arg0)
    {
        resultSessionSearch = resultSessionSearch.limit(arg0);
    }
}
