package edu.ezip.ing1.pds.application;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.services.MaisonAutomatisationService;
import edu.ezip.ing1.pds.services.MaisonCapteurService;
import edu.ezip.ing1.pds.services.MaisonProgrammeService;
import edu.ezip.ing1.pds.services.MaisonRoomService;
import edu.ezip.ing1.pds.client.commons.ClientRequest;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application {
    public JFrame frame;
    public ArrayList<MaisonProgrammes> programmes = new ArrayList<MaisonProgrammes>();
    public ArrayList<MaisonAutomatisations> automatisations = new ArrayList<MaisonAutomatisations>();
    public ArrayList<MaisonCapteurs> capteurs = new ArrayList<>(); {}
    public ArrayList<MaisonRooms> rooms = new ArrayList<>();
    public ArrayList<String> automatisationsNoms = new ArrayList<String>();
    public ArrayList<String> programmesNoms = new ArrayList<>();
    public ArrayList<String> capteursNoms_cE = new ArrayList<>();
    private final static String LoggingLabel = "Application";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    private static final Deque<ClientRequest> clientRequests = new ArrayDeque<ClientRequest>();
    final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    final MaisonCapteurService update_delete_Capteur;
    final MaisonAutomatisationService update_automatisation;
    {
        try {
            update_delete_Capteur = new MaisonCapteurService(networkConfig);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    {
        try {
            update_automatisation = new MaisonAutomatisationService(networkConfig);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void initialize() {

        frame = new JFrame("Domotique maison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 350);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        // BD connexion
        String url = "jdbc:mysql://172.0.0.1:3306/domotique";
        String username = "root";
        String password = "Zozoleplubo@2005";

        final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
        logger.debug("Load Network config file : {}", networkConfig.toString());


// Main Menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 1));
        JButton btnAutomations_and_programs = new JButton(" Automatisations et programmes");
        JButton btnSensorsManagement = new JButton("Gestion des capteurs");
        JButton btnHouseManagement = new JButton("Gestion de la maison");
        menuPanel.add(btnAutomations_and_programs);
        menuPanel.add(btnSensorsManagement);
        menuPanel.add(btnHouseManagement);
        mainPanel.add(menuPanel, "MenuPanel");



// Automations and programs Menu Panel
        JPanel Automations_and_programsPanel = new JPanel();
        Automations_and_programsPanel.setLayout(new GridLayout(6, 1));

        JButton btnNewAutomations = new JButton("Définir une nouvelle automatisation");
        JButton btnViewAutomations = new JButton("Voir les automatisations");
        JButton btnNewPrograms = new JButton("Définir un nouveau programme");
        JButton btnViewPrograms = new JButton("Voir les programmes");
        JButton btnEtatAutomation = new JButton("Changer l'état d'une automatisation");
        JButton btnBacktoMainmenu = new JButton("Retour au menu principal");

        Automations_and_programsPanel.add(btnNewAutomations);
        Automations_and_programsPanel.add(btnViewAutomations);
        Automations_and_programsPanel.add(btnEtatAutomation);
        Automations_and_programsPanel.add(btnNewPrograms);
        Automations_and_programsPanel.add(btnViewPrograms);
        Automations_and_programsPanel.add(btnBacktoMainmenu);

        mainPanel.add(Automations_and_programsPanel, "Automations_and_ProgramsPanel");




// Capteurs Menu Panel
        JPanel CapteursPanel = new JPanel();
        CapteursPanel.setLayout(new GridLayout(5, 1));

        JButton btnNewCapteur = new JButton("Définir un nouveau capteur");
        JButton btnVoirCapteurs = new JButton("Voir les capteurs");
        JButton btnChangerEtat = new JButton("Changer l'état des capteurs");
        JButton btnRetourCapteurs = new JButton("Retour");
        JButton btnSupprimerCapteur = new JButton("Supprimer un capteur");

        CapteursPanel.add(btnNewCapteur);
        CapteursPanel.add(btnVoirCapteurs);
        CapteursPanel.add(btnChangerEtat);
        CapteursPanel.add(btnSupprimerCapteur);
        CapteursPanel.add(btnRetourCapteurs);

        mainPanel.add(CapteursPanel, "CapteursPanel");




// Gestion de la maison Menu Panel
        JPanel HouseManagementPanel = new JPanel();
        HouseManagementPanel.setLayout(new GridLayout(3, 1));

        JButton btnNewRoom = new JButton("Nouvelle pièce");
        JButton btnViewRoom = new JButton("Mes pièces");
        JButton btnBackToMenu_Room = new JButton("Retour");

        HouseManagementPanel.add(btnNewRoom);
        HouseManagementPanel.add(btnViewRoom);
        HouseManagementPanel.add(btnBackToMenu_Room);

        mainPanel.add(HouseManagementPanel, "HouseManagementPanel");



// Automation Definition Panel
        JPanel AutomationPanel = new JPanel();
        AutomationPanel.setLayout(new GridLayout(6, 2));

        JLabel lblAutomationName = new JLabel("Nom de l'automatisation:");
        JTextField txtAutomationName = new JTextField();

        JLabel lblSensor_activation = new JLabel("Activation du capteur: ");
        JComboBox<String> cbSensor_activation = new JComboBox<>(new String[]{
                "Capteur 1", "Capteur 2", "Capteur 3", "Capteur 4", "Capteur 5"
        });
        //JComboBox<Maison_Automatisation.TypeCapteurs> cbSensor_activation = new JComboBox<>(Maison_Automatisation.TypeCapteurs.values());

        JLabel lblSensor_program = new JLabel("Execution du programme:");
        JComboBox<String> cbSensor_programme = new JComboBox<>(new String[]{
                "Programme 1", "Programme 2", "Programme 3", "Programme 4", "Programme 5"
        });
        //JComboBox<Maison_Automatisation.TypeProgramme> cbSensor_programme = new JComboBox<>(Maison_Automatisation.TypeProgramme.values());

        JLabel lblEtatAutomatisation = new JLabel("Etat de l'automatisation:");
        JCheckBox cbEtatAutomatisation = new JCheckBox();
        cbEtatAutomatisation.setSelected(true);

        JLabel lblExplicationAutomatisationOFF = new JLabel("Pas coché = OFF");
        JLabel lblExplicationAutomatisationON = new JLabel("Coché = ON");

        JButton btnSaveAutomation = new JButton("Enregistrer");
        JButton btnBackToMenu_Automation = new JButton("Retour au menu");

        AutomationPanel.add(lblAutomationName);
        AutomationPanel.add(txtAutomationName);
        AutomationPanel.add(lblSensor_activation);
        AutomationPanel.add(cbSensor_activation);
        AutomationPanel.add(lblSensor_program);
        AutomationPanel.add(cbSensor_programme);
        AutomationPanel.add(lblExplicationAutomatisationON);
        AutomationPanel.add(lblExplicationAutomatisationOFF);
        AutomationPanel.add(lblEtatAutomatisation);
        AutomationPanel.add(cbEtatAutomatisation);
        AutomationPanel.add(btnSaveAutomation);
        AutomationPanel.add(btnBackToMenu_Automation);

        mainPanel.add(AutomationPanel, "AutomationPanel");



//Etat Automatisation panel
        JPanel EtatAutomatisationPanel = new JPanel();
        EtatAutomatisationPanel.setLayout(new GridLayout(3, 2));

        JLabel lblChoixAutomatisation = new JLabel("Automatisation:");
        JLabel lblEtatAutomatisation_EtatAutomatisation= new JLabel("Etat:");

        JComboBox<String> cbAutomatisationsExistantes = new JComboBox<>(new String[]{
        });
        JComboBox<String> cbEtatAutomatisation_ChangerEtat = new JComboBox<>(new String[]{
                "ON", "OFF"
        });

        JButton btnEnregistrerEtatAutomatisation = new JButton("Enregistrer");
        JButton btnBackToMenu_ChangerEtatAutomatisation = new JButton("Retour au menu");

        EtatAutomatisationPanel.add(lblChoixAutomatisation);
        EtatAutomatisationPanel.add(lblEtatAutomatisation_EtatAutomatisation);
        EtatAutomatisationPanel.add(cbAutomatisationsExistantes);
        EtatAutomatisationPanel.add(cbEtatAutomatisation_ChangerEtat);
        EtatAutomatisationPanel.add(btnEnregistrerEtatAutomatisation);
        EtatAutomatisationPanel.add(btnBackToMenu_ChangerEtatAutomatisation);

        mainPanel.add(EtatAutomatisationPanel, "EtatAutomatisationPanel");



//View Automation panel
        JPanel viewAutomationPanel = new JPanel();
        viewAutomationPanel.setLayout(new BorderLayout());

        JTextArea txtAutomations = new JTextArea();
        txtAutomations.setEditable(false);
        JScrollPane scrollPaneAutomation = new JScrollPane(txtAutomations);

        JButton btnBackToMenuAutomationProgramm = new JButton("Retour au menu");
        viewAutomationPanel.add(scrollPaneAutomation, BorderLayout.CENTER);
        viewAutomationPanel.add(btnBackToMenuAutomationProgramm, BorderLayout.SOUTH);

        mainPanel.add(viewAutomationPanel, "ViewAutomationPanel");



// Program Definition Panel
        JPanel ProgramPanel = new JPanel();
        ProgramPanel.setLayout(new GridLayout(8, 2));

        JLabel lblProgramName = new JLabel("Nom du programme:");
        JTextField txtProgramName = new JTextField();

        JLabel lblPiece = new JLabel("Pièce:");
        JComboBox<String> cbPiece = new JComboBox<>(new String[]{
                "Entree", "Cuisine", "Salon", "Salle de bain", "Chambre parents", "Chambre enfants 1", "Chambre enfants 2", "Chambre enfants 3", "Chambre enfants 4"
        });
//        JComboBox<Maison_programme.TypePiece> cbPiece = new JComboBox<>(Maison_programme.TypePiece.values());

        JLabel lblChauffage = new JLabel("Type de chauffage:");
        JComboBox<String> cbChauffage = new JComboBox<>(new String[]{
                "Radiateur", "Seche-serviette"
        });
        //JComboBox<Maison_programme.TypeChauffage> cbChauffage = new JComboBox<>(Maison_programme.TypeChauffage.values());

        JLabel lblTemperature = new JLabel("Température:");
        JSpinner spTemperature = new JSpinner(new SpinnerNumberModel(20, 10, 30, 1));

        JLabel lblJour = new JLabel("Jour:");
        JComboBox<String> cbJour = new JComboBox<>(new String[]{
                "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"
        });
        //JComboBox<Maison_programme.JoursSemaine> cbJour = new JComboBox<>(Maison_programme.JoursSemaine.values());

        JLabel lblHeureDebut = new JLabel("Heure de début:");
        JSpinner spHeureDebut = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));

        JLabel lblHeureFin = new JLabel("Heure de fin:");
        JSpinner spHeureFin = new JSpinner(new SpinnerNumberModel(0, 0, 24, 1));

        JButton btnSaveProgram = new JButton("Enregistrer");
        JButton btnBackToMenu_Program = new JButton("Retour au menu");

        ProgramPanel.add(lblProgramName);
        ProgramPanel.add(txtProgramName);
        ProgramPanel.add(lblPiece);
        ProgramPanel.add(cbPiece);
        ProgramPanel.add(lblChauffage);
        ProgramPanel.add(cbChauffage);
        ProgramPanel.add(lblTemperature);
        ProgramPanel.add(spTemperature);
        ProgramPanel.add(lblJour);
        ProgramPanel.add(cbJour);
        ProgramPanel.add(lblHeureDebut);
        ProgramPanel.add(spHeureDebut);
        ProgramPanel.add(lblHeureFin);
        ProgramPanel.add(spHeureFin);
        ProgramPanel.add(btnSaveProgram);
        ProgramPanel.add(btnBackToMenu_Program);

        mainPanel.add(ProgramPanel, "ProgramPanel");



// NewCapteur Panel
        JPanel NewCapteursPanel = new JPanel();
        NewCapteursPanel.setLayout(new GridLayout(5, 2));

        JLabel lblNomCapteur = new JLabel("Nom du capteur:");
        JTextField txtNomCapteur = new JTextField();

        JLabel lblTypeCapteur = new JLabel("Type du capteur: ");
        JComboBox<String> cbTypeCapteur = new JComboBox<>(new String[]{
                "TEMPERATURE", "LUMINOSITE", "MOUVEMENT"
        });

        JLabel lblEtatCapteur = new JLabel("Etat du capteur:");
        JCheckBox cbEtatCapteur = new JCheckBox();
        cbEtatCapteur.setSelected(true);

        JLabel lblExplicationOFF = new JLabel("Pas coché = OFF");
        JLabel lblExplicationON = new JLabel("Coché = ON");

        JButton btnSaveCapteur = new JButton("Enregistrer");
        JButton btnBackToMenu_NewCapteur = new JButton("Retour au menu");

        NewCapteursPanel.add(lblNomCapteur);
        NewCapteursPanel.add(txtNomCapteur);
        NewCapteursPanel.add(lblTypeCapteur);
        NewCapteursPanel.add(cbTypeCapteur);
        NewCapteursPanel.add(lblEtatCapteur);
        NewCapteursPanel.add(cbEtatCapteur);
        NewCapteursPanel.add(lblExplicationOFF);
        NewCapteursPanel.add(lblExplicationON);
        NewCapteursPanel.add(btnSaveCapteur);
        NewCapteursPanel.add(btnBackToMenu_NewCapteur);

        mainPanel.add(NewCapteursPanel, "NewCapteursPanel");



// Nouvelle piece panel
        JPanel pnlRoom = new JPanel();
        pnlRoom.setLayout(new GridLayout(4, 2));

        JLabel lblNameRoom = new JLabel("Nom de la pièce :");
        JTextField txtNameRoom = new JTextField();

        JLabel lblTypeRoom = new JLabel("Type de pièce : ");
//        JComboBox<Maison_Room.TypeRoom> cbTypeRoom = new JComboBox<>(Maison_Room.TypeRoom.values());
        JComboBox<String> cbTypeRoom = new JComboBox<>(new String[]{
                "Entree", "Salon", "Cuisine", "Salle_de_bain", "Toilettes", "Chambre", "Autre"
        });

        JLabel lblSurfaceRoom = new JLabel("Surface de la pièce (en m²) :");
        JSpinner spSurfaceRoom = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));

        JButton btnBackToMenuNewRoom = new JButton("Retour au menu");
        JButton btnSaveRoom = new JButton("Enregistrer");

        pnlRoom.add(lblNameRoom);
        pnlRoom.add(txtNameRoom);
        pnlRoom.add(lblTypeRoom);
        pnlRoom.add(cbTypeRoom);
        pnlRoom.add(lblSurfaceRoom);
        pnlRoom.add(spSurfaceRoom);
        pnlRoom.add(btnBackToMenuNewRoom);
        pnlRoom.add(btnSaveRoom);

        mainPanel.add(pnlRoom, "RoomPanel");



//View Programm panel
        JPanel viewProgramsPanel = new JPanel();
        viewProgramsPanel.setLayout(new BorderLayout());

        JTextArea txtPrograms = new JTextArea();
        txtPrograms.setEditable(false);

        JScrollPane scrollPaneProgram = new JScrollPane(txtPrograms);

        JButton btnBackToMenuViewProgramm = new JButton("Retour au menu");
        viewProgramsPanel.add(scrollPaneProgram, BorderLayout.CENTER);
        viewProgramsPanel.add(btnBackToMenuViewProgramm, BorderLayout.SOUTH);

        mainPanel.add(viewProgramsPanel, "ViewProgramsPanel");



// Voir Capteurs
        JPanel voirCapteurPanel = new JPanel();
        voirCapteurPanel.setLayout(new BorderLayout());

        JTextArea txtCapteurs = new JTextArea();
        txtCapteurs.setEditable(false);

        JScrollPane scrollPane_capteurs = new JScrollPane(txtCapteurs);
        JButton btnBackToMenu_VoirCapteurs = new JButton("Retour au menu");

        voirCapteurPanel.add(scrollPane_capteurs, BorderLayout.CENTER);
        voirCapteurPanel.add(btnBackToMenu_VoirCapteurs, BorderLayout.SOUTH);

        mainPanel.add(voirCapteurPanel, "voirCapteurPanel");


// Voir Rooms
        JPanel voirRoomPanel = new JPanel();
        voirRoomPanel.setLayout(new BorderLayout());

        JTextArea txtRooms = new JTextArea();
        txtRooms.setEditable(false);

        JScrollPane scrollPane_rooms = new JScrollPane(txtRooms);
        JButton btnBackToMenu_VoirRooms = new JButton("Retour au menu");

        voirRoomPanel.add(scrollPane_rooms, BorderLayout.CENTER);
        voirRoomPanel.add(btnBackToMenu_VoirRooms, BorderLayout.SOUTH);

        mainPanel.add(voirRoomPanel, "voirRoomPanel");



// Changer Etat Capteur
        JPanel EtatCapteurPanel = new JPanel();
        EtatCapteurPanel.setLayout(new GridLayout(3, 2));

        JLabel lblChoixCapteur = new JLabel("Capteur:");
        JLabel lblEtatCapteur_EtatCapteur = new JLabel("Etat:");

        JComboBox<String> cbCapteursExistants = new JComboBox<>(new String[]{
        });
        JComboBox<String> cbEtatCapteur_ChangerEtat = new JComboBox<>(new String[]{
                "ON", "OFF"
        });

        JButton btnEnregistrerEtatCapteur = new JButton("Enregistrer");
        JButton btnBackToMenu_ChangerEtat = new JButton("Retour au menu");

        EtatCapteurPanel.add(lblChoixCapteur);
        EtatCapteurPanel.add(lblEtatCapteur_EtatCapteur);
        EtatCapteurPanel.add(cbCapteursExistants);
        EtatCapteurPanel.add(cbEtatCapteur_ChangerEtat);
        EtatCapteurPanel.add(btnEnregistrerEtatCapteur);
        EtatCapteurPanel.add(btnBackToMenu_ChangerEtat);

        mainPanel.add(EtatCapteurPanel, "EtatCapteurPanel");



//Capteur Supprimer Panel
        JPanel SupprimerCapteurPanel = new JPanel();
        SupprimerCapteurPanel.setLayout(new GridLayout(4, 1));

        JLabel lblSupprimerCapteur = new JLabel("Supprimer un capteur:");
        JComboBox<String> cbCapteursExistants_Supp = new JComboBox<>(new String[]{});

        JButton btnEnregistrerSupprimerCapteur = new JButton("Supprimer");
        JButton btnBackToMenu_SupprimerCapteur = new JButton("Retour au menu");

        SupprimerCapteurPanel.add(lblSupprimerCapteur);
        SupprimerCapteurPanel.add(cbCapteursExistants_Supp);
        SupprimerCapteurPanel.add(btnEnregistrerSupprimerCapteur);
        SupprimerCapteurPanel.add(btnBackToMenu_SupprimerCapteur);

        mainPanel.add(SupprimerCapteurPanel, "SupprimerCapteurPanel");


        // Events
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        //Retour à l'accueil
        btnBacktoMainmenu.addActionListener(e -> cardLayout.show(mainPanel, "MenuPanel"));
        btnBackToMenu_Room.addActionListener(e -> cardLayout.show(mainPanel, "MenuPanel"));
        btnRetourCapteurs.addActionListener(e -> cardLayout.show(mainPanel, "MenuPanel"));

        //Retour Menu Automatisations et Programmes
        btnBackToMenu_Automation.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnBackToMenuViewProgramm.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnBackToMenuAutomationProgramm.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnBackToMenu_Program.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnAutomations_and_programs.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnBackToMenu_ChangerEtatAutomatisation.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));


        //Boutons Automatisations et Programmes
        btnNewAutomations.addActionListener(e -> cardLayout.show(mainPanel, "AutomationPanel"));
        btnNewPrograms.addActionListener(e -> cardLayout.show(mainPanel, "ProgramPanel"));
        btnEtatAutomation.addActionListener(e -> cardLayout.show(mainPanel, "EtatAutomatisationPanel"));

        //Retour Menu Capteurs
        btnSensorsManagement.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));
        btnBackToMenu_NewCapteur.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));
        btnBackToMenu_VoirCapteurs.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));
        btnBackToMenu_ChangerEtat.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));
        btnBackToMenu_SupprimerCapteur.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));

        //Boutons Capteurs
        btnVoirCapteurs.addActionListener(e -> cardLayout.show(mainPanel, "voirCapteurPanel"));
        btnNewCapteur.addActionListener(e -> cardLayout.show(mainPanel, "NewCapteursPanel"));
        btnChangerEtat.addActionListener(e -> cardLayout.show(mainPanel, "EtatCapteurPanel"));
        btnSupprimerCapteur.addActionListener(e -> cardLayout.show(mainPanel, "SupprimerCapteurPanel"));

        //Retour Menu Rooms
        btnBackToMenuNewRoom.addActionListener(e -> cardLayout.show(mainPanel, "HouseManagementPanel"));
        btnBackToMenu_VoirRooms.addActionListener(e -> cardLayout.show(mainPanel, "HouseManagementPanel"));

        //Boutons Rooms
        btnViewRoom.addActionListener(e -> cardLayout.show(mainPanel, "voirRoomPanel"));
        btnHouseManagement.addActionListener(e -> cardLayout.show(mainPanel, "HouseManagementPanel"));
        btnNewRoom.addActionListener(e -> cardLayout.show(mainPanel, "RoomPanel"));


