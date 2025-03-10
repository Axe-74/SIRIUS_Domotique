package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisations;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;


import java.io.IOException;

public class SelectAllAutomationClientRequest extends ClientRequest<Object, MaisonAutomatisations> {

    public SelectAllAutomationClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public MaisonAutomatisations readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final MaisonAutomatisations maison_automatisations = mapper.readValue(body, MaisonAutomatisations.class);
        return maison_automatisations;
    }
}
