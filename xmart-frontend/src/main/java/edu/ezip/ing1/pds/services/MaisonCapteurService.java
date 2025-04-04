package edu.ezip.ing1.pds.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.InsertCapteurClientRequest;
import edu.ezip.ing1.pds.requests.SelectAllCapteursClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class MaisonCapteurService {
    private final static String LoggingLabel = "FrontEnd - CapteurService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    //private final static String studentsToBeInserted = "students-to-be-inserted.yaml";

    final String insertRequestOrder = "INSERT_CAPTEUR";
    final String selectRequestOrder = "SELECT_ALL_CAPTEURS";
    final String updateRequestOrder = "UPDATE_CAPTEUR";
    final String deleteRequestOrder = "DELETE_CAPTEUR";

    private final NetworkConfig networkConfig;

    public MaisonCapteurService(NetworkConfig networkConfig) throws InterruptedException {
        this.networkConfig = networkConfig;
    }

    public void insertCapteur(MaisonCapteur maisonCapteur)throws InterruptedException, IOException {
        logger.debug("insertCapteur pour : {}", maisonCapteur.getName());
        iudCapteurs(maisonCapteur, insertRequestOrder);
    }

    public void updateCapteur(MaisonCapteur maisonCapteur)throws InterruptedException, IOException {
        logger.debug("updateCapteur pour : {}", maisonCapteur.getName());
        iudCapteurs(maisonCapteur, updateRequestOrder);
    }

    public void deleteCapteur(MaisonCapteur maisonCapteur)throws InterruptedException, IOException {
        logger.debug("deleteCapteur pour : {}", maisonCapteur.getName());
        iudCapteurs(maisonCapteur, deleteRequestOrder);
    }

    public void iudCapteurs(MaisonCapteur maisonCapteur, String requestOrder) throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
//        final MaisonCapteurs guys = ConfigLoader.loadConfig(MaisonCapteurs.class, studentsToBeInserted);

        int birthdate = 0;

        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(maisonCapteur);
        logger.trace("Capteur with its JSON face : {}", jsonifiedGuy);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedGuy);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte[] requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final InsertCapteurClientRequest clientRequest = new InsertCapteurClientRequest(
                networkConfig,
                birthdate++, request, maisonCapteur, requestBytes);
        clientRequests.push(clientRequest);

        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest2.join();
            final MaisonCapteur maisonCapteur2 = (MaisonCapteur) clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    maisonCapteur2.getName(), maisonCapteur2.getTypecapteur(), maisonCapteur2.getEtat(),
                    clientRequest2.getResult());
        }
    }

    public MaisonCapteurs selectAllCapteurs() throws InterruptedException, IOException {
        int birthdate = 0;
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(selectRequestOrder);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);
        LoggingUtils.logDataMultiLine(logger, Level.TRACE, requestBytes);
        final SelectAllCapteursClientRequest clientRequest = new SelectAllCapteursClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if(!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (MaisonCapteurs) joinedClientRequest.getResult();
        }
        else {
            logger.error("Pas trouvé de capteurs.");
            return null;
        }
    }

}