//Boutons plus complexes

        //Boutons Affichage
        btnViewPrograms.addActionListener(e -> {
            try {MaisonProgrammeService maisonProgrammeService = new MaisonProgrammeService(networkConfig);
                MaisonProgrammes maisonProgrammes = maisonProgrammeService.select_all_program();
                programmes.clear();
                programmes.add(maisonProgrammes);
                System.out.println("Import réussi!");
                System.out.println(programmes);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            StringBuilder sb_program= new StringBuilder();
            if (programmes.isEmpty()) {
                sb_program.append("Aucun programme enregistré.\n");
            } else {
                sb_program.append("programme enregistrés :\n");
                for (MaisonProgrammes Maisonprogramme : programmes)
                    for (MaisonProgramme programme : Maisonprogramme.getMaisonProgrammes()) {
                        sb_program.append("Nom : ").append(programme.getNomProgramme()).append("\n")
                                .append("Piéce : ").append(programme.getTypePiece()).append("\n")
                                .append("Chauffage : ").append(programme.getTypeChauffage()).append("\n")
                                .append("Jour : ").append(programme.getJourSemaine()).append("\n")
                                .append("Température : ").append(programme.getTemperature()).append("\n")
                                .append("Heure début : ").append(programme.getHeureDebut()).append("\n")
                                .append("Heure fin : ").append(programme.getHeureFin()).append("\n\n");
                    }
            }
            txtPrograms.setText(sb_program.toString());
            cardLayout.show(mainPanel, "ViewProgramsPanel");
        });

        btnViewAutomations.addActionListener(e -> {
            try {MaisonAutomatisationService maisonAutomatisationService = new MaisonAutomatisationService(networkConfig);
                MaisonAutomatisations maisonAutomatisations = maisonAutomatisationService.select_all_automation();
                automatisations.clear();
                automatisations.add(maisonAutomatisations);
                System.out.println("Import réussi!");
                System.out.println(automatisations);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            StringBuilder sb_automation= new StringBuilder();
            if (automatisations.isEmpty()) {
                sb_automation.append("Aucune automatisation enregistré.\n");
            } else {
                sb_automation.append("Automatisations enregistrés :\n");
                for (MaisonAutomatisations Maisonauto : automatisations)
                    for (MaisonAutomatisation auto : Maisonauto.getMaisonAutomatisations()) {
                        sb_automation.append("Nom : ").append(auto.getNomAutomatisation()).append("\n")
                                .append("Capteur écouté ").append(auto.getTypeCapteur()).append("\n")
                                .append("Programme executé : ").append(auto.getTypeProgramme()).append("\n")
                                .append("Etat de l'automatisation : ").append(auto.getEtatAutomatisation()).append("\n\n");
                    }
            }
            txtPrograms.setText(sb_automation.toString());
            cardLayout.show(mainPanel, "ViewProgramsPanel");
        });

        btnVoirCapteurs.addActionListener(e -> {
            try {MaisonCapteurService maisonCapteurService = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteurs = maisonCapteurService.selectAllCapteurs();
                System.out.println(maisonCapteurs);
                capteurs.clear();
                capteurs.add(maisonCapteurs);
                System.out.println("Import réussi!");
                System.out.println(capteurs);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            StringBuilder sb_capteur= new StringBuilder();
            if (capteurs.isEmpty()) {
                sb_capteur.append("Aucun capteur enregistré.\n");
            } else {
                sb_capteur.append("Capteurs enregistrés :\n");
                for (MaisonCapteurs maisonCapteurs : capteurs)
                    for (MaisonCapteur cap : maisonCapteurs.getCapteurs()) {
                        sb_capteur.append("Nom : ").append(cap.getName()).append("\n")
                                .append("Type : ").append(cap.getTypecapteur()).append("\n")
                                .append("Etat : ").append(cap.getEtat()).append("\n\n");
                    }
            }
            txtCapteurs.setText(sb_capteur.toString());
            cardLayout.show(mainPanel, "voirCapteurPanel");
        });

//        btnViewRoom.addActionListener(e -> {
//            String query = "SELECT * FROM rooms";
//            rooms.clear();
//            try (Connection conn = DriverManager.getConnection(url, username, password);
//                 PreparedStatement stmt = conn.prepareStatement(query);
//                 ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    String nom_room = rs.getString("nom_room");
//                    String type_room = rs.getString("type_room");
//                    int room_surface = rs.getInt("room_surface");
//                    Maison_Room.TypeRoom typeRoom = Maison_Room.TypeRoom.valueOf(type_room);
//                    Maison_Room room = new Maison_Room(nom_room,typeRoom,room_surface);
//                    rooms.add(room);
//                }
//                rs.close();
//                stmt.close();
//                System.out.println("Import réussi!");
//            } catch (SQLException ex) {
//                throw new RuntimeException("Erreur lors de la récupération des pièce : " + ex.getMessage());
//            }
//            StringBuilder sb_room= new StringBuilder();
//            if (rooms.isEmpty()) {
//                sb_room.append("Aucune pièce enregistrée.\n");
//            } else {
//                JPanel ViewRoomPanel = new JPanel();
//                ViewRoomPanel.setLayout(new FlowLayout());
//
//                for (Maison_Room room : rooms) {
//                    JButton btnRoom_i = new JButton(room.NameRoom);
//                    ViewRoomPanel.add(btnRoom_i);
//                    mainPanel.add(ViewRoomPanel, "ViewRoomPanel");
//
//                    JPanel Inroom_iPanel = new JPanel();
//                    Inroom_iPanel.setLayout(new FlowLayout());
//                    JButton btnBackToMenuInRoom_i = new JButton("Retour");
//                    Inroom_iPanel.add(btnBackToMenuInRoom_i, BorderLayout.SOUTH);
//                    btnBackToMenuInRoom_i.addActionListener(ev -> cardLayout.show(mainPanel, "ViewRoomPanel"));
//                    mainPanel.add(Inroom_iPanel, "Inroom_iPanel");
//                    btnRoom_i.addActionListener(ev -> cardLayout.show(mainPanel, "Inroom_iPanel"));
//                }
//                JButton btnBackToMenuViewRoom = new JButton("Retour");
//                ViewRoomPanel.add(btnBackToMenuViewRoom);
//                btnBackToMenuViewRoom.addActionListener(ev -> cardLayout.show(mainPanel, "HouseManagementPanel"));
//            }
//            cardLayout.show(mainPanel, "ViewRoomPanel");
//        });

        btnViewRoom.addActionListener(e -> {
            try {
                MaisonRoomService maisonRoomService = new MaisonRoomService(networkConfig);
                MaisonRooms maisonRooms = maisonRoomService.selectRooms();
                rooms.clear();
                rooms.add(maisonRooms);
                System.out.println("Import réussi!");
                System.out.println(rooms);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            StringBuilder sb_room= new StringBuilder();
            if (rooms.isEmpty()) {
                sb_room.append("Aucune pièce enregistré.\n");
            } else {
                sb_room.append("pièce enregistrés :\n");
                for (MaisonRooms maisonRooms : rooms)
                    for (MaisonRoom room : maisonRooms.getMaisonRooms()) {
                        sb_room.append("Nom : ").append(room.getName()).append("\n")
                                .append("Type : ").append(room.getType()).append("\n")
                                .append("Surface : ").append(room.getSurface()).append("\n\n");
                    }
            }
            txtRooms.setText(sb_room.toString());
            cardLayout.show(mainPanel, "voirRoomPanel");
        });



        //Bouton Changement Etat Capteur
        btnChangerEtat.addActionListener(e -> {
            try {
                capteursNoms_cE.clear();
                MaisonCapteurService maisonCapteurServiceFind = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteurFind = maisonCapteurServiceFind.selectAllCapteurs();
                capteurs.clear();
                capteurs.add(maisonCapteurFind);
                for (MaisonCapteurs capt : capteurs)
                    for (MaisonCapteur cap : capt.getCapteurs()) {
                        capteursNoms_cE.add(cap.getName());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(capteursNoms_cE.toArray(new String[0]));
            cbCapteursExistants.removeAllItems();
            cbCapteursExistants.setModel(model);
        });
        btnEnregistrerEtatCapteur.addActionListener(e -> {
            String cap_select = cbCapteursExistants.getSelectedItem().toString();
            String etat_select = cbEtatCapteur_ChangerEtat.getSelectedItem().toString();
            int i = 0 ;
            try {
                MaisonCapteurService maisonCapteurServiceFind = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteurFind = maisonCapteurServiceFind.selectAllCapteurs();
                capteurs.clear();
                capteurs.add(maisonCapteurFind);

                for (MaisonCapteurs capt : capteurs) {
                    for (MaisonCapteur cap : capt.getCapteurs()) {
                        if (cap.getName().equals(cap_select)){
                            cap.setEtat(etat_select);
                            update_delete_Capteur.updateCapteur(cap);
                            break;
                        }
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });



        //Bouton Changement Etat automatisation
        btnEtatAutomation.addActionListener(e -> {
//            String query1 = "SELECT nom_capteur FROM capteurs;";
//            capteursNoms_cE.clear();
//            try (Connection conn = DriverManager.getConnection(url, username, password);
//                 PreparedStatement stmt = conn.prepareStatement(query1);
//                 ResultSet  rs = stmt.executeQuery()){
//                while (rs.next()){
//                    String nom_capteur_comparaison = rs.getString("nom_capteur");;
//                    capteursNoms_cE.add(nom_capteur_comparaison);
//                }
//                rs.close();
//                stmt.close();
//                System.out.println("Import réussi!");
//            } catch (SQLException ex) {
//                throw new RuntimeException("Erreur lors de la récupération des capteurs : " + ex.getMessage());
//            }
//            String query2 = "SELECT * FROM capteurs";
//            capteurs_affichage.clear();
//            try (Connection conn = DriverManager.getConnection(url, username, password);
//                 PreparedStatement stmt = conn.prepareStatement(query2);
//                 ResultSet rs = stmt.executeQuery()) {
//                while (rs.next()) {
//                    String nom = rs.getString("nom_capteur");
//                    String type = rs.getString("type_capteur");
//                    String etat = rs.getString("etat_capteur");
//                    System.out.println(nom);
//                    System.out.println(etat);
//                    System.out.println("Type récupéré : " + type);
//                    Maison_Capteurs.TypeCapteur typeCap = Maison_Capteurs.TypeCapteur.valueOf(type);
//                    Maison_Capteurs.EtatCapteur etatCap = Maison_Capteurs.EtatCapteur.valueOf(etat);
//                    Maison_Capteurs capteur = new Maison_Capteurs(nom, typeCap, etatCap);
//                    System.out.println(capteur);
//                    capteurs_affichage.add(nom);
//                    capteurs_affichage.add(type);
//                    capteurs_affichage.add(etat);}
//                rs.close();
//                stmt.close();
//                System.out.println("Import réussi!");
//                System.out.println(capteurs_affichage);
//            } catch (SQLException ex) {
//                throw new RuntimeException("Erreur lors de la récupération des capteurs : " + ex.getMessage());
//            }
//            StringBuilder sb_automatisation= new StringBuilder();
//            if (automatisations.isEmpty()) {
//                sb_automatisation.append("Aucune automatisation enregistré.\n");
//            } else {
//                sb_automatisation.append("Automatisations enregistrés :\n");
//                for (MaisonAutomatisations maisonAutomatisations : automatisations)
//                    for (MaisonAutomatisation auto : maisonAutomatisations.getMaisonAutomatisations()) {
//                        sb_automatisation.append("Nom : ").append(auto.getNomAutomatisation()).append("\n")
//                                .append("Type capteur: ").append(auto.getTypeCapteur()).append("\n")
//                                .append("Type programme: ").append(auto.getTypeProgramme()).append("\n")
//                                .append("Etat : ").append(auto.getEtatAutomatisation()).append("\n\n");
//                    }
//            }

            try {
                automatisationsNoms.clear();
                MaisonAutomatisationService maisonAutomatisationService = new MaisonAutomatisationService(networkConfig);
                MaisonAutomatisations maisonAutomatisationsFind = maisonAutomatisationService.select_all_automation();
                automatisations.clear();
                automatisations.add(maisonAutomatisationsFind);
                System.out.println(automatisations);
                for (MaisonAutomatisations auto : automatisations)
                    for (MaisonAutomatisation aut : auto.getMaisonAutomatisations()) {
                        automatisationsNoms.add(aut.getNomAutomatisation());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(automatisationsNoms.toArray(new String[0]));
            cbAutomatisationsExistantes.removeAllItems();
            cbAutomatisationsExistantes.setModel(model);
        });
        btnEnregistrerEtatAutomatisation.addActionListener(e -> {
            String auto_select = cbAutomatisationsExistantes.getSelectedItem().toString();
            String etat_auto_select = cbEtatAutomatisation_ChangerEtat.getSelectedItem().toString();
            int i = 0 ;
            try {
                MaisonAutomatisationService maisonAutomatisationService = new MaisonAutomatisationService(networkConfig);
                MaisonAutomatisations maisonAutomatisationsFind = maisonAutomatisationService.select_all_automation();
                automatisations.clear();
                automatisations.add(maisonAutomatisationsFind);

                for (MaisonAutomatisations auto : automatisations) {
                    for (MaisonAutomatisation aut : auto.getMaisonAutomatisations()) {
                        if (aut.getNomAutomatisation().equals(auto_select)){
                            aut.setEtatAutomatisation(etat_auto_select);
                            update_automatisation.updateAutomation(aut);
                            break;
                        }
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });



        //Bouton Suppression Capteur
        btnSupprimerCapteur.addActionListener(e -> {
            try {
                capteursNoms_cE.clear();
                MaisonCapteurService maisonCapteurServiceFind = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteurFind = maisonCapteurServiceFind.selectAllCapteurs();
                capteurs.clear();
                capteurs.add(maisonCapteurFind);
                for (MaisonCapteurs capt : capteurs)
                    for (MaisonCapteur cap : capt.getCapteurs()) {
                        capteursNoms_cE.add(cap.getName());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(capteursNoms_cE.toArray(new String[0]));
            cbCapteursExistants_Supp.removeAllItems();
            cbCapteursExistants_Supp.setModel(model);
        });
        btnEnregistrerSupprimerCapteur.addActionListener(e -> {
            String cap_select = cbCapteursExistants_Supp.getSelectedItem().toString();
            try {
                MaisonCapteurService maisonCapteurServiceFind = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteurFind = maisonCapteurServiceFind.selectAllCapteurs();
                capteurs.clear();
                capteurs.add(maisonCapteurFind);
                for (MaisonCapteurs capt : capteurs) {
                    for (MaisonCapteur cap : capt.getCapteurs()) {
                        if (cap.getName().equals(cap_select)){
                            logger.debug("Suppression du capteur : {}", cap.getName());
                            update_delete_Capteur.deleteCapteur(cap);
                            break;
                        }
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });



        //Boutons Sauvegarde
        btnSaveProgram.addActionListener(e -> {
            String nomProgramme = txtProgramName.getText().trim();
            String PieceSelection = (String) cbPiece.getSelectedItem();
            String ChauffageSelection = (String) cbChauffage.getSelectedItem();
            String JourSelection = (String) cbJour.getSelectedItem();
            Integer TemperatureSelection = (int) spTemperature.getValue();
            Integer HeureDebutSelection = (int) spHeureDebut.getValue();
            Integer HeureFinSelection = (int) spHeureFin.getValue();
            if (nomProgramme.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir un nom de programme.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (HeureDebutSelection >= HeureFinSelection) {
                JOptionPane.showMessageDialog(frame, "L'heure de début doit être inférieure à l'heure de fin.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MaisonProgramme maisonProgramme = new MaisonProgramme(nomProgramme,PieceSelection,ChauffageSelection,JourSelection,TemperatureSelection,HeureDebutSelection,HeureFinSelection);
            int CountProgramNameEqual = 0;
            try {
                MaisonProgrammeService maisonProgrammeServiceFind = new MaisonProgrammeService(networkConfig);
                MaisonProgrammes maisonProgrammeFind = maisonProgrammeServiceFind.select_all_program();
                programmes.clear();
                programmes.add(maisonProgrammeFind);
                for (MaisonProgrammes Maisonprogramme : programmes)
                    for (MaisonProgramme programme : Maisonprogramme.getMaisonProgrammes()) {
                        if (nomProgramme.equals(programme.getNomProgramme())){
                            CountProgramNameEqual = CountProgramNameEqual + 1;
                        }
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if(CountProgramNameEqual >= 1){
                JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                MaisonProgrammeService maisonProgrammeService =new MaisonProgrammeService(networkConfig);
                maisonProgrammeService.insertProgram(maisonProgramme,"INSERT_PROGRAM");
                JOptionPane.showMessageDialog(frame, "Programmes enregistré avec succès!");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        btnSaveAutomation.addActionListener(e -> {
            // Validation des données
            String nomAutomation = txtAutomationName.getText().trim();
            String CapteurSelection = (String) cbSensor_activation.getSelectedItem();
            String ProgrammeSelection = (String) cbSensor_programme.getSelectedItem();
            String etatAutomation ;
            if (cbEtatAutomatisation.isSelected()) {
                etatAutomation = "ON";
            } else {
                etatAutomation = "OFF";
            }
            if (nomAutomation.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir un nom d'automatisation.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Insertion des données
            int CountAutomationNameEqual = 0;
            MaisonAutomatisation maisonAutomatisation = new MaisonAutomatisation(0,nomAutomation,CapteurSelection,ProgrammeSelection,etatAutomation);
            try {
                MaisonAutomatisationService maisonAutomatisationServiceFind = new MaisonAutomatisationService(networkConfig);
                MaisonAutomatisations maisonAutomatisationFind = maisonAutomatisationServiceFind.select_all_automation();
                automatisations.clear();
                automatisations.add(maisonAutomatisationFind);
                for (MaisonAutomatisations Maisonauto : automatisations)
                    for (MaisonAutomatisation auto : Maisonauto.getMaisonAutomatisations()) {
                        if (nomAutomation.equals(auto.getNomAutomatisation())){
                            CountAutomationNameEqual = CountAutomationNameEqual + 1;
                        }
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if(CountAutomationNameEqual >= 1){
                JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MaisonAutomatisationService maisonAutomatisationService =new MaisonAutomatisationService(networkConfig);
                maisonAutomatisationService.insertAutomation(maisonAutomatisation,"INSERT_AUTOMATION");
                JOptionPane.showMessageDialog(frame, "Automatisation enregistré avec succès!");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }



            // Création et sauvegarde de l'automatisation
//            Maison_Automatisation nouvelleAutomatisation = new Maison_Automatisation(nomAutomation, capteurSelect, programmeSelect);
//            automatisations.add(nouvelleAutomatisation);
//            System.out.println(automatisations);
//            //Connection connection = DriverManager.getConnection(url, username, password);
//            String query = "INSERT INTO automatisations (nom_automatisation, type_capteur, type_programme) VALUES (?, ?, ?)";
//            try (Connection conn = DriverManager.getConnection(url, username, password);
//                 PreparedStatement stmt = conn.prepareStatement(query)){
//                Maison_Automatisation auto = automatisations.get(automatisations.size() - 1);
//                stmt.setString(1, auto.NomAutomatisation);
//                stmt.setString(2, auto.TypeCapteurs.toString());
//                stmt.setString(3, auto.TypeProgramme.toString());
//                stmt.executeUpdate();
//                stmt.close();
//                JOptionPane.showMessageDialog(frame, "Automatisation enregistré avec succès!");
//                cardLayout.show(mainPanel, "Automations_and_ProgramsPanel");
//                System.out.println("import reussi");
//            } catch (SQLException ex) {
//                throw new RuntimeException("Erreur lors de la sauvegarde des programmes : " + ex.getMessage());
//            }
        });

        btnSaveCapteur.addActionListener(e -> {
            //Récupération des données
            String nomCapteur = txtNomCapteur.getText().trim();
            String capteurSelect = (String) cbTypeCapteur.getSelectedItem();
            String etatSelect ;
            if (cbEtatCapteur.isSelected()) {
                etatSelect = "ON";
            } else {
                etatSelect = "OFF";
            }
            //Validation des données
            if (nomCapteur.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir un nom de capteur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Insertion des données
            int CountAutomationNameEqual = 0;
            MaisonCapteur maisonCapteur = new MaisonCapteur(nomCapteur,capteurSelect,etatSelect, 0);
            try {
                MaisonCapteurService maisonCapteurServiceFind = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteurFind = maisonCapteurServiceFind.selectAllCapteurs();
                capteurs.clear();
                capteurs.add(maisonCapteurFind);
                for (MaisonCapteurs capt : capteurs)
                    for (MaisonCapteur cap : capt.getCapteurs()) {
                        if (nomCapteur.equalsIgnoreCase(cap.getName())){
                            CountAutomationNameEqual ++;
                        }
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if(CountAutomationNameEqual >= 1){
                JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MaisonCapteurService maisonCapteurService =new MaisonCapteurService(networkConfig);
                maisonCapteurService.insertCapteur(maisonCapteur);
                JOptionPane.showMessageDialog(frame, "Capteur enregistré avec succès!");
                cardLayout.show(mainPanel, "MenuPanel");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

//        btnSaveRoom.addActionListener(e -> {
//            //Récupération des données
//            String nameRoom = txtNameRoom.getText().trim();
//            Maison_Room.TypeRoom roomSelect = (Maison_Room.TypeRoom) cbTypeRoom.getSelectedItem();
//            int roomSurface = (int) spSurfaceRoom.getValue();
//            // Validation des données
//            if (nameRoom.isEmpty()) {
//                JOptionPane.showMessageDialog(frame, "Veuillez nommer la pièce.", "Erreur", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            if (roomSurface <= 0) {
//                JOptionPane.showMessageDialog(frame, "La surface de la pièce doit être positive.", "Erreur", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            // Création et sauvegarde de la piece
//            Maison_Room nouvellePiece = new Maison_Room(nameRoom, roomSelect, roomSurface);
//            rooms.add(nouvellePiece);
//
//            //Connection connection = DriverManager.getConnection(url, username, password);
//            String query = "INSERT INTO rooms (nom_room, type_room, room_surface) VALUES (?, ?, ?)";
//            try (Connection conn = DriverManager.getConnection(url, username, password);
//                 PreparedStatement stmt = conn.prepareStatement(query)) {
//                Maison_Room room = rooms.get(rooms.size() - 1);
//                stmt.setString(1, room.NameRoom);
//                stmt.setString(2, room.TypeRoom.toString());
//                stmt.setInt(3, room.RoomSurface);
//                stmt.executeUpdate();
//                System.out.println("Import réussi!");
//                JOptionPane.showMessageDialog(frame, "Pièce enregistré avec succès!");
//                cardLayout.show(mainPanel, "HouseManagementPanel");
//                stmt.close();
//            } catch (SQLException ex) {
//                throw new RuntimeException("Erreur lors de la sauvegarde des pièces : " + ex.getMessage());
//            }
//        });
        btnSaveRoom.addActionListener(e -> {
            String nameRoom = txtNameRoom.getText().trim();
            String typeRoom = (String) cbTypeRoom.getSelectedItem();
            Integer surfaceRoom = (int) spSurfaceRoom.getValue();
            if (nameRoom.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir le nom de la pièce.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (surfaceRoom <= 0) {
                JOptionPane.showMessageDialog(frame, "La surface de la pièce doit être srictement supérieur à 0.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MaisonRoom maisonRoom = new MaisonRoom(nameRoom,typeRoom,surfaceRoom);
            int CountRoomNameEqual = 0;
            try {
                MaisonRoomService maisonRoomServiceFind = new MaisonRoomService(networkConfig);
                MaisonRooms maisonRoomsFind = maisonRoomServiceFind.selectRooms();
                rooms.clear();
                rooms.add(maisonRoomsFind);
                for (MaisonRooms maisonRooms : rooms)
                    for (MaisonRoom maisonRoom1 : maisonRooms.getMaisonRooms()) {
                        if (nameRoom.equals(maisonRoom1.getName())){
                            CountRoomNameEqual = CountRoomNameEqual + 1;
                        }
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if(CountRoomNameEqual >= 1){
                JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                MaisonRoomService maisonRoomService =new MaisonRoomService(networkConfig);
                maisonRoomService.insertRoom(maisonRoom,"INSERT_ROOM");
                JOptionPane.showMessageDialog(frame, "Pièce enregistrée avec succès!");
                cardLayout.show(mainPanel, "HouseManagementPanel");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        //FIN
        cardLayout.show(mainPanel, "MenuPanel");
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }
}

