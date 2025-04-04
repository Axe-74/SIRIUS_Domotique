package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class MaisonRooms {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonRooms")
    private  Set<MaisonRoom> maisonRooms = new LinkedHashSet<MaisonRoom>();

    public Set<MaisonRoom> getMaisonRooms() {
        return maisonRooms;
    }

    public void setMaisonRooms(Set<MaisonRoom> maisonRooms) {
        this.maisonRooms = maisonRooms;
    }

    public final MaisonRooms add (final MaisonRoom maison_Room) {
        maisonRooms.add(maison_Room);
        return this;
    }

    @Override
    public String toString() {
        return "MaisonRooms{" +
                "maisonRooms=" + maisonRooms +
                '}';
    }
}
