package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation;
import edu.ezip.ing1.pds.business.dto.MaisonProgramme;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertProgramClientRequest extends ClientRequest<MaisonProgramme, String> {

    public InsertProgramClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, MaisonProgramme info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> programIdMap = mapper.readValue(body, Map.class);
        final String result  = programIdMap.get("program_id").toString();
        return result;
    }
}
