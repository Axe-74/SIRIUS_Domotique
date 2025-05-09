package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonProgrammes;
import edu.ezip.ing1.pds.business.dto.MaisonProgrammesFenetres;
import edu.ezip.ing1.pds.business.dto.MaisonProgrammesLumieres;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import java.io.IOException;

public class SelectAllProgramsFenetresClientRequest extends ClientRequest<Object, MaisonProgrammesFenetres> {

    public SelectAllProgramsFenetresClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public MaisonProgrammesFenetres readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final MaisonProgrammesFenetres maisonProgrammesFenetres = mapper.readValue(body, MaisonProgrammesFenetres.class);
        return maisonProgrammesFenetres;
    }
}
