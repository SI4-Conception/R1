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
    private String adresse;
    private Sport sport;
    private User organisateur;

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

    public SessionRecurringBuilder setAdresse(String adresse)
    {
        this.adresse = adresse;
        return this;
    }

    public SessionRecurringBuilder setSport(Sport sport)
    {
        this.sport = sport;
        return this;
    }

    public SessionRecurringBuilder setOrganisateur(User organisateur)
    {
        this.organisateur = organisateur;
        return this;
    }

    public SessionRecurring createSessionRecurring() throws InvalidSessionDataException
    {
        return new SessionRecurring(first, period, duration, adresse, sport, organisateur);
    }
}