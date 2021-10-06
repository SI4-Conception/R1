package fr.polytech.conception.r1;

import java.time.LocalDateTime;

public class Session
{
    private LocalDateTime debut;
    private LocalDateTime fin;
    private LocalDateTime dateLimiteInscription;
    private String adresse;
    private boolean reserveAuxAmis;
    private int minParticipants;
    private int maxParticipants;
    private Niveau difficulte;
    private boolean estAnnulee;

    public Session() {
        //
    }
}
