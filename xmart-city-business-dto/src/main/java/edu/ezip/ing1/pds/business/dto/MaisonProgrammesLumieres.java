package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class MaisonProgrammesLumieres {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonProgrammesLumieres")
    private Set<MaisonProgrammeLumiere> maisonprogrammeLumieres = new LinkedHashSet<MaisonProgrammeLumiere>();

    public Set<MaisonProgrammeLumiere> getMaisonProgrammesLumieres() {
        return maisonprogrammeLumieres;
    }

    public void setMaisonProgrammeLumiere(Set<MaisonProgrammeLumiere> maisonProgrammeLumiere) {
        this.maisonprogrammeLumieres = maisonProgrammeLumiere;
    }

    public final MaisonProgrammesLumieres add (final MaisonProgrammeLumiere maison_ProgrammeLumiere) {
        maisonprogrammeLumieres.add(maison_ProgrammeLumiere);
        return this;
    }

    @Override
    public String toString() {
        return "MaisonProgrammesLumieres{" +
                "MaisonProgrammesLumieres=" + maisonprogrammeLumieres +
                '}';
    }
}

