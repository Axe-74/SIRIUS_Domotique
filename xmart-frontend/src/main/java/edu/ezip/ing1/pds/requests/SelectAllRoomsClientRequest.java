package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonRooms;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllRoomsClientRequest extends ClientRequest<Object, MaisonRooms> {

    public SelectAllRoomsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public MaisonRooms readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final MaisonRooms maisonRooms = mapper.readValue(body, MaisonRooms.class);
        return maisonRooms;
    }
}
