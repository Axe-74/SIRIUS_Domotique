//package edu.ezip.ing1.pds;
//
//import java.util.Observable;
//
//public class Maison_Room extends Observable {
//    String NameRoom;
//    static TypeRoom TypeRoom;
//    Integer RoomSurface;
//
//    public Maison_Room(String nameRoom, TypeRoom typeRoom, Integer roomSurface) {
//        NameRoom = nameRoom;
//        TypeRoom = typeRoom;
//        RoomSurface = roomSurface;
//    }
//
//    public enum TypeRoom {
//        Entree, Salon, Cuisine, Salle_de_bain, Toilettes, Chambre, Autre
//    }
//
//    public static TypeRoom findByNameTypeRoom(String name) {
//        TypeRoom result = null;
//        for (TypeRoom typeRoom : TypeRoom.values())
//            if (typeRoom.name().equalsIgnoreCase(name)) {
//                result = typeRoom;
//                break;
//            }
//        return result;
//    }
//
//    @Override
//    public String toString() {
//        return "Nom : " + NameRoom;
//    }
//}
//
