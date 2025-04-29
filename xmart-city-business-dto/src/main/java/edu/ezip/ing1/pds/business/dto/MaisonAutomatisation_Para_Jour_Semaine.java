package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "MaisonAutomatisation_Para_Jour_Semaine")
public class MaisonAutomatisation_Para_Jour_Semaine {
    private  String Nom;
    private int ID_Para_Jour_Semaine;

    public MaisonAutomatisation_Para_Jour_Semaine() {
    }

    public final MaisonAutomatisation_Para_Jour_Semaine build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "ID_Para_Jour_Semaine", "Nom");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, String.valueOf(ID_Para_Jour_Semaine), Nom);
    }
    public MaisonAutomatisation_Para_Jour_Semaine(Integer ID_Para_Jour_Semaine, String Nom) {
        this.ID_Para_Jour_Semaine = ID_Para_Jour_Semaine;
        this.Nom = Nom;
    }

    public Integer getID_Para_Jour_Semaine() {
        return ID_Para_Jour_Semaine;
    }

    public String getNom() {
        return Nom;
    }


    @JsonProperty("MaisonAutomatisation_Para_Jour_Semaine_Nom")
    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    @JsonProperty("MaisonAutomatisation_Para_Jour_Semaine_ID_Para_Jour_Semaine")
    public void setID_Para_Jour_Semaine(int ID_Para_Jour_Semaine) {
        this.ID_Para_Jour_Semaine = ID_Para_Jour_Semaine;
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
                "Nom='" + Nom + '\'' +
                ", ID_Para_Jour_Semaine='" + ID_Para_Jour_Semaine + '\'' +
                '}';
    }
}