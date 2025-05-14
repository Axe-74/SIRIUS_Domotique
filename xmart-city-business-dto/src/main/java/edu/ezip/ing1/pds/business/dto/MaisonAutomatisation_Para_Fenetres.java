package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MaisonAutomatisation_Para_Fenetres {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonAutomatisation_Para_Fenetres")
    private List<MaisonAutomatisation_Para_Fenetre> maisonAutomatisation_para_fenetres = new ArrayList<>();

    public List<MaisonAutomatisation_Para_Fenetre> getMaisonAutomatisation_para_fenetres() {
        return maisonAutomatisation_para_fenetres;
    }

    public void setMaisonAutomatisation_Para_Fenetre(List<MaisonAutomatisation_Para_Fenetre> maisonAutomatisation_para_fenetres) {
        this.maisonAutomatisation_para_fenetres = maisonAutomatisation_para_fenetres;
    }

    public final MaisonAutomatisation_Para_Fenetres add(final MaisonAutomatisation_Para_Fenetre fenetre) {
        maisonAutomatisation_para_fenetres.add(fenetre);
        return this;
    }

    @Override
    public String toString() {
        return "MaisonAutomatisation_Para_Fenetres{" +
                "MaisonAutomatisation_Para_Fenetres=" + maisonAutomatisation_para_fenetres +
                '}';
    }
}
