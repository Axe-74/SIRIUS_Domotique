package edu.ezip.ing1.pds;

import javax.swing.*;
import java.util.Observable;

public class Maison_Capteurs extends Observable {
    static TypeCapteur TypeCapteur;
    String NomCapteur;
    static EtatCapteur EtatCapteur;

    public Maison_Capteurs(String nomCapteur, TypeCapteur typeCapteur, EtatCapteur etatCapteur) {
        this.NomCapteur = nomCapteur;
        this.TypeCapteur = typeCapteur;
        this.EtatCapteur = etatCapteur;
    }

    public enum TypeCapteur {
        LUMINOSITE, MOUVEMENT, TEMPERATURE
    };

    public enum EtatCapteur {
        ON, OFF
    };

    public void setNomCapteur(String nomCapteur) {
        NomCapteur = nomCapteur;
    }

    public void setTypeCapteur(TypeCapteur typeCapteur) {
        TypeCapteur = typeCapteur;
    }

    //Retourne le type de capteur
    public static TypeCapteur findByNameTypeCapteur(String name) {
        TypeCapteur result = null;
        for (TypeCapteur typeCapteur : TypeCapteur.values())
            if (typeCapteur.name().equalsIgnoreCase(name)) {
                result = typeCapteur;
                break;
            }
        return result;
    }

    //Retourne l'état du capteur
    public static EtatCapteur findByNameEtatCapteur(JCheckBox cbEtatCapteur) {
        EtatCapteur result = null;
        if (cbEtatCapteur.isSelected()) {
            result = EtatCapteur.ON;
        } else {
            result = EtatCapteur.OFF;
        }
        return result;
    }

    @Override
    public String toString() {
        return "Nom : " + NomCapteur + ", Capteurs de : " + TypeCapteur + ", Etat : " + EtatCapteur;
    }
}

