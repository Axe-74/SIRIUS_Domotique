package edu.ezip.ing1.pds;

import edu.ezip.ing1.pds.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class Application {
    public JFrame frame;
    public ArrayList<Maison> programmes = new ArrayList<Maison>();

    public Application() {
        programmes = chargerProgrammes();
        initialize();
    }

    public void initialize() {
        frame = new JFrame("Domotique maison");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        System.out.println("bonjour");

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        // Main Menu panel

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3,1));
        JButton btnAutomations_and_programs = new JButton(" Automatisations et programmes");
        JButton btnSensorsManagement = new JButton("Gestion des capteurs");
        JButton btnHouseManagement = new JButton("Gestion de la maison");
        menuPanel.add(btnAutomations_and_programs);
        menuPanel.add(btnSensorsManagement);
        menuPanel.add(btnHouseManagement);
        mainPanel.add(menuPanel,"MenuPanel");

        // Automations and programs Menu Panel
        JPanel Automations_and_programsPanel = new JPanel();
        Automations_and_programsPanel.setLayout(new GridLayout(4, 1));

        JButton btnNewAutomations = new JButton("Définir une nouvelle automatisation");
        JButton btnViewAutomations = new JButton("Voir les automatisations");
        JButton btnNewPrograms = new JButton("Dédinir un nouveau programme");
        JButton btnViewPrograms = new JButton("Voir les programmes");

        Automations_and_programsPanel.add(btnNewAutomations);
        Automations_and_programsPanel.add(btnViewAutomations);
        Automations_and_programsPanel.add(btnNewPrograms);
        Automations_and_programsPanel.add(btnViewPrograms);


        mainPanel.add(Automations_and_programsPanel, "Automations_and_ProgramsPanel");

        // Automation Definition Panel
        JPanel AutomationPanel = new JPanel();
        AutomationPanel.setLayout(new GridLayout(4, 2));

        JLabel lblAutomationName = new JLabel("Nom de l'automatisation:");
        JTextField txtAutomationName = new JTextField();

        JLabel lblSensor_activation = new JLabel("Activation du capteur: ");
        JComboBox<String> cbSensor_activation = new JComboBox<>(new String[]{
                "Capteur 1", "Capteur 2", "capteur 3", "Capteur 4", "Capteur 5"
        });

        JLabel lblSensor_programme = new JLabel("Execution du programme:");
        JComboBox<String> cbSensor_programme = new JComboBox<>(new String[]{
                "Programme 1", "Programme 2", "Programme 3", "Programme 4", "Programme 5"
        });

        JButton btnSaveAutomation = new JButton("Enregistrer");
        JButton btnBackToMenu_Automation = new JButton("Retour au menu");

        AutomationPanel.add(lblAutomationName);
        AutomationPanel.add(txtAutomationName);
        AutomationPanel.add(lblSensor_activation);
        AutomationPanel.add(cbSensor_activation);
        AutomationPanel.add(lblSensor_programme);
        AutomationPanel.add(cbSensor_programme);
        AutomationPanel.add(btnSaveAutomation);
        AutomationPanel.add(btnBackToMenu_Automation);

        mainPanel.add(AutomationPanel, "AutomationPanel");


        // Program Definition Panel
        JPanel programPanel = new JPanel();
        programPanel.setLayout(new GridLayout(8, 2));

        JLabel lblProgramName = new JLabel("Nom du programme:");
        JTextField txtProgramName = new JTextField();

        JLabel lblPiece = new JLabel("Pièce:");
        JComboBox<Maison.TypePiece> cbPiece = new JComboBox<>(Maison.TypePiece.values());

        JLabel lblChauffage = new JLabel("Type de chauffage:");
        JComboBox<Maison.TypeChauffage> cbChauffage = new JComboBox<>(Maison.TypeChauffage.values());

        JLabel lblTemperature = new JLabel("Température:");
        JSpinner spTemperature = new JSpinner(new SpinnerNumberModel(20, 10, 30, 1));

        JLabel lblJour = new JLabel("Jour:");
        JComboBox<Maison.JoursSemaine> cbJour = new JComboBox<>(Maison.JoursSemaine.values());

        JLabel lblHeureDebut = new JLabel("Heure de début:");
        JSpinner spHeureDebut = new JSpinner(new SpinnerNumberModel(0, 0, 23, 1));

        JLabel lblHeureFin = new JLabel("Heure de fin:");
        JSpinner spHeureFin = new JSpinner(new SpinnerNumberModel(0, 0, 24, 1));


        JButton btnSaveProgram = new JButton("Enregistrer");
        JButton btnBackToMenu_Program = new JButton("Retour au menu");

        programPanel.add(lblProgramName);
        programPanel.add(txtProgramName);
        programPanel.add(lblPiece);
        programPanel.add(cbPiece);
        programPanel.add(lblChauffage);
        programPanel.add(cbChauffage);
        programPanel.add(lblTemperature);
        programPanel.add(spTemperature);
        programPanel.add(lblJour);
        programPanel.add(cbJour);
        programPanel.add(lblHeureDebut);
        programPanel.add(spHeureDebut);
        programPanel.add(lblHeureFin);
        programPanel.add(spHeureFin);
        programPanel.add(btnSaveProgram);
        programPanel.add(btnBackToMenu_Program);

        mainPanel.add(programPanel, "ProgramPanel");

        //View Programm panel
        JPanel viewProgramsPanel = new JPanel();
        viewProgramsPanel.setLayout(new BorderLayout());

        JTextArea txtPrograms = new JTextArea();
        txtPrograms.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(txtPrograms);

        JButton btnBackToMenuViewProgramm = new JButton("Retour au menu");
        viewProgramsPanel.add(scrollPane, BorderLayout.CENTER);
        viewProgramsPanel.add(btnBackToMenuViewProgramm, BorderLayout.SOUTH);

        mainPanel.add(viewProgramsPanel, "ViewProgramsPanel");


        // Events
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        btnAutomations_and_programs.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnNewAutomations.addActionListener(e -> cardLayout.show(mainPanel, "AutomationPanel"));
        btnNewPrograms.addActionListener(e -> cardLayout.show(mainPanel, "ProgramPanel"));
        btnBackToMenu_Automation.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnBackToMenuViewProgramm.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnBackToMenu_Program.addActionListener(e -> cardLayout.show(mainPanel, "Automations_and_ProgramsPanel"));
        btnViewPrograms.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            if (programmes.isEmpty()) {
                sb.append("Aucun programme enregistré.\n");
            } else {
                sb.append("Programmes enregistrés :\n");
                for (Maison prog : programmes) {
                    sb.append("Nom : ").append(prog.NomProgramme).append("\n")
                            .append("Pièce : ").append(prog.TypePiece).append("\n")
                            .append("Chauffage : ").append(prog.TypeChauffage).append("\n")
                            .append("Température : ").append(prog.TemperaturePiece).append("°C\n")
                            .append("Jour : ").append(prog.JoursSemaine).append("\n")
                            .append("Heure : de ").append(prog.HeureDebut).append("h à ").append(prog.HeureFin).append("h\n\n");
                }
            }
            txtPrograms.setText(sb.toString());
            cardLayout.show(mainPanel, "ViewProgramsPanel");
        });
        btnSaveProgram.addActionListener(e -> {
            String nomProgramme = txtProgramName.getText().trim();
            Maison.TypePiece pieceSelect = (Maison.TypePiece) cbPiece.getSelectedItem();
            Maison.TypeChauffage chauffageSelect = (Maison.TypeChauffage) cbChauffage.getSelectedItem();
            int temperature = (int) spTemperature.getValue();
            Maison.JoursSemaine joursSemaineSelect = (Maison.JoursSemaine) cbJour.getSelectedItem();
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

            // Créer et sauvegarder le programme
            Maison nouveauProgramme = new Maison(nomProgramme, pieceSelect, chauffageSelect, temperature, joursSemaineSelect, heureDebut, heureFin);
            programmes.add(nouveauProgramme);

            try {
                sauvegarderProgrammes(programmes);
                JOptionPane.showMessageDialog(frame, "Programme enregistré avec succès!");
                cardLayout.show(mainPanel, "Automations_and_ProgramsPanel");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erreur lors de la sauvegarde : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        cardLayout.show(mainPanel, "MenuPanel");
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private void sauvegarderProgrammes(ArrayList<Maison> programmes) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("programmes.txt", false))) { // écrase le contenu précédent
            for (Maison prog : programmes) {
                System.out.println("Nom du programme : " + prog.NomProgramme);
                writer.println(prog.NomProgramme + ";" + prog.TypePiece + ";" + prog.TypeChauffage + ";" +
                        prog.TemperaturePiece + ";" + prog.JoursSemaine + ";" + prog.HeureDebut + ";" + prog.HeureFin);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde des programmes : " + e.getMessage());
        }
    }

    private ArrayList<Maison> chargerProgrammes() {
        // Charger les programmes depuis un fichier ou une source de données
        //ArrayList<Maison> programmes = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("programmes.txt"))) {
            while (scanner.hasNextLine()) {
                String ligne = scanner.nextLine();
                String[] donnees = ligne.split(";");

                // Vérification du nombre de données avant de traiter
                if (donnees.length == 7) {
                    try {
                        String typePiece = String.valueOf(Maison.findByNameTypePiece(donnees[1]));
                        String typeChauffage = String.valueOf(Maison.findByNameTypeChauffage(donnees[2]));
                        String joursSemaine = String.valueOf(Maison.findByNameJoursSemaine(donnees[4]));

                        Maison prog = new Maison(donnees[0], Maison.TypePiece.valueOf(donnees[1]), Maison.TypeChauffage.valueOf(donnees[2]), Integer.parseInt(donnees[3]), Maison.JoursSemaine.valueOf(donnees[4]), Integer.parseInt(donnees[5]), Integer.parseInt(donnees[6]) );
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Application::new);
    }
}
