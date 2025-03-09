package edu.ezip.ing1.pds.business.server.automatisation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Maison_Automatisation;
import edu.ezip.ing1.pds.business.dto.Maison_Automatisations;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.commons.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class InsertRequestAutomation {

    private final static String LoggingLabel = "B u s i n e s s - S e r v e r";
    private final Logger logger = LoggerFactory.getLogger(LoggingLabel);

    private enum Queries {
        //SELECT_ALL_STUDENTS("SELECT t.name, t.firstname, t.groupname FROM students t"),
        SELECT_ALL_AUTOMATION("SELECT * FROM automatisations"),
        INSERT_AUTOMATION("INSERT INTO automatisations (nom_automatisation, type_capteur, type_programme) VALUES (?, ?, ?)");
        private final String query;

        private Queries(final String query) {
            this.query = query;
        }
    }

    public static InsertRequestAutomation inst = null;
    public static final InsertRequestAutomation getInstance()  {
        if(inst == null) {
            inst = new InsertRequestAutomation();
        }
        return inst;
    }

    private InsertRequestAutomation() {

    }

    public final Response dispatch(final Request request, final Connection connection)
            throws InvocationTargetException, IllegalAccessException, SQLException, IOException {
        Response response = null;

        final Queries queryEnum = Enum.valueOf(Queries.class, request.getRequestOrder());
        switch(queryEnum) {
            case SELECT_ALL_AUTOMATION:
                response = SelectAllAutomation(request, connection);
                break;
            case INSERT_AUTOMATION:
                response = InsertAutomation(request, connection);
                break;
            default:
                break;
        }

        return response;
    }

    private Response InsertAutomation(final Request request, final Connection connection) throws SQLException, IOException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Maison_Automatisation student = objectMapper.readValue(request.getRequestBody(), Maison_Automatisation.class);
        final PreparedStatement stmt = connection.prepareStatement(Queries.INSERT_AUTOMATION.query);
        stmt.setString(1, student.getNomAutomatisation());
        stmt.setString(2, student.getTypeCapteur());
        stmt.setString(3, student.getTypeProgramme());
        stmt.executeUpdate();
        final Statement stmt2 = connection.createStatement();
        final ResultSet res = stmt2.executeQuery("SELECT LAST_INSERT_ID()");
        res.next();
        //Maison_Automatisation.setId(res.getInt(1));
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(student));
    }


    private Response SelectAllAutomation(final Request request, final Connection connection) throws SQLException, JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Statement stmt = connection.createStatement();
        final ResultSet res = stmt.executeQuery(Queries.SELECT_ALL_AUTOMATION.query);
        Maison_Automatisations maison_automatisations = new Maison_Automatisations();
        while (res.next()) {
            Maison_Automatisation maison_automatisation = new Maison_Automatisation();
            maison_automatisation.setNomAutomatisation(res.getString(1));
            maison_automatisation.setTypeCapteur(res.getString(2));
            maison_automatisation.setTypeProgramme(res.getString(3));
            maison_automatisations.add(maison_automatisation);
        }
        return new Response(request.getRequestId(), objectMapper.writeValueAsString(maison_automatisations));
    }

}
