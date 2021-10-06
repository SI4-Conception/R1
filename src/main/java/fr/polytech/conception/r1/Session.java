package fr.polytech.conception.r1;

import java.time.ZonedDateTime;
import java.util.List;

public class Session
{
    private ZonedDateTime debut;
    private ZonedDateTime fin;
    private ZonedDateTime dateLimiteInscription;
    private String adresse;
    private boolean reserveAuxAmis;
    private int minParticipants;
    private int maxParticipants;
    private Niveau difficulte;
    private boolean estAnnulee;
    private Sport sport;
    private Utilisateur organisateur;
    private List<Utilisateur> participants;

    public Session() {
        //
    }
}
