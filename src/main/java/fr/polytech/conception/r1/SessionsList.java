package fr.polytech.conception.r1;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<Session> sessions = new ArrayList<>();

    private SessionsList()
    {
        //
    }

    public List<Session> chercherSession(User user, Sport sport, String adresse, ZonedDateTime debut, ZonedDateTime fin, String organizer)
    {
        return sessions.stream()
                .filter(sport != null ? s -> s.getSport().equals(sport) : s -> true)
                .filter(adresse != null && !adresse.equals("") ? s -> s.getAdresse().contains(adresse) : s -> true)
                .filter(debut != null ? s -> s.getDebut().isAfter(debut) : s -> true)
                .filter(fin != null ? s -> s.getFin().isBefore(fin) : s -> true)
                .filter(organizer != null && !organizer.equals("") ? s -> s.getOrganisateur().getPseudo().equals(organizer) : s -> true)
                .filter(s -> (!s.isReserveAuxAmis() || user.getFriends().contains(s.getOrganisateur())))
                .filter(s -> (s.getDifficulte() == Level.DEBUTANT || (user.getFavouriteSports().containsKey(s.getSport()) && s.getDifficulte().compareTo(user.getFavouriteSports().get(s.getSport())) >= 0)))
                .filter(s -> s.getDateLimiteInscription().isAfter(ZonedDateTime.now()))
                .filter(s -> !s.getOrganisateur().haveIBlacklistedUser(user))
                .collect(Collectors.toList());
    }

    public void addSession(Session session)
    {
        sessions.add(session);
    }

    public void cleanAllSessions()
    {
        sessions.clear();
    }

    public List<Session> defaultSessionSearch(User user)
    {
        return sessions.stream().filter(s -> (!s.isReserveAuxAmis() || user.getFriends().contains(s.getOrganisateur()))).filter(s -> (s.getDifficulte() == Level.DEBUTANT || (user.getFavouriteSports().containsKey(s.getSport()) && s.getDifficulte().compareTo(user.getFavouriteSports().get(s.getSport())) >= 0)))
                .filter(s -> s.getDateLimiteInscription().isAfter(ZonedDateTime.now()))
                .filter(s -> !s.getOrganisateur().haveIBlacklistedUser(user)).sorted().collect(Collectors.toList());
    }
}
