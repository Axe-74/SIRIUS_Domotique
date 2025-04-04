package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class MaisonCapteurs {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("capteurs")
    private Set<MaisonCapteur> capteurs = new LinkedHashSet<MaisonCapteur>();

    public Set<MaisonCapteur> getCapteurs() {
        return capteurs;
    }

    public void setCapteurs(Set<MaisonCapteur> capteurs) {
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
