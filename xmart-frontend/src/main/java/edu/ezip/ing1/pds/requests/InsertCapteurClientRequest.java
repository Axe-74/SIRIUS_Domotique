package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation;
import edu.ezip.ing1.pds.business.dto.MaisonCapteur;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertCapteurClientRequest extends ClientRequest<MaisonCapteur, String> {

    public InsertCapteurClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, MaisonCapteur info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        System.out.println("Réponse JSON du serveur : " + body);
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> capteurIdMap = mapper.readValue(body, Map.class);
        final String result  = capteurIdMap.get("id").toString();
        return result;
    }
}
