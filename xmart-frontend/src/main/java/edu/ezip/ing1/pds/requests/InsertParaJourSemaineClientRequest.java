package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation_Para_Jour_Semaine;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertParaJourSemaineClientRequest extends ClientRequest<MaisonAutomatisation_Para_Jour_Semaine, String> {
    public InsertParaJourSemaineClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, MaisonAutomatisation_Para_Jour_Semaine info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> automationparajoursemaineIdMap = mapper.readValue(body, Map.class);
        final String result  = automationparajoursemaineIdMap.get("automationparajoursemaine_id").toString();
        return result;
    }
}

