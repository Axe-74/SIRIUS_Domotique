package edu.ezip.ing1.pds.application;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.io.IOException;
import java.util.*;

import edu.ezip.ing1.pds.business.dto.*;
import edu.ezip.ing1.pds.features.rooms.visualisation2D.VisualisationRooms;
import edu.ezip.ing1.pds.services.*;
import edu.ezip.ing1.pds.client.commons.ConfigLoader;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Application {
    public JFrame frame;
    public ArrayList<MaisonProgrammes> programmes = new ArrayList<MaisonProgrammes>();
    public ArrayList<MaisonProgrammesFenetres> programmesFenetres = new ArrayList<>();
    public ArrayList<MaisonProgrammesLumieres> programmesLumieres = new ArrayList<>();
    public ArrayList<MaisonAutomatisations> automatisations = new ArrayList<MaisonAutomatisations>();
    public ArrayList<MaisonCapteurs> capteurs = new ArrayList<>(); {}
    public ArrayList<MaisonRooms> rooms = new ArrayList<>();
    public ArrayList<String> automatisationsNoms = new ArrayList<String>();
    public ArrayList<String> capteursNoms_cE = new ArrayList<>();
    public ArrayList<String> JourSemaine_cE = new ArrayList<>();
    public Map<String, String> Lumiere_dic = new LinkedHashMap<>();
    public Map<String, String> JourSemaine_dic = new LinkedHashMap<>();
    public Map<String, String> TypeChauffage_dic = new LinkedHashMap<>();
    public Map<String, String> TypeCapteur_dic = new LinkedHashMap<>();
    public Map<String, String> TypeRoom_dic = new LinkedHashMap<>();
    public Map<String, String> Fenetre_dic = new LinkedHashMap<>();
    public String valueIDJour= "1";
    public String valueIDTypeChauffage= "1";
    public String valueIDTypeCapteur = "4";
    public String valueIDTypeRoom;
    public String valueIDTypeRoom2;
    public String valueIDLumiere = "1";
    public String valueIDFenetre= "1";
    public ArrayList<String> TypeChauffage_cE = new ArrayList<>();
    public ArrayList<String> ProgrammeNoms_cE = new ArrayList<>();
    public ArrayList<String> ProgrammeLumiereNoms_cE = new ArrayList<>();
    public ArrayList<String> ProgrammeFenetreNoms_cE = new ArrayList<>();
    public ArrayList<String> roomsNoms = new ArrayList<>();
    public ArrayList<MaisonAutomatisation_Para_Jour_Semaines> JourSemaine = new ArrayList<>();
    public ArrayList<MaisonAutomatisation_Para_Type_Chauffages> TypeChauffage = new ArrayList<>();
    public ArrayList<Capteur_Para_Types> capteur_types = new ArrayList<>();
    public ArrayList<Room_Para_Types> room_types = new ArrayList<>();
    public ArrayList<MaisonAutomatisation_Para_Lumieres> Lumiere = new ArrayList<>();
    public ArrayList<MaisonAutomatisation_Para_Fenetres> Fenetre = new ArrayList<>();
    private final static String LoggingLabel = "Application";
    private final static Logger logger = LoggerFactory.getLogger(LoggingLabel);
    private final static String networkConfigFile = "network.yaml";
    final NetworkConfig networkConfig = ConfigLoader.loadConfig(NetworkConfig.class, networkConfigFile);
    final MaisonCapteurService update_delete_Capteur;
    final MaisonAutomatisationService update_delete_automatisation;
    final MaisonRoomService update_delete_room;
    {
        try {
            update_delete_Capteur = new MaisonCapteurService(networkConfig);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    {
        try {
            update_delete_automatisation = new MaisonAutomatisationService(networkConfig);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    {update_delete_room = new MaisonRoomService(networkConfig);}

    public void initialize() {

        frame = new JFrame("Domotique maison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 350);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

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
        Automations_and_programsPanel.setLayout(new GridLayout(8, 1));

        JButton btnNewAutomations = new JButton("Définir une nouvelle automatisation");
        JButton btnViewAutomations = new JButton("Voir les automatisations");
        JButton btnDeleteAutomation = new JButton("Supprimer une automatisation");
        JButton btnNewPrograms = new JButton("Définir un nouveau programme");
        JButton btnViewPrograms = new JButton("Voir les programmes");
        JButton btnEtatAutomation = new JButton("Changer l'état d'une automatisation");
        JButton btnSimuleAutomation = new JButton("Simuler les automatisations");
        JButton btnBacktoMainmenu = new JButton("Retour au menu principal");

        Automations_and_programsPanel.add(btnNewAutomations);
        Automations_and_programsPanel.add(btnViewAutomations);
        Automations_and_programsPanel.add(btnEtatAutomation);
        Automations_and_programsPanel.add(btnDeleteAutomation);
        Automations_and_programsPanel.add(btnNewPrograms);
        Automations_and_programsPanel.add(btnViewPrograms);
        Automations_and_programsPanel.add(btnSimuleAutomation);
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
        HouseManagementPanel.setLayout(new GridLayout(6, 1));

        JButton btnNewRoom = new JButton("Nouvelle pièce");
        JButton btnViewRoom = new JButton("Mes pièces");
        JButton btnVisualiserRoom = new JButton("Visualiser ma maison");
        JButton btnModifierRoom = new JButton("Modifier une pièce");
        JButton btnSupprimerRoom = new JButton("Supprimer une pièce");
        JButton btnBackToMenu_Room = new JButton("Retour");

        HouseManagementPanel.add(btnNewRoom);
        HouseManagementPanel.add(btnViewRoom);
        HouseManagementPanel.add(btnVisualiserRoom);
        HouseManagementPanel.add(btnModifierRoom);
        HouseManagementPanel.add(btnSupprimerRoom);
        HouseManagementPanel.add(btnBackToMenu_Room);

        mainPanel.add(HouseManagementPanel, "HouseManagementPanel");


// Automation Definition Panel
        JPanel AutomationPanel = new JPanel();
        AutomationPanel.setLayout(new GridLayout(6, 2));

        JLabel lblAutomationName = new JLabel("Nom de l'automatisation:");
        JTextField txtAutomationName = new JTextField();

        JLabel lblSensor_activation = new JLabel("Activation du capteur: ");
        JComboBox<String> cbSensor_activation = new JComboBox<>();


//        JComboBox<String> cbSensor_activation = new JComboBox<>(new String[]{
//                "Capteur 1", "Capteur 2", "Capteur 3", "Capteur 4", "Capteur 5"
//        });
        JComboBox<String> cbSensor_programme = new JComboBox<>();
        JLabel lblSensor_program = new JLabel("Execution du programme:");

//        JComboBox<String> cbSensor_programme = new JComboBox<>(new String[]{
//                "Programme 1", "Programme 2", "Programme 3", "Programme 4", "Programme 5"
//        });

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
        AutomationPanel.add(btnBackToMenu_Automation);
        AutomationPanel.add(btnSaveAutomation);

        mainPanel.add(AutomationPanel, "AutomationPanel");


//Etat Automatisation panel
        JPanel EtatAutomatisationPanel = new JPanel();
        EtatAutomatisationPanel.setLayout(new GridLayout(3, 2));

        JLabel lblChoixAutomatisation = new JLabel("Automatisation:");
        JLabel lblEtatAutomatisation_EtatAutomatisation = new JLabel("Etat:");

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
        EtatAutomatisationPanel.add(btnBackToMenu_ChangerEtatAutomatisation);
        EtatAutomatisationPanel.add(btnEnregistrerEtatAutomatisation);

        mainPanel.add(EtatAutomatisationPanel, "EtatAutomatisationPanel");


//View Automation panel

        JPanel viewAutomationPanel = new JPanel();
        viewAutomationPanel.setLayout(new BorderLayout());

        String[] columnNamesAutomation = {
                "Nom", "Programme exécuté ", "Capteur écouté", "Etat"
        };

        DefaultTableModel tableModelAutomation = new DefaultTableModel(columnNamesAutomation, 0);
        JTable tableAutomation = new JTable(tableModelAutomation) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableAutomation.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tableAutomation.setRowHeight(24);
        tableAutomation.setGridColor(Color.LIGHT_GRAY);
        tableAutomation.setSelectionBackground(new Color(200, 230, 255));
        tableAutomation.setSelectionForeground(Color.BLACK);
        tableAutomation.setBackground(Color.WHITE);
        tableAutomation.setForeground(Color.DARK_GRAY);

        JTableHeader headerAutomation = tableAutomation.getTableHeader();
        headerAutomation.setFont(new Font("SansSerif", Font.BOLD, 15));
        headerAutomation.setBackground(new Color(240, 240, 240));

        DefaultTableCellRenderer centerRendererAutomation = new DefaultTableCellRenderer();
        centerRendererAutomation.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableAutomation.getColumnCount(); i++) {
            tableAutomation.getColumnModel().getColumn(i).setCellRenderer(centerRendererAutomation);
        }
        JScrollPane scrollPaneAutomation = new JScrollPane(tableAutomation);

        viewAutomationPanel.add(scrollPaneAutomation, BorderLayout.CENTER);

        JButton btnBackToMenuAutomationProgramm = new JButton("Retour au menu");
        viewAutomationPanel.add(btnBackToMenuAutomationProgramm, BorderLayout.SOUTH);

        mainPanel.add(viewAutomationPanel, "ViewAutomationsPanel");

//Automatisation Supprimer Panel
        JPanel SupprimerAutomatisationPanel = new JPanel();
        SupprimerAutomatisationPanel.setLayout(new GridLayout(2, 2));

        JLabel lblSupprimerAutomatisation = new JLabel("Supprimer une automatisation:");
        lblSupprimerAutomatisation.setHorizontalAlignment(SwingConstants.CENTER);
        JComboBox<String> cbAutomatisationsExistantes_Supp = new JComboBox<>(new String[]{});

        JButton btnEnregistrerSupprimerAutomatisation = new JButton("Supprimer");
        JButton btnBackToMenu_SupprimerAutomatisation = new JButton("Retour au menu");

        SupprimerAutomatisationPanel.add(lblSupprimerAutomatisation);
        SupprimerAutomatisationPanel.add(cbAutomatisationsExistantes_Supp);
        SupprimerAutomatisationPanel.add(btnBackToMenu_SupprimerAutomatisation);
        SupprimerAutomatisationPanel.add(btnEnregistrerSupprimerAutomatisation);

        mainPanel.add(SupprimerAutomatisationPanel, "SupprimerAutomatisationPanel");


// Simule Automation Panel

        JPanel SimulerAutomatisationsPanel = new JPanel();
        SimulerAutomatisationsPanel.setLayout(new GridLayout(2, 2));

        JLabel lblSimulerAutomatisation = new JLabel("Supprimer une automatisation:");
        lblSimulerAutomatisation.setHorizontalAlignment(SwingConstants.CENTER);
        JComboBox<String> cbAutomatisationsExistantes_Simuler = new JComboBox<>(new String[]{});

        JButton btnSimulerAutomatisation = new JButton("Simuler les automatisations");
        JButton btnBackToMenu_SimulerAutomatisation = new JButton("Retour au menu");

        SimulerAutomatisationsPanel.add(lblSimulerAutomatisation);
        SimulerAutomatisationsPanel.add(cbAutomatisationsExistantes_Simuler);
        SimulerAutomatisationsPanel.add(btnBackToMenu_SimulerAutomatisation);
        SimulerAutomatisationsPanel.add(btnSimulerAutomatisation);

        mainPanel.add(SimulerAutomatisationsPanel, "SimulerAutomatisationPanel");

// All Program panel
        JPanel AllProgramPanel = new JPanel();
        AllProgramPanel.setLayout(new GridLayout(4, 1));

        JButton btnProgram = new JButton("Programmer un chauffage");
        JButton btnProgramLight = new JButton("Programmer une lumière");
        JButton btnProgramWindow = new JButton("Programmer une fenêtre");
        JButton btnBackToMenu_AllProgram = new JButton("Retour au menu");

        AllProgramPanel.add(btnProgram);
        AllProgramPanel.add(btnProgramWindow);
        AllProgramPanel.add(btnProgramLight);
        AllProgramPanel.add(btnBackToMenu_AllProgram);

        mainPanel.add(AllProgramPanel, "AllProgramPanel");

// Program Definition Panel

        JPanel ProgramPanel = new JPanel();
        ProgramPanel.setLayout(new GridLayout(8, 2));

        JLabel lblProgramName = new JLabel("Nom du programme:");
        JTextField txtProgramName = new JTextField();

        JLabel lblPiece = new JLabel("Pièce:");
        JComboBox<String> cbPiece = new JComboBox<>();

        JLabel lblChauffage = new JLabel("Type de chauffage:");
        JComboBox<String> cbChauffage = new JComboBox<>();

        JLabel lblTemperature = new JLabel("Température:");
        JSpinner spTemperature = new JSpinner(new SpinnerNumberModel(20, 10, 30, 1));

        JLabel lblJour = new JLabel("Jour de la semaine");
        JComboBox<String> cbJour = new JComboBox<>();

        JLabel lblHeureDebut = new JLabel("Heure de début:");
        JSpinner spHeureDebut = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));

        JLabel lblHeureFin = new JLabel("Heure de fin:");
        JSpinner spHeureFin = new JSpinner(new SpinnerNumberModel(1, 0, 24, 1));

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
        ProgramPanel.add(btnBackToMenu_Program);
        ProgramPanel.add(btnSaveProgram);

        mainPanel.add(ProgramPanel, "ProgramPanel");

// Program light Definition Panel

        JPanel ProgramLightPanel = new JPanel();
        ProgramLightPanel.setLayout(new GridLayout(8, 2));

        JLabel lblProgramLightName = new JLabel("Nom du programme:");
        JTextField txtProgramLightName = new JTextField();

        JLabel lblLightPiece = new JLabel("Pièce:");
        JComboBox<String> cbLightPiece = new JComboBox<>();

        JLabel lblLight = new JLabel("Lumière:");
        JComboBox<String> cbLight = new JComboBox<>();

        JLabel lblLightIntensite = new JLabel("Intensité");
        JSpinner spLightIntensite = new JSpinner(new SpinnerNumberModel(50, 10, 100, 1));

        JLabel lblLightJour = new JLabel("Jour de la semaine");
        JComboBox<String> cbLightJour = new JComboBox<>();

        JLabel lblLightHeureDebut = new JLabel("Heure de début:");
        JSpinner spLightHeureDebut = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));

        JLabel lblLightHeureFin = new JLabel("Heure de fin:");
        JSpinner spLightHeureFin = new JSpinner(new SpinnerNumberModel(1, 0, 24, 1));

        JButton btnLightSaveProgram = new JButton("Enregistrer");
        JButton btnLightBackToMenu_Program = new JButton("Retour au menu");

        ProgramLightPanel.add(lblProgramLightName);
        ProgramLightPanel.add(txtProgramLightName);
        ProgramLightPanel.add(lblLightPiece);
        ProgramLightPanel.add(cbLightPiece);
        ProgramLightPanel.add(lblLight);
        ProgramLightPanel.add(cbLight);
        ProgramLightPanel.add(lblLightIntensite);
        ProgramLightPanel.add(spLightIntensite);
        ProgramLightPanel.add(lblLightJour);
        ProgramLightPanel.add(cbLightJour);
        ProgramLightPanel.add(lblLightHeureDebut);
        ProgramLightPanel.add(spLightHeureDebut);
        ProgramLightPanel.add(lblLightHeureFin);
        ProgramLightPanel.add(spLightHeureFin);
        ProgramLightPanel.add(btnLightBackToMenu_Program);
        ProgramLightPanel.add(btnLightSaveProgram);

        mainPanel.add(ProgramLightPanel, "ProgramLightPanel");

// Program Definition Panel

        JPanel ProgramWindowPanel = new JPanel();
        ProgramWindowPanel.setLayout(new GridLayout(8, 2));

        JLabel lblProgramWindowName = new JLabel("Nom du programme:");
        JTextField txtProgramWindowName = new JTextField();

        JLabel lblWindowPiece = new JLabel("Pièce:");
        JComboBox<String> cbWindowPiece = new JComboBox<>();

        JLabel lblWindow = new JLabel("Fenêtre:");
        JComboBox<String> cbWindow = new JComboBox<>();

        JLabel lblWindowOuverture = new JLabel("Ouverture");
        JSpinner spWindowOuverture = new JSpinner(new SpinnerNumberModel(33, 1, 90, 1));

        JLabel lbWindowlJour = new JLabel("Jour de la semaine");
        JComboBox<String> cbWindowJour = new JComboBox<>();

        JLabel lblWindowHeureDebut = new JLabel("Heure de début:");
        JSpinner spWindowHeureDebut = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));

        JLabel lblWindowHeureFin = new JLabel("Heure de fin:");
        JSpinner spWindowHeureFin = new JSpinner(new SpinnerNumberModel(0, 0, 24, 1));

        JButton btnWindowSaveProgram = new JButton("Enregistrer");
        JButton btnWindowBackToMenu_Program = new JButton("Retour au menu");

        ProgramWindowPanel.add(lblProgramWindowName);
        ProgramWindowPanel.add(txtProgramWindowName);
        ProgramWindowPanel.add(lblWindowPiece);
        ProgramWindowPanel.add(cbWindowPiece);
        ProgramWindowPanel.add(lblWindow);
        ProgramWindowPanel.add(cbWindow);
        ProgramWindowPanel.add(lblWindowOuverture);
        ProgramWindowPanel.add(spWindowOuverture);
        ProgramWindowPanel.add(lbWindowlJour);
        ProgramWindowPanel.add(cbWindowJour);
        ProgramWindowPanel.add(lblWindowHeureDebut);
        ProgramWindowPanel.add(spWindowHeureDebut);
        ProgramWindowPanel.add(lblWindowHeureFin);
        ProgramWindowPanel.add(spWindowHeureFin);
        ProgramWindowPanel.add(btnWindowBackToMenu_Program);
        ProgramWindowPanel.add(btnWindowSaveProgram);

        mainPanel.add(ProgramWindowPanel, "ProgramWindowPanel");

