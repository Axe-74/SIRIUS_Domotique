package edu.ezip.ing1.pds.business.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@JsonRootName(value = "MaisonProgrammeLumiere")
public class MaisonProgrammeLumiere {
    private  String NomProgramme;
    private  String TypePiece;
    private  String Lumiere;
    private  String JourSemaine;
    private  int HeureDebut;
    private  int HeureFin;
    private  int Temperature;
    private int id;



    public MaisonProgrammeLumiere() {
    }

    public final MaisonProgrammeLumiere build(final ResultSet resultSet)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        setFieldsFromResulset(resultSet, "nom_programme", "type_piece", "type_chauffage", "jour_semaine","temperature_piece", "heure_debut", "heure_fin");
        return this;
    }
    public final PreparedStatement build(PreparedStatement preparedStatement)
            throws SQLException, NoSuchFieldException, IllegalAccessException {
        return buildPreparedStatement(preparedStatement, NomProgramme,TypePiece,JourSemaine, Lumiere, String.valueOf(Temperature),String.valueOf(HeureDebut), String.valueOf(HeureFin));
    }
    public MaisonProgrammeLumiere(String NomProgramme, String TypePiece, String JourSemaine, String Lumiere,int Temperature, int HeureDebut, int HeureFin) {
        this.id = id;
        this.NomProgramme = NomProgramme ;
        this.TypePiece = TypePiece;
        this.JourSemaine = JourSemaine;
        this.Lumiere = Lumiere;
        this.Temperature = Temperature;
        this.HeureDebut = HeureDebut;
        this.HeureFin = HeureFin;
    }
    public int getId() {
        return id;
    }

    public String getNomProgramme() {
        return NomProgramme;
    }

    public String getTypePiece() {
        return TypePiece;
    }

    public String getJourSemaine() {
        return JourSemaine;
    }

    public String getLumiere(){return Lumiere;}

    public int getHeureDebut(){return HeureDebut;}

    public int getHeureFin(){return HeureFin;}

    public int getTemperature(){return Temperature;}




    @JsonProperty("Maison_Programme_NomProgramme")
    public void setNomProgramme(String NomProgramme) {
        this.NomProgramme = NomProgramme;
    }

    @JsonProperty("Maison_Programme_TypePiece")
    public void setTypePiece(String TypePiece) {
        this.TypePiece = TypePiece;
    }

    @JsonProperty("Maison_Programme_TypeChauffage")
    public void setJourSemaine(String JourSemaine) {
        this.JourSemaine = JourSemaine;
    }

    @JsonProperty("Maison_Programme_Lumiere")
    public void setLumiere(String Lumiere) {
        this.Lumiere = Lumiere;
    }

    @JsonProperty("Maison_Programme_HeureDebut")
    public void setHeureDebut(int HeureDebut) {
        this.HeureDebut = HeureDebut;
    }

    @JsonProperty("Maison_Programme_HeureFin")
    public void setHeureFin(int HeureFin) {
        this.HeureFin = HeureFin;
    }

    @JsonProperty("Maison_Programme_Temperature")
    public void setTemperature(int Temperature) {
        this.Temperature = Temperature;
    }

    @JsonProperty("Maison_Programme_id")
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
        return "MaisonProgramme{" +
                "NomProgramme='" + NomProgramme + '\'' +
                ", TypePiece='" + TypePiece + '\'' +
                ", Lumieres='" + Lumiere    + '\'' +
                ", JourSemaine='" + JourSemaine + '\'' +
                '}';
    }
}
