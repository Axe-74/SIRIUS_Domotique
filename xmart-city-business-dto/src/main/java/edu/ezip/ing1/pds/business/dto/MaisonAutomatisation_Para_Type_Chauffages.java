package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MaisonAutomatisation_Para_Type_Chauffages {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonAutomatisation_Para_Type_Chauffages")
    private List<MaisonAutomatisation_Para_Type_Chauffage> maisonAutomatisation_para_type_chauffages = new ArrayList<>();

    public List<MaisonAutomatisation_Para_Type_Chauffage> getMaisonAutomatisation_para_type_chauffages() {
        return maisonAutomatisation_para_type_chauffages;
    }

    public void setMaisonAutomatisation_Para_Type_Chauffage(List<MaisonAutomatisation_Para_Type_Chauffage> maisonAutomatisation_para_type_chauffages) {
        this.maisonAutomatisation_para_type_chauffages = maisonAutomatisation_para_type_chauffages;
    }

    public final MaisonAutomatisation_Para_Type_Chauffages add(final MaisonAutomatisation_Para_Type_Chauffage typeChauffage) {
        maisonAutomatisation_para_type_chauffages.add(typeChauffage);
        return this;
    }

    @Override
    public String toString() {
        return "MaisonAutomatisation_Para_Type_Chauffages{" +
                "MaisonAutomatisation_Para_Type_Chauffages=" + maisonAutomatisation_para_type_chauffages +
                '}';
    }
}