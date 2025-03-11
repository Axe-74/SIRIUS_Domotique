package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "MaisonAutomatisation")
public class MaisonAutomatisation {
    private  String NomAutomatisation;
    private  String TypeCapteur;
    private  String TypeProgramme;
    private int Id;

    public MaisonAutomatisation() {
    }

    public final MaisonAutomatisation build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "id", "nom_automatisation", "type_capteur","type_programme");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, String.valueOf(Id), NomAutomatisation, TypeCapteur,TypeProgramme);
    }
    public MaisonAutomatisation(Integer Id, String NomAutomatisation, String TypeCapteur, String TypeProgramme) {
        this.Id = Id;
        this.NomAutomatisation = NomAutomatisation;
        this.TypeCapteur = TypeCapteur;
        this.TypeProgramme = TypeProgramme;
    }

    public Integer getId() {
        return Id;
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

    @JsonProperty("Maison_Automatisation_NomAutomatisation")
    public void setNomAutomatisation(String NomAutomatisation) {
        this.NomAutomatisation = NomAutomatisation;
    }

    @JsonProperty("Maison_Automatisation_TypeCapteurs")
    public void setTypeCapteur(String TypeCapteur) {
        this.TypeCapteur = TypeCapteur;
    }

    @JsonProperty("Maison_Automatisation_TypeProgramme")
    public void setTypeProgramme(String TypeProgramme) {
        this.TypeProgramme = TypeProgramme;
    }

    @JsonProperty("Maison_Automatisation_id")
    public void setId(int id) {
        this.Id = id;
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
