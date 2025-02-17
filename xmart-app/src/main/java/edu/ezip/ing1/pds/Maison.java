package edu.ezip.ing1.pds;

import java.util.Observable;

public class Maison extends Observable {
    static TypePiece TypePiece;
    String NomProgramme;
    static TypeChauffage TypeChauffage;
    Integer TemperaturePiece;
    Integer HeureDebut;
    Integer HeureFin;
    static JoursSemaine JoursSemaine;


    public Maison(String nomProgramme, TypePiece typePiece, TypeChauffage typeChauffage, int temperaturePiece, JoursSemaine joursSemaine, int heureDebut, int heureFin) {
        NomProgramme = nomProgramme;
        TypePiece = typePiece;
        TypeChauffage = typeChauffage;
        TemperaturePiece = temperaturePiece;
        JoursSemaine = joursSemaine;
        HeureDebut = heureDebut;
        HeureFin = heureFin;
    }

    public enum TypePiece {
        ENTREE, CUISINE, SALON, SALLE_DE_BAIN, CHAMBRE_PARENTS, CHAMBRE_ENFANTS_1, CHAMBRE_ENFANTS_2, CHAMBRE_ENFANTS_3, CHAMBRE_ENFANTS_4
    };

    public enum capteurs {
        CAPTEURS1, CAPTEURS2, CAPTEURS3
    };

    public enum TypeChauffage {
        RADIATEUR, SECHE_SERVIETTE
    };

    public enum JoursSemaine {
        LUNDI, MARDI, MERCREDI, JEUDI, VENDREDI, SAMEDI, DIMANCHE
    };

    public void setNomProgramme(String nomProgramme) {
        NomProgramme = nomProgramme;
    }

    public void setPiece(TypePiece typePiece) {
        TypePiece = typePiece;
    }

    public void setChauffage(TypeChauffage typeChauffage) {
        TypeChauffage = typeChauffage;
    }

    public void setTemperaturePiece(int temperaturePiece) {
        this.TemperaturePiece = temperaturePiece;
    }

    public void setHeureDebut(int heureDebut) {
        this.HeureDebut = heureDebut;
    }

    public void setHeureFin(int heureFin) {
        this.HeureFin = heureFin;
    }

    public void setJours(JoursSemaine joursSemaine) {
        JoursSemaine = joursSemaine;
    }

    //Retourne le type de piece
    public static TypePiece findByNameTypePiece(String name) {
        TypePiece result = null;
        for (TypePiece typePiece : TypePiece.values())
            if (typePiece.name().equalsIgnoreCase(name)) {
                result = typePiece;
                break;
            }
        return result;
    }

    //Retourne le type de chauffage
    public static TypeChauffage findByNameTypeChauffage(String name) {
        TypeChauffage result = null;
        for (TypeChauffage typeChauffage : TypeChauffage.values())
            if (typeChauffage.name().equalsIgnoreCase(name)) {
                result = typeChauffage;
                break;
            }
        return result;
    }

    //Retourne le jour de la semaine
    public static JoursSemaine findByNameJoursSemaine(String name) {
        JoursSemaine result = null;
        for (JoursSemaine joursSemaine : JoursSemaine.values())
            if (joursSemaine.name().equalsIgnoreCase(name)) {
                result = joursSemaine;
                break;
            }
        return result;
    }

    @Override
    public String toString() {
        return "Nom : " + NomProgramme + ", Piece : " + TypePiece + ", Chauffage : " + TypeChauffage + ", Température : " + TemperaturePiece + "°C, Plage horaire : " + HeureDebut + "h à " + HeureFin + "h, Jour : " + JoursSemaine;
    }
}

