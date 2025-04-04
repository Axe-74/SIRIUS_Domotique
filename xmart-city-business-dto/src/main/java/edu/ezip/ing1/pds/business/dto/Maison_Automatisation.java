package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "Maison_Automatisation")
public class Maison_Automatisation {
    private  String NomAutomatisation;
    private  String TypeCapteur;
    private  String TypeProgramme;
    private int id;



    public Maison_Automatisation() {
    }
    public final Maison_Automatisation  build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "NomAutomatisation", "TypeCapteur","TypeProgramme");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, NomAutomatisation, TypeCapteur,TypeProgramme);
    }
    public Maison_Automatisation(String NomAutomatisation, String TypeCapteur, String TypeProgramme) {
        this.NomAutomatisation = NomAutomatisation;
        this.TypeCapteur = TypeCapteur;
        this.TypeProgramme = TypeProgramme;
    }

    public String getNomAutomatisation() {
        return NomAutomatisation;
    }

    public String getTypeCapteur() {
        return TypeCapteur;
    }

    public String getTypeProgramme() {
        return TypeProgramme;
    }

    public int getId() {
        return id;
    }


    @JsonProperty("Maison_Automatisation_NomAutomatisation")
    public void setNomAutomatisation(String name) {
        this.NomAutomatisation = NomAutomatisation;
    }

    @JsonProperty("Maison_Automatisation_TypeCapteurs")
    public void setTypeCapteur(String firstname) {
        this.TypeCapteur = TypeCapteur;
    }

    @JsonProperty("Maison_Automatisation_TypeProgramme")
    public void setTypeProgramme(String group) {
        this.TypeProgramme = TypeProgramme;
    }

    @JsonProperty("Maison_Automatisation_id")
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
        return "Maison_Automatisation{" +
                "NomAutomatisation='" + NomAutomatisation + '\'' +
                ", TypeCapteur='" + TypeCapteur + '\'' +
                ", TypeProgramme='" + TypeProgramme + '\'' +
                '}';
    }
}
