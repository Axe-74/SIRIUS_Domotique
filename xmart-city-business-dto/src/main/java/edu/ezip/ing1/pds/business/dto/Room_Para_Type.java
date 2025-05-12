package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "Room_Para_Type")
public class Room_Para_Type {
    private  String Nom;
    private int ID_Para_TypeRoom;

    public Room_Para_Type() {
    }

    public final edu.ezip.ing1.pds.business.dto.Room_Para_Type build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "ID_Para_TypeRoom", "Nom");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, String.valueOf(ID_Para_TypeRoom), Nom);
    }
    public Room_Para_Type(Integer ID_Para_TypeRoom, String Nom) {
        this.ID_Para_TypeRoom = ID_Para_TypeRoom;
        this.Nom = Nom;
    }

    public Integer getID_Para_TypeRoom() {
        return ID_Para_TypeRoom;
    }

    public String getNom() {
        return Nom;
    }


    @JsonProperty("Room_Para_Type")
    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    @JsonProperty("Room_Para_Room_ID")
    public void setID_Para_TypeRoom(int ID_Para_TypeRoom) {
        this.ID_Para_TypeRoom = ID_Para_TypeRoom;
    }

    private void setFieldsFromResulset(final ResultSet resultSet, final String ... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        for(final String fieldName : fieldNames ) {
            final Field field = this.getClass().getDeclaredField(fieldName);
            field.set(this, resultSet.getObject(fieldName));
        }
    }
    private final PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, final String ... fieldNames )
            throws NoSuchFieldException, SQLException, IllegalAccessException {
        int ix = 0;
        for(final String fieldName : fieldNames ) {
            preparedStatement.setString(++ix, fieldName);
        }
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "Room_Para_Type{" +
                "Nom='" + Nom + '\'' +
                ", ID_Para_TypeRoom='" + ID_Para_TypeRoom + '\'' +
                '}';
    }
}



