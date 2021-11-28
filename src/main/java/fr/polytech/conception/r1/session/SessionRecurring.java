package fr.polytech.conception.r1.session;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

import fr.polytech.conception.r1.InvalidSessionDataException;
import fr.polytech.conception.r1.Level;
import fr.polytech.conception.r1.Sport;
import fr.polytech.conception.r1.profile.User;

public class SessionRecurring extends Session
{
    private final Map<ZonedDateTime, SessionRecurringInstance> cachedInstances = new TreeMap<>(ZonedDateTime::compareTo);
    private final Period period;
    private final Duration duration;
    private final ZonedDateTime first;
    private SessionRecurringInstance savedParamsForSessionRecurring = null;

    public SessionRecurring(ZonedDateTime first, Period period, Duration duration, String adresse, Sport sport, User organisateur) throws InvalidSessionDataException
    {
        super(adresse, sport, organisateur);
        checkDataRecurringSession(first, period, duration, adresse, sport, Duration.ZERO, organisateur);
        this.first = first;
        this.period = period;
        this.duration = duration;
        initiateSavedParamsForSessionRecurring(duration, adresse, sport, Duration.ZERO, organisateur);
    }

    public SessionRecurring(ZonedDateTime first, Period period, Duration duration, String adresse, Sport sport, Duration minRegistrationTime, User organisateur) throws InvalidSessionDataException
    {
        super(adresse, sport, organisateur);
        checkDataRecurringSession(first, period, duration, adresse, sport, minRegistrationTime, organisateur);
        this.first = first;
        this.period = period;
        this.duration = duration;
        this.minRegistrationTime = minRegistrationTime;
        initiateSavedParamsForSessionRecurring(duration, adresse, sport, minRegistrationTime, organisateur);
    }

    private void checkDataRecurringSession(ZonedDateTime first, Period period, Duration duration, String adresse, Sport sport, Duration minRegistrationTime, User organisateur) throws InvalidSessionDataException
    {
        if(first.isBefore(ZonedDateTime.now()))
        {
            throw new InvalidSessionDataException("Cannot create session from the past");
        }
    }

    private void initiateSavedParamsForSessionRecurring(Duration duration, String adresse, Sport sport, Duration minRegistrationTime, User organisateur)
    {
        ZonedDateTime beginDate = ZonedDateTime.parse("3000-01-01T12:00:00.000+01:00[Europe/Paris]");
        try
        {
            savedParamsForSessionRecurring = new SessionRecurringInstance(beginDate, beginDate.plus(duration), adresse, sport, organisateur, false, minRegistrationTime, this);
        }
        catch (InvalidSessionDataException e)
        {
            throw new RuntimeException("Internal error during saving session creating", e);
        }
    }

    private Stream<ZonedDateTime> getInstanceDates()
    {
        return Stream.iterate(first, date -> date.plus(period));
    }

    private SessionRecurringInstance getOneshotInstance(ZonedDateTime date)
    {
        if(!cachedInstances.containsKey(date))
        {
            try
            {
                SessionRecurringInstance sessionRecurringInstance = new SessionRecurringInstance(date, date.plus(duration), getAddress(), getSport(), getOrganizer(), false, minRegistrationTime, this);
                sessionRecurringInstance.setMinParticipants(savedParamsForSessionRecurring.getMinParticipants());
                sessionRecurringInstance.setMaxParticipants(savedParamsForSessionRecurring.getMaxParticipants());
                sessionRecurringInstance.setAddress(savedParamsForSessionRecurring.getAddress());
                sessionRecurringInstance.setFriendsOnly(savedParamsForSessionRecurring.friendsOnly);
                sessionRecurringInstance.setDifficulty(savedParamsForSessionRecurring.getDifficulty());
                sessionRecurringInstance.setSport(savedParamsForSessionRecurring.getSport());
                cachedInstances.put(date, sessionRecurringInstance);
                return sessionRecurringInstance;
            }
            catch (InvalidSessionDataException e)
            {
                throw new RuntimeException("Internal error during session creating", e);
            }
        }
        return cachedInstances.get(date);
    }

    private SessionRecurringInstance getNthInstance(int n)
    {
        return getOneshotInstance(first.plus(period.multipliedBy(n)));
    }

    @Override
    public Stream<SessionRecurringInstance> getOneshots(ZonedDateTime end)
    {
        return getInstanceDates().takeWhile(date -> date.isBefore(end)).map(this::getOneshotInstance);
    }

    Stream<Map.Entry<ZonedDateTime, SessionRecurringInstance>> getOneshotsAfter(ZonedDateTime after)
    {
        return cachedInstances.entrySet().stream().filter(s -> s.getKey().isAfter(after));
    }

    private Stream<? extends Session> getSessionsToChange()
    {
        return getOneshotsAfter(ZonedDateTime.now()).map(Map.Entry::getValue);
        //return Stream.concat(Stream.of(this), getOneshotsAfter(ZonedDateTime.now()).map(Map.Entry::getValue));
    }

    public interface SessionModification
    {
        void apply(Session s) throws Exception;
    }

    private void applyToRemainingSessions(SessionModification modification)
    {
        try
        {
            modification.apply(savedParamsForSessionRecurring);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Internal error during saving session modifying", e);
        }
        getSessionsToChange().forEach(s ->
        {
            try
            {
                modification.apply(s);
            }
            catch (Exception e)
            {
                throw new RuntimeException("Internal error during session modifying", e);
            }
        });
    }

    @Override
    public void setAddress(String address)
    {
        applyToRemainingSessions(s -> s.setAddress(address));
    }

    @Override
    public void setFriendsOnly(boolean friendsOnly)
    {
        applyToRemainingSessions(s -> s.setFriendsOnly(friendsOnly));
    }

    @Override
    public void setMinParticipants(int minParticipants)
    {
        applyToRemainingSessions(s -> s.setMinParticipants(minParticipants));
    }

    @Override
    public void setMaxParticipants(int maxParticipants)
    {
        applyToRemainingSessions(s -> s.setMaxParticipants(maxParticipants));
    }

    @Override
    public void setDifficulty(Level difficulty)
    {
        applyToRemainingSessions(s -> s.setDifficulty(difficulty));
    }

    @Override
    public void setSport(Sport sport)
    {
        applyToRemainingSessions(s -> s.setSport(sport));
    }

    @Override
    public void setMinRegistrationTime(Duration d)
    {
        applyToRemainingSessions(s -> s.setMinRegistrationTime(d));
    }
}
