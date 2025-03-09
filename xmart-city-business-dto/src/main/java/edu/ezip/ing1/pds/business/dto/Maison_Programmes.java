package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedHashSet;
import java.util.Set;

public class Maison_Programmes {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Maison_Programmes")
    private  Set<Maison_Programme> maison_programmes = new LinkedHashSet<Maison_Programme>();

    public Set<Maison_Programme> getMaison_Programmes() {
        return maison_programmes;
    }

    public void setMaison_Programmes(Set<Maison_Programme> maison_programmes) {
        this.maison_programmes = maison_programmes;
    }

    public final Maison_Programmes add (final Maison_Programme Maison_Programme) {
        maison_programmes.add(Maison_Programme);
        return this;
    }

    @Override
    public String toString() {
        return "Maison_Programmes{" +
                "Maison_Programmes=" + maison_programmes +
                '}';
    }
}
