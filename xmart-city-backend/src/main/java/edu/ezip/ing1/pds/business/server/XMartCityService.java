package edu.ezip.ing1.pds.business.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class XMartCityService {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r - XMartCityService";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);
    //SELECT_ALL_AUTOMATION("SELECT a.id, a.nom_automatisation, a.type_capteur, a.type_programme FROM automatisations a"),
    private enum Queries {
        //SELECT_ALL_STUDENTS("SELECT t.name, t.firstname, t.groupname FROM students t"),
        SELECT_ALL_STUDENTS("SELECT t.name, t.firstname, t.groupname, t.id FROM students t"),
        INSERT_STUDENT("INSERT into students (name, firstname, groupname) values (?, ?, ?)"),
        SELECT_ALL_AUTOMATION("SELECT * FROM automatisations"),
        INSERT_AUTOMATION("INSERT INTO automatisations (nom_automatisation, type_capteur, type_programme) VALUES (?, ?, ?)"),;
        //SELECT_ALL_CAPTEURS("SELECT nom_capteur FROM capteurs;"),
        //INSERT_CAPTEUR("INSERT INTO capteurs (nom_capteur, type_capteur, etat_capteur) VALUES (?, ?, ?)");
        private final String query;

        private Queries(final String query) {
            this.query = query;
        }
    }

    public static XMartCityService inst = null;

    public static final XMartCityService getInstance() {
        if (inst == null) {
            inst = new XMartCityService();
        }
        return inst;
    }

    private XMartCityService() {

    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        final Queries queryEnum = Enum.valueOf(Queries.class, request.getRequestOrder());
        switch (queryEnum) {
            case SELECT_ALL_STUDENTS:
                response = SelectAllStudents(request, connection);
                break;
            case INSERT_STUDENT:
                response = InsertStudent(request, connection);
                break;
            case SELECT_ALL_AUTOMATION:
                response = SelectAllAutomation(request, connection);
                break;
            case INSERT_AUTOMATION:
                response = InsertAutomation(request, connection);
                break;
//            case SELECT_ALL_CAPTEURS:
//                response = SelectAllCapteurs(request, connection);
//                break;
            default:
                break;
        }

        return response;
    }

    private Response InsertStudent(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Student student = objectMapper.readValue(request.getRequestBody(), Student.class);
        final PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_STUDENT.query);
        stmt.setString(1, student.getName());
        stmt.setString(2, student.getFirstname());
        stmt.setString(3, student.getGroup());
        stmt.executeUpdate();
        final Statement stmt2 = connection.createStatement();
        final ResultSet res = stmt2.executeQuery("SELECT LAST_INSERT_ID()");
        res.next();
        student.setId(res.getInt(1));
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(student));
    }

    private Response SelectAllStudents(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_STUDENTS.query);
        Students students = new Students();
        while (res.next()) {
            Student student = new Student();
            student.setName(res.getString(1));
            student.setFirstname(res.getString(2));
            student.setGroup(res.getString(3));
            student.setId(res.getInt(4));
            students.add(student);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(students));
    }

    private Response InsertAutomation(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final MaisonAutomatisation maisonAutomatisation = objectMapper.readValue(request.getRequestBody(), MaisonAutomatisation.class);
        final PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_AUTOMATION.query);
        stmt.setString(1, maisonAutomatisation.getNomAutomatisation());
        stmt.setString(2, maisonAutomatisation.getTypeCapteur());
        stmt.setString(3, maisonAutomatisation.getTypeProgramme());
        stmt.executeUpdate();

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(maisonAutomatisation));
    }

    private Response SelectAllAutomation(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_AUTOMATION.query);
        MaisonAutomatisations maisonAutomatisations = new MaisonAutomatisations();
        while (res.next()) {
            MaisonAutomatisation maisonAutomatisation = new MaisonAutomatisation();
            maisonAutomatisation.setId(Integer.parseInt(res.getString(1)));
            maisonAutomatisation.setNomAutomatisation(res.getString(2));
            maisonAutomatisation.setTypeCapteur(res.getString(3));
            maisonAutomatisation.setTypeProgramme(res.getString(4));

            System.out.println("Nom: " + maisonAutomatisation.getNomAutomatisation());
            System.out.println("Capteur: " + maisonAutomatisation.getTypeCapteur());
            System.out.println("Programme: " + maisonAutomatisation.getTypeProgramme());
            System.out.println("---------------------------");


            maisonAutomatisations.add(maisonAutomatisation);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(maisonAutomatisations));
    }

//    private Response SelectAllCapteurs(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
//        final ObjectMapper objectMapper = new ObjectMapper();
//        final Statement stmt = connection.createStatement();
//        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_CAPTEURS.query);
//        MaisonCapteurs capteurs = new MaisonCapteurs();
//        while (res.next()) {
//            MaisonCapteur capteur = new MaisonCapteur();
//            capteur.setIdCapteur(res.getInt(1));
//            capteur.setName(res.getString(2));
//            capteur.setTypecapteur(res.getString(3));
//            capteur.setEtat(res.getString(4));
//            capteurs.add(capteur);
//        }
//        return new Response(request.getRequestId(), objectMapper.writeValueAsString(capteurs));
//    }

}