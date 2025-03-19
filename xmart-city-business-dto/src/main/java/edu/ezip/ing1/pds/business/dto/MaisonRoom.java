package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "MaisonRoom")
public class MaisonRoom {
    private  String name;
    private  String type;
    private  int surface;
    private  int id;


    public MaisonRoom() {
    }
    public final MaisonRoom build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "id", "name_room", "type_room", "room_surface");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, name, type, String.valueOf(surface));
    }
    public MaisonRoom(String name, String type, int surface) {
        this.name = name;
        this.type = type;
        this.surface = surface;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getSurface() {
        return surface;
    }

    public int getId() { return id; }

    @JsonProperty("room_name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("room_type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("room_surface")
    public void setSurface(int surface) {
        this.surface = surface;
    }

    @JsonProperty("room_id")
    public void setId(int id) {
        this.id = id;
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
        return "Room{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", surface='" + surface + '\'' +
                '}';
    }
}
