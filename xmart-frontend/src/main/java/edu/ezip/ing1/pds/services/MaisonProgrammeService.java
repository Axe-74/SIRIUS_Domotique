package edu.ezip.ing1.pds.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisations;
import edu.ezip.ing1.pds.business.dto.MaisonProgramme;
import edu.ezip.ing1.pds.business.dto.MaisonProgrammes;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.InsertProgramClientRequest;
import edu.ezip.ing1.pds.requests.SelectAllAutomationClientRequest;
import edu.ezip.ing1.pds.requests.SelectAllProgramsClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class MaisonProgrammeService {
    private final static String LoggingLabel = "FrontEnd - MaisonProgrammeService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String studentsToBeInserted = "students-to-be-inserted.yaml";

    final String insertRequestOrder = "INSERT_PROGRAM";
    final String selectRequestOrder = "SELECT_ALL_PROGRAM";
    final String selectNameRequestOrder = "SELECT_ALL_AUTOMATION";

    private final NetworkConfig networkConfig;

    public MaisonProgrammeService(NetworkConfig networkConfig) throws InterruptedException {
        this.networkConfig = networkConfig;
    }

    public void insertProgram(MaisonProgramme maisonProgramme, String requestOrder) throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();


        int birthdate = 0;

        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(maisonProgramme);
        logger.trace("Program with its JSON face : {}", jsonifiedGuy);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(insertRequestOrder);
        request.setRequestContent(jsonifiedGuy);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final InsertProgramClientRequest clientRequest = new InsertProgramClientRequest(
                networkConfig,
                birthdate++, request, maisonProgramme, requestBytes);
        clientRequests.push(clientRequest);


        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest2.join();
            final MaisonProgramme maisonProgramme1 = (MaisonProgramme) clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    maisonProgramme1.getNomProgramme(), maisonProgramme1.getTypePiece(), maisonProgramme1.getTypeChauffage(),maisonProgramme1.getJourSemaine(),maisonProgramme1.getTemperature(),maisonProgramme1.getHeureDebut(),maisonProgramme1.getHeureFin(),
                    clientRequest2.getResult());
        }
    }

    public MaisonProgrammes select_all_program() throws InterruptedException, IOException {
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
            logger.error("No program found");
            return null;
        }
    }


//    public MaisonAutomatisations select_name_automation() throws InterruptedException, IOException {
//        int birthdate = 0;
//        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
//        final ObjectMapper objectMapper = new ObjectMapper();
//        final String requestId = UUID.randomUUID().toString();
//        final Request request = new Request();
//        request.setRequestId(requestId);
//        request.setRequestOrder(selectNameRequestOrder);
//        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
//        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
//        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
//        final SelectAllAutomationClientRequest clientRequest = new SelectAllAutomationClientRequest(
//                networkConfig,
//                birthdate++, request, null, requestBytes);
//        clientRequests.push(clientRequest);
//
//        if (!clientRequests.isEmpty()) {
//            final ClientRequest joinedClientRequest = clientRequests.pop();
//            joinedClientRequest.join();
//            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
//            return (MaisonAutomatisations) joinedClientRequest.getResult();
//        } else {
//            logger.error("No automations found");
//            return null;
//        }
//    }
}