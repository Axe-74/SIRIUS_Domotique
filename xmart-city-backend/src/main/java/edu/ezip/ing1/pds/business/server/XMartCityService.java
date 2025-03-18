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

    private enum Queries {
        //SELECT_ALL_STUDENTS("SELECT t.name, t.firstname, t.groupname FROM students t"),
        SELECT_ALL_STUDENTS("SELECT t.name, t.firstname, t.groupname, t.id FROM students t"),
        INSERT_STUDENT("INSERT into students (name, firstname, groupname) values (?, ?, ?)"),
        SELECT_ALL_AUTOMATION("SELECT * FROM automatisations"),
        INSERT_AUTOMATION("INSERT INTO automatisations (nom_automatisation, type_capteur, type_programme) VALUES (?, ?, ?)"),
        SELECT_ALL_CAPTEURS("SELECT id, nom_capteur, type_capteur, etat_capteur FROM capteurs"),
        INSERT_CAPTEUR("INSERT INTO capteurs (nom_capteur, type_capteur, etat_capteur) VALUES (?, ?, ?)"),
//        SELECT_ALL_ROOMS("SELECT r.nom_room, r.type_room, r.room_surface, r.id FROM rooms r"),
//        INSERT_ROOM("INSERT into rooms (nom_room, type_room, room_surface) VALUES (?, ?, ?)"),
        SELECT_NAME_AUTOMATION("SELECT nom_automatisation FROM automatisations"),
        SELECT_ALL_PROGRAM("SELECT * FROM programmes"),
        INSERT_PROGRAM("INSERT INTO programmes (nom_programme, type_piece, type_chauffage,  jour_semaine,temperature_piece, heure_debut, heure_fin) VALUES (?, ?, ?, ?, ?, ?, ?)"),
        SELECT_NAME_PROGRAM("SELECT nom_programme FROM programmes");

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
            case SELECT_ALL_CAPTEURS:
                response = SelectAllCapteurs(request, connection);
                break;
            case INSERT_CAPTEUR:
                response = InsertCapteur(request, connection);
                break;
//            case SELECT_ALL_ROOMS:
//                response = SelectAllRooms(request, connection);
//                break;
//            case INSERT_ROOM:
//                response = InsertRoom(request, connection);
//                break;
            case SELECT_NAME_AUTOMATION:
                response = SelectNameAutomation(request, connection);
                break;
            case SELECT_ALL_PROGRAM:
                response = SelectAllProgram(request, connection);
                break;
            case INSERT_PROGRAM:
                response = InsertProgram(request, connection);
                break;
            case SELECT_NAME_PROGRAM:
                response = SelectNameProgram(request, connection);
                break;
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
            maisonAutomatisations.add(maisonAutomatisation);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(maisonAutomatisations));
    }

    private Response SelectAllCapteurs(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_CAPTEURS.query);
        MaisonCapteurs capteurs = new MaisonCapteurs();
        while (res.next()) {
            MaisonCapteur capteur = new MaisonCapteur();
            capteur.setIdCapteur(res.getInt(1));
            capteur.setName(res.getString(2));
            capteur.setTypecapteur(res.getString(3));
            capteur.setEtat(res.getString(4));
            capteurs.add(capteur);
            System.out.println("id: " + capteur.getIdCapteur());
            System.out.println("name: " + capteur.getName());
            System.out.println("etat: " + capteur.getEtat());
            System.out.println("type: " + capteur.getTypecapteur());
            System.out.println(capteur);
            System.out.println(capteurs);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(capteurs));
    }

    private Response InsertCapteur(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final MaisonCapteur maisonCapteur = objectMapper.readValue(request.getRequestBody(), MaisonCapteur.class);
        final PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_CAPTEUR.query);
        stmt.setString(1, maisonCapteur.getName());
        stmt.setString(2, maisonCapteur.getTypecapteur());
        stmt.setString(3, maisonCapteur.getEtat());
        stmt.executeUpdate();

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(maisonCapteur));
    }

