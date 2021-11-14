package fr.polytech.conception.r1;

import fr.polytech.conception.r1.profile.User;

public class Invitation
{
    private final User organizer;
    private final User guest;
    private final Session session;

    public Invitation(User organizer, User guest, Session session)
    {
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

    public Session getSession()
    {
        return session;
    }

    public enum Status {
        PENDING,
        //ACCEPTED,
        DELINED,
        ARCHIVED,
        BLACKLISTED
    }
}
