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

public class MaisonAutomatisationParaTypeChauffageService {
    private final static String LoggingLabel = "FrontEnd - MaisonAutomatisationParaTypeChauffageService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);

    final String insertRequestOrder = "INSERT_NAME_HEATER";
    final String selectRequestOrder = "SELECT_ALL_NAME_HEATER";
    final String selectNameAutomation = "SELECT_NAME_AUTOMATION";
    final String updateRequestOrder = "UPDATE_NAME_DAY";
    final String deleteRequestOrder = "DELETE_NAME_DAY";

    private final NetworkConfig networkConfig;

    public MaisonAutomatisationParaTypeChauffageService(NetworkConfig networkConfig) throws InterruptedException {
        this.networkConfig = networkConfig;
    }

    public void updatename_heater(MaisonAutomatisation_Para_Type_Chauffage maisonAutomatisation_para_type_chauffage) throws InterruptedException, IOException {
        logger.debug("updateAutomation pour : {}", maisonAutomatisation_para_type_chauffage.getNom());
        insert_update_delete_Name_heater(maisonAutomatisation_para_type_chauffage, updateRequestOrder);
    }

    public void deletename_heater(MaisonAutomatisation_Para_Type_Chauffage maisonAutomatisation_para_type_chauffage) throws InterruptedException, IOException {
        logger.debug("deleteAutomation pour : {}", maisonAutomatisation_para_type_chauffage.getNom());
        insert_update_delete_Name_heater(maisonAutomatisation_para_type_chauffage, deleteRequestOrder);
    }

    public void insertname_heater(MaisonAutomatisation_Para_Type_Chauffage maisonAutomatisation_para_type_chauffage) throws InterruptedException, IOException {
        logger.debug("insertAutomation pour : {}", maisonAutomatisation_para_type_chauffage.getNom());
        insert_update_delete_Name_heater(maisonAutomatisation_para_type_chauffage, insertRequestOrder);
    }


    public void insert_update_delete_Name_heater(MaisonAutomatisation_Para_Type_Chauffage maisonAutomatisation_para_type_chauffage, String requestOrder) throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

        int birthdate = 0;

        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(maisonAutomatisation_para_type_chauffage);
        logger.trace("Automation with its JSON face : {}", jsonifiedGuy);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedGuy);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final InsertParaTypeChauffageClientRequest clientRequest = new InsertParaTypeChauffageClientRequest(
                networkConfig,
                birthdate++, request, maisonAutomatisation_para_type_chauffage, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest2.join();
            final MaisonAutomatisation_Para_Type_Chauffage maisonAutomatisation_para_type_chauffage1 = (MaisonAutomatisation_Para_Type_Chauffage) clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    maisonAutomatisation_para_type_chauffage1.getID_Para_Jour_Chauffage(), maisonAutomatisation_para_type_chauffage1.getNom(),
                    clientRequest2.getResult());
        }
    }

    public MaisonAutomatisation_Para_Type_Chauffages select_all_name_heater() throws InterruptedException, IOException {
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
        final SelectAllParaTypeChauffageClientRequest clientRequest = new SelectAllParaTypeChauffageClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if (!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (MaisonAutomatisation_Para_Type_Chauffages) joinedClientRequest.getResult();
        } else {
            logger.error("No automations found");
            return null;
        }
    }
}

//    public MaisonAutomatisations select_name_automation() throws InterruptedException, IOException {
//        int birthdate = 0;
//        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
//        final ObjectMapper objectMapper = new ObjectMapper();
//        final String requestId = UUID.randomUUID().toString();
//        final Request request = new Request();
//        request.setRequestId(requestId);
//        request.setRequestOrder(selectNameAutomation);
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
//}