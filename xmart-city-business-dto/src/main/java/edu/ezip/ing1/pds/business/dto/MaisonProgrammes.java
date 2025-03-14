package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class MaisonProgrammes {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonProgrammes")
    //private  Set<MaisonProgramme> maison_programmes = new LinkedHashSet<MaisonProgramme>();
    private  Set<MaisonProgramme> maisonprogrammes = new LinkedHashSet<MaisonProgramme>();

    public Set<MaisonProgramme> getMaisonProgrammes() {
        return maisonprogrammes;
    }

    public void setMaisonProgramme(Set<MaisonProgramme> maisonProgramme) {
        this.maisonprogrammes = maisonProgramme;
    }

    public final MaisonProgrammes add (final MaisonProgramme maison_Programme) {
        maisonprogrammes.add(maison_Programme);
        return this;
    }

    @Override
    public String toString() {
        return "Maison_Programmes{" +
                "Maison_Programmes=" + maisonprogrammes +
                '}';
    }
}
