package edu.ezip.ing1.pds.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class MaisonProgrammeLumiereService {
    private final static String LoggingLabel = "FrontEnd - MaisonProgrammeService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String studentsToBeInserted = "students-to-be-inserted.yaml";

    final String insertRequestOrder = "INSERT_PROGRAM_LIGHT";
    final String selectRequestOrder = "SELECT_ALL_PROGRAM_LIGHT";
    final String selectNameRequestOrder = "SELECT_NAME_PROGRAM_LIGHT";

    private final NetworkConfig networkConfig;

    public MaisonProgrammeLumiereService(NetworkConfig networkConfig) throws InterruptedException {
        this.networkConfig = networkConfig;
    }

    public void insertLightProgram(MaisonProgrammeLumiere maisonProgrammeLumiere, String requestOrder) throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


        int birthdate = 0;

        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(maisonProgrammeLumiere);
        logger.trace("Program with its JSON face : {}", jsonifiedGuy);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(insertRequestOrder);
        request.setRequestContent(jsonifiedGuy);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final InsertProgramsLumieresClientRequest clientRequest = new InsertProgramsLumieresClientRequest(
                networkConfig,
                birthdate++, request, maisonProgrammeLumiere, requestBytes);
        clientRequests.push(clientRequest);


        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest2.join();
            final MaisonProgrammeLumiere maisonProgrammeLumiere1 = (MaisonProgrammeLumiere) clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    maisonProgrammeLumiere1.getNomProgramme(), maisonProgrammeLumiere1.getTypePiece(), maisonProgrammeLumiere1.getLumiere(),maisonProgrammeLumiere1.getJourSemaine(),maisonProgrammeLumiere1.getTemperature(),maisonProgrammeLumiere1.getHeureDebut(),maisonProgrammeLumiere1.getHeureFin(),
                    clientRequest2.getResult());
        }
    }

    public MaisonProgrammesLumieres select_all_Light_program() throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(selectRequestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllProgramsLumieresClientRequest clientRequest = new SelectAllProgramsLumieresClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (MaisonProgrammesLumieres) joinedClientRequest.getResult();
        } else {
            logger.error("No program found");
            return null;
        }
    }

    public MaisonProgrammes  select_name_Light_program() throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(selectNameRequestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllProgramsClientRequest clientRequest = new SelectAllProgramsClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (MaisonProgrammes) joinedClientRequest.getResult();
        } else {
            logger.error("No name found");
            return null;
        }
    }
}