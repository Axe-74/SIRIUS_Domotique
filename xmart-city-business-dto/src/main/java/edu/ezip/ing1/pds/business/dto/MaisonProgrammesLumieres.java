package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MaisonProgrammesLumieres {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonProgrammesLumieres")
    private List<MaisonProgrammeLumiere> maisonprogrammeLumieres = new ArrayList<>();

    public List<MaisonProgrammeLumiere> getMaisonProgrammesLumieres() {
        return maisonprogrammeLumieres;
    }

    public void setMaisonProgrammeLumiere(List<MaisonProgrammeLumiere> maisonProgrammeLumiere) {
        this.maisonprogrammeLumieres = maisonProgrammeLumiere;
    }

    public final MaisonProgrammesLumieres add(final MaisonProgrammeLumiere maisonProgrammeLumiere) {
        maisonprogrammeLumieres.add(maisonProgrammeLumiere);
        return this;
    }

    @Override
    public String toString() {
        return "MaisonProgrammesLumieres{" +
                "MaisonProgrammesLumieres=" + maisonprogrammeLumieres +
                '}';
    }
}
