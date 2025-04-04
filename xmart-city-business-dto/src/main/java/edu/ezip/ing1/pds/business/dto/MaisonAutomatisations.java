package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class MaisonAutomatisations {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Maison_automatisations")
    private  Set<MaisonAutomatisation> maisonAutomatisations = new LinkedHashSet<MaisonAutomatisation>();

    public Set<MaisonAutomatisation> getMaisonAutomatisations() {
        return maisonAutomatisations;
    }

    public void setMaisonAutomatisation(Set<MaisonAutomatisation> maison_automatisations) {
        this.maisonAutomatisations = maison_automatisations;
    }

    public final MaisonAutomatisations add (final MaisonAutomatisation Maison_Automatisation) {
        maisonAutomatisations.add(Maison_Automatisation);
        return this;
    }

    @Override
    public String toString() {
        return "Maison_Automatisations{" +
                "Maison_Programmes=" + maisonAutomatisations +
                '}';
    }
}
