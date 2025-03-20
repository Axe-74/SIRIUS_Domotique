package edu.ezip.ing1.pds.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.ezip.commons.LoggingUtils;
import edu.ezip.ing1.pds.business.dto.MaisonCapteur;
import edu.ezip.ing1.pds.business.dto.MaisonRoom;
import edu.ezip.ing1.pds.business.dto.MaisonRooms;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import edu.ezip.ing1.pds.requests.InsertRoomsClientRequest;
import edu.ezip.ing1.pds.requests.SelectAllRoomsClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;

public class MaisonRoomService {
    private final static String LoggingLabel = "FrontEnd - MaisonRoomService";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String roomsToBeInserted = "rooms-to-be-inserted.yaml";

    final String insertRequestOrder = "INSERT_ROOM";
    final String selectRequestOrder = "SELECT_ALL_ROOMS";
    final String updateRequestOrder = "UPDATE_ROOM";
    final String deleteRequestOrder = "DELETE_ROOM";


    private final NetworkConfig networkConfig;

    public MaisonRoomService(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public void insertRoom(MaisonRoom maisonRoom) throws InterruptedException, IOException {
        iudRoom(maisonRoom, insertRequestOrder);
    }

    public void updateRoom(MaisonRoom maisonRoom) throws InterruptedException, IOException {
        iudRoom(maisonRoom, updateRequestOrder);
    }

    public void deleteRoom(MaisonRoom maisonRoom)throws InterruptedException, IOException {
        logger.debug("deleteRoom pour : {}", maisonRoom.getName());
        iudRoom(maisonRoom, deleteRequestOrder);
    }

    public void iudRoom(MaisonRoom maisonRoom, String requestOrder) throws InterruptedException, IOException {
        final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

        int birthdate = 0;

        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonifiedGuy = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(maisonRoom);
        logger.trace("Room with its JSON face : {}", jsonifiedGuy);
        final String requestId = UUID.randomUUID().toString();
        final Request request = new Request();
        request.setRequestId(requestId);
        request.setRequestOrder(requestOrder);
        request.setRequestContent(jsonifiedGuy);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        final byte []  requestBytes = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(request);

        final InsertRoomsClientRequest clientRequest = new InsertRoomsClientRequest(
                    networkConfig,
                    birthdate++, request, maisonRoom, requestBytes);
            clientRequests.push(clientRequest);


        while (!clientRequests.isEmpty()) {
            final ClientRequest clientRequest2 = clientRequests.pop();
            clientRequest2.join();
            final MaisonRoom maisonRoom1 = (MaisonRoom)clientRequest2.getInfo();
            logger.debug("Thread {} complete : {} {} {} --> {}",
                    clientRequest2.getThreadName(),
                    maisonRoom1.getName(), maisonRoom1.getType(), maisonRoom1.getSurface(),
                    clientRequest2.getResult());
        }
    }

    public MaisonRooms selectRooms() throws InterruptedException, IOException {
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
        final SelectAllRoomsClientRequest clientRequest = new SelectAllRoomsClientRequest(
                networkConfig,
                birthdate++, request, null, requestBytes);
        clientRequests.push(clientRequest);

        if(!clientRequests.isEmpty()) {
            final ClientRequest joinedClientRequest = clientRequests.pop();
            joinedClientRequest.join();
            logger.debug("Thread {} complete.", joinedClientRequest.getThreadName());
            return (MaisonRooms) joinedClientRequest.getResult();
        }
        else {
            logger.error("No rooms found");
            return null;
        }
    }
}
