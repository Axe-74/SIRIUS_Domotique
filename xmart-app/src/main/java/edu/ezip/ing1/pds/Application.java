package edu.ezip.ing1.pds;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.io.*;

public class Application {
    public JFrame frame;
    public ArrayList<Maison_programme> programmes = new ArrayList<Maison_programme>();
    public ArrayList<Maison_Automatisation> automatisations = new ArrayList<Maison_Automatisation>();
    public ArrayList<Maison_Capteurs> capteurs = new ArrayList<>(); {}
    public ArrayList<Maison_Room> rooms = new ArrayList<>();
    public ArrayList<String> automatisationsNoms = new ArrayList<>();
    public ArrayList<String> capteursNoms = new ArrayList<>();
    public ArrayList<String> programmesNoms = new ArrayList<>();

    public Application()  {
//        automatisations = loadautomatisation();
//        programmes = loadprogramms();
//        rooms = loadrooms();
//        capteurs = loadcapteurs();
        initialize();
    }

    public void initialize() {
        frame = new JFrame("Domotique maison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 350);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        // BD connexion
        String url = "jdbc:mysql://172.31.250.26:3306/domotique";
        String username = "sirius";
        String password = "domotique";


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
        Automations_and_programsPanel.setLayout(new GridLayout(5, 1));

        JButton btnNewAutomations = new JButton("Définir une nouvelle automatisation");
        JButton btnViewAutomations = new JButton("Voir les automatisations");
        JButton btnNewPrograms = new JButton("Définir un nouveau programme");
        JButton btnViewPrograms = new JButton("Voir les programmes");
        JButton btnBacktoMainmenu = new JButton("Retour au menu principal");

        Automations_and_programsPanel.add(btnNewAutomations);
        Automations_and_programsPanel.add(btnViewAutomations);
        Automations_and_programsPanel.add(btnNewPrograms);
        Automations_and_programsPanel.add(btnViewPrograms);
        Automations_and_programsPanel.add(btnBacktoMainmenu);

        mainPanel.add(Automations_and_programsPanel, "Automations_and_ProgramsPanel");



// Capteurs Menu Panel
        JPanel CapteursPanel = new JPanel();
        CapteursPanel.setLayout(new GridLayout(4, 1));

        JButton btnNewCapteur = new JButton("Définir un nouveau capteur");
        JButton btnVoirCapteurs = new JButton("Voir les capteurs");
        JButton btnChangerEtat = new JButton("Changer l'état des capteurs");
        JButton btnRetourCapteurs = new JButton("Retour");

        CapteursPanel.add(btnNewCapteur);
        CapteursPanel.add(btnVoirCapteurs);
        CapteursPanel.add(btnChangerEtat);
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
        AutomationPanel.setLayout(new GridLayout(4, 2));

        JLabel lblAutomationName = new JLabel("Nom de l'automatisation:");
        JTextField txtAutomationName = new JTextField();

        JLabel lblSensor_activation = new JLabel("Activation du capteur: ");
        JComboBox<Maison_Automatisation.TypeCapteurs> cbSensor_activation = new JComboBox<>(Maison_Automatisation.TypeCapteurs.values());

        JLabel lblSensor_program = new JLabel("Execution du programme:");
        JComboBox<Maison_Automatisation.TypeProgramme> cbSensor_programme = new JComboBox<>(Maison_Automatisation.TypeProgramme.values());

        JButton btnSaveAutomation = new JButton("Enregistrer");
        JButton btnBackToMenu_Automation = new JButton("Retour au menu");

        AutomationPanel.add(lblAutomationName);
        AutomationPanel.add(txtAutomationName);
        AutomationPanel.add(lblSensor_activation);
        AutomationPanel.add(cbSensor_activation);
        AutomationPanel.add(lblSensor_program);
        AutomationPanel.add(cbSensor_programme);
        AutomationPanel.add(btnSaveAutomation);
        AutomationPanel.add(btnBackToMenu_Automation);

        mainPanel.add(AutomationPanel, "AutomationPanel");



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
        JComboBox<Maison_programme.TypePiece> cbPiece = new JComboBox<>(Maison_programme.TypePiece.values());

        JLabel lblChauffage = new JLabel("Type de chauffage:");
        JComboBox<Maison_programme.TypeChauffage> cbChauffage = new JComboBox<>(Maison_programme.TypeChauffage.values());

        JLabel lblTemperature = new JLabel("Température:");
        JSpinner spTemperature = new JSpinner(new SpinnerNumberModel(20, 10, 30, 1));

        JLabel lblJour = new JLabel("Jour:");
        JComboBox<Maison_programme.JoursSemaine> cbJour = new JComboBox<>(Maison_programme.JoursSemaine.values());

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
        JComboBox<Maison_Capteurs.TypeCapteur> cbTypeCapteur = new JComboBox<>(new Maison_Capteurs.TypeCapteur[]{
                Maison_Capteurs.TypeCapteur.TEMPERATURE, Maison_Capteurs.TypeCapteur.LUMINOSITE, Maison_Capteurs.TypeCapteur.MOUVEMENT
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
        JComboBox<Maison_Room.TypeRoom> cbTypeRoom = new JComboBox<>(Maison_Room.TypeRoom.values());

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



// Changer Etat Capteur
        JPanel EtatCapteurPanel = new JPanel();
        EtatCapteurPanel.setLayout(new GridLayout(3, 2));

        JLabel lblChoixCapteur = new JLabel("Capteur:");
        JLabel lblEtatCapteur_EtatCapteur = new JLabel("Etat:");

        JComboBox<String> cbCapteursExistants = new JComboBox<>(new String[]{
                "Capteur 1", "Capteur 2", "Capteur 3"
        });
        JComboBox<String> cbEtatCapteur_ChangerEtat = new JComboBox<>(new String[]{
                "0", "1"
        });

        JLabel lblExplicationONOFF = new JLabel("0 = OFF, 1 = ON");
        JButton btnBackToMenu_ChangerEtat = new JButton("Retour au menu");

        EtatCapteurPanel.add(lblChoixCapteur);
        EtatCapteurPanel.add(lblEtatCapteur_EtatCapteur);
        EtatCapteurPanel.add(cbCapteursExistants);
        EtatCapteurPanel.add(cbEtatCapteur_ChangerEtat);
        EtatCapteurPanel.add(lblExplicationONOFF);
        EtatCapteurPanel.add(btnBackToMenu_ChangerEtat);

        mainPanel.add(EtatCapteurPanel, "EtatCapteurPanel");




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

        //Boutons Automatisations et Programmes
        btnNewAutomations.addActionListener(e -> cardLayout.show(mainPanel, "AutomationPanel"));
        btnNewPrograms.addActionListener(e -> cardLayout.show(mainPanel, "ProgramPanel"));

        //Retour Menu Capteurs
        btnSensorsManagement.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));
        btnBackToMenu_NewCapteur.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));
        btnBackToMenu_VoirCapteurs.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));
        btnBackToMenu_ChangerEtat.addActionListener(e -> cardLayout.show(mainPanel, "CapteursPanel"));

        //Boutons Capteurs
        btnVoirCapteurs.addActionListener(e -> cardLayout.show(mainPanel, "voirCapteurPanel"));
        btnNewCapteur.addActionListener(e -> cardLayout.show(mainPanel, "NewCapteursPanel"));
        btnChangerEtat.addActionListener(e -> cardLayout.show(mainPanel, "EtatCapteurPanel"));

        //Boutons Rooms
        btnHouseManagement.addActionListener(e -> cardLayout.show(mainPanel, "HouseManagementPanel"));
        btnNewRoom.addActionListener(e -> cardLayout.show(mainPanel, "RoomPanel"));
        btnBackToMenuNewRoom.addActionListener(e -> cardLayout.show(mainPanel, "HouseManagementPanel"));

        //Boutons plus complexes

        //Boutons Affichage
        btnViewPrograms.addActionListener(e -> {
            //Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM programmes";
            programmes.clear();
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nom_programme = rs.getString("nom_programme");
                    String type_piece = rs.getString("type_piece");
                    String type_chauffage = rs.getString("type_chauffage");
                    int temperature_piece = rs.getInt("temperature_piece");
                    String jour_semaine = rs.getString("jour_semaine");
                    int heure_début = rs.getInt("heure_debut");
                    int heure_fin = rs.getInt("heure_fin");

                    Maison_programme.TypePiece typePiece = Maison_programme.TypePiece.valueOf(type_piece);
                    Maison_programme.TypeChauffage typeChauffage = Maison_programme.TypeChauffage.valueOf(type_chauffage);
                    Maison_programme.JoursSemaine jourSemaine = Maison_programme.JoursSemaine.valueOf(jour_semaine);
                    Maison_programme programme = new Maison_programme(nom_programme,typePiece,typeChauffage, temperature_piece,jourSemaine,heure_début,heure_fin);
                    programmes.add(programme);
                }
                rs.close();
                stmt.close();
                System.out.println("Import réussi!");
            } catch (SQLException ex) {
                throw new RuntimeException("Erreur lors de la récupération des capteurs : " + ex.getMessage());
            }
            StringBuilder sb_program = new StringBuilder();
            if (programmes.isEmpty()) {
                sb_program.append("Aucun programme enregistré.\n");
            } else {
                sb_program.append("Programmes enregistrés :\n");
                for (Maison_programme prog : programmes) {
                    sb_program.append("Nom : ").append(prog.NomProgramme).append("\n")
                            .append("Pièce : ").append(prog.TypePiece).append("\n")
                            .append("Chauffage : ").append(prog.TypeChauffage).append("\n")
                            .append("Température : ").append(prog.TemperaturePiece).append("°C\n")
                            .append("Jour : ").append(prog.JoursSemaine).append("\n")
                            .append("Heure : de ").append(prog.HeureDebut).append("h à ").append(prog.HeureFin).append("h\n\n");
                }
            }
            txtPrograms.setText(sb_program.toString());
            cardLayout.show(mainPanel, "ViewProgramsPanel");
        });

        btnViewAutomations.addActionListener(e -> {
            //Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM automatisations";
            automatisations.clear();
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String nom_automatisation = rs.getString("nom_automatisation");
                    String type_capteur = rs.getString("type_capteur");
                    String type_programme_automatisation = rs.getString("type_programme");
                    Maison_Automatisation.TypeCapteurs typeCapteurs = Maison_Automatisation.TypeCapteurs.valueOf(type_capteur);
                    Maison_Automatisation.TypeProgramme typeProgrammeAutomatisation = Maison_Automatisation.TypeProgramme.valueOf(type_programme_automatisation);
                    Maison_Automatisation auto = new Maison_Automatisation(nom_automatisation, typeCapteurs, typeProgrammeAutomatisation);
                    automatisations.add(auto);
                }
                rs.close();
                stmt.close();
                System.out.println("Import réussi!");
            } catch (SQLException ex) {
                throw new RuntimeException("Erreur lors de la récupération des automatisations : " + ex.getMessage());
            }

            StringBuilder sb_automation= new StringBuilder();
            if (automatisations.isEmpty()) {
                sb_automation.append("Aucune automatisation enregistré.\n");
            } else {
                sb_automation.append("Automatisations enregistrés :\n");
                for (Maison_Automatisation auto : automatisations) {
                    sb_automation.append("Nom : ").append(auto.NomAutomatisation).append("\n")
                            .append("Capteur écouté ").append(auto.TypeCapteurs).append("\n")
                            .append("Programme executé : ").append(auto.TypeProgramme).append("\n\n");
                }
            }
            txtPrograms.setText(sb_automation.toString());
            cardLayout.show(mainPanel, "ViewProgramsPanel");
        });

        btnVoirCapteurs.addActionListener(e -> {
            StringBuilder sb_VoirCapteurs = new StringBuilder();
            if (capteurs.isEmpty()) {
                sb_VoirCapteurs.append("Aucun capteur enregistré.\n");
            } else {
                sb_VoirCapteurs.append("Capteurs enregistrés :\n");
                for (Maison_Capteurs capt : capteurs) {
                    sb_VoirCapteurs.append("Nom : ").append(capt.NomCapteur).append("\n")
                            .append("Type : ").append(capt.TypeCapteur).append("\n")
                            .append("Etat : ").append(capt.EtatCapteur).append("\n\n");
                }
            }
            System.out.println(capteurs);
            txtCapteurs.setText(sb_VoirCapteurs.toString());
            cardLayout.show(mainPanel, "voirCapteurPanel");
        });

        btnViewRoom.addActionListener(e -> {
            StringBuilder sb_room= new StringBuilder();
            if (rooms.isEmpty()) {
                sb_room.append("Aucune pièce enregistrée.\n");
            } else {
                JPanel ViewRoomPanel = new JPanel();
                ViewRoomPanel.setLayout(new FlowLayout());

                for (Maison_Room room : rooms) {
                    JButton btnRoom_i = new JButton(room.NameRoom);
                    ViewRoomPanel.add(btnRoom_i);
                    mainPanel.add(ViewRoomPanel, "ViewRoomPanel");

                    JPanel Inroom_iPanel = new JPanel();
                    Inroom_iPanel.setLayout(new FlowLayout());
                    JButton btnBackToMenuInRoom_i = new JButton("Retour");
                    Inroom_iPanel.add(btnBackToMenuInRoom_i, BorderLayout.SOUTH);
                    btnBackToMenuInRoom_i.addActionListener(ev -> cardLayout.show(mainPanel, "ViewRoomPanel"));
                    mainPanel.add(Inroom_iPanel, "Inroom_iPanel");
                    btnRoom_i.addActionListener(ev -> cardLayout.show(mainPanel, "Inroom_iPanel"));
                }
                JButton btnBackToMenuViewRoom = new JButton("Retour");
                ViewRoomPanel.add(btnBackToMenuViewRoom);
                btnBackToMenuViewRoom.addActionListener(ev -> cardLayout.show(mainPanel, "HouseManagementPanel"));
            }
            cardLayout.show(mainPanel, "ViewRoomPanel");
        });

        //Bouton récupération BD
        btnSensorsManagement.addActionListener(e -> {
            //Connection connection = DriverManager.getConnection(url, username, password);
            String query = "SELECT * FROM capteurs";
            capteurs.clear();
            try (Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        String nom = rs.getString("nom_capteur");
                        String type = rs.getString("type_capteur");
                        String etat = rs.getString("etat_capteur");
                        Maison_Capteurs.TypeCapteur typeCap = Maison_Capteurs.TypeCapteur.valueOf(type);
                        Maison_Capteurs.EtatCapteur etatCap = Maison_Capteurs.EtatCapteur.valueOf(etat);
                        Maison_Capteurs capteur = new Maison_Capteurs(nom, typeCap, etatCap);
                        capteurs.add(capteur);
                    }
                    rs.close();
                    stmt.close();
                System.out.println("Import réussi!");
            } catch (SQLException ex) {
                throw new RuntimeException("Erreur lors de la récupération des capteurs : " + ex.getMessage());
            }
        });

        //Boutons Sauvegarde
        btnSaveProgram.addActionListener(e -> {
            //Récupération des données entrées
            String nomProgramme = txtProgramName.getText().trim();
            Maison_programme.TypePiece pieceSelect = (Maison_programme.TypePiece) cbPiece.getSelectedItem();
            Maison_programme.TypeChauffage chauffageSelect = (Maison_programme.TypeChauffage) cbChauffage.getSelectedItem();
            int temperature = (int) spTemperature.getValue();
            Maison_programme.JoursSemaine joursSemaineSelect = (Maison_programme.JoursSemaine) cbJour.getSelectedItem();
            int heureDebut = (int) spHeureDebut.getValue();
            int heureFin = (int) spHeureFin.getValue();
            // Validation des données
            if (nomProgramme.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir un nom de programme.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (heureDebut >= heureFin) {
                JOptionPane.showMessageDialog(frame, "L'heure de début doit être inférieure à l'heure de fin.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String query1 = "SELECT nom_programme FROM programmes;";
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = conn.prepareStatement(query1);
                 ResultSet  rs = stmt.executeQuery()){
                while (rs.next()){
                    String nom_programme_comparaison = rs.getString("nom_programme");;
                    programmesNoms.add(nom_programme_comparaison);
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erreur lors de la récupération : " + ex.getMessage());
            }
            for (String pN : programmesNoms){
                if (pN.equals(nomProgramme)) {
                    JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            // Création et sauvegarde du programme
            Maison_programme nouveauProgramme = new Maison_programme(nomProgramme, pieceSelect, chauffageSelect, temperature, joursSemaineSelect, heureDebut, heureFin);
            programmes.add(nouveauProgramme);

            //Connection connection = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO programmes (nom_programme, type_piece, type_chauffage, temperature_piece, jour_semaine, heure_debut, heure_fin) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {
                Maison_programme prog = programmes.get(programmes.size() - 1);
                    stmt.setString(1, prog.NomProgramme);
                    stmt.setString(2, prog.TypePiece.toString());
                    stmt.setString(3, prog.TypeChauffage.toString());
                    stmt.setInt(4, prog.TemperaturePiece);
                    stmt.setString(5, prog.JoursSemaine.toString());
                    stmt.setInt(6, prog.HeureDebut);
                    stmt.setInt(7, prog.HeureFin);
                    stmt.executeUpdate();
                    System.out.println("import reussi");
                    stmt.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Erreur lors de la sauvegarde des programmes : " + ex.getMessage());
            }
            try {
                Saveprogramms(programmes);
                JOptionPane.showMessageDialog(frame, "Programme enregistré avec succès!");
                cardLayout.show(mainPanel, "Automations_and_ProgramsPanel");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erreur lors de la sauvegarde : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSaveCapteur.addActionListener(e -> {
            //Récupération des données
            String nomCapteur = txtNomCapteur.getText().trim();
            Maison_Capteurs.TypeCapteur capteurSelect = (Maison_Capteurs.TypeCapteur) cbTypeCapteur.getSelectedItem();
            Maison_Capteurs.EtatCapteur etatSelect ;
            if (cbEtatCapteur.isSelected()) {
                etatSelect = Maison_Capteurs.EtatCapteur.ON;
            } else {
                etatSelect = Maison_Capteurs.EtatCapteur.OFF;
            }
            //Validation des données
            if (nomCapteur.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir un nom de capteur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String query1 = "SELECT nom_capteur FROM capteurs;";
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = conn.prepareStatement(query1);
                 ResultSet  rs = stmt.executeQuery()){
                while (rs.next()){
                    String nom_capteur_comparaison = rs.getString("nom_capteur");;
                    capteursNoms.add(nom_capteur_comparaison);
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erreur lors de la récupération : " + ex.getMessage());
            }
            for (String cN : capteursNoms){
                if (cN.equals(nomCapteur)) {
                    JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            //Création et sauvegarde du capteur
            Maison_Capteurs nouveauCapteur = new Maison_Capteurs(nomCapteur, capteurSelect, etatSelect);
            capteurs.add(nouveauCapteur);

            //Connection connection = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO capteurs (nom_capteur, type_capteur, etat_capteur) VALUES (?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement stmt = conn.prepareStatement(query)) {
                Maison_Capteurs capt = capteurs.get(capteurs.size() - 1);
                stmt.setString(1, capt.NomCapteur);
                stmt.setString(2, capt.TypeCapteur.toString());
                stmt.setString(3, capt.EtatCapteur.toString());
                stmt.executeUpdate();
                System.out.println("Import réussi!");
                stmt.close();
            } catch (SQLException ex) {
                throw new RuntimeException("Erreur lors de la sauvegarde des capteurs : " + ex.getMessage());
            }
            try {
                sauvegarderCapteurs(capteurs);
                JOptionPane.showMessageDialog(frame, "Capteur enregistré avec succès!");
                cardLayout.show(mainPanel, "CapteursPanel");
                System.out.println(capteurs);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erreur lors de la sauvegarde : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSaveRoom.addActionListener(e -> {
            //Récupération des données
            String nameRoom = txtNameRoom.getText().trim();
            Maison_Room.TypeRoom roomSelect = (Maison_Room.TypeRoom) cbTypeRoom.getSelectedItem();
            int roomSurface = (int) spSurfaceRoom.getValue();
            // Validation des données
            if (nameRoom.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez nommer la pièce.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (roomSurface <= 0) {
                JOptionPane.showMessageDialog(frame, "La surface de la pièce doit être positive.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Création et sauvegarde de la piece
            Maison_Room nouvellePiece = new Maison_Room(nameRoom, roomSelect, roomSurface);
            rooms.add(nouvellePiece);
            try {
                Saverooms(rooms);
                JOptionPane.showMessageDialog(frame, "Pièce enregistré avec succès!");
                cardLayout.show(mainPanel, "HouseManagementPanel");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erreur lors de la sauvegarde : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSaveAutomation.addActionListener(e -> {
            // Récupération des données
            String nomAutomation = txtAutomationName.getText().trim();
            Maison_Automatisation.TypeCapteurs capteurSelect = (Maison_Automatisation.TypeCapteurs) cbSensor_activation.getSelectedItem();
            Maison_Automatisation.TypeProgramme programmeSelect = (Maison_Automatisation.TypeProgramme) cbSensor_programme.getSelectedItem();
            // Validation des données
            if (nomAutomation.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Veuillez saisir un nom d'automatisation.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String query1 = "SELECT nom_automatisation FROM automatisations;";
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = conn.prepareStatement(query1);
                 ResultSet  rs = stmt.executeQuery()){
                while (rs.next()){
                    String nom_automatisation_comparaison = rs.getString("nom_automatisation");;
                    automatisationsNoms.add(nom_automatisation_comparaison);
                }
            } catch (SQLException ex) {
                throw new RuntimeException("Erreur lors de la récupération : " + ex.getMessage());
            }
            for (String aN : automatisationsNoms){
                if (aN.equals(nomAutomation)) {
                    JOptionPane.showMessageDialog(frame, "Nom déjà pris,en prendre un autre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }


            // Création et sauvegarde de l'automatisation
            Maison_Automatisation nouvelleAutomatisation = new Maison_Automatisation(nomAutomation, capteurSelect, programmeSelect);
            automatisations.add(nouvelleAutomatisation);
            System.out.println(automatisations);
            //Connection connection = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO automatisations (nom_automatisation, type_capteur, type_programme) VALUES (?, ?, ?)";
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 PreparedStatement stmt = conn.prepareStatement(query)){
                Maison_Automatisation auto = automatisations.get(automatisations.size() - 1);
                stmt.setString(1, auto.NomAutomatisation);
                stmt.setString(2, auto.TypeCapteurs.toString());
                stmt.setString(3, auto.TypeProgramme.toString());
                stmt.executeUpdate();
                stmt.close();
                System.out.println("import reussi");
            } catch (SQLException ex) {
                throw new RuntimeException("Erreur lors de la sauvegarde des programmes : " + ex.getMessage());
            }
            try {
                Saveautomation(automatisations);
                JOptionPane.showMessageDialog(frame, "Automatisation enregistré avec succès!");
                cardLayout.show(mainPanel, "Automations_and_ProgramsPanel");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erreur lors de la sauvegarde : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        //FIN
        cardLayout.show(mainPanel, "MenuPanel");
        frame.add(mainPanel);
        frame.setVisible(true);
    }


    //Sauvegardes
    private void Saveautomation(ArrayList<Maison_Automatisation> automatisations) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("automatisation.txt", false))) { // écrase le contenu précédent
            for (Maison_Automatisation auto : automatisations) {
                System.out.println("Nom de l'automatisation : " + auto.NomAutomatisation);
                writer.println(auto.NomAutomatisation + ";" + auto.TypeCapteurs + ";" + auto.TypeProgramme);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde des automatisations : " + e.getMessage());
        }
    }

    private void Saveprogramms(ArrayList<Maison_programme> programmes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("programmes.txt", false))) { // écrase le contenu précédent
            for (Maison_programme prog : programmes) {
                System.out.println("Nom du programme : " + prog.NomProgramme);
                writer.println(prog.NomProgramme + ";" + prog.TypePiece + ";" + prog.TypeChauffage + ";" +
                        prog.TemperaturePiece + ";" + prog.JoursSemaine + ";" + prog.HeureDebut + ";" + prog.HeureFin);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde des programmes : " + e.getMessage());
        }
    }

    private void sauvegarderCapteurs(ArrayList<Maison_Capteurs> capteurs) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("capteurs.txt", false))) { // écrase le contenu précédent
            for (Maison_Capteurs capt : capteurs) {
                System.out.println("Nom du capteur : " + capt.NomCapteur);
                writer.println(capt.NomCapteur + ";" + capt.TypeCapteur + ";" + capt.EtatCapteur);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde des capteurs : " + e.getMessage());
        }
    }

    private void Saverooms(ArrayList<Maison_Room> rooms) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("pieces.txt", false))) {
            for (Maison_Room room : rooms) {
                System.out.println("Nom de la pièce : " + room.NameRoom);
                writer.println(room.NameRoom + ";" + room.TypeRoom + ";" + room.RoomSurface);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde des pièces : " + e.getMessage());
        }
    }

    //Load
    private ArrayList<Maison_Automatisation> loadautomatisation() {
        // Charger les programmes depuis un fichier ou une source de données
        //ArrayList<Maison> programmes = new ArrayList<>();
        try (Scanner scanner_auto = new Scanner(new File("automatisation.txt"))) {
            while (scanner_auto.hasNextLine()) {
                String ligne = scanner_auto.nextLine();
                String[] donnees = ligne.split(";");

                // Vérification du nombre de données avant de traiter
                if (donnees.length == 3) {
                    try {
                        String typecapteurs = String.valueOf(Maison_Automatisation.findByNameTypeCapteurs(donnees[1]));
                        String typeprogramme = String.valueOf(Maison_Automatisation.findByNameTypeProgramme(donnees[2]));

                        Maison_Automatisation auto = new Maison_Automatisation(donnees[0], Maison_Automatisation.TypeCapteurs.valueOf(donnees[1]), Maison_Automatisation.TypeProgramme.valueOf(donnees[2]));
                        automatisations.add(auto);
                    } catch (Exception e) {
                        System.out.println("Erreur dans le traitement d'une ligne : " + ligne);
                    }
                } else {
                    System.out.println("Ligne mal formatée : " + ligne);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier programmes.txt non trouvé : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des automatisation : " + e.getMessage());
        }
        return automatisations;
    }

    private ArrayList<Maison_programme> loadprogramms() {
        // Charger les programmes depuis un fichier ou une source de données
        //ArrayList<Maison> programmes = new ArrayList<>();
        try (Scanner scanner_program = new Scanner(new File("programmes.txt"))) {
            while (scanner_program.hasNextLine()) {
                String ligne = scanner_program.nextLine();
                String[] donnees = ligne.split(";");

                // Vérification du nombre de données avant de traiter
                if (donnees.length == 7) {
                    try {
                        String typePiece = String.valueOf(Maison_programme.findByNameTypePiece(donnees[1]));
                        String typeChauffage = String.valueOf(Maison_programme.findByNameTypeChauffage(donnees[2]));
                        String joursSemaine = String.valueOf(Maison_programme.findByNameJoursSemaine(donnees[4]));

                        Maison_programme prog = new Maison_programme(donnees[0], Maison_programme.TypePiece.valueOf(donnees[1]), Maison_programme.TypeChauffage.valueOf(donnees[2]), Integer.parseInt(donnees[3]), Maison_programme.JoursSemaine.valueOf(donnees[4]), Integer.parseInt(donnees[5]), Integer.parseInt(donnees[6]) );
                        programmes.add(prog);
                    } catch (Exception e) {
                        System.out.println("Erreur dans le traitement d'une ligne : " + ligne);
                    }
                } else {
                    System.out.println("Ligne mal formatée : " + ligne);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier programmes.txt non trouvé : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des programmes : " + e.getMessage());
        }
        return programmes;
    }

    private ArrayList<Maison_Capteurs> loadcapteurs() {
        try (Scanner scanner = new Scanner(new File("capteurs.txt"))) {
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] donnees = ligne.split(";");
                // Vérification du nombre de données avant de traiter
                if (donnees.length == 3) {
                    try {
                        Maison_Capteurs.TypeCapteur typeCapteur = Maison_Capteurs.TypeCapteur.valueOf(donnees[1]);
                        Maison_Capteurs.EtatCapteur etatCapteur = Maison_Capteurs.EtatCapteur.valueOf(donnees[2]);
                        Maison_Capteurs capteur = new Maison_Capteurs(donnees[0], typeCapteur, etatCapteur);
                        capteurs.add(capteur);
                    } catch (Exception e) {
                        System.out.println("Erreur dans le traitement d'une ligne : " + ligne);
                    }
                } else {
                    System.out.println("Ligne mal formatée : " + ligne);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier capteurs.txt non trouvé : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des capteurs : " + e.getMessage());
        }
        return capteurs;
    }

    private ArrayList<Maison_Room> loadrooms() {
        try (Scanner scanner = new Scanner(new File("pieces.txt"))) {
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] donnees = ligne.split(";");

                // Vérification du nombre de données avant de traiter
                if (donnees.length == 3) {
                    try {
                        String typeRoom = String.valueOf(Maison_Room.findByNameTypeRoom(donnees[1]));

                        Maison_Room room = new Maison_Room(donnees[0], Maison_Room.TypeRoom.valueOf(donnees[1]), Integer.parseInt(donnees[2]) );
                        rooms.add(room);
                    } catch (Exception e) {
                        System.out.println("Erreur dans le traitement d'une ligne : " + ligne);
                    }
                } else {
                    System.out.println("Ligne mal formatée : " + ligne);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fichier pieces.txt non trouvé : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des pièces : " + e.getMessage());
        }
        return rooms;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }
}



