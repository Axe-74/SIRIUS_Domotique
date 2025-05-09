package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class Capteur_Para_Types {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Capteur_Para_Types")
    private Set<Capteur_Para_Type> Capteur_Para_Types = new LinkedHashSet<Capteur_Para_Type>();

    public Set<Capteur_Para_Type> getCapteur_Para_Types() {
        return Capteur_Para_Types;
    }

    public void setCapteur_Para_Type(Set<Capteur_Para_Type> Capteur_Para_Types) {
        this.Capteur_Para_Types = Capteur_Para_Types;
    }

    public final Capteur_Para_Types add (final Capteur_Para_Type Capteur_Para_Type) {
        Capteur_Para_Types.add(Capteur_Para_Type);
        return this;
    }

    @Override
    public String toString() {
        return "Capteur_Para_Types{" +
                "Capteur_Para_Types=" + Capteur_Para_Types +
                '}';
    }
}
