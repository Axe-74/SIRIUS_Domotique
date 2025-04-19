package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@JsonRootName(value = "capteurs")
public class MaisonCapteur {
    private  String nameCapteur;
    private  String typeCapteur;
    private String pieceCapteur;
    private  String etat;
    private int idCapteur;

    public MaisonCapteur() {
    }
    public final MaisonCapteur build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "id", "nom_capteur", "type_capteur", "etat_capteur");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, nameCapteur, typeCapteur,etat);
    }
    public MaisonCapteur(String nameCapteur, String typeCapteur, String pieceCapteur, String etat, int idCapteur) {
        this.nameCapteur = nameCapteur;
        this.typeCapteur = typeCapteur;
        this.pieceCapteur = pieceCapteur;
        this.etat = etat;
        this.idCapteur = idCapteur;
    }
    public String getName() {
        return nameCapteur;
    }

    public String getPieceCapteur() {
        return pieceCapteur;
    }

    public String getTypeCapteur () {
        return typeCapteur;
    }

    public String getEtat() {
        return etat;
    }

    public int getIdCapteur() {
        return idCapteur;
    }

    @JsonProperty("capteur_name")
    public void setName(String nameCapteur) {
        this.nameCapteur = nameCapteur;
    }

    @JsonProperty("capteur_typecapteur")
    public void setTypecapteur(String typeCapteur) {
        this.typeCapteur = typeCapteur;
    }

    @JsonProperty("capteur_etat")
    public void setEtat(String etat) {
        this.etat = etat;
    }

    @JsonProperty("capteur_piece")
    public void setPieceCapteur(String pieceCapteur) {
        this.pieceCapteur = pieceCapteur;
    }

    @JsonProperty("capteur_id")
    public void setIdCapteur(int idCapteur) {
        this.idCapteur = idCapteur;
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
        return "Capteur{" +
                "name='" + nameCapteur + '\'' +
                ", type='" + typeCapteur + '\'' +
                ", etat='" + etat + '\'' +
                ", piece='" + pieceCapteur + '\'' +
                ", id='" + idCapteur + '\'' +
                '}';
    }

}
