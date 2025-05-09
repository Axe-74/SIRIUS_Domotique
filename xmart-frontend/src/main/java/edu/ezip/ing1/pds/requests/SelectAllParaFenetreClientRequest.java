package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation_Para_Fenetres;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation_Para_Jour_Semaines;
import edu.ezip.ing1.pds.business.dto.MaisonProgrammes;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import java.io.IOException;

public class SelectAllParaFenetreClientRequest extends ClientRequest<Object, MaisonAutomatisation_Para_Fenetres> {

    public SelectAllParaFenetreClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public MaisonAutomatisation_Para_Fenetres readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final MaisonAutomatisation_Para_Fenetres maisonAutomatisation_para_fenetres = mapper.readValue(body, MaisonAutomatisation_Para_Fenetres.class);
        return maisonAutomatisation_para_fenetres;
    }
}
