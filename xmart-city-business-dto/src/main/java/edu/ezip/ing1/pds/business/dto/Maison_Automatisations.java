package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedHashSet;
import java.util.Set;

public class Maison_Automatisations {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Maison_Automatisations")
    private  Set<Maison_Automatisation> maison_automatisations = new LinkedHashSet<Maison_Automatisation>();

    public Set<Maison_Automatisation> getMaison_Automatisations() {
        return maison_automatisations;
    }

    public void setMaison_Automatisations(Set<Maison_Automatisation> maison_automatisations) {
        this.maison_automatisations = maison_automatisations;
    }

    public final Maison_Automatisations add (final Maison_Automatisation Maison_Automatisation) {
        maison_automatisations.add(Maison_Automatisation);
        return this;
    }

    @Override
    public String toString() {
        return "Maison_Automatisations{" +
                "Maison_Programmes=" + maison_automatisations +
                '}';
    }
}
