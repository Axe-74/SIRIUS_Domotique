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
        frame.setLocationRelativeTo(null);

        int windowWidth = 500;
        int windowLength = 400;
        frame.setSize(windowWidth, windowLength);

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
                panel.add(btnRoom, gbc);
                index++;
            }
        }

        frame.add(panel);
        frame.setVisible(true);
    }
}