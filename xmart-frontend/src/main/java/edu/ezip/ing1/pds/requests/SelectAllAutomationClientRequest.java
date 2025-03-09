package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Maison_Automatisations;
import edu.ezip.ing1.pds.business.dto.Students;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;


import java.io.IOException;

public class SelectAllAutomationClientRequest extends ClientRequest<Object, Maison_Automatisations> {

    public SelectAllAutomationClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Maison_Automatisations readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Maison_Automatisations maison_automatisations = mapper.readValue(body, Maison_Automatisations.class);
        return maison_automatisations;
    }
}