// NewCapteur Panel
        JPanel NewCapteursPanel = new JPanel();
        NewCapteursPanel.setLayout(new GridLayout(6, 2));

        JLabel lblNomCapteur = new JLabel("Nom du capteur:");
        lblNomCapteur.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField txtNomCapteur = new JTextField();

        JLabel lblTypeCapteur = new JLabel("Type du capteur: ");
        lblTypeCapteur.setHorizontalAlignment(SwingConstants.CENTER);
        JComboBox<String> cbTypeCapteur = new JComboBox<>();

        JLabel lblPieceCapteur = new JLabel("Pièce du capteur: ");
        lblPieceCapteur.setHorizontalAlignment(SwingConstants.CENTER);
        JComboBox<String> cbPieceCapteur = new JComboBox<>();

        JLabel lblEtatCapteur = new JLabel("Etat du capteur:");
        lblEtatCapteur.setHorizontalAlignment(SwingConstants.CENTER);
        JCheckBox cbEtatCapteur = new JCheckBox();
        cbEtatCapteur.setSelected(true);
        JPanel panelEtat = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelEtat.add(cbEtatCapteur);

        JLabel lblExplicationOFF = new JLabel("Pas coché = OFF");
        lblExplicationOFF.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel lblExplicationON = new JLabel("Coché = ON");
        lblExplicationON.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnSaveCapteur = new JButton("Enregistrer");
        JButton btnSuivantSaveCapteur = new JButton("Suivant");
        JButton btnBackToMenu_NewCapteur = new JButton("Retour au menu");

        NewCapteursPanel.add(lblNomCapteur);
        NewCapteursPanel.add(txtNomCapteur);
        NewCapteursPanel.add(lblTypeCapteur);
        NewCapteursPanel.add(cbTypeCapteur);
        NewCapteursPanel.add(lblPieceCapteur);
        NewCapteursPanel.add(cbPieceCapteur);
        NewCapteursPanel.add(lblEtatCapteur);
        NewCapteursPanel.add(panelEtat);
        NewCapteursPanel.add(lblExplicationOFF);
        NewCapteursPanel.add(lblExplicationON);
        NewCapteursPanel.add(btnBackToMenu_NewCapteur);
        NewCapteursPanel.add(btnSuivantSaveCapteur);

        mainPanel.add(NewCapteursPanel, "NewCapteursPanel");


//NewCapteur Température
        JPanel CapteurTemp = new JPanel();
        CapteurTemp.setLayout(new GridLayout(3, 2));

        JLabel lblTypeSelectionneTemp = new JLabel("Type:");
        JLabel lblFreqEchan = new JLabel("Fréquence d'échantillonnage (Hz):");

        JLabel lblTemp = new JLabel("Température");
        SpinnerNumberModel modelTemp = new SpinnerNumberModel(1.0, 0.5, 10.0, 0.5);
        JSpinner spTemp = new JSpinner(modelTemp);
        JSpinner.NumberEditor editorTemp = new JSpinner.NumberEditor(spTemp, "0.0"); //format personnalisé
        spTemp.setEditor(editorTemp);

        JButton btnEnregistrerTypeTemp = new JButton("Enregistrer");
        JButton btnBackToMenu_TypeTemp = new JButton("Retour");

        CapteurTemp.add(lblTypeSelectionneTemp);
        CapteurTemp.add(lblFreqEchan);
        CapteurTemp.add(lblTemp);
        CapteurTemp.add(spTemp);
        CapteurTemp.add(btnBackToMenu_TypeTemp);
        CapteurTemp.add(btnEnregistrerTypeTemp);

        mainPanel.add(CapteurTemp, "CapteurTemp");


