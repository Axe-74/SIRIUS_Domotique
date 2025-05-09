package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation_Para_Jour_Semaines;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation_Para_Lumieres;
import edu.ezip.ing1.pds.business.dto.MaisonProgrammes;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;
import java.io.IOException;

public class SelectAllParaLumiereClientRequest extends ClientRequest<Object, MaisonAutomatisation_Para_Lumieres> {

    public SelectAllParaLumiereClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public MaisonAutomatisation_Para_Lumieres readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final MaisonAutomatisation_Para_Lumieres maisonAutomatisation_para_lumieres = mapper.readValue(body, MaisonAutomatisation_Para_Lumieres.class);
        return maisonAutomatisation_para_lumieres;
    }
}
