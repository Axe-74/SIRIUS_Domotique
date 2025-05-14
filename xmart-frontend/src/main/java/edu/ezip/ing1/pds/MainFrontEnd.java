package edu.ezip.ing1.pds;

import edu.ezip.ing1.pds.client.commons.ClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.ezip.ing1.pds.application.Application;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class MainFrontEnd {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    public static void main(String[] args) throws IOException, InterruptedException {
        Application application = new Application();
        application.initialize();
    }
}