//NewCapteur Luminosité
        JPanel CapteurLum = new JPanel();
        CapteurLum.setLayout(new GridLayout(3, 2));

        JLabel lblTypeSelectionneLum = new JLabel("Type:");
        JLabel lblIntenMax = new JLabel("Intensité maximale (lux):");

        JLabel lblLum = new JLabel("Luminosité");
        SpinnerNumberModel modelLum = new SpinnerNumberModel(65000.0, 10000.0, 100000.0, 1000.0);
        JSpinner spLum = new JSpinner(modelLum);
        JSpinner.NumberEditor editorLum = new JSpinner.NumberEditor(spLum, "0");
        spLum.setEditor(editorLum);

        JButton btnEnregistrerTypeLum = new JButton("Enregistrer");
        JButton btnBackToMenu_TypeLum = new JButton("Retour");

        CapteurLum.add(lblTypeSelectionneLum);
        CapteurLum.add(lblIntenMax);
        CapteurLum.add(lblLum);
        CapteurLum.add(spLum);
        CapteurLum.add(btnBackToMenu_TypeLum);
        CapteurLum.add(btnEnregistrerTypeLum);

        mainPanel.add(CapteurLum, "CapteurLum");


//NewCapteur Mouvement
        JPanel CapteurMouv = new JPanel();
        CapteurMouv.setLayout(new GridLayout(3, 2));

        JLabel lblTypeSelectionneMouv = new JLabel("Type:");
        JLabel lblPortee = new JLabel("Portée (mètres):");

        JLabel lblMouv = new JLabel("Mouvement");
        SpinnerNumberModel modelMouv = new SpinnerNumberModel(5.0, 3.0, 15.0, 0.5);
        JSpinner spMouv = new JSpinner(modelMouv);
        JSpinner.NumberEditor editorMouv = new JSpinner.NumberEditor(spMouv, "0.0");
        spMouv.setEditor(editorMouv);

        JButton btnEnregistrerTypeMouv = new JButton("Enregistrer");
        JButton btnBackToMenu_TypeMouv = new JButton("Retour");

        CapteurMouv.add(lblTypeSelectionneMouv);
        CapteurMouv.add(lblPortee);
        CapteurMouv.add(lblMouv);
        CapteurMouv.add(spMouv);
        CapteurMouv.add(btnBackToMenu_TypeMouv);
        CapteurMouv.add(btnEnregistrerTypeMouv);

        mainPanel.add(CapteurMouv, "CapteurMouv");

// Nouvelle piece panel
        JPanel pnlRoom = new JPanel();
        pnlRoom.setLayout(new GridLayout(4, 2));

        JLabel lblNameRoom = new JLabel("Nom de la pièce :");
        JTextField txtNameRoom = new JTextField();

        JLabel lblTypeRoom = new JLabel("Type de pièce : ");
//        JComboBox<String> cbTypeRoom = new JComboBox<>(new String[]{
//                "Entree", "Salon", "Cuisine", "Salle_de_bain", "Toilettes", "Chambre", "Autre"
//        });
        JComboBox<String> cbTypeRoom = new JComboBox<>();

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

        String[] columnNames = {
                "Nom", "Pièce", "Chauffage/Fenetre/Lumière", "Jour", "Température/Ouverture/Intensité", "Heure Début", "Heure Fin"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(24);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(new Color(200, 230, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.DARK_GRAY);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("SansSerif", Font.BOLD, 15));
        header.setBackground(new Color(240, 240, 240));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        JScrollPane scrollPane = new JScrollPane(table);


        viewProgramsPanel.add(scrollPane, BorderLayout.CENTER);

        JButton btnBackToMenuViewProgramm = new JButton("Retour au menu");
        viewProgramsPanel.add(btnBackToMenuViewProgramm, BorderLayout.SOUTH);

        mainPanel.add(viewProgramsPanel, "ViewProgramsPanel");


// Voir Capteurs
        JPanel voirCapteurPanel = new JPanel();
        voirCapteurPanel.setLayout(new BorderLayout());

        String[] columnNamesCapteurs = {
                "Nom", "Type", "Pièce", "Réglage", "Etat"
        };

        DefaultTableModel tableModelCapteur = new DefaultTableModel(columnNamesCapteurs, 0);
        JTable tableCapteur = new JTable(tableModelCapteur) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableCapteur.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tableCapteur.setRowHeight(24);
        tableCapteur.setGridColor(Color.LIGHT_GRAY);
        tableCapteur.setSelectionBackground(new Color(200, 230, 255));
        tableCapteur.setSelectionForeground(Color.BLACK);
        tableCapteur.setBackground(Color.WHITE);
        tableCapteur.setForeground(Color.DARK_GRAY);

        JTableHeader headerCapteur = tableCapteur.getTableHeader();
        headerCapteur.setFont(new Font("SansSerif", Font.BOLD, 15));
        headerCapteur.setBackground(new Color(240, 240, 240));

        DefaultTableCellRenderer centerRendererCapteur = new DefaultTableCellRenderer();
        centerRendererCapteur.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableCapteur.getColumnCount(); i++) {
            tableCapteur.getColumnModel().getColumn(i).setCellRenderer(centerRendererCapteur);
        }

        JScrollPane scrollPane_capteurs = new JScrollPane(tableCapteur);
        JButton btnBackToMenu_VoirCapteurs = new JButton("Retour au menu");

        voirCapteurPanel.add(scrollPane_capteurs, BorderLayout.CENTER);
        voirCapteurPanel.add(btnBackToMenu_VoirCapteurs, BorderLayout.SOUTH);

        mainPanel.add(voirCapteurPanel, "voirCapteurPanel");


// Voir Rooms
        JPanel voirRoomPanel = new JPanel();
        voirRoomPanel.setLayout(new BorderLayout());

        String[] columnNamesRooms = {
                "Nom", "Type", "Surface", "Capteurs"
        };

        DefaultTableModel tableModelRoom = new DefaultTableModel(columnNamesRooms, 0);
        JTable tableRoom = new JTable(tableModelRoom) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableRoom.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tableRoom.setRowHeight(24);
        tableRoom.setGridColor(Color.LIGHT_GRAY);
        tableRoom.setSelectionBackground(new Color(200, 230, 255));
        tableRoom.setSelectionForeground(Color.BLACK);
        tableRoom.setBackground(Color.WHITE);
        tableRoom.setForeground(Color.DARK_GRAY);

        JTableHeader headerRoom = tableRoom.getTableHeader();
        headerRoom.setFont(new Font("SansSerif", Font.BOLD, 15));
        headerRoom.setBackground(new Color(240, 240, 240));

        DefaultTableCellRenderer centerRendererRoom = new DefaultTableCellRenderer();
        centerRendererRoom.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableRoom.getColumnCount(); i++) {
            tableRoom.getColumnModel().getColumn(i).setCellRenderer(centerRendererRoom);
        }

        JScrollPane scrollPane_rooms = new JScrollPane(tableRoom);
        JButton btnBackToMenu_VoirRooms = new JButton("Retour");

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
        EtatCapteurPanel.add(btnBackToMenu_ChangerEtat);
        EtatCapteurPanel.add(btnEnregistrerEtatCapteur);

        mainPanel.add(EtatCapteurPanel, "EtatCapteurPanel");


// Choix de la pièce à modifier
        JPanel RoomDefiniePanel = new JPanel();
        RoomDefiniePanel.setLayout(new GridLayout(2, 2));

        JLabel lblChoixRoom = new JLabel("Pièces :");

        JComboBox<String> cbRoomsExistantes = new JComboBox<>(new String[]{
        });

        JButton btnChoisirRoom = new JButton("Choisir cette pièce");
        JButton btnBackToMenu_ModifierRoom = new JButton("Retour au menu");

        RoomDefiniePanel.add(lblChoixRoom);
        RoomDefiniePanel.add(cbRoomsExistantes);
        RoomDefiniePanel.add(btnBackToMenu_ModifierRoom);
        RoomDefiniePanel.add(btnChoisirRoom);

        mainPanel.add(RoomDefiniePanel, "RoomDefiniePanel");

// Modification des données de la pièce choisie
        JPanel pnlRoom2 = new JPanel();
        pnlRoom2.setLayout(new GridLayout(4, 2));

        JLabel lblNameRoom2 = new JLabel("Nom de la pièce :");
        JTextField txtNameRoom2 = new JTextField();

        JLabel lblTypeRoom2 = new JLabel("Type de pièce : ");
//        JComboBox<String> cbTypeRoom2 = new JComboBox<>(new String[]{
//                "Entree", "Salon", "Cuisine", "Salle_de_bain", "Toilettes", "Chambre", "Autre"
//        });
        JComboBox<String> cbTypeRoom2 = new JComboBox<>();

        JLabel lblSurfaceRoom2 = new JLabel("Surface de la pièce (en m²) :");
        JSpinner spSurfaceRoom2 = new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));

        JButton btnBackToMenuNewRoom2 = new JButton("Retour au menu");
        JButton btnSaveModifRoom = new JButton("Enregistrer");

        pnlRoom2.add(lblNameRoom2);
        pnlRoom2.add(txtNameRoom2);
        pnlRoom2.add(lblTypeRoom2);
        pnlRoom2.add(cbTypeRoom2);
        pnlRoom2.add(lblSurfaceRoom2);
        pnlRoom2.add(spSurfaceRoom2);
        pnlRoom2.add(btnBackToMenuNewRoom2);
        pnlRoom2.add(btnSaveModifRoom);

        mainPanel.add(pnlRoom2, "ModifierRoomPanel");


//Capteur Supprimer Panel
        JPanel SupprimerCapteurPanel = new JPanel();
        SupprimerCapteurPanel.setLayout(new GridLayout(2, 2));

        JLabel lblSupprimerCapteur = new JLabel("Supprimer un capteur:");
        lblSupprimerCapteur.setHorizontalAlignment(SwingConstants.CENTER);
        JComboBox<String> cbCapteursExistants_Supp = new JComboBox<>(new String[]{});

        JButton btnEnregistrerSupprimerCapteur = new JButton("Supprimer");
        JButton btnBackToMenu_SupprimerCapteur = new JButton("Retour au menu");

        SupprimerCapteurPanel.add(lblSupprimerCapteur);
        SupprimerCapteurPanel.add(cbCapteursExistants_Supp);
        SupprimerCapteurPanel.add(btnBackToMenu_SupprimerCapteur);
        SupprimerCapteurPanel.add(btnEnregistrerSupprimerCapteur);

        mainPanel.add(SupprimerCapteurPanel, "SupprimerCapteurPanel");

