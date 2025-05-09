package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation_Para_Lumiere;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertParaLumiereClientRequest extends ClientRequest<MaisonAutomatisation_Para_Lumiere, String> {
    public InsertParaLumiereClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, MaisonAutomatisation_Para_Lumiere info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> automationparalumiereIdMap = mapper.readValue(body, Map.class);
        final String result  = automationparalumiereIdMap.get("automationparalumiere_id").toString();
        return result;
    }
}

