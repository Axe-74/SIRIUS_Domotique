package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Room_Para_Types;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllParaTypeRoomClientRequest extends ClientRequest<Object, Room_Para_Types> {

    public SelectAllParaTypeRoomClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Room_Para_Types readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Room_Para_Types room_Para_Types = mapper.readValue(body, Room_Para_Types.class);
        return room_Para_Types;
    }
}