//Pièce Supprimer Panel
        JPanel SupprimerRoomPanel = new JPanel();
        SupprimerRoomPanel.setLayout(new GridLayout(2, 2));

        JLabel lblSupprimerRoom = new JLabel("Supprimer une pièce :");
        lblSupprimerRoom.setHorizontalAlignment(SwingConstants.CENTER);
        JComboBox<String> cbRoomsExistantes_Supp = new JComboBox<>(new String[]{});

        JButton btnEnregistrerSupprimerRoom = new JButton("Supprimer");
        JButton btnBackToMenu_SupprimerRoom = new JButton("Retour au menu");

        SupprimerRoomPanel.add(lblSupprimerRoom);
        SupprimerRoomPanel.add(cbRoomsExistantes_Supp);
        SupprimerRoomPanel.add(btnBackToMenu_SupprimerRoom);
        SupprimerRoomPanel.add(btnEnregistrerSupprimerRoom);

        mainPanel.add(SupprimerRoomPanel, "SupprimerRoomPanel");


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
        btnBackToMenu_AllProgram.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnBackToMenu_Program.addActionListener(e -> cardLayout.show(mainPanel, "AllProgramPanel"));
        btnWindowBackToMenu_Program.addActionListener(e -> cardLayout.show(mainPanel, "AllProgramPanel"));
        btnLightBackToMenu_Program.addActionListener(e -> cardLayout.show(mainPanel, "AllProgramPanel"));
        btnNewPrograms.addActionListener(e -> cardLayout.show(mainPanel, "AllProgramPanel"));
        btnAutomations_and_programs.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnBackToMenu_ChangerEtatAutomatisation.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnBackToMenu_SupprimerAutomatisation.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnBackToMenu_SimulerAutomatisation.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));


        //Boutons Automatisations et Programmes
        btnNewAutomations.addActionListener(e -> {
            txtAutomationName.setText("");
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
            DefaultComboBoxModel ComboBoxSensorAutomation = new DefaultComboBoxModel(capteursNoms_cE.toArray(new String[0]));
            cbSensor_activation.removeAllItems();
            cbSensor_activation.setModel(ComboBoxSensorAutomation);

            try {
                ProgrammeNoms_cE.clear();
                MaisonProgrammeService maisonProgrammeService = new MaisonProgrammeService(networkConfig);
                MaisonProgrammes maisonProgrammeFind = maisonProgrammeService.select_all_program();
                programmes.clear();
                programmes.add(maisonProgrammeFind);
                for (MaisonProgrammes Prog : programmes)
                    for (MaisonProgramme Pro : Prog.getMaisonProgrammes()) {
                        ProgrammeNoms_cE.add(Pro.getNomProgramme());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
//            DefaultComboBoxModel ComboBoxProgrammAutomation = new DefaultComboBoxModel(ProgrammeNoms_cE.toArray(new String[0]));
//            cbSensor_programme.removeAllItems();
//            cbSensor_programme.setModel(ComboBoxProgrammAutomation);

            try {
//                ProgrammeFenetreNoms_cE.clear();
                MaisonProgrammeFenetreService maisonProgrammeFenetreService = new MaisonProgrammeFenetreService(networkConfig);
                MaisonProgrammesFenetres maisonProgrammeFenetresFind = maisonProgrammeFenetreService.select_all_Window_program();
                programmesFenetres.clear();
                programmesFenetres.add(maisonProgrammeFenetresFind);
                for (MaisonProgrammesFenetres maisonProgrammesFenetres : programmesFenetres) {
                    for (MaisonProgrammeFenetre maisonProgrammesFenetre1 : maisonProgrammesFenetres.getMaisonProgrammesFenetres()) {
                        ProgrammeNoms_cE.add(maisonProgrammesFenetre1.getNomProgramme());
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel ComboBoxProgrammLightAutomation = new DefaultComboBoxModel(ProgrammeFenetreNoms_cE.toArray(new String[0]));
            cbSensor_programme.setModel(ComboBoxProgrammLightAutomation);

            try {
                ProgrammeLumiereNoms_cE.clear();
                MaisonProgrammeLumiereService maisonProgrammeLumiereService = new MaisonProgrammeLumiereService(networkConfig);
                MaisonProgrammesLumieres maisonProgrammesLumieres = maisonProgrammeLumiereService.select_all_Light_program();
                programmesLumieres.clear();
                programmesLumieres.add(maisonProgrammesLumieres);
                for (MaisonProgrammesLumieres maisonProgrammeslumieres : programmesLumieres) {
                    for (MaisonProgrammeLumiere maisonProgrammesLumiere1 : maisonProgrammeslumieres.getMaisonProgrammesLumieres())
                        ProgrammeNoms_cE.add(maisonProgrammesLumiere1.getNomProgramme());
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel ComboBoxProgrammWindowAutomation = new DefaultComboBoxModel(ProgrammeNoms_cE.toArray(new String[0]));
            cbSensor_programme.setModel(ComboBoxProgrammWindowAutomation);

            cardLayout.show(mainPanel, "AutomationPanel");
        });
        btnProgram.addActionListener(e -> {
            txtProgramName.setText("");
            try {
                roomsNoms.clear();
                MaisonRoomService maisonRoomServiceFind = new MaisonRoomService(networkConfig);
                MaisonRooms maisonRoomFind = maisonRoomServiceFind.selectRooms();
                rooms.clear();
                rooms.add(maisonRoomFind);
                for (MaisonRooms maisonRooms : rooms)
                    for (MaisonRoom maisonRoom : maisonRooms.getMaisonRooms()) {
                        roomsNoms.add(maisonRoom.getName());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel ComboBoxRoom = new DefaultComboBoxModel(roomsNoms.toArray(new String[0]));
            cbPiece.removeAllItems();
            cbPiece.setModel(ComboBoxRoom);

            try {
                MaisonAutomatisationParaJourSemaineService maisonAutomatisationParaJourSemaineService = new MaisonAutomatisationParaJourSemaineService(networkConfig);
                MaisonAutomatisation_Para_Jour_Semaines maisonAutomatisation_para_jour_semaines = maisonAutomatisationParaJourSemaineService.select_all_name_day();
                JourSemaine.clear();
                JourSemaine.add(maisonAutomatisation_para_jour_semaines);
                System.out.println("Import réussi!");
                System.out.println(JourSemaine);
                for (MaisonAutomatisation_Para_Jour_Semaines JourSe : JourSemaine)
                    for (MaisonAutomatisation_Para_Jour_Semaine Jour : JourSe.getMaisonAutomatisation_para_jour_semaines()) {
                        JourSemaine_dic.put(Jour.getNom(), Jour.getID_Para_Jour_Semaine().toString());
//                        JourSemaine_cE.add(Jour.getNom());

                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            DefaultComboBoxModel ComboBoxDay = new DefaultComboBoxModel(JourSemaine_dic.keySet().toArray(new String[0]));
            cbJour.removeAllItems();
            cbJour.setModel(ComboBoxDay);

            try {
                MaisonAutomatisationParaTypeChauffageService maisonAutomatisationParaTypeChauffageService = new MaisonAutomatisationParaTypeChauffageService(networkConfig);
                MaisonAutomatisation_Para_Type_Chauffages maisonAutomatisation_para_type_chauffages = maisonAutomatisationParaTypeChauffageService.select_all_name_heater();
                TypeChauffage.clear();
                TypeChauffage.add(maisonAutomatisation_para_type_chauffages);
                System.out.println("Import réussi!");
                System.out.println(TypeChauffage);
                for (MaisonAutomatisation_Para_Type_Chauffages ChauffageSe : TypeChauffage)
                    for (MaisonAutomatisation_Para_Type_Chauffage Chauffage : ChauffageSe.getMaisonAutomatisation_para_type_chauffages()) {
//                        TypeChauffage_cE.add(Chauffage.getNom());
                        TypeChauffage_dic.put(Chauffage.getNom(), Chauffage.getID_Para_Jour_Chauffage().toString());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            DefaultComboBoxModel ComboBoxHeater = new DefaultComboBoxModel(TypeChauffage_dic.keySet().toArray(new String[0]));
            cbChauffage.removeAllItems();
            cbChauffage.setModel(ComboBoxHeater);

            cardLayout.show(mainPanel, "ProgramPanel");
        });
        btnProgramLight.addActionListener(e -> {
            txtProgramLightName.setText("");
            try {
                roomsNoms.clear();
                MaisonRoomService maisonRoomServiceFind = new MaisonRoomService(networkConfig);
                MaisonRooms maisonRoomFind = maisonRoomServiceFind.selectRooms();
                rooms.clear();
                rooms.add(maisonRoomFind);
                for (MaisonRooms maisonRooms : rooms)
                    for (MaisonRoom maisonRoom : maisonRooms.getMaisonRooms()) {
                        roomsNoms.add(maisonRoom.getName());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel ComboBoxRoom = new DefaultComboBoxModel(roomsNoms.toArray(new String[0]));
            cbLightPiece.removeAllItems();
            cbLightPiece.setModel(ComboBoxRoom);

            try {
                MaisonAutomatisationParaJourSemaineService maisonAutomatisationParaJourSemaineService = new MaisonAutomatisationParaJourSemaineService(networkConfig);
                MaisonAutomatisation_Para_Jour_Semaines maisonAutomatisation_para_jour_semaines = maisonAutomatisationParaJourSemaineService.select_all_name_day();
                JourSemaine.clear();
                JourSemaine.add(maisonAutomatisation_para_jour_semaines);
                System.out.println("Import réussi!");
                System.out.println(JourSemaine);
                for (MaisonAutomatisation_Para_Jour_Semaines JourSe : JourSemaine)
                    for (MaisonAutomatisation_Para_Jour_Semaine Jour : JourSe.getMaisonAutomatisation_para_jour_semaines()) {
                        JourSemaine_dic.put(Jour.getNom(), Jour.getID_Para_Jour_Semaine().toString());
//                        JourSemaine_cE.add(Jour.getNom());

                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            DefaultComboBoxModel ComboBoxDay = new DefaultComboBoxModel(JourSemaine_dic.keySet().toArray(new String[0]));
            cbLightJour.removeAllItems();
            cbLightJour.setModel(ComboBoxDay);

            try {
                MaisonAutomatisationParaLumiereService maisonAutomatisationParaLumiereService = new MaisonAutomatisationParaLumiereService(networkConfig);
                MaisonAutomatisation_Para_Lumieres maisonAutomatisation_para_lumieres = maisonAutomatisationParaLumiereService.select_all_name_light();
                Lumiere.clear();
                Lumiere.add(maisonAutomatisation_para_lumieres);
                System.out.println("Import réussi!");
                System.out.println(Lumiere);
                for (MaisonAutomatisation_Para_Lumieres LumiereSe : Lumiere)
                    for (MaisonAutomatisation_Para_Lumiere Lumiere : LumiereSe.getMaisonAutomatisation_para_lumieres()) {
//                        TypeChauffage_cE.add(Chauffage.getNom());
                        Lumiere_dic.put(Lumiere.getNom(), Lumiere.getID_Para_Lumiere().toString());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            DefaultComboBoxModel ComboBoxHeater = new DefaultComboBoxModel(Lumiere_dic.keySet().toArray(new String[0]));
            cbLight.removeAllItems();
            cbLight.setModel(ComboBoxHeater);

            cardLayout.show(mainPanel, "ProgramLightPanel");
        });
        btnProgramWindow.addActionListener(e -> {
            txtProgramWindowName.setText("");
            try {
                roomsNoms.clear();
                MaisonRoomService maisonRoomServiceFind = new MaisonRoomService(networkConfig);
                MaisonRooms maisonRoomFind = maisonRoomServiceFind.selectRooms();
                rooms.clear();
                rooms.add(maisonRoomFind);
                for (MaisonRooms maisonRooms : rooms)
                    for (MaisonRoom maisonRoom : maisonRooms.getMaisonRooms()) {
                        roomsNoms.add(maisonRoom.getName());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel ComboBoxRoom = new DefaultComboBoxModel(roomsNoms.toArray(new String[0]));
            cbWindowPiece.removeAllItems();
            cbWindowPiece.setModel(ComboBoxRoom);

            try {
                MaisonAutomatisationParaJourSemaineService maisonAutomatisationParaJourSemaineService = new MaisonAutomatisationParaJourSemaineService(networkConfig);
                MaisonAutomatisation_Para_Jour_Semaines maisonAutomatisation_para_jour_semaines = maisonAutomatisationParaJourSemaineService.select_all_name_day();
                JourSemaine.clear();
                JourSemaine.add(maisonAutomatisation_para_jour_semaines);
                System.out.println("Import réussi!");
                System.out.println(JourSemaine);
                for (MaisonAutomatisation_Para_Jour_Semaines JourSe : JourSemaine)
                    for (MaisonAutomatisation_Para_Jour_Semaine Jour : JourSe.getMaisonAutomatisation_para_jour_semaines()) {
                        JourSemaine_dic.put(Jour.getNom(), Jour.getID_Para_Jour_Semaine().toString());
//                        JourSemaine_cE.add(Jour.getNom());

                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            DefaultComboBoxModel ComboBoxDay = new DefaultComboBoxModel(JourSemaine_dic.keySet().toArray(new String[0]));
            cbWindowJour.removeAllItems();
            cbWindowJour.setModel(ComboBoxDay);

            try {
                MaisonAutomatisationParaFenetreService maisonAutomatisationParaFenetreService = new MaisonAutomatisationParaFenetreService(networkConfig);
                MaisonAutomatisation_Para_Fenetres maisonAutomatisation_para_fenetres = maisonAutomatisationParaFenetreService.select_all_name_window();
                Fenetre.clear();
                Fenetre.add(maisonAutomatisation_para_fenetres);
                System.out.println("Import réussi!");
                System.out.println(Fenetre);
                for (MaisonAutomatisation_Para_Fenetres FentereSe : Fenetre)
                    for (MaisonAutomatisation_Para_Fenetre Fenetre : FentereSe.getMaisonAutomatisation_para_fenetres()) {
//                        TypeChauffage_cE.add(Chauffage.getNom());
                        Fenetre_dic.put(Fenetre.getNom(), Fenetre.getID_Para_Fenetre().toString());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            DefaultComboBoxModel ComboBoxHeater = new DefaultComboBoxModel(Fenetre_dic.keySet().toArray(new String[0]));
            cbWindow.removeAllItems();
            cbWindow.setModel(ComboBoxHeater);

            cardLayout.show(mainPanel, "ProgramWindowPanel");
        });
        btnEtatAutomation.addActionListener(e -> cardLayout.show(mainPanel, "EtatAutomatisationPanel"));
        btnDeleteAutomation.addActionListener(e -> cardLayout.show(mainPanel, "SupprimerAutomatisationPanel"));
        btnSimuleAutomation.addActionListener(e -> cardLayout.show(mainPanel, "SimulerAutomatisationPanel"));


        //Retour Menu Capteurs
        btnSensorsManagement.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));
        btnBackToMenu_NewCapteur.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));
        btnBackToMenu_VoirCapteurs.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));
        btnBackToMenu_ChangerEtat.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));
        btnBackToMenu_SupprimerCapteur.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));

        //Boutons Capteurs
        btnVoirCapteurs.addActionListener(e -> cardLayout.show(mainPanel, "voirCapteurPanel"));
        btnNewCapteur.addActionListener(e -> {
            txtNomCapteur.setText("");
            try {
                roomsNoms.clear();
                MaisonRoomService maisonRoomServiceFind = new MaisonRoomService(networkConfig);
                MaisonRooms maisonRoomFind = maisonRoomServiceFind.selectRooms();
                rooms.clear();
                rooms.add(maisonRoomFind);
                for (MaisonRooms maisonRooms : rooms)
                    for (MaisonRoom maisonRoom : maisonRooms.getMaisonRooms()) {
                        roomsNoms.add(maisonRoom.getName());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel ComboBoxRoom = new DefaultComboBoxModel(roomsNoms.toArray(new String[0]));
            cbPieceCapteur.removeAllItems();
            cbPieceCapteur.setModel(ComboBoxRoom);

            System.out.println("roomsNoms : " + roomsNoms);

            try {
                CapteurParaTypeService capteurParaTypeService = new CapteurParaTypeService(networkConfig);
                Capteur_Para_Types capteur_para_types = capteurParaTypeService.selectRequestOrder();
                capteur_types.clear();
                capteur_types.add(capteur_para_types);
                System.out.println("Import réussi!");
                System.out.println(capteur_types);
                for (Capteur_Para_Types CapteurTypes : capteur_types)
                    for (Capteur_Para_Type CapteurType : CapteurTypes.getCapteur_Para_Types()) {
                        TypeCapteur_dic.put(CapteurType.getNom(), CapteurType.getID_Para_TypeCapteur().toString());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            String valueIDTypeCapteur = "4";
            System.out.println(valueIDTypeCapteur);
            DefaultComboBoxModel ComboBoxType = new DefaultComboBoxModel(TypeCapteur_dic.keySet().toArray(new String[0]));
            cbTypeCapteur.removeAllItems();
            cbTypeCapteur.setModel(ComboBoxType);
            cardLayout.show(mainPanel, "NewCapteursPanel");
        });
        btnChangerEtat.addActionListener(e -> cardLayout.show(mainPanel, "EtatCapteurPanel"));
        btnSupprimerCapteur.addActionListener(e -> cardLayout.show(mainPanel, "SupprimerCapteurPanel"));

        //Boutons Capteurs R3
        btnBackToMenu_TypeLum.addActionListener(e -> {
                    cardLayout.show(mainPanel, "NewCapteursPanel");
                    float valeurReglage = (float) spLum.getValue();
                    System.out.println("valeurReglage = " + valeurReglage);
                }
        );
        btnBackToMenu_TypeTemp.addActionListener(e -> cardLayout.show(mainPanel, "NewCapteursPanel"));
        btnBackToMenu_TypeMouv.addActionListener(e -> cardLayout.show(mainPanel, "NewCapteursPanel"));
        btnSuivantSaveCapteur.addActionListener(e -> {
            spTemp.setValue(1.0);
            spLum.setValue(65000.0);
            spMouv.setValue(5.0);
            String nomCapteur = txtNomCapteur.getText().trim();
            String capteurTypeSelect = (String) cbTypeCapteur.getSelectedItem();
            String capteurPieceSelect = (String) cbPieceCapteur.getSelectedItem();
            String etatSelect;
            float valeurReglage = ((Number) spLum.getValue()).floatValue();
            if (cbEtatCapteur.isSelected()) {
                etatSelect = "ON";
            } else {
                etatSelect = "OFF";
            }
            if (nomCapteur.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir un nom de capteur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int CountAutomationNameEqual = 0;
            try {
                MaisonCapteurService maisonCapteurServiceFind = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteurFind = maisonCapteurServiceFind.selectAllCapteurs();
                capteurs.clear();
                capteurs.add(maisonCapteurFind);
                for (MaisonCapteurs capt : capteurs)
                    for (MaisonCapteur cap : capt.getCapteurs()) {
                        if (nomCapteur.equalsIgnoreCase(cap.getName())) {
                            CountAutomationNameEqual++;
                        }
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (CountAutomationNameEqual >= 1) {
                JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if ("1".equals(valueIDTypeCapteur)) {
                cardLayout.show(mainPanel, "CapteurLum");
            } else if ("2".equals(valueIDTypeCapteur)) {
                cardLayout.show(mainPanel, "CapteurMouv");
            } else if ("3".equals(valueIDTypeCapteur)) {
                cardLayout.show(mainPanel, "CapteurTemp");
            } else {
                JOptionPane.showMessageDialog(frame, "Veuillez sélectionner un type de capteur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ;
            ;
        });

        btnEnregistrerTypeLum.addActionListener(e -> {
            //Récupération des données
            String nomCapteur = txtNomCapteur.getText().trim();
            String capteurTypeSelect = (String) cbTypeCapteur.getSelectedItem();
            String capteurPieceSelect = (String) cbPieceCapteur.getSelectedItem();
            String etatSelect;
            float valeurReglage = ((Number) spLum.getValue()).floatValue();
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
            MaisonCapteur maisonCapteur = new MaisonCapteur(nomCapteur, valueIDTypeCapteur, capteurPieceSelect, etatSelect, 0, valeurReglage);
            try {
                MaisonCapteurService maisonCapteurServiceFind = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteurFind = maisonCapteurServiceFind.selectAllCapteurs();
                capteurs.clear();
                capteurs.add(maisonCapteurFind);
                for (MaisonCapteurs capt : capteurs)
                    for (MaisonCapteur cap : capt.getCapteurs()) {
                        if (nomCapteur.equalsIgnoreCase(cap.getName())) {
                            CountAutomationNameEqual++;
                        }
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (CountAutomationNameEqual >= 1) {
                JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MaisonCapteurService maisonCapteurService = new MaisonCapteurService(networkConfig);
                maisonCapteurService.insertCapteur(maisonCapteur);
                JOptionPane.showMessageDialog(frame, "Capteur enregistré avec succès!");
                cardLayout.show(mainPanel, "CapteursPanel");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnEnregistrerTypeMouv.addActionListener(e -> {
            //Récupération des données
            String nomCapteur = txtNomCapteur.getText().trim();
            String capteurTypeSelect = (String) cbTypeCapteur.getSelectedItem();
            String capteurPieceSelect = (String) cbPieceCapteur.getSelectedItem();
            String etatSelect;
            float valeurReglage = ((Number) spMouv.getValue()).floatValue();
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
            MaisonCapteur maisonCapteur = new MaisonCapteur(nomCapteur, valueIDTypeCapteur, capteurPieceSelect, etatSelect, 0, valeurReglage);
            try {
                MaisonCapteurService maisonCapteurServiceFind = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteurFind = maisonCapteurServiceFind.selectAllCapteurs();
                capteurs.clear();
                capteurs.add(maisonCapteurFind);
                for (MaisonCapteurs capt : capteurs)
                    for (MaisonCapteur cap : capt.getCapteurs()) {
                        if (nomCapteur.equalsIgnoreCase(cap.getName())) {
                            CountAutomationNameEqual++;
                        }
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (CountAutomationNameEqual >= 1) {
                JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MaisonCapteurService maisonCapteurService = new MaisonCapteurService(networkConfig);
                maisonCapteurService.insertCapteur(maisonCapteur);
                JOptionPane.showMessageDialog(frame, "Capteur enregistré avec succès!");
                cardLayout.show(mainPanel, "CapteursPanel");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        btnEnregistrerTypeTemp.addActionListener(e -> {
            //Récupération des données
            String nomCapteur = txtNomCapteur.getText().trim();
            String capteurTypeSelect = (String) cbTypeCapteur.getSelectedItem();
            String capteurPieceSelect = (String) cbPieceCapteur.getSelectedItem();
            String etatSelect;
            float valeurReglage = ((Number) spTemp.getValue()).floatValue();
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
            MaisonCapteur maisonCapteur = new MaisonCapteur(nomCapteur, valueIDTypeCapteur, capteurPieceSelect, etatSelect, 0, valeurReglage);
            try {
                MaisonCapteurService maisonCapteurServiceFind = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteurFind = maisonCapteurServiceFind.selectAllCapteurs();
                capteurs.clear();
                capteurs.add(maisonCapteurFind);
                for (MaisonCapteurs capt : capteurs)
                    for (MaisonCapteur cap : capt.getCapteurs()) {
                        if (nomCapteur.equalsIgnoreCase(cap.getName())) {
                            CountAutomationNameEqual++;
                        }
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (CountAutomationNameEqual >= 1) {
                JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MaisonCapteurService maisonCapteurService = new MaisonCapteurService(networkConfig);
                maisonCapteurService.insertCapteur(maisonCapteur);
                JOptionPane.showMessageDialog(frame, "Capteur enregistré avec succès!");
                cardLayout.show(mainPanel, "CapteursPanel");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Retour Menu Rooms
        btnBackToMenuNewRoom.addActionListener(e -> cardLayout.show(mainPanel, "HouseManagementPanel"));
        btnBackToMenu_VoirRooms.addActionListener(e -> cardLayout.show(mainPanel, "HouseManagementPanel"));
        btnBackToMenuNewRoom2.addActionListener(e -> cardLayout.show(mainPanel, "HouseManagementPanel"));
        btnBackToMenu_ModifierRoom.addActionListener(e -> cardLayout.show(mainPanel, "HouseManagementPanel"));
        btnBackToMenu_SupprimerRoom.addActionListener(e -> cardLayout.show(mainPanel, "HouseManagementPanel"));

        //Boutons Rooms
        btnViewRoom.addActionListener(e -> cardLayout.show(mainPanel, "voirRoomPanel"));
        btnHouseManagement.addActionListener(e -> cardLayout.show(mainPanel, "HouseManagementPanel"));
        btnNewRoom.addActionListener(e -> {
            txtNameRoom.setText("");
            try {
                RoomParaTypeService roomParaTypeService = new RoomParaTypeService(networkConfig);
                Room_Para_Types room_para_types = roomParaTypeService.selectRequestOrder();
                room_types.clear();
                room_types.add(room_para_types);
                System.out.println("Import réussi!");
                System.out.println(room_types);
                for (Room_Para_Types RoomTypes : room_types)
                    for (Room_Para_Type RoomType : RoomTypes.getRoom_Para_Types()) {
                        TypeRoom_dic.put(RoomType.getNom(), RoomType.getID_Para_TypeRoom().toString());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel ComboBoxTypeRoom = new DefaultComboBoxModel(TypeRoom_dic.keySet().toArray(new String[0]));
            cbTypeRoom.removeAllItems();
            cbTypeRoom.setModel(ComboBoxTypeRoom);
            cardLayout.show(mainPanel, "RoomPanel");
        });

        btnModifierRoom.addActionListener(e -> cardLayout.show(mainPanel, "RoomDefiniePanel"));
        btnChoisirRoom.addActionListener(e -> {
            txtNameRoom2.setText("");
            try {
                RoomParaTypeService roomParaTypeService = new RoomParaTypeService(networkConfig);
                Room_Para_Types room_para_types = roomParaTypeService.selectRequestOrder();
                room_types.clear();
                room_types.add(room_para_types);
                System.out.println("Import réussi!");
                System.out.println(room_types);
                for (Room_Para_Types RoomTypes : room_types)
                    for (Room_Para_Type RoomType : RoomTypes.getRoom_Para_Types()) {
                        TypeRoom_dic.put(RoomType.getNom(), RoomType.getID_Para_TypeRoom().toString());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel ComboBoxTypeRoom = new DefaultComboBoxModel(TypeRoom_dic.keySet().toArray(new String[0]));
            cbTypeRoom2.removeAllItems();
            cbTypeRoom2.setModel(ComboBoxTypeRoom);
            cardLayout.show(mainPanel, "ModifierRoomPanel");
        });
        btnSupprimerRoom.addActionListener(e -> cardLayout.show(mainPanel, "SupprimerRoomPanel"));


//Boutons plus complexes

        //Boutons Affichage
        btnViewPrograms.addActionListener(e -> {
            try {
                MaisonProgrammeService maisonProgrammeService = new MaisonProgrammeService(networkConfig);
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

            tableModel.setRowCount(0); // ➤ vide l'ancien contenu
            if (programmes.isEmpty()) {
                tableModel.addRow(new Object[]{"Aucun programme", "", "", "", "", "", ""});
            } else {
                for (MaisonProgrammes maisonProg : programmes) {
                    for (MaisonProgramme prog : maisonProg.getMaisonProgrammes()) {
                        Object[] row = {
                                prog.getNomProgramme(),
                                prog.getTypePiece(),
                                prog.getTypeChauffage(),
                                prog.getJourSemaine(),
                                prog.getTemperature(),
                                prog.getHeureDebut(),
                                prog.getHeureFin()
                        };
                        tableModel.addRow(row);
                    }
                }
            }
            try {
                MaisonProgrammeLumiereService maisonProgrammeLumiereService = new MaisonProgrammeLumiereService(networkConfig);
                MaisonProgrammesLumieres maisonProgrammesLumieres = maisonProgrammeLumiereService.select_all_Light_program();
                programmesLumieres.clear();
                programmesLumieres.add(maisonProgrammesLumieres);
                System.out.println("Import réussi!");
                System.out.println(programmesLumieres);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            for (MaisonProgrammesLumieres maisonProgrammesLumieres : programmesLumieres) {
                for (MaisonProgrammeLumiere maisonProgrammesLumiere1 : maisonProgrammesLumieres.getMaisonProgrammesLumieres()) {
                    Object[] row = {
                            maisonProgrammesLumiere1.getNomProgramme(),
                            maisonProgrammesLumiere1.getTypePiece(),
                            maisonProgrammesLumiere1.getLumiere(),
                            maisonProgrammesLumiere1.getJourSemaine(),
                            maisonProgrammesLumiere1.getTemperature(),
                            maisonProgrammesLumiere1.getHeureDebut(),
                            maisonProgrammesLumiere1.getHeureFin()
                    };
                    tableModel.addRow(row);
                    cardLayout.show(mainPanel, "ViewProgramsPanel");
                }
            }
            try {
                MaisonProgrammeFenetreService maisonProgrammeFenetreService = new MaisonProgrammeFenetreService(networkConfig);
                MaisonProgrammesFenetres maisonProgrammesFenetres = maisonProgrammeFenetreService.select_all_Window_program();
                programmesFenetres.clear();
                programmesFenetres.add(maisonProgrammesFenetres);
                System.out.println("Import réussi!");
                System.out.println(programmesLumieres);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            for (MaisonProgrammesFenetres maisonProgrammesFenetres : programmesFenetres) {
                for (MaisonProgrammeFenetre maisonProgrammesFenetre1 : maisonProgrammesFenetres.getMaisonProgrammesFenetres()) {
                    Object[] row = {
                            maisonProgrammesFenetre1.getNomProgramme(),
                            maisonProgrammesFenetre1.getTypePiece(),
                            maisonProgrammesFenetre1.getFenetre(),
                            maisonProgrammesFenetre1.getJourSemaine(),
                            maisonProgrammesFenetre1.getTemperature(),
                            maisonProgrammesFenetre1.getHeureDebut(),
                            maisonProgrammesFenetre1.getHeureFin()
                    };
                    tableModel.addRow(row);
                    cardLayout.show(mainPanel, "ViewProgramsPanel");

                }
            }
        });

        btnViewAutomations.addActionListener(e -> {
            try {
                MaisonAutomatisationService maisonAutomatisationService = new MaisonAutomatisationService(networkConfig);
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

            tableModelAutomation.setRowCount(0); // ➤ vide l'ancien contenu
            if (automatisations.isEmpty()) {
                // Affiche une ligne vide ou un message dans une autre UI si besoin
                tableModelAutomation.addRow(new Object[]{"Aucune automatisations", "", "", "", "", "", ""});
            } else {
                for (MaisonAutomatisations maisonauto : automatisations) {
                    for (MaisonAutomatisation auto : maisonauto.getMaisonAutomatisations()) {
                        Object[] row = {
                                auto.getNomAutomatisation(),
                                auto.getTypeProgramme(),
                                auto.getTypeCapteur(),
                                auto.getEtatAutomatisation()
                        };
                        tableModelAutomation.addRow(row);
                    }
                }
            }
            cardLayout.show(mainPanel, "ViewAutomationsPanel");
        });

        btnVoirCapteurs.addActionListener(e -> {
            try {
                MaisonCapteurService maisonCapteurService = new MaisonCapteurService(networkConfig);
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

            tableModelCapteur.setRowCount(0); // vide l'ancien contenu
            if (capteurs.isEmpty()) {
                tableModelCapteur.addRow(new Object[]{"Aucun capteur.", "", "", "", "", "", ""});
            } else {
                for (MaisonCapteurs maisoncapt : capteurs) {
                    for (MaisonCapteur capt : maisoncapt.getCapteurs()) {
                        Object[] row = {
                                capt.getName(),
                                capt.getTypeCapteur(),
                                capt.getPieceCapteur(),
                                capt.getReglageCapteur(),
                                capt.getEtat()
                        };
                        System.out.println(capt.getPieceCapteur());
                        System.out.println(capt.getReglageCapteur());
                        tableModelCapteur.addRow(row);
                    }
                }
                cardLayout.show(mainPanel, "voirCapteurPanel");
            }
        });

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

            tableModelRoom.setRowCount(0);
            if (rooms.isEmpty()) {
                tableModelRoom.addRow(new Object[]{"Aucune pièce.", "", "", "", "", "", ""});
            } else {
                Set<String> roomDoublon = new HashSet<>();
                for (MaisonRooms maisonroom : rooms) {
                    for (MaisonRoom room : maisonroom.getMaisonRooms()) {
                        String roomNameDoublon = room.getName();
                        Object[] row = {
                                room.getName(),
                                room.getType(),
                                room.getSurface(),
                                room.getCapteur(),
                        };
//                        System.out.println(room.getCapteurPiece());
                        tableModelRoom.addRow(row);
                    }
                }
            }
            cardLayout.show(mainPanel, "voirRoomPanel");
        });

        btnVisualiserRoom.addActionListener(e -> {
            VisualisationRooms visualisationRooms = new VisualisationRooms(networkConfig);
            visualisationRooms.showRooms();
            cardLayout.show(mainPanel, "visualisationRoomPanel");
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
            int i = 0;
            try {
                MaisonCapteurService maisonCapteurServiceFind = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteurFind = maisonCapteurServiceFind.selectAllCapteurs();
                capteurs.clear();
                capteurs.add(maisonCapteurFind);

                for (MaisonCapteurs capt : capteurs) {
                    for (MaisonCapteur cap : capt.getCapteurs()) {
                        if (cap.getName().equals(cap_select)) {
                            cap.setEtat(etat_select);
                            update_delete_Capteur.updateCapteur(cap);
                            JOptionPane.showMessageDialog(frame, "Changement d'état enregistré avec succès!");
                            cardLayout.show(mainPanel, "CapteursPanel");
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
            int i = 0;
            try {
                MaisonAutomatisationService maisonAutomatisationService = new MaisonAutomatisationService(networkConfig);
                MaisonAutomatisations maisonAutomatisationsFind = maisonAutomatisationService.select_all_automation();
                automatisations.clear();
                automatisations.add(maisonAutomatisationsFind);

                for (MaisonAutomatisations auto : automatisations) {
                    for (MaisonAutomatisation aut : auto.getMaisonAutomatisations()) {
                        if (aut.getNomAutomatisation().equals(auto_select)) {
                            aut.setEtatAutomatisation(etat_auto_select);
                            update_delete_automatisation.updateAutomation(aut);
                            JOptionPane.showMessageDialog(frame, "Changement d'état enregistré avec succès!");
                            cardLayout.show(mainPanel, "Automations_and_ProgramsPanel");
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

        //Bouton Modifier les données d'une pièce
        btnModifierRoom.addActionListener(e -> {
            StringBuilder sb_room = new StringBuilder();
            if (rooms.isEmpty()) {
                sb_room.append("Aucune pièce enregistrée.\n");
            } else {
                sb_room.append("Pièces enregistrées :\n");
                for (MaisonRooms maisonRooms : rooms)
                    for (MaisonRoom maisonRoom : maisonRooms.getMaisonRooms()) {
                        sb_room.append("Nom : ").append(maisonRoom.getName()).append("\n")
                                .append("Type : ").append(maisonRoom.getType()).append("\n")
                                .append("Surface : ").append(maisonRoom.getSurface()).append("\n\n");
                    }
            }

            try {
                roomsNoms.clear();
                MaisonRoomService maisonRoomServiceFind = new MaisonRoomService(networkConfig);
                MaisonRooms maisonRoomFind = maisonRoomServiceFind.selectRooms();
                rooms.clear();
                rooms.add(maisonRoomFind);
                for (MaisonRooms maisonRooms : rooms)
                    for (MaisonRoom maisonRoom : maisonRooms.getMaisonRooms()) {
                        roomsNoms.add(maisonRoom.getName());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(roomsNoms.toArray(new String[0]));
            cbRoomsExistantes.removeAllItems();
            cbRoomsExistantes.setModel(model);
        });

        //Bouton enregistrer les modifications des données d'une pièce
        btnSaveModifRoom.addActionListener(e -> {
            String room_select = cbRoomsExistantes.getSelectedItem().toString();
            String name_select = txtNameRoom2.getText().toString();
            String type_select = cbTypeRoom2.getSelectedItem().toString();
            Object surface_select = spSurfaceRoom2.getValue().toString();
            int surface_select_int = Integer.parseInt(surface_select.toString());
            if (name_select.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir le nom de la pièce.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                MaisonRoomService maisonRoomServiceFind = new MaisonRoomService(networkConfig);
                MaisonRooms maisonRoomFind = maisonRoomServiceFind.selectRooms();
                rooms.clear();
                rooms.add(maisonRoomFind);
                for (MaisonRooms maisonRooms : rooms) {
                    System.out.println(maisonRooms);
                    for (MaisonRoom maisonRoom : maisonRooms.getMaisonRooms()) {
                        System.out.println(maisonRoom);
                        if (maisonRoom.getName().equals(room_select)) {
                            System.out.println(maisonRoom.getId());
                            maisonRoom.setName(name_select);
                            maisonRoom.setType(type_select);
                            maisonRoom.setSurface(surface_select_int);
                            update_delete_room.updateRoom(maisonRoom);
                            break;
                        }
                    }
                }
                JOptionPane.showMessageDialog(frame, "Pièce modifiée avec succès!");
                cardLayout.show(mainPanel, "HouseManagementPanel");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        //Bouton Suppression Automatisation
        btnDeleteAutomation.addActionListener(e -> {
            try {
                automatisationsNoms.clear();
                MaisonAutomatisationService maisonAutomatisationServiceFind = new MaisonAutomatisationService(networkConfig);
                MaisonAutomatisations maisonAutomatisationsFind = maisonAutomatisationServiceFind.select_all_automation();
                automatisations.clear();
                automatisations.add(maisonAutomatisationsFind);
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
            cbAutomatisationsExistantes_Supp.removeAllItems();
            cbAutomatisationsExistantes_Supp.setModel(model);
        });
        btnEnregistrerSupprimerAutomatisation.addActionListener(e -> {
            String auto_select = cbAutomatisationsExistantes_Supp.getSelectedItem().toString();
            try {
                MaisonAutomatisationService maisonAutomatisationServiceFind = new MaisonAutomatisationService(networkConfig);
                MaisonAutomatisations maisonAutomatisationsFind = maisonAutomatisationServiceFind.select_all_automation();
                automatisations.clear();
                automatisations.add(maisonAutomatisationsFind);
                for (MaisonAutomatisations auto : automatisations) {
                    for (MaisonAutomatisation aut : auto.getMaisonAutomatisations()) {
                        if (aut.getNomAutomatisation().equals(auto_select)) {
                            logger.debug("Suppression de l'automatisation : {}", aut.getNomAutomatisation());
                            update_delete_automatisation.deleteAutomation(aut);
                            JOptionPane.showMessageDialog(frame, "Automatisation supprimé avec succès!");
                            cardLayout.show(mainPanel, "Automations_and_ProgramsPanel");
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

        //Bouton Simulation Automatisation
        btnSimuleAutomation.addActionListener(e -> {
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
                System.out.println(capteursNoms_cE);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(capteursNoms_cE.toArray(new String[0]));
            cbAutomatisationsExistantes_Simuler.removeAllItems();
            cbAutomatisationsExistantes_Simuler.setModel(model);
        });
        btnSimulerAutomatisation.addActionListener(e -> {
            String auto_simule_select = cbAutomatisationsExistantes_Simuler.getSelectedItem().toString();
            try {
                MaisonAutomatisationService maisonAutomatisationService = new MaisonAutomatisationService(networkConfig);
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
            automatisationsNoms.clear();
            for (MaisonAutomatisations maisonauto : automatisations) {
                for (MaisonAutomatisation auto : maisonauto.getMaisonAutomatisations()) {
                    if (auto_simule_select.equalsIgnoreCase(auto.getTypeCapteur())) {
                        automatisationsNoms.add(auto.getNomAutomatisation());
                    }
                }
            }
            StringBuilder SbAutoTrigger = new StringBuilder("Automatisations exécutées :\n");
            for (String element : automatisationsNoms) {
                SbAutoTrigger.append("- ").append(element).append("\n");
            }

            // Affichage dans une boîte de dialogue
            JOptionPane.showMessageDialog(null, SbAutoTrigger.toString(), "Domotique maison", JOptionPane.INFORMATION_MESSAGE);

            System.out.println(automatisationsNoms);
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
                        if (cap.getName().equals(cap_select)) {
                            logger.debug("Suppression du capteur : {}", cap.getName());
                            update_delete_Capteur.deleteCapteur(cap);
                            JOptionPane.showMessageDialog(frame, "Capteur supprimé avec succès!");
                            cardLayout.show(mainPanel, "CapteursPanel");
                            break;
                        }
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Boolean autofind = false;
            try {
                MaisonAutomatisationService maisonAutomatisationServiceFind = new MaisonAutomatisationService(networkConfig);
                MaisonAutomatisations maisonAutomatisationsFind = maisonAutomatisationServiceFind.select_all_automation();
                automatisations.clear();
                automatisations.add(maisonAutomatisationsFind);
                for (MaisonAutomatisations auto : automatisations) {
                    for (MaisonAutomatisation aut : auto.getMaisonAutomatisations()) {
                        if (aut.getTypeCapteur().equals(cap_select)) {
                            logger.debug("Suppression de l'automatisation : {}", aut.getNomAutomatisation());
                            update_delete_automatisation.deleteAutomation(aut);
                            autofind = true;
                        }
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (autofind = true) {
                JOptionPane.showMessageDialog(frame, "Automatisation(s) supprimée(s) avec succès!");
            } else {
                JOptionPane.showMessageDialog(frame, "Aucune automatisation trouvée pour ce capteur.");
            }
            cardLayout.show(mainPanel, "CapteursPanel");

        });


    //Bouton Suppression Pièce
        btnSupprimerRoom.addActionListener(e -> {
            try {
                roomsNoms.clear();
                MaisonRoomService maisonRoomServiceFind = new MaisonRoomService(networkConfig);
                MaisonRooms maisonRoomFind = maisonRoomServiceFind.selectRooms();
                rooms.clear();
                rooms.add(maisonRoomFind);
                for (MaisonRooms maisonRooms : rooms)
                    for (MaisonRoom maisonRoom : maisonRooms.getMaisonRooms()) {
                        roomsNoms.add(maisonRoom.getName());
                    }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            DefaultComboBoxModel model = new DefaultComboBoxModel(roomsNoms.toArray(new String[0]));
            cbRoomsExistantes_Supp.removeAllItems();
            cbRoomsExistantes_Supp.setModel(model);
        });
        btnEnregistrerSupprimerRoom.addActionListener(e -> {
            String room_select = cbRoomsExistantes_Supp.getSelectedItem().toString();
            try {
                MaisonRoomService maisonRoomServiceFind = new MaisonRoomService(networkConfig);
                MaisonRooms maisonRoomFind = maisonRoomServiceFind.selectRooms();
                rooms.clear();
                rooms.add(maisonRoomFind);
                for (MaisonRooms maisonRooms : rooms) {
                    for (MaisonRoom maisonRoom : maisonRooms.getMaisonRooms()) {
                        if (maisonRoom.getName().equals(room_select)){
                            logger.debug("Suppression de la pièce : {}", maisonRoom.getName());
                            update_delete_room.deleteRoom(maisonRoom);
                            JOptionPane.showMessageDialog(frame, "Pièce supprimée avec succès!");
                            break;
                        }
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Boolean autofind = false;
            try {
                MaisonCapteurService maisonCapteurServiceFind = new MaisonCapteurService(networkConfig);
                MaisonCapteurs maisonCapteursFind = maisonCapteurServiceFind.selectAllCapteurs();
                capteurs.clear();
                capteurs.add(maisonCapteursFind);
                for (MaisonCapteurs capt : capteurs) {
                    for (MaisonCapteur cap : capt.getCapteurs()) {
                        if (cap.getPieceCapteur().equals(room_select)) {
                            logger.debug("Suppression du capteur : {}", cap.getName());
                            update_delete_Capteur.deleteCapteur(cap);
                            autofind = true;
                        }
                    }
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (autofind = true) {
                JOptionPane.showMessageDialog(frame, "Capteur(s) lié(s) supprimé(s) avec succès!");
            } else {
                JOptionPane.showMessageDialog(frame, "Aucun capteur trouvé pour cette pièce.");
            }
            cardLayout.show(mainPanel, "HouseManagementPanel");
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
            MaisonProgramme maisonProgramme = new MaisonProgramme(nomProgramme,PieceSelection,valueIDTypeChauffage, valueIDJour,TemperatureSelection,HeureDebutSelection,HeureFinSelection);
            int CountProgramNameEqual = 0;
            try {
                MaisonProgrammeService maisonProgrammeServiceFind = new MaisonProgrammeService(networkConfig);
                MaisonProgrammes maisonProgrammeFind = maisonProgrammeServiceFind.select_all_program();
                programmes.clear();
                programmes.add(maisonProgrammeFind);
                for (MaisonProgrammes Maisonprogramme : programmes)
                    for (MaisonProgramme programme : Maisonprogramme.getMaisonProgrammes()) {
                        if (nomProgramme.equalsIgnoreCase(programme.getNomProgramme())){
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

        btnWindowSaveProgram.addActionListener(e -> {
            String nomProgramme = txtProgramWindowName.getText().trim();
            String PieceSelection = (String) cbWindowPiece.getSelectedItem();
            String ChauffageSelection = (String) cbWindow.getSelectedItem();
            String JourSelection = (String) cbWindowJour.getSelectedItem();
            Integer OuvertureSelection = (int) spWindowOuverture.getValue();
            Integer HeureDebutSelection = (int) spWindowHeureDebut.getValue();
            Integer HeureFinSelection = (int) spWindowHeureFin.getValue();
            if (nomProgramme.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir un nom de programme.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (HeureDebutSelection >= HeureFinSelection) {
                JOptionPane.showMessageDialog(frame, "L'heure de début doit être inférieure à l'heure de fin.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MaisonProgrammeFenetre maisonProgrammeFenetre = new MaisonProgrammeFenetre(nomProgramme,PieceSelection,valueIDFenetre, valueIDJour,OuvertureSelection,HeureDebutSelection,HeureFinSelection);
            int CountProgramNameEqual = 0;
            try {
                MaisonProgrammeFenetreService maisonProgrammeFenetreServiceFind = new MaisonProgrammeFenetreService(networkConfig);
                MaisonProgrammesFenetres maisonProgrammeFenetresFind = maisonProgrammeFenetreServiceFind.select_all_Window_program();
                programmesFenetres.clear();
                programmesFenetres.add(maisonProgrammeFenetresFind);
                for (MaisonProgrammesFenetres MaisonprogrammeFenetre : programmesFenetres)
                    for (MaisonProgrammeFenetre programmeFenetre : MaisonprogrammeFenetre.getMaisonProgrammesFenetres()) {
                        if (nomProgramme.equalsIgnoreCase(programmeFenetre.getNomProgramme())){
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
                MaisonProgrammeFenetreService maisonProgrammeFenetreService =new MaisonProgrammeFenetreService(networkConfig);
                maisonProgrammeFenetreService.insert_Window_Program(maisonProgrammeFenetre,"INSERT_PROGRAM_WINDOW");
                JOptionPane.showMessageDialog(frame, "Programmes enregistré avec succès!");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        btnLightSaveProgram.addActionListener(e -> {
            String nomProgramme = txtProgramLightName.getText().trim();
            String PieceSelection = (String) cbLightPiece.getSelectedItem();
            String ChauffageSelection = (String) cbLight.getSelectedItem();
            String JourSelection = (String) cbLightJour.getSelectedItem();
            Integer OuvertureSelection = (int) spLightIntensite.getValue();
            Integer HeureDebutSelection = (int) spLightHeureDebut.getValue();
            Integer HeureFinSelection = (int) spLightHeureFin.getValue();
            if (nomProgramme.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir un nom de programme.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (HeureDebutSelection >= HeureFinSelection) {
                JOptionPane.showMessageDialog(frame, "L'heure de début doit être inférieure à l'heure de fin.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            MaisonProgrammeLumiere maisonProgrammeLumiere = new MaisonProgrammeLumiere(nomProgramme,PieceSelection,valueIDLumiere, valueIDJour,OuvertureSelection,HeureDebutSelection,HeureFinSelection);
            int CountProgramNameEqual = 0;
            try {
                MaisonProgrammeLumiereService maisonProgrammeLumiereService = new MaisonProgrammeLumiereService(networkConfig);
                MaisonProgrammesLumieres maisonProgrammeLumieresFind = maisonProgrammeLumiereService.select_all_Light_program();
                programmesLumieres.clear();
                programmesLumieres.add(maisonProgrammeLumieresFind);
                for (MaisonProgrammesLumieres MaisonprogrammeLumiere : programmesLumieres)
                    for (MaisonProgrammeLumiere programmeLumiere : MaisonprogrammeLumiere.getMaisonProgrammesLumieres()) {
                        if (nomProgramme.equalsIgnoreCase(programmeLumiere.getNomProgramme())){
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
                MaisonProgrammeLumiereService maisonProgrammeLumiereService =new MaisonProgrammeLumiereService(networkConfig);
                maisonProgrammeLumiereService.insertLightProgram(maisonProgrammeLumiere,"INSERT_PROGRAM_LIGHT");
                JOptionPane.showMessageDialog(frame, "Programmes enregistré avec succès!");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        });

        cbJour.addActionListener(e ->  {
                String selectedKeyDay = (String) cbJour.getSelectedItem();

                valueIDJour = JourSemaine_dic.get(selectedKeyDay);

                System.out.println("Jour sélectionné : " + selectedKeyDay + ", ID associé : " + valueIDJour);
        });

        cbWindowJour.addActionListener(e ->  {
            String selectedKeyDay = (String) cbWindowJour.getSelectedItem();

            valueIDJour = JourSemaine_dic.get(selectedKeyDay);

            System.out.println("Jour sélectionné : " + selectedKeyDay + ", ID associé : " + valueIDJour);
        });

        cbLightJour.addActionListener(e ->  {
            String selectedKeyDay = (String) cbLightJour.getSelectedItem();

            valueIDJour = JourSemaine_dic.get(selectedKeyDay);

            System.out.println("Jour sélectionné : " + selectedKeyDay + ", ID associé : " + valueIDJour);
        });

        cbChauffage.addActionListener(e ->  {
            String selectedKeyTypeChauffage = (String) cbChauffage.getSelectedItem();

            valueIDTypeChauffage = TypeChauffage_dic.get(selectedKeyTypeChauffage);

            System.out.println("Chauffage Séléctionné : " + selectedKeyTypeChauffage + ", ID associé : " + valueIDTypeChauffage);
        });

        cbLight.addActionListener(e ->  {
            String selectedKeyLight = (String) cbLight.getSelectedItem();

            valueIDLumiere = Lumiere_dic.get(selectedKeyLight);

            System.out.println("Lumière Séléctionné : " + selectedKeyLight + ", ID associé : " + valueIDLumiere);
        });

        cbWindow.addActionListener(e ->  {
            String selectedKeyWindow = (String) cbWindow.getSelectedItem();

            valueIDFenetre = Fenetre_dic.get(selectedKeyWindow);

            System.out.println("Fenêtre Séléctionné : " + selectedKeyWindow + ", ID associé : " + valueIDFenetre);
        });

        cbTypeCapteur.addActionListener(e ->  {
            String selectedKeyWindow = (String) cbTypeCapteur.getSelectedItem();

            valueIDTypeCapteur = TypeCapteur_dic.get(selectedKeyWindow);

            System.out.println("Type de capteur sélectionné : " + selectedKeyWindow + ", ID associé : " + valueIDTypeCapteur);
        });

        cbTypeRoom.addActionListener(e ->  {
            String selectedKeyTypeRoom = (String) cbTypeRoom.getSelectedItem();

            valueIDTypeRoom = TypeRoom_dic.get(selectedKeyTypeRoom);

            System.out.println("Type de pièce sélectionné : " + selectedKeyTypeRoom + ", ID associé : " + valueIDTypeRoom);
        });

        cbTypeRoom2.addActionListener(e ->  {
            String selectedKeyTypeRoom2 = (String) cbTypeRoom2.getSelectedItem();

            valueIDTypeRoom2 = TypeRoom_dic.get(selectedKeyTypeRoom2);

            System.out.println("Type de pièce sélectionné : " + selectedKeyTypeRoom2 + ", ID associé : " + valueIDTypeRoom2);
        });

        btnSaveAutomation.addActionListener(e -> {
                    // Validation des données
                    String nomAutomation = txtAutomationName.getText().trim();
                    String CapteurSelection = (String) cbSensor_activation.getSelectedItem();
                    String ProgrammeSelection = (String) cbSensor_programme.getSelectedItem();
                    String etatAutomation;
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
                    MaisonAutomatisation maisonAutomatisation = new MaisonAutomatisation(0, nomAutomation, CapteurSelection, ProgrammeSelection, etatAutomation);
                    try {
                        MaisonAutomatisationService maisonAutomatisationServiceFind = new MaisonAutomatisationService(networkConfig);
                        MaisonAutomatisations maisonAutomatisationFind = maisonAutomatisationServiceFind.select_all_automation();
                        automatisations.clear();
                        automatisations.add(maisonAutomatisationFind);
                        for (MaisonAutomatisations Maisonauto : automatisations)
                            for (MaisonAutomatisation auto : Maisonauto.getMaisonAutomatisations()) {
                                if (nomAutomation.equalsIgnoreCase(auto.getNomAutomatisation())) {
                                    CountAutomationNameEqual = CountAutomationNameEqual + 1;
                                }
                            }
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (CountAutomationNameEqual >= 1) {
                        JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    try {
                        MaisonAutomatisationService maisonAutomatisationService = new MaisonAutomatisationService(networkConfig);
                        maisonAutomatisationService.insertAutomation(maisonAutomatisation);
                        JOptionPane.showMessageDialog(frame, "Automatisation enregistré avec succès!");
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
        });;

        btnSaveCapteur.addActionListener(e -> {
            //Récupération des données
            String nomCapteur = txtNomCapteur.getText().trim();
            String capteurTypeSelect = (String) cbTypeCapteur.getSelectedItem();
            String capteurPieceSelect = (String) cbPieceCapteur.getSelectedItem();
            String etatSelect ;
            float valeurReglage = ((Number) spLum.getValue()).floatValue();
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
            MaisonCapteur maisonCapteur = new MaisonCapteur(nomCapteur,valueIDTypeCapteur,capteurPieceSelect, etatSelect, 0, valeurReglage);
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
                cardLayout.show(mainPanel, "CapteursPanel");
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

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
            System.out.println(valueIDTypeRoom);
            MaisonRoom maisonRoom = new MaisonRoom(nameRoom,valueIDTypeRoom,surfaceRoom,0);
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
                maisonRoomService.insertRoom(maisonRoom);
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

