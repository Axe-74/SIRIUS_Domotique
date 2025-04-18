package edu.ezip.ing1.pds.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisations;
import edu.ezip.ing1.pds.business.dto.MaisonCapteur;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.InsertAutomationClientRequest;
import edu.ezip.ing1.pds.requests.SelectAllAutomationClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class MaisonAutomatisationService {
    private final static String LoggingLabel = "FrontEnd - MaisonAutomatisationService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    final String insertRequestOrder = "INSERT_AUTOMATION";
    final String selectRequestOrder = "SELECT_ALL_AUTOMATION";
    final String selectNameAutomation = "SELECT_NAME_AUTOMATION";
    final String updateRequestOrder = "UPDATE_AUTOMATION";
    final String deleteRequestOrder = "DELETE_AUTOMATION";

    private final NetworkConfig networkConfig;

    public MaisonAutomatisationService(NetworkConfig networkConfig) throws InterruptedException {
        this.networkConfig = networkConfig;
    }

    public void updateAutomation(MaisonAutomatisation maisonAutomatisation)throws InterruptedException, IOException {
        logger.debug("updateAutomation pour : {}", maisonAutomatisation.getNomAutomatisation());
        insert_update_delete_Automation(maisonAutomatisation, updateRequestOrder);
    }

    public void deleteAutomation(MaisonAutomatisation maisonAutomatisation)throws InterruptedException, IOException {
        logger.debug("deleteAutomation pour : {}", maisonAutomatisation.getNomAutomatisation());
        insert_update_delete_Automation(maisonAutomatisation, deleteRequestOrder);
    }

    public void insertAutomation(MaisonAutomatisation maisonAutomatisation)throws InterruptedException, IOException {
        logger.debug("insertAutomation pour : {}", maisonAutomatisation.getNomAutomatisation());
        insert_update_delete_Automation(maisonAutomatisation, insertRequestOrder);
    }



    public void insert_update_delete_Automation(MaisonAutomatisation maisonAutomatisation, String requestOrder) throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

        int birthdate = 0;

        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(maisonAutomatisation);
        logger.trace("Automation with its JSON face : {}", jsonifiedGuy);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedGuy);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final InsertAutomationClientRequest clientRequest = new InsertAutomationClientRequest(
                networkConfig,
                birthdate++, request, maisonAutomatisation, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest2.join();
            final MaisonAutomatisation maisonAutomatisation2 = (MaisonAutomatisation) clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    maisonAutomatisation2.getNomAutomatisation(), maisonAutomatisation2.getTypeCapteur(), maisonAutomatisation2.getTypeProgramme(), maisonAutomatisation2.getEtatAutomatisation(),
                    clientRequest2.getResult());
        }
    }

    public MaisonAutomatisations select_all_automation() throws InterruptedException, IOException {
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
        final SelectAllAutomationClientRequest clientRequest = new SelectAllAutomationClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (MaisonAutomatisations) joinedClientRequest.getResult();
        } else {
            logger.error("No automations found");
            return null;
        }
    }

    public MaisonAutomatisations select_name_automation() throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(selectNameAutomation);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllAutomationClientRequest clientRequest = new SelectAllAutomationClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (MaisonAutomatisations) joinedClientRequest.getResult();
        } else {
            logger.error("No automations found");
            return null;
        }
    }
}