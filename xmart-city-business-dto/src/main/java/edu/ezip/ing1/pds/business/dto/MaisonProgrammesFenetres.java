package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class MaisonProgrammesFenetres {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonProgrammesFenetres")
    private  Set<MaisonProgrammeFenetre> maisonprogrammesfenetre = new LinkedHashSet<MaisonProgrammeFenetre>();

    public Set<MaisonProgrammeFenetre> getMaisonProgrammesFenetres() {
        return maisonprogrammesfenetre;
    }

    public void setMaisonProgrammeFenetre(Set<MaisonProgrammeFenetre> maisonProgrammeFenetre) {
        this.maisonprogrammesfenetre = maisonProgrammeFenetre;
    }

    public final MaisonProgrammesFenetres add (final MaisonProgrammeFenetre maison_ProgrammeFenetre) {
        maisonprogrammesfenetre.add(maison_ProgrammeFenetre);
        return this;
    }

    @Override
    public String toString() {
        return "Maison_ProgrammesFenetres{" +
                "Maison_ProgrammesFenetres=" + maisonprogrammesfenetre +
                '}';
    }
}
