package attrapeTruc.modele.reseau;

import attrapeTruc.modele.*;
import attrapeTruc.controleur.*;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/**
 * classe Client.java
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */

public class Client implements Runnable {

    /**
     * Controleur
     * @see Controleur
     */
    private Controleur ctrl;

    /**
     * Socket vers le serveur
     * @see Socket
     */
    private Socket         toServer;

    /**
     * Permet d'écrire
     * @see PrintWriter
     */
    private PrintWriter    out;

    /**
     * Permemt de lire
     * @see BufferedReader
     */
    private BufferedReader in;

    /**
     * identifiant du client
     */
    private int id;

    /**
     * Protocole de jeu
     * @see Protocole
     */
    private Protocole protocole;


    /**
     * Controleur du CLient
     * @param port le port du client
     * @param hote l'hote de la partie
     * @param ctrl controleur
     * @throws IOException
     */
    public Client(int port, String hote, Controleur ctrl) throws IOException {

        this.ctrl = ctrl;

        this.id = -1; //Par défaut
        this.protocole = new Protocole();

        //Ouverture du socket
        toServer = new Socket(hote, port);
        System.out.println("Client créé");

        out = new PrintWriter(toServer.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(toServer.getInputStream()));
    }

    /**
     * Lancement du client
     */
    public void run() {
		Thread printingHook = new Thread(() -> out.println("QUIT"));
		Runtime.getRuntime().addShutdownHook(printingHook);
        String message = "";
        try {
            while(!message.equals("QUIT")) {
                message = in.readLine();
                if(!message.equals("QUIT")){
                    String mode = message.split(";;;")[0];
                    String args = message.split(";;;")[1];

                    traiter(mode,args);
                }

            }

        } catch(Exception e){}
		this.ctrl.fermerFenetre();
        out.println("QUIT");
		System.exit(0);
    }

    /**
     * @return l'ensemble des joueurs
     */
    public ArrayList<Joueur> getJoueurs() {return protocole.getJoueurs() ; }

    /**
     * @return l'ensemble des Items
     */
    public ArrayList<Item>   getItems()   {return protocole.getItems()   ; }

    /**
     * Traite ce que doit faire le client
     * @param mode
     * @param args
     */
    public void traiter(String mode, String args)
    {
        if(mode.equals("console")) {
            //System.out.println(args);
            JOptionPane.showMessageDialog(null, String.format(args, 175, 175));
        }

        if(mode.equals("requete")) {
            String m = JOptionPane.showInputDialog(args);
            out.println(m);
        }

        if(mode.equals("protocole")) {
            this.protocole.getObj(args);
        }

        if(mode.equals("lancerM")) {
            this.ctrl.lancerMenu(this, out);
        }

        if(mode.equals("lancer")) {
            this.ctrl.lancerJeu(out);
        }

        if(mode.equals("infoJ")) {
            this.id = Integer.parseInt(args);
        }

        if(mode.equals("quitter")) {
			this.ctrl.fermerFenetre();
			out.println("QUIT");
			System.exit(0);
        }

    }

    /**
     * Dit au joueur de bouger et dans quelle direction
     * @param direction
     */
    public void bouger(char direction) {
        out.println("bouger;;;" + direction + "-"  + this.id);
    }
}
