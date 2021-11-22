package fr.polytech.conception.r1.session;

import java.time.ZonedDateTime;

import fr.polytech.conception.r1.InvalidSessionDataException;
import fr.polytech.conception.r1.Sport;
import fr.polytech.conception.r1.profile.User;

public class SessionOneshotBuilder
{
    private final String adresse;
    private final Sport sport;
    private final User organisateur;

    private boolean isSponsored = false;
    private final ZonedDateTime start;
    private final ZonedDateTime fin;

    public SessionOneshotBuilder(ZonedDateTime start, ZonedDateTime fin, String adresse, Sport sport, User organisateur) {
        this.organisateur = organisateur;
        this.adresse = adresse;
        this.sport = sport;
        this.start = start;
        this.fin = fin;
    }

    public SessionOneshotBuilder withIsSponsored(boolean isSponsored)
    {
        this.isSponsored = isSponsored;
        return this;
    }

    SessionOneshot build() throws InvalidSessionDataException
    {
        return new SessionOneshot(start, fin, adresse, sport, organisateur, isSponsored);
    }
}
