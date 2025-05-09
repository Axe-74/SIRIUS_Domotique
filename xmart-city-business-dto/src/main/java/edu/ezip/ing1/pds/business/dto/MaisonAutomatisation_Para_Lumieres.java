package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class MaisonAutomatisation_Para_Lumieres {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonAutomatisation_Para_Lumieres")
    private Set<MaisonAutomatisation_Para_Lumiere> maisonAutomatisation_para_lumieres = new LinkedHashSet<MaisonAutomatisation_Para_Lumiere>();

    public Set<MaisonAutomatisation_Para_Lumiere> getMaisonAutomatisation_para_lumieres() {
        return maisonAutomatisation_para_lumieres;
    }

    public void setMaisonAutomatisation_Para_Lumiere(Set<MaisonAutomatisation_Para_Lumiere> maisonAutomatisation_para_lumieres) {
        this.maisonAutomatisation_para_lumieres = maisonAutomatisation_para_lumieres;
    }

    public final MaisonAutomatisation_Para_Lumieres add (final MaisonAutomatisation_Para_Lumiere Maison_Automatisation_Para_Lumiere) {
        maisonAutomatisation_para_lumieres.add(Maison_Automatisation_Para_Lumiere);
        return this;
    }

    @Override
    public String toString() {
        return "MaisonAutomatisation_Para_Lumieres{" +
                "MaisonAutomatisation_Para_Lumieres=" + maisonAutomatisation_para_lumieres +
                '}';
    }
}
