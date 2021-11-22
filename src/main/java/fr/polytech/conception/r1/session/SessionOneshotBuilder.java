package fr.polytech.conception.r1.session;

import java.time.ZonedDateTime;

import fr.polytech.conception.r1.InvalidSessionDataException;
import fr.polytech.conception.r1.Sport;
import fr.polytech.conception.r1.profile.User;

public class SessionOneshotBuilder
{
    private ZonedDateTime start;
    private ZonedDateTime end;
    private String address;
    private Sport sport;
    private User organizer;
    private boolean isSponsored;

    public SessionOneshotBuilder setStart(ZonedDateTime start)
    {
        this.start = start;
        return this;
    }

    public SessionOneshotBuilder setEnd(ZonedDateTime end)
    {
        this.end = end;
        return this;
    }

    public SessionOneshotBuilder setAddress(String address)
    {
        this.address = address;
        return this;
    }

    public SessionOneshotBuilder setSport(Sport sport)
    {
        this.sport = sport;
        return this;
    }

    public SessionOneshotBuilder setOrganizer(User organizer)
    {
        this.organizer = organizer;
        return this;
    }

    public SessionOneshotBuilder setIsSponsored(boolean isSponsored)
    {
        this.isSponsored = isSponsored;
        return this;
    }

    public SessionOneshot createSessionOneshot() throws InvalidSessionDataException
    {
        return new SessionOneshot(start, end, address, sport, organizer, isSponsored);
    }
}