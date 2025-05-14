package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MaisonCapteurs {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("capteurs")
    private List<MaisonCapteur> capteurs = new ArrayList<MaisonCapteur>();

    public List<MaisonCapteur> getCapteurs() {
        return capteurs;
    }

    public void setCapteurs(List<MaisonCapteur> capteurs) {
        this.capteurs = capteurs;
    }

    public final MaisonCapteurs add (final MaisonCapteur capteur) {
        capteurs.add(capteur);
        return this;
    }

    @Override
    public String toString() {
        return "Capteurs{" +
                "capteurs=" + capteurs +
                '}';
    }
}
