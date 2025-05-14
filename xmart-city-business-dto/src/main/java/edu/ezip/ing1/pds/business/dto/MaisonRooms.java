package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class MaisonRooms {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("MaisonRooms")
    private List<MaisonRoom> maisonRooms = new ArrayList<>();

    public List<MaisonRoom> getMaisonRooms() {
        return maisonRooms;
    }

    public void setMaisonRooms(List<MaisonRoom> maisonRooms) {
        this.maisonRooms = maisonRooms;
    }

    public final MaisonRooms add(final MaisonRoom maisonRoom) {
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