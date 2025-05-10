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

public class MaisonProgrammeFenetreService {
    private final static String LoggingLabel = "FrontEnd - MaisonProgrammeService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String studentsToBeInserted = "students-to-be-inserted.yaml";

    final String insertRequestOrder = "INSERT_PROGRAM_WINDOW";
    final String selectRequestOrder = "SELECT_ALL_PROGRAM_WINDOW";
    final String selectNameRequestOrder = "SELECT_NAME_PROGRAM_WINDOW";

    private final NetworkConfig networkConfig;

    public MaisonProgrammeFenetreService(NetworkConfig networkConfig) throws InterruptedException {
        this.networkConfig = networkConfig;
    }

    public void insert_Window_Program(MaisonProgrammeFenetre maisonProgrammeFenetre, String requestOrder) throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


        int birthdate = 0;

        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(maisonProgrammeFenetre);
        logger.trace("Program with its JSON face : {}", jsonifiedGuy);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(insertRequestOrder);
        request.setRequestContent(jsonifiedGuy);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final InsertProgramsFenetreClientRequest clientRequest = new InsertProgramsFenetreClientRequest(
                networkConfig,
                birthdate++, request, maisonProgrammeFenetre, requestBytes);
        clientRequests.push(clientRequest);


        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest2.join();
            final MaisonProgrammeFenetre maisonProgrammeFenetre1 = (MaisonProgrammeFenetre) clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    maisonProgrammeFenetre1.getNomProgramme(), maisonProgrammeFenetre1.getTypePiece(), maisonProgrammeFenetre1.getFenetre(),maisonProgrammeFenetre1.getJourSemaine(),maisonProgrammeFenetre1.getTemperature(),maisonProgrammeFenetre1.getHeureDebut(),maisonProgrammeFenetre1.getHeureFin(),
                    clientRequest2.getResult());
        }
    }

    public MaisonProgrammesFenetres select_all_Window_program() throws InterruptedException, IOException {
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
        final SelectAllProgramsFenetresClientRequest clientRequest = new SelectAllProgramsFenetresClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (MaisonProgrammesFenetres) joinedClientRequest.getResult();
        } else {
            logger.error("No program found");
            return null;
        }
    }

    public MaisonProgrammes select_name_Window_program() throws InterruptedException, IOException {
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