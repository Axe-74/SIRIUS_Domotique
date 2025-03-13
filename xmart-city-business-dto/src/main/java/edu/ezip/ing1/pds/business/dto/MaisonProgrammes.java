package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class MaisonProgrammes {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Maison_Programmes")
    //private  Set<MaisonProgramme> maison_programmes = new LinkedHashSet<MaisonProgramme>();
    private  Set<MaisonProgramme> maison_programmes = new LinkedHashSet<MaisonProgramme>();

    public Set<MaisonProgramme> getMaison_Programmes() {
        return maison_programmes;
    }

    public void setMaison_Programmes(Set<MaisonProgramme> maison_programmes) {
        this.maison_programmes = maison_programmes;
    }

    public final MaisonProgrammes add (final MaisonProgramme maisonProgramme) {
        maison_programmes.add(maisonProgramme);
        return this;
    }

    @Override
    public String toString() {
        return "Maison_Programmes{" +
                "Maison_Programmes=" + maison_programmes +
                '}';
    }
}
