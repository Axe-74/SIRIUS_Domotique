package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Room_Para_Types {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Room_Para_Types")
    private List<Room_Para_Type> room_Para_Types = new ArrayList<>();

    public List<Room_Para_Type> getRoom_Para_Types() {
        return room_Para_Types;
    }

    public void setRoom_Para_Types(List<Room_Para_Type> room_Para_Types) {
        this.room_Para_Types = room_Para_Types;
    }

    public final Room_Para_Types add(final Room_Para_Type room_Para_Type) {
        room_Para_Types.add(room_Para_Type);
        return this;
    }

    @Override
    public String toString() {
        return "Room_Para_Types{" +
                "room_Para_Types=" + room_Para_Types +
                '}';
    }
}