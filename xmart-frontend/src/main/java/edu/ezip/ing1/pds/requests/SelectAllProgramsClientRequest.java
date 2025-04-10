package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisations;
import edu.ezip.ing1.pds.business.dto.MaisonProgrammes;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import java.io.IOException;

public class SelectAllProgramsClientRequest extends ClientRequest<Object, MaisonProgrammes> {

    public SelectAllProgramsClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public MaisonProgrammes readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final MaisonProgrammes maisonProgrammes = mapper.readValue(body, MaisonProgrammes.class);
        return maisonProgrammes;
    }
}
