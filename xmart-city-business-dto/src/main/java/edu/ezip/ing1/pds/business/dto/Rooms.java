package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.LinkedHashSet;
import java.util.Set;

public class Rooms {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("rooms")
    private  Set<Room> rooms = new LinkedHashSet<Room>();

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public final Rooms add (final Room room) {
        rooms.add(room);
        return this;
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "rooms=" + rooms +
                '}';
    }
}
