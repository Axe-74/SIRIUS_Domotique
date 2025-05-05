package edu.ezip.ing1.pds.requests;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ezip.ing1.pds.business.dto.Capteur_Para_Type;
import edu.ezip.ing1.pds.business.dto.Capteur_Para_Types;
import edu.ezip.ing1.pds.business.dto.MaisonAutomatisation_Para_Type_Chauffage;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.commons.Request;

import java.io.IOException;
import java.util.Map;

public class InsertParaTypeCapteurClientRequest extends ClientRequest<Capteur_Para_Type, String> {
    public InsertParaTypeCapteurClientRequest(
            NetworkConfig networkConfig, int myBirthDate, Request request, Capteur_Para_Type info, byte[] bytes)
            throws IOException {
        super(networkConfig, myBirthDate, request, info, bytes);
    }

    @Override
    public String readResult(String body) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final Map<String, Integer> capteurparatypeIdMap = mapper.readValue(body, Map.class);
        final String result  = capteurparatypeIdMap.get("capteurparatype_id").toString();
        return result;
    }
}