package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class MaisonAutomatisation_Para_Fenetres {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonAutomatisation_Para_Fenetres")
    private Set<MaisonAutomatisation_Para_Fenetre> maisonAutomatisation_para_fenetres = new LinkedHashSet<MaisonAutomatisation_Para_Fenetre>();

    public Set<MaisonAutomatisation_Para_Fenetre> getMaisonAutomatisation_para_fentres() {
        return maisonAutomatisation_para_fenetres;
    }

    public void setMaisonAutomatisation_Para_Fenetre(Set<MaisonAutomatisation_Para_Fenetre > maisonAutomatisation_para_fenetres) {
        this.maisonAutomatisation_para_fenetres = maisonAutomatisation_para_fenetres;
    }

    public final MaisonAutomatisation_Para_Fenetres add (final MaisonAutomatisation_Para_Fenetre Maison_Automatisation_Para_Fenetre) {
        maisonAutomatisation_para_fenetres.add(Maison_Automatisation_Para_Fenetre);
        return this;
    }

    @Override
    public String toString() {
        return "MaisonAutomatisation_Para_Fenetres{" +
                "MaisonAutomatisation_Para_Fenetres=" + maisonAutomatisation_para_fenetres +
                '}';
    }
}
