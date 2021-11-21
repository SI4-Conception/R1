package fr.polytech.conception.r1.session;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;

import fr.polytech.conception.r1.InvalidSessionDataException;
import fr.polytech.conception.r1.Sport;
import fr.polytech.conception.r1.profile.User;

public class SessionRecurringBuilder
{
    private final String adresse;

    private final Sport sport;
    private final User organisateur;

    private final Period period;
    private final Duration duration;
    private final ZonedDateTime premiere;

    SessionRecurringBuilder(ZonedDateTime premiere, Period period, Duration duration, String adresse, Sport sport, User organisateur)
    {
        this.organisateur = organisateur;
        this.adresse = adresse;
        this.sport = sport;
        this.premiere = premiere;
        this.period = period;
        this.duration = duration;
    }

    SessionRecurring build() throws InvalidSessionDataException
    {
        return new SessionRecurring(premiere, period, duration, adresse, sport, organisateur);
    }
}
