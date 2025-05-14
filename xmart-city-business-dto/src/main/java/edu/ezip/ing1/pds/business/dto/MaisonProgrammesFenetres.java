package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MaisonProgrammesFenetres {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonProgrammesFenetres")
    private List<MaisonProgrammeFenetre> maisonprogrammesfenetre = new ArrayList<>();

    public List<MaisonProgrammeFenetre> getMaisonProgrammesFenetres() {
        return maisonprogrammesfenetre;
    }

    public void setMaisonProgrammeFenetre(List<MaisonProgrammeFenetre> maisonProgrammeFenetre) {
        this.maisonprogrammesfenetre = maisonProgrammeFenetre;
    }

    public final MaisonProgrammesFenetres add(final MaisonProgrammeFenetre maisonProgrammeFenetre) {
        maisonprogrammesfenetre.add(maisonProgrammeFenetre);
        return this;
    }

    @Override
    public String toString() {
        return "MaisonProgrammesFenetres{" +
                "MaisonProgrammesFenetres=" + maisonprogrammesfenetre +
                '}';
    }
}
