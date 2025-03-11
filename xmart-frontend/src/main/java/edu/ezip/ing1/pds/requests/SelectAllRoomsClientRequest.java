package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Rooms;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllRoomsClientRequest extends ClientRequest<Object, Rooms> {

    public SelectAllRoomsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Rooms readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Rooms rooms = mapper.readValue(body, Rooms.class);
        return rooms;
    }
}
