package edu.ezip.ing1.pds;

import java.util.Observable;

public class Maison_Automatisation extends Observable {
    String NomAutomatisation;
    static TypeCapteurs TypeCapteurs;
    static TypeProgramme TypeProgramme;


    public Maison_Automatisation(String nomautomatisation, TypeCapteurs typecapteurs, TypeProgramme typeprogramme) {
        NomAutomatisation = nomautomatisation ;
        TypeCapteurs = typecapteurs;
        TypeProgramme = typeprogramme;
    }

    public enum TypeCapteurs {
        CAPTEURS_1, CAPTEURS_2, CAPTEURS_3, CAPTEURS_4
    };

    public enum TypeProgramme {
        PROGRAMME_1, PROGRAMME_2,PROGRAMME_3
    };

    public void setNomProgramme(String nomautomatisation) {
        NomAutomatisation = nomautomatisation;
    };


    //Retourne le type de piece
    public static TypeCapteurs findByNameTypeCapteurs(String name) {
        TypeCapteurs result = null;
        for (TypeCapteurs typePiece : TypeCapteurs.values())
            if (typePiece.name().equalsIgnoreCase(name)) {
                result = typePiece;
                break;
            }
        return result;
    }

    //Retourne le type de chauffage
    public static TypeProgramme findByNameTypeProgramme(String name) {
        TypeProgramme result = null;
        for (TypeProgramme typeChauffage : TypeProgramme.values())
            if (typeChauffage.name().equalsIgnoreCase(name)) {
                result = typeChauffage;
                break;
            }
        return result;
    }
}