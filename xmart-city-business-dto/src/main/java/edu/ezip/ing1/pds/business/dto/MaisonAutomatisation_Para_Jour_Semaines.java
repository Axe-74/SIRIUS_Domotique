package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class MaisonAutomatisation_Para_Jour_Semaines {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonAutomatisation_Para_Jour_Semaines")
    private Set<MaisonAutomatisation_Para_Jour_Semaine> maisonAutomatisation_para_jour_semaines = new LinkedHashSet<MaisonAutomatisation_Para_Jour_Semaine>();

    public Set<MaisonAutomatisation_Para_Jour_Semaine> getMaisonAutomatisation_para_jour_semaines() {
        return maisonAutomatisation_para_jour_semaines;
    }

    public void setMaisonAutomatisation_Para_Jour_Semaine(Set<MaisonAutomatisation_Para_Jour_Semaine> maisonAutomatisation_para_jour_semaines) {
        this.maisonAutomatisation_para_jour_semaines = maisonAutomatisation_para_jour_semaines;
    }

    public final MaisonAutomatisation_Para_Jour_Semaines add (final MaisonAutomatisation_Para_Jour_Semaine Maison_Automatisation_Para_Jour_Semaine) {
        maisonAutomatisation_para_jour_semaines.add(Maison_Automatisation_Para_Jour_Semaine);
        return this;
    }

    @Override
    public String toString() {
        return "MaisonAutomatisation_Para_Jour_Semaines{" +
                "MaisonAutomatisation_Para_Jour_Semaines=" + maisonAutomatisation_para_jour_semaines +
                '}';
    }
}
