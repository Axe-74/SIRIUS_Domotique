package edu.ezip.ing1.pds.features.rooms.visualisation2D;

public class TripleArrayRoom {
    String nameRoom;
    int lengthRoom;
    int widthRoom;

    public TripleArrayRoom(String nameRoom, int lengthRoom, int widthRoom) {
        this.nameRoom = nameRoom;
        this.lengthRoom = lengthRoom;
        this.widthRoom = widthRoom;
    }

    public String toString() {
        return "TripleArrayRoom{name='" + nameRoom + "', surface1=" + lengthRoom + ", surface2=" + widthRoom + "}";
    }

    public String getName() {
        return nameRoom;
    }

    public Integer getSurface1() {
        return lengthRoom;
    }

    public Integer getSurface2() {
        return widthRoom;
    }
}
