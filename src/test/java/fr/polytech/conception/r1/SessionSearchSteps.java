package fr.polytech.conception.r1;

import com.google.common.collect.Streams;

import org.junit.Assert;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import fr.polytech.conception.r1.profile.InvalidFriendshipException;
import fr.polytech.conception.r1.profile.InvalidProfileDataException;
import fr.polytech.conception.r1.profile.User;
import fr.polytech.conception.r1.session.SessionOneshot;
import fr.polytech.conception.r1.session.SessionRecurringGenerator;
import fr.polytech.conception.r1.session.SessionsList;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SessionSearchSteps
{
    private final List<User> otherUsers = new ArrayList<>();
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
        otherUsers.clear();
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
        sessionOneshot.setFriendsOnly(true);
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
        SessionRecurringGenerator sessionRecurringGenerator = new SessionRecurringGenerator(premiere, Period.ofDays(arg2), duration, "", sport, theo);
        sessionsList.addSession(sessionRecurringGenerator);
    }

    @And("Karl looks only for the {int} first results of the search")
    public void karlLooksOnlyForTheFirstResultsOfTheSearch(int arg0)
    {
        resultSessionSearch = resultSessionSearch.limit(arg0);
    }

    @Given("Users {string} friends with Karl")
    public void usersFriendsWithKarl(String arg0)
    {
        Stream<String> otherUsersStream = Arrays.stream(arg0.split(", "));
        otherUsersStream.forEach(username ->
        {
            try
            {
                User user = new User(username, "p455w04d", username + "@mail.com");
                otherUsers.add(user);
                user.sendFriendRequest(karl);
                karl.acceptFriendRequest(user);
            }
            catch (InvalidProfileDataException | InvalidFriendshipException e)
            {
                e.printStackTrace();
                Assert.fail();
            }
        });
    }

    @And("User {string} participates to the session of {string}")
    public void userParticipatesToTheSessionOf(String arg0, String arg1)
    {
        final User user = otherUsers.stream().filter(u -> u.getNickname().equals(arg0)).findFirst().orElse(null);
        Assert.assertNotNull(user);
        sessionsList.defaultSessionSearch(user).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg1)).forEach(user::participate);
    }

    @When("Karl does a friends-participating search on the sessions")
    public void karlDoesAFriendsParticipatingSearchOnTheSessions()
    {
        resultSessionSearch = sessionsList.friendsParticipatingSessionSearch(karl);
    }
}