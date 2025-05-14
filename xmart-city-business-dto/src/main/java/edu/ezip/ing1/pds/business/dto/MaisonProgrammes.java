package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MaisonProgrammes {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonProgrammes")
    private List<MaisonProgramme> maisonprogrammes = new ArrayList<>();

    public List<MaisonProgramme> getMaisonProgrammes() {
        return maisonprogrammes;
    }

    public void setMaisonProgramme(List<MaisonProgramme> maisonProgramme) {
        this.maisonprogrammes = maisonProgramme;
    }

    public final MaisonProgrammes add(final MaisonProgramme maisonProgramme) {
        maisonprogrammes.add(maisonProgramme);
        return this;
    }

    @Override
    public String toString() {
        return "MaisonProgrammes{" +
                "MaisonProgrammes=" + maisonprogrammes +
                '}';
    }
}
