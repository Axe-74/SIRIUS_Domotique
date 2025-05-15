package edu.ezip.ing1.pds.features.rooms.visualisation2D;

import edu.ezip.ing1.pds.business.dto.MaisonRoom;
import edu.ezip.ing1.pds.business.dto.MaisonRooms;
import edu.ezip.ing1.pds.services.MaisonRoomService;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class VisualisationRooms {

    ArrayList<MaisonRooms> rooms = new ArrayList<>();

    private NetworkConfig networkConfig;

    public VisualisationRooms(NetworkConfig networkConfig) {
        this.networkConfig = networkConfig;
    }

    public void showRooms() {

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

        JFrame frame = new JFrame("Ma maison");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        int windowWidth = 500;
        int windowLength = 400;
        frame.setSize(windowWidth, windowLength);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        int nbColonnes = 3;
        int index = 0;

        for (MaisonRooms maisonroom : rooms) {
            for (MaisonRoom room : maisonroom.getMaisonRooms()) {
                String nameRoom = room.getName();
                int largeur = room.getSurface();
                int longueur = room.getSurface();

                double proportionX = (double) largeur / windowWidth;
                double proportionY = (double) longueur / windowLength;

                gbc.gridx = index % nbColonnes;      // colonne
                gbc.gridy = index / nbColonnes;      // ligne

                gbc.gridwidth = Math.max(1, (int) (proportionX * 20));
                gbc.gridheight = Math.max(1, (int) (proportionY * 20));

                gbc.weightx = proportionX;
                gbc.weighty = proportionY;

                JButton btnRoom = new JButton(nameRoom);

                JLabel label;

                JPanel dansRoomPanel = new JPanel(new BorderLayout());
                if(room.getCapteur() != null) {
                    JPanel capteurPanel = new JPanel();
                    capteurPanel.setLayout(new BoxLayout(capteurPanel, BoxLayout.Y_AXIS));
                    capteurPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                    JLabel titreLabel = new JLabel("Capteurs dans la pièce");
                    titreLabel.setFont(new Font("Arial", Font.BOLD, 20));
                    titreLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
                    capteurPanel.add(titreLabel);

                    label = new JLabel(room.getCapteur());
                    capteurPanel.add(label);

                    dansRoomPanel.add(capteurPanel);
                } else {
                    label = new JLabel("Il n'y a pas de capteur dans cette pièce.", SwingConstants.CENTER);
                    label.setFont(new Font("Arial", Font.BOLD, 15));
                    dansRoomPanel.add(label);
                }

                JButton btnRetour = new JButton("Retour");
                btnRetour.addActionListener(e -> cardLayout.show(mainPanel, "mainPanel"));

                dansRoomPanel.add(btnRetour, BorderLayout.SOUTH);

                mainPanel.add(dansRoomPanel, nameRoom);

                btnRoom.addActionListener(e -> cardLayout.show(mainPanel, nameRoom));

                panel.add(btnRoom, gbc);

                index++;
            }
        }
        mainPanel.add(panel, "mainPanel");
        frame.add(mainPanel);

        cardLayout.show(mainPanel, "mainPanel");
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}