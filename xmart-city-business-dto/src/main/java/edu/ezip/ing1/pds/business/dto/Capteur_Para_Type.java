package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "Capteur_Para_Type")
public class Capteur_Para_Type {
    private  String Nom;
    private int ID_Para_TypeCapteur;

    public Capteur_Para_Type() {
    }

    public final edu.ezip.ing1.pds.business.dto.Capteur_Para_Type build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "ID_Para_TypeCapteur", "Nom");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, String.valueOf(ID_Para_TypeCapteur), Nom);
    }
    public Capteur_Para_Type(Integer ID_Para_TypeCapteur, String Nom) {
        this.ID_Para_TypeCapteur = ID_Para_TypeCapteur;
        this.Nom = Nom;
    }

    public Integer getID_Para_TypeCapteur() {
        return ID_Para_TypeCapteur;
    }

    public String getNom() {
        return Nom;
    }


    @JsonProperty("Capteur_Para_Type")
    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    @JsonProperty("Capteur_Para_Capteur_ID")
    public void setID_Para_TypeCapteur(int ID_Para_TypeCapteur) {
        this.ID_Para_TypeCapteur = ID_Para_TypeCapteur;
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
        return "Capteur_Para_Type{" +
                "Nom='" + Nom + '\'' +
                ", ID_Para_TypeCapteur='" + ID_Para_TypeCapteur + '\'' +
                '}';
    }
}



