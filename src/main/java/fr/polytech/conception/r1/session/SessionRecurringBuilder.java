package fr.polytech.conception.r1.session;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;

import fr.polytech.conception.r1.InvalidSessionDataException;
import fr.polytech.conception.r1.Sport;
import fr.polytech.conception.r1.profile.User;

public class SessionRecurringBuilder
{
    private ZonedDateTime first;
    private Period period;
    private Duration duration;
    private String address;
    private Sport sport;
    private User organizer;

    public SessionRecurringBuilder setFirst(ZonedDateTime first)
    {
        this.first = first;
        return this;
    }

    public SessionRecurringBuilder setPeriod(Period period)
    {
        this.period = period;
        return this;
    }

    public SessionRecurringBuilder setDuration(Duration duration)
    {
        this.duration = duration;
        return this;
    }

    public SessionRecurringBuilder setAddress(String address)
    {
        this.address = address;
        return this;
    }

    public SessionRecurringBuilder setSport(Sport sport)
    {
        this.sport = sport;
        return this;
    }

    public SessionRecurringBuilder setOrganizer(User organizer)
    {
        this.organizer = organizer;
        return this;
    }

    public SessionRecurringGenerator createSessionRecurring() throws InvalidSessionDataException
    {
        return new SessionRecurringGenerator(first, period, duration, address, sport, organizer);
    }
}