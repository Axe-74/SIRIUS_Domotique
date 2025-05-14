package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MaisonAutomatisations {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Maison_automatisations")
    private List<MaisonAutomatisation> maisonAutomatisations = new ArrayList<>();

    public List<MaisonAutomatisation> getMaisonAutomatisations() {
        return maisonAutomatisations;
    }

    public void setMaisonAutomatisation(List<MaisonAutomatisation> maisonAutomatisations) {
        this.maisonAutomatisations = maisonAutomatisations;
    }

    public final MaisonAutomatisations add(final MaisonAutomatisation maisonAutomatisation) {
        maisonAutomatisations.add(maisonAutomatisation);
        return this;
    }

    @Override
    public String toString() {
        return "Maison_Automatisations{" +
                "MaisonAutomatisations=" + maisonAutomatisations +
                '}';
    }
}
