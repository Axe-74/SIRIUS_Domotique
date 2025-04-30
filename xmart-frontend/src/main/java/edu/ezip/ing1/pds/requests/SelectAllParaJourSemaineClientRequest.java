package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation_Para_Jour_Semaines;
import edu.ezip.ing1.pds.business.dto.MaisonProgrammes;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import java.io.IOException;

public class SelectAllParaJourSemaineClientRequest extends ClientRequest<Object, MaisonAutomatisation_Para_Jour_Semaines> {

    public SelectAllParaJourSemaineClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public MaisonAutomatisation_Para_Jour_Semaines readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final MaisonAutomatisation_Para_Jour_Semaines maisonAutomatisation_para_jour_semaines = mapper.readValue(body, MaisonAutomatisation_Para_Jour_Semaines.class);
        return maisonAutomatisation_para_jour_semaines;
    }
}
