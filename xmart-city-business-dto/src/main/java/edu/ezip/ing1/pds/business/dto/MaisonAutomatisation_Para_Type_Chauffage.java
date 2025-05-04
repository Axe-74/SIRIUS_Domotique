package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "MaisonAutomatisation_Para_Jour_Semaine")
public class MaisonAutomatisation_Para_Type_Chauffage {
    private  String Nom;
    private int ID_Para_Jour_Chauffage;

    public MaisonAutomatisation_Para_Type_Chauffage() {
    }

    public final edu.ezip.ing1.pds.business.dto.MaisonAutomatisation_Para_Type_Chauffage build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "ID_Para_Jour_Chauffage", "Nom");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, String.valueOf(ID_Para_Jour_Chauffage), Nom);
    }
    public MaisonAutomatisation_Para_Type_Chauffage(Integer ID_Para_Jour_Chauffage, String Nom) {
        this.ID_Para_Jour_Chauffage = ID_Para_Jour_Chauffage;
        this.Nom = Nom;
    }

    public Integer getID_Para_Jour_Chauffage() {
        return ID_Para_Jour_Chauffage;
    }

    public String getNom() {
        return Nom;
    }


    @JsonProperty("MaisonAutomatisation_Para_Jour_Semaine_Nom")
    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    @JsonProperty("MaisonAutomatisation_Para_Jour_Semaine_ID_Para_Jour_Semaine")
    public void setID_Para_Jour_Chauffage(int ID_Para_Jour_Chauffage) {
        this.ID_Para_Jour_Chauffage = ID_Para_Jour_Chauffage;
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
        return "MaisonAutomatisation_Para_Type_Chauffage{" +
                "Nom='" + Nom + '\'' +
                ", ID_Para_Jour_Chauffage='" + ID_Para_Jour_Chauffage + '\'' +
                '}';
    }
}