package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "MaisonAutomatisation_Para_Fenetre")
public class MaisonAutomatisation_Para_Fenetre {
    private  String Nom;
    private int ID_Para_Fenetre;

    public MaisonAutomatisation_Para_Fenetre() {
    }

    public final MaisonAutomatisation_Para_Fenetre build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "ID_Para_Fenetre", "Nom");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, String.valueOf(ID_Para_Fenetre), Nom);
    }
    public MaisonAutomatisation_Para_Fenetre(Integer ID_Para_Fenetre, String Nom) {
        this.ID_Para_Fenetre = ID_Para_Fenetre;
        this.Nom = Nom;
    }

    public Integer getID_Para_Fenetre() {
        return ID_Para_Fenetre;
    }

    public String getNom() {
        return Nom;
    }


    @JsonProperty("MaisonAutomatisation_Para_Lumiere_Nom")
    public void setNom(String Nom) {
        this.Nom = Nom;
    }

    @JsonProperty("MaisonAutomatisation_Para_Jour_Semaine_ID_Para_Lumiere")
    public void setID_Para_Fenetre(int ID_Para_Fenetre) {
        this.ID_Para_Fenetre = ID_Para_Fenetre;
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
                ", ID_Para_Fenetre='" + ID_Para_Fenetre + '\'' +
                '}';
    }
}