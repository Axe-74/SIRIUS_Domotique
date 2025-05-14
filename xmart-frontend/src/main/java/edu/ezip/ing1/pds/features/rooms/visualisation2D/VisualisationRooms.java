package edu.ezip.ing1.pds.features.rooms.visualisation2D;

import edu.ezip.ing1.pds.business.dto.MaisonRoom;
import edu.ezip.ing1.pds.business.dto.MaisonRooms;
import edu.ezip.ing1.pds.services.MaisonRoomService;
import edu.ezip.ing1.pds.client.commons.NetworkConfig;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class VisualisationRooms {

    ArrayList<MaisonRooms> rooms = new ArrayList<>();
    ArrayList<TripleArrayRoom> nameRoom_and_lw = new ArrayList<>();
    Set<String> nomsDejaAjoutes = new HashSet<>();

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
        gbc.insets = new Insets(1, 1, 1, 1);

        nameRoom_and_lw.clear();

        for (MaisonRooms maisonroom : rooms) {
            for (MaisonRoom room : maisonroom.getMaisonRooms()) {
                if (!nomsDejaAjoutes.contains(room.getName())) {
                    nameRoom_and_lw.add(new TripleArrayRoom(room.getName(), room.getSurface(), room.getSurface()));
                    nomsDejaAjoutes.add(room.getName());
//                    System.out.println("room.getName() : " + room.getName());
                }
            }
        }
        System.out.println("nameRoom_and_lw : " + nameRoom_and_lw);

        int nbColonnes = 3;
        int index = 0;

        for (TripleArrayRoom tripleArrayRoom : nameRoom_and_lw) {
            String nameRoom = tripleArrayRoom.getName();
            int largeur = tripleArrayRoom.getSurface1();
            int longueur = tripleArrayRoom.getSurface2();

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

        frame.add(panel);
        frame.setVisible(true);
    }
}