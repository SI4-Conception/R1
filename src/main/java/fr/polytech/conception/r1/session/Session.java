package fr.polytech.conception.r1.session;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

import fr.polytech.conception.r1.InvalidSessionDataException;
import fr.polytech.conception.r1.Level;
import fr.polytech.conception.r1.Sport;
import fr.polytech.conception.r1.profile.User;

public abstract class Session
{
    private String adresse;

    protected boolean reserveAuxAmis = false;
    private int minParticipants = 1;
    /**
     * Set maxParticipants to zero to specify infinite number of participants
     */
    protected int maxParticipants = 0;
    private Level difficulte = Level.DEBUTANT;
    private Sport sport;
    protected User organisateur;

    public Session(String adresse, Sport sport, User organisateur) throws InvalidSessionDataException
    {
        this.organisateur = organisateur;
        this.adresse = adresse;
        this.sport = sport;
        this.organisateur.getListSessionsOrganisees().add(this);
    }

    public String getAdresse()
    {
        return adresse;
    }

    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    public boolean isReserveAuxAmis()
    {
        return reserveAuxAmis;
    }

    public void setReserveAuxAmis(boolean reserveAuxAmis)
    {
        this.reserveAuxAmis = reserveAuxAmis;
    }

    public int getMinParticipants()
    {
        return minParticipants;
    }

    public void setMinParticipants(int minParticipants) throws InvalidSessionDataException
    {
        checkParticipantsBounds(minParticipants, maxParticipants);
        this.minParticipants = minParticipants;
    }

    public int getMaxParticipants()
    {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) throws InvalidSessionDataException
    {
        checkParticipantsBounds(minParticipants, maxParticipants);
        this.maxParticipants = maxParticipants;
    }

    public Level getDifficulte()
    {
        return difficulte;
    }

    public void setDifficulte(Level difficulte)
    {
        this.difficulte = difficulte;
    }

    public Sport getSport()
    {
        return sport;
    }

    public void setSport(Sport sport)
    {
        this.sport = sport;
    }

    public User getOrganisateur()
    {
        return organisateur;
    }

    void checkDatesOrder(ZonedDateTime fin, ZonedDateTime debut) throws InvalidSessionDataException
    {
        if (fin.isBefore(debut))
        {
            throw new InvalidSessionDataException("La date de fin est avant le debut.");
        }
    }

    private void checkParticipantsBounds(int minParticipants, int maxParticipants) throws InvalidSessionDataException
    {
        if (minParticipants > maxParticipants || minParticipants < 0 || maxParticipants <= 0)
        {
            throw new InvalidSessionDataException("Le nombre min de participants doit etre <= au nombre max, et leur nombres doivent etre positifs");
        }
    }

    public abstract Stream<? extends SessionOneshot> getOneshots(ZonedDateTime fin);
}
