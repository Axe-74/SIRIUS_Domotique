package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class MaisonRooms {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("rooms")
    private  Set<MaisonRoom> maisonRooms = new LinkedHashSet<MaisonRoom>();

    public Set<MaisonRoom> getRooms() {
        return maisonRooms;
    }

    public void setRooms(Set<MaisonRoom> maisonRooms) {
        this.maisonRooms = maisonRooms;
    }

    public final MaisonRooms add (final MaisonRoom maisonRoom) {
        maisonRooms.add(maisonRoom);
        return this;
    }

    @Override
    public String toString() {
        return "MaisonRooms{" +
                "maisonRooms=" + maisonRooms +
                '}';
    }
}
