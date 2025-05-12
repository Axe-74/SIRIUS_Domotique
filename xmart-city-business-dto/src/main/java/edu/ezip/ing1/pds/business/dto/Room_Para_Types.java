package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedHashSet;
import java.util.Set;

public class Room_Para_Types {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Room_Para_Types")
    private Set<Room_Para_Type> Room_Para_Types = new LinkedHashSet<Room_Para_Type>();

    public Set<Room_Para_Type> getRoom_Para_Types() {
        return Room_Para_Types;
    }

    public void setRoom_Para_Type(Set<Room_Para_Type> Room_Para_Types) {
        this.Room_Para_Types = Room_Para_Types;
    }

    public final Room_Para_Types add (final Room_Para_Type Room_Para_Type) {
        Room_Para_Types.add(Room_Para_Type);
        return this;
    }

    @Override
    public String toString() {
        return "Room_Para_Types{" +
                "Room_Para_Types=" + Room_Para_Types +
                '}';
    }
}
