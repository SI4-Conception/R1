package fr.polytech.conception.r1;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import fr.polytech.conception.r1.profile.User;

public class SessionRecurring extends Session
{
    private final Map<ZonedDateTime, SessionOneshot> cachedInstances = new HashMap<>();
    private final Period period;
    private final Duration duration;
    private final ZonedDateTime premiere;

    public SessionRecurring(ZonedDateTime premiere, Period period, Duration duration, String adresse, Sport sport, User organisateur) throws InvalidSessionDataException
    {
        super(adresse, sport, organisateur);
        this.premiere = premiere;
        this.period = period;
        this.duration = duration;
    }

    private Stream<ZonedDateTime> getInstanceDates()
    {
        return Stream.iterate(premiere, date -> date.plus(period));
    }

    private SessionOneshot getOneshotInstance(ZonedDateTime date)
    {
        return cachedInstances.computeIfAbsent(date, d ->
        {
            try
            {
                return new SessionOneshot(d, d.plus(duration), getAdresse(), getSport(), getOrganisateur(), false);
            }
            catch (InvalidSessionDataException e)
            {
                throw new RuntimeException("Internal error during session creating", e);
            }
        });
    }

    private SessionOneshot getNthInstance(int n)
    {
        return getOneshotInstance(premiere.plus(period.multipliedBy(n)));
    }

    @Override
    public Stream<? extends SessionOneshot> getOneshots(ZonedDateTime fin)
    {
        return getInstanceDates().takeWhile(date -> date.isBefore(fin)).map(this::getOneshotInstance);
    }
}
