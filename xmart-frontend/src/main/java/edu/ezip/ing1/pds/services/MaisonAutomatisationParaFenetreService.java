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

public class MaisonAutomatisationParaFenetreService {
    private final static String LoggingLabel = "FrontEnd - MaisonAutomatisationParaFenetreService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    final String insertRequestOrder = "INSERT_NAME_WINDOW";
    final String selectRequestOrder = "SELECT_ALL_NAME_WINDOW";
    final String updateRequestOrder = "UPDATE_NAME_WINDOW";
    final String deleteRequestOrder = "DELETE_NAME_WINDOW";

    private final NetworkConfig networkConfig;

    public MaisonAutomatisationParaFenetreService(NetworkConfig networkConfig) throws InterruptedException {
        this.networkConfig = networkConfig;
    }

    public void updatename_day(MaisonAutomatisation_Para_Fenetre maisonAutomatisation_para_fenetre) throws InterruptedException, IOException {
        logger.debug("updateAutomation pour : {}", maisonAutomatisation_para_fenetre.getNom());
        insert_update_delete_Name_Day(maisonAutomatisation_para_fenetre, updateRequestOrder);
    }

    public void deletename_day(MaisonAutomatisation_Para_Fenetre maisonAutomatisation_para_fenetre) throws InterruptedException, IOException {
        logger.debug("deleteAutomation pour : {}", maisonAutomatisation_para_fenetre.getNom());
        insert_update_delete_Name_Day(maisonAutomatisation_para_fenetre, deleteRequestOrder);
    }

    public void insertname_day(MaisonAutomatisation_Para_Fenetre maisonAutomatisation_para_fenetre) throws InterruptedException, IOException {
        logger.debug("insertAutomation pour : {}", maisonAutomatisation_para_fenetre.getNom());
        insert_update_delete_Name_Day(maisonAutomatisation_para_fenetre, insertRequestOrder);
    }


    public void insert_update_delete_Name_Day(MaisonAutomatisation_Para_Fenetre maisonAutomatisation_para_fenetre, String requestOrder) throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

        int birthdate = 0;

        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(maisonAutomatisation_para_fenetre);
        logger.trace("Automation with its JSON face : {}", jsonifiedGuy);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedGuy);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final InsertParaFenetreClientRequest clientRequest = new InsertParaFenetreClientRequest(
                networkConfig,
                birthdate++, request, maisonAutomatisation_para_fenetre, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest2.join();
            final MaisonAutomatisation_Para_Fenetre maisonAutomatisation_para_fenetre1 = (MaisonAutomatisation_Para_Fenetre) clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    maisonAutomatisation_para_fenetre1.getID_Para_Fenetre(), maisonAutomatisation_para_fenetre.getNom(),
                    clientRequest2.getResult());
        }
    }

    public MaisonAutomatisation_Para_Fenetres select_all_name_day() throws InterruptedException, IOException {
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
        final SelectAllParaFenetreClientRequest clientRequest = new SelectAllParaFenetreClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (MaisonAutomatisation_Para_Fenetres) joinedClientRequest.getResult();
        } else {
            logger.error("No automations found");
            return null;
        }
    }
}