//    private Response InsertRoom(final Request request, final Connection connection) throws SQLException, IOException {
//        final ObjectMapper objectMapper = new ObjectMapper();
//        final Room room = objectMapper.readValue(request.getRequestBody(), Room.class);
//        final PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_ROOM.query);
//        stmt.setString(1, room.getName());
//        stmt.setString(2, room.getType());
//        stmt.setInt(3, room.getSurface());
//        stmt.executeUpdate();
//        final Statement stmt2 = connection.createStatement();
//        final ResultSet res = stmt2.executeQuery("SELECT LAST_INSERT_ID()");
//        res.next();
//        room.setId(res.getInt(1));
//        return new Response(request.getRequestId(), objectMapper.writeValueAsString(room));
//    }

//    private Response SelectAllRooms(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
//        final ObjectMapper objectMapper = new ObjectMapper();
//        final Statement stmt = connection.createStatement();
//        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_ROOMS.query);
//        Rooms rooms = new Rooms();
//        while (res.next()) {
//            Room room = new Room();
//            room.setId(Integer.parseInt(res.getString(1)));
//            room.setName(res.getString(2));
//            room.setType(res.getString(3));
//            room.setSurface(Integer.parseInt(res.getString(4)));
//            rooms.add(room);
//        }
//        return new Response(request.getRequestId(), objectMapper.writeValueAsString(rooms));
//    }

    private Response SelectNameAutomation(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_NAME_AUTOMATION.query);
        MaisonAutomatisations maisonAutomatisations = new MaisonAutomatisations();
        while (res.next()) {
            MaisonAutomatisation maisonAutomatisation = new MaisonAutomatisation();
            maisonAutomatisation.setNomAutomatisation(res.getString(1));
            System.out.println("NOM" + maisonAutomatisation.getNomAutomatisation());
            maisonAutomatisations.add(maisonAutomatisation);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(maisonAutomatisations));
    }

    private Response SelectAllProgram(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_PROGRAM.query);
        MaisonProgrammes maisonProgrammes = new MaisonProgrammes();
        while (res.next()) {
            MaisonProgramme maisonProgramme = new MaisonProgramme();
            maisonProgramme.setNomProgramme(res.getString(2));
            maisonProgramme.setTypePiece(res.getString(3));
            maisonProgramme.setTypeChauffage(res.getString(4));
            maisonProgramme.setJourSemaine(res.getString(6));
            maisonProgramme.setTemperature(Integer.parseInt(res.getString(5)));
            maisonProgramme.setHeureDebut(Integer.parseInt(res.getString(7)));
            maisonProgramme.setHeureFin(Integer.parseInt(res.getString(8)));
            maisonProgrammes.add(maisonProgramme);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(maisonProgrammes));
    }

    private Response InsertProgram(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final MaisonProgramme maisonProgramme = objectMapper.readValue(request.getRequestBody(), MaisonProgramme.class);
        final PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_PROGRAM.query);
        stmt.setString(1, maisonProgramme.getNomProgramme());
        stmt.setString(2, maisonProgramme.getTypePiece());
        stmt.setString(3, maisonProgramme.getTypeChauffage());
        stmt.setString(4, maisonProgramme.getJourSemaine());
        stmt.setInt(5,maisonProgramme.getTemperature());
        stmt.setInt(6,maisonProgramme.getHeureDebut());
        stmt.setInt(7,maisonProgramme.getHeureFin());
        stmt.executeUpdate();

        return new Response(request.getRequestId(), objectMapper.writeValueAsString(maisonProgramme));
    }

    private Response SelectNameProgram(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_NAME_PROGRAM.query);
        MaisonProgrammes maisonProgrammes = new MaisonProgrammes();
        while (res.next()) {
            MaisonProgramme maisonProgramme = new MaisonProgramme();
            maisonProgramme.setNomProgramme(res.getString(2));
            maisonProgrammes.add(maisonProgramme);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(maisonProgrammes));
    }

}