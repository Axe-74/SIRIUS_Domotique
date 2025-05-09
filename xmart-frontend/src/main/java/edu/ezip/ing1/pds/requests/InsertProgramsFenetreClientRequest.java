package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation;
import edu.ezip.ing1.pds.business.dto.MaisonProgramme;
import edu.ezip.ing1.pds.business.dto.MaisonProgrammeFenetre;
import edu.ezip.ing1.pds.business.dto.MaisonProgrammeLumiere;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertProgramsFenetreClientRequest extends ClientRequest<MaisonProgrammeFenetre, String> {

    public InsertProgramsFenetreClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, MaisonProgrammeFenetre info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> programFenetreIdMap = mapper.readValue(body, Map.class);
        final String result  = programFenetreIdMap.get("program_id").toString();
        return result;
    }
}
