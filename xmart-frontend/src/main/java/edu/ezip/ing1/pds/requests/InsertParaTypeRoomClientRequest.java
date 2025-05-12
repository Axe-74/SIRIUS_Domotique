package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Room_Para_Type;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertParaTypeRoomClientRequest extends ClientRequest<Room_Para_Type, String> {
    public InsertParaTypeRoomClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Room_Para_Type info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> roomparatypeIdMap = mapper.readValue(body, Map.class);
        final String result  = roomparatypeIdMap.get("roomparatype_id").toString();
        return result;
    }
}