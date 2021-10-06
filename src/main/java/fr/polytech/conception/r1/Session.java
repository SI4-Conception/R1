package fr.polytech.conception.r1;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

public class Session
{
    private ZonedDateTime debut;
    private ZonedDateTime fin;
    private ZonedDateTime dateLimiteInscription;
    private String adresse;
    private boolean reserveAuxAmis = false;
    private int minParticipants = 1;
    /**
     * Set maxParticipants to zero to specify infinite number of participants
     */
    private int maxParticipants = 0;
    private Niveau difficulte = Niveau.INTERMEDIAIRE;
    private boolean estAnnulee = false;
    private Sport sport;
    private Utilisateur organisateur;
    private List<Utilisateur> participants = new LinkedList<>();

    public Session(ZonedDateTime debut, ZonedDateTime fin, String adresse, Sport sport) {
        // TODO: verify here than passed params are corrects
        this.debut = debut;
        this.fin = fin;
        this.adresse = adresse;
        this.sport = sport;
    }

    public ZonedDateTime getDebut()
    {
        return debut;
    }

    public ZonedDateTime getFin()
    {
        return fin;
    }

    public ZonedDateTime getDateLimiteInscription()
    {
        return dateLimiteInscription;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public boolean isReserveAuxAmis()
    {
        return reserveAuxAmis;
    }

    public int getMinParticipants()
    {
        return minParticipants;
    }

    public int getMaxParticipants()
    {
        return maxParticipants;
    }

    public Niveau getDifficulte()
    {
        return difficulte;
    }

    public boolean isEstAnnulee()
    {
        return estAnnulee;
    }

    public Sport getSport()
    {
        return sport;
    }

    public Utilisateur getOrganisateur()
    {
        return organisateur;
    }

    public List<Utilisateur> getParticipants()
    {
        return participants;
    }
}
