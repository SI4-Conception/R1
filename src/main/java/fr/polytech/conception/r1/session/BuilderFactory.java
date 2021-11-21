/*
package fr.polytech.conception.r1.session;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fr.polytech.conception.r1.InvalidSessionDataException;
import fr.polytech.conception.r1.Level;
import fr.polytech.conception.r1.Sport;
import fr.polytech.conception.r1.profile.User;

public class BuilderFactory // implements IBuilderFactory
{
    public static final int TYPE_SESSION_BUILDER_ONE_SHOT = 1;
    public static final int TYPE_SESSION_BUILDER_RECURRING = 2;

    // @Override
    public Builder getBuilder(int typeBuilder)
    {
        Builder builder = null;

        switch (typeBuilder) {
            case TYPE_SESSION_BUILDER_ONE_SHOT:
                builder = new SessionOneshotBuilder();
                break;
            case TYPE_SESSION_BUILDER_RECURRING:
                builder = new SessionRecurringBuilder();
                break;
            default:
                throw new IllegalArgumentException("Type de session inconnu");
        }

        return builder;
    }

    private abstract class Builder
    {

    }

    private class SessionOneshotBuilder extends Builder
    {
        private String adresse;

        private boolean reserveAuxAmis = false;
        private int minParticipants = 1;
        /**
         * Set maxParticipants to zero to specify infinite number of participants
         *//*
        private int maxParticipants = 0;
        private Level difficulte = Level.DEBUTANT;
        private Sport sport;
        private User organisateur;

        private boolean isSponsored = false;
        private ZonedDateTime debut;
        private ZonedDateTime fin;
        private ZonedDateTime dateLimiteInscription;
        private boolean estAnnulee = false;
        private List<User> participants = new LinkedList<>();
        private double sponsoredSessionPrice = 0d;

        Session build() throws InvalidSessionDataException
        {
            return new SessionOneshot(debut, fin, adresse, sport, organisateur, isSponsored);
        }
    }

    private class SessionRecurringBuilder extends Builder
    {
        private String adresse;

        private boolean reserveAuxAmis = false;
        private int minParticipants = 1;
        /**
         * Set maxParticipants to zero to specify infinite number of participants
         *//*
        private int maxParticipants = 0;
        private Level difficulte = Level.DEBUTANT;
        private Sport sport;
        private User organisateur;

        private final Map<ZonedDateTime, SessionOneshot> cachedInstances = new HashMap<>();
        private Period period;
        private Duration duration;
        private ZonedDateTime premiere;

        Session build() throws InvalidSessionDataException
        {
            return new SessionRecurring(premiere, period, duration, adresse, sport, organisateur);
        }
    }
}
*/