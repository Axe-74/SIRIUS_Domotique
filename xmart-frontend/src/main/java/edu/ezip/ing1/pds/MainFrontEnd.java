package edu.ezip.ing1.pds;

import de.vandermeer.asciitable.AsciiTable;
import edu.ezip.ing1.pds.business.dto.Maison_Automatisation;
import edu.ezip.ing1.pds.business.dto.Maison_Automatisations;
import edu.ezip.ing1.pds.business.dto.Student;
import edu.ezip.ing1.pds.business.dto.Students;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;
import edu.ezip.ing1.pds.services.Maison_AutomatisationService;
import edu.ezip.ing1.pds.services.StudentService;
import edu.ezip.ing1.pds.services.Maison_AutomatisationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.swing.*;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class MainFrontEnd {

    private final static String LoggingLabel = "FrontEnd";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();

    public static void main(String[] args) throws IOException, InterruptedException {
        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());

//        final StudentService studentService = new StudentService(networkConfig);
//        studentService.insertStudents();
//        Students students = studentService.selectStudents();
//        final AsciiTable asciiTable = new AsciiTable();
//        for (final Student student : students.getStudents()) {
//            asciiTable.addRule();
//            asciiTable.addRow(student.getFirstname(), student.getName(), student.getGroup());
//        }
//        asciiTable.addRule();


//        final Maison_AutomatisationService maison_automatisationService = new Maison_AutomatisationService(networkConfig);
//        //maison_automatisationService.insertStudents();
//        Maison_Automatisations maison_automatisations = maison_automatisationService.select_all_automation();
//        final AsciiTable asciiTable = new AsciiTable();
//        for (final Maison_Automatisation maison_automatisation  : maison_automatisations.getMaison_Automatisations()) {
//            asciiTable.addRule();
//            asciiTable.addRow(maison_automatisation.getNomAutomatisation(), maison_automatisation.getTypeCapteur(), maison_automatisation.getTypeProgramme());
//        }
//        asciiTable.addRule();
//
//
//        Maison_AutomatisationService.insertStudents();

//        Maison_Automatisations maison_automatisations = Maison_AutomatisationService.Sel();
//        final AsciiTable asciiTable = new AsciiTable();
//        for (final Maison_Automatisation maison_automatisation : Maison_Automatisations.get()) {
//            asciiTable.addRule();
//            asciiTable.addRow(student.getFirstname(), student.getName(), student.getGroup());
//        }
//        asciiTable.addRule();
//        logger.debug("\n{}\n", asciiTable.render());


        //logger.debug("Load Network...");
        //App. aa = new app("");
        Application application = new Application();
        application.initialize();

        //logger.debug("Load Network after");
    }
}
