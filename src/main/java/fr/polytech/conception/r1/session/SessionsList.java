package fr.polytech.conception.r1.session;

import java.time.Period;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import fr.polytech.conception.r1.Level;
import fr.polytech.conception.r1.Sport;
import fr.polytech.conception.r1.profile.User;

public class SessionsList
{
    private static SessionsList SESSIONSLIST = null;
    private final List<SessionInterface> sessions = new ArrayList<>();

    private SessionsList()
    {
        //
    }

    public static SessionsList getInstance()
    {
        if (SESSIONSLIST == null)
        {
            SESSIONSLIST = new SessionsList();
        }
        return SESSIONSLIST;
    }

    private Stream<SessionOneshot> getOneshots(ZonedDateTime fin)
    {
        return sessions.stream().flatMap(s -> s.getOneshots(fin));
    }

    public Stream<SessionOneshot> searchSession(User user, Sport sport, String adresse, ZonedDateTime debut, ZonedDateTime fin, String organizer)
    {
        var res = getOneshots(fin);
        if (sport != null)
            res = res.filter(s -> s.getSport() == sport);
        if (adresse != null && !adresse.isEmpty())
            res = res.filter(s -> s.getAddress().contains(adresse));
        if (debut != null)
            res = res.filter(s -> s.getStart().isAfter(debut));
        if (fin != null)
            res = res.filter(s -> s.getEnd().isBefore(fin));
        if (organizer != null && !organizer.isEmpty())
            res = res.filter(s -> s.getOrganizer().getNickname().contains(organizer));
        return res
                .filter(s -> (!s.isFriendsOnly() || user.getFriends().contains(s.getOrganizer())))
                .filter(s -> (s.getDifficulty() == Level.BEGINNER || (user.getFavouriteSports().containsKey(s.getSport()) && s.getDifficulty().compareTo(user.getFavouriteSports().get(s.getSport())) >= 0)))
                .filter(s -> s.getEntryDeadline().isAfter(ZonedDateTime.now()))
                .filter(s -> !s.getOrganizer().haveIBlacklistedUser(user));
    }

    public void addSession(SessionInterface session)
    {
        sessions.add(session);
    }

    public void cleanAllSessions()
    {
        sessions.clear();
    }

    public Stream<SessionOneshot> defaultSessionSearch(User user)
    {
        return getOneshots(ZonedDateTime.now().plus(Period.ofYears(1))).filter(s -> (!s.isFriendsOnly() || user.getFriends().contains(s.getOrganizer()))).filter(s -> (s.getDifficulty() == Level.BEGINNER || (user.getFavouriteSports().containsKey(s.getSport()) && s.getDifficulty().compareTo(user.getFavouriteSports().get(s.getSport())) >= 0)))
                .filter(s -> s.getEntryDeadline().isAfter(ZonedDateTime.now()))
                .filter(s -> !s.getOrganizer().haveIBlacklistedUser(user)).sorted();
    }

    public Stream<SessionOneshot> friendsParticipatingSessionSearch(User user)
    {
        return defaultSessionSearch(user).filter(sessionOneshot -> sessionOneshot.getParticipants().stream().anyMatch(partcipant -> user.getFriends().contains(partcipant)));
    }
}
