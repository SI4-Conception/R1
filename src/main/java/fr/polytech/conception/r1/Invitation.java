package fr.polytech.conception.r1;

import java.time.ZonedDateTime;

import fr.polytech.conception.r1.profile.User;
import fr.polytech.conception.r1.session.SessionOneshot;

public class Invitation
{
    private final User organizer;
    private final User guest;
    private final SessionOneshot session;

    public Invitation(User organizer, User guest, SessionOneshot session)
    {
        if (session.getDebut().isBefore(ZonedDateTime.now()))
        {
            throw new IllegalArgumentException("Cannot create an invitation of a passed session");
        }
        this.organizer = organizer;
        this.guest = guest;
        this.session = session;
    }

    public User getOrganizer()
    {
        return organizer;
    }

    public User getGuest()
    {
        return guest;
    }

    public SessionOneshot getSession()
    {
        return session;
    }

    public enum Status {
        PENDING,
        //ACCEPTED,
        DECLINED,
        ARCHIVED,
        BLACKLISTED
    }
}
