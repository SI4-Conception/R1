package fr.polytech.conception.r1.session;

import java.time.Period;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.polytech.conception.r1.Level;
import fr.polytech.conception.r1.Sport;
import fr.polytech.conception.r1.profile.User;

public class SessionsList
{
    private static SessionsList SESSIONSLIST = null;

    public static SessionsList getInstance()
    {
        if(SESSIONSLIST == null)
        {
            SESSIONSLIST = new SessionsList();
        }
        return SESSIONSLIST;
    }

    private final List<Session> sessions = new ArrayList<>();

    private SessionsList()
    {
        //
    }

    private Stream<SessionOneshot> getOneshots(ZonedDateTime fin)
    {
        return sessions.stream().flatMap(s -> s.getOneshots(fin));
    }

    public Stream<SessionOneshot> chercherSession(User user, Sport sport, String adresse, ZonedDateTime debut, ZonedDateTime fin, String organizer)
    {
        var res = getOneshots(fin);
        if (sport != null)
            res = res.filter(s -> s.getSport() == sport);
        if (adresse != null && !adresse.isEmpty())
            res = res.filter(s -> s.getAdresse().contains(adresse));
        if (debut != null)
            res = res.filter(s -> s.getDebut().isAfter(debut));
        if (fin != null)
            res = res.filter(s -> s.getFin().isBefore(fin));
        if (organizer != null && !organizer.isEmpty())
            res = res.filter(s -> s.getOrganisateur().getPseudo().contains(organizer));
        return res
                .filter(s -> (!s.isReserveAuxAmis() || user.getFriends().contains(s.getOrganisateur())))
                .filter(s -> (s.getDifficulte() == Level.DEBUTANT || (user.getFavouriteSports().containsKey(s.getSport()) && s.getDifficulte().compareTo(user.getFavouriteSports().get(s.getSport())) >= 0)))
                .filter(s -> s.getDateLimiteInscription().isAfter(ZonedDateTime.now()))
                .filter(s -> !s.getOrganisateur().haveIBlacklistedUser(user));
    }

    public void addSession(Session session)
    {
        sessions.add(session);
    }

    public void cleanAllSessions()
    {
        sessions.clear();
    }
    
    public Stream<SessionOneshot> defaultSessionSearch(User user)
    {
        return getOneshots(ZonedDateTime.now().plus(Period.ofYears(1))).filter(s -> (!s.isReserveAuxAmis() || user.getFriends().contains(s.getOrganisateur()))).filter(s -> (s.getDifficulte() == Level.DEBUTANT || (user.getFavouriteSports().containsKey(s.getSport()) && s.getDifficulte().compareTo(user.getFavouriteSports().get(s.getSport())) >= 0)))
                .filter(s -> s.getDateLimiteInscription().isAfter(ZonedDateTime.now()))
                .filter(s -> !s.getOrganisateur().haveIBlacklistedUser(user)).sorted();
    }
}
