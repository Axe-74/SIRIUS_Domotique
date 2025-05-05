package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Capteur_Para_Types;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation_Para_Type_Chauffages;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;

public class SelectAllParaTypeCapteurClientRequest extends ClientRequest<Object, Capteur_Para_Types> {

    public SelectAllParaTypeCapteurClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Object info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public Capteur_Para_Types readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Capteur_Para_Types capteur_Para_Types = mapper.readValue(body, Capteur_Para_Types.class);
        return capteur_Para_Types;
    }
}
