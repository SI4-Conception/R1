package fr.polytech.conception.r1;

import com.google.common.collect.Streams;

import org.junit.Assert;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import fr.polytech.conception.r1.profile.InvalidProfileDataException;
import fr.polytech.conception.r1.profile.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SessionSearchSteps
{
    private SessionsList sessionsList;
    private final User theo = new User("theo", "Mdp2Theo", "theo@mail.com");
    private final User karl = new User("karl", "1L0v3C4p1t4l", "karl@mail.com");
    private Stream<Session> resultSessionSearch;

    public SessionSearchSteps() throws InvalidProfileDataException
    {
    }

    @Given("A list of sessions for searching")
    public void aListOfSessionsForSearching()
    {
        sessionsList = SessionsList.getInstance();
    }


    @And("An unsposored session created by Theo of {string} at {string} with granted access")
    public void anUnsposoredSessionOfAtWithGrantedAccess(String arg0, String arg1) throws InvalidSessionDataException
    {
        ZonedDateTime sessionBegin = ZonedDateTime.parse(arg1);
        Sport sport = Sport.getByName(arg0);
        sessionsList.addSession(new Session(sessionBegin, sessionBegin.plusDays(1), "", sport, theo));
    }

    @When("Karl do a default search on the sessions")
    public void iDoADefaultSearchOnTheSessions()
    {
        resultSessionSearch = sessionsList.defaultSessionSearch(karl);
    }

    @Then("Karl should find the sessions with the following order: {string}")
    public void iShouldFindTheSessionsWithTheFollowingOrder(String arg0)
    {
        var expectedSessionList = Arrays.stream(arg0.split(", "));
        Assert.assertTrue(Streams.zip(expectedSessionList, resultSessionSearch,
                        (exp, rec) -> exp.equals(rec.getSport().getName()))
                .allMatch(x -> x));
    }
}
