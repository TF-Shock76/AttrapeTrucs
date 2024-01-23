package attrapeTruc.controleur;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

import attrapeTruc.vue.*;
import attrapeTruc.vue.draw.*;
import attrapeTruc.modele.*;
import attrapeTruc.modele.reseau.*;

/**
* classe Controleur.java
* @author Théo Crauffon
* @author Anne-Laure Drouard
* @author Swan Remacle
* @author Thibault Fouchet
* @version 1.0
*/
public class Controleur {

    /**
    * Contient la Frame Pricipale
    * @see FramePrincipale
    */
    private FramePrincipale frmPrc;

    /**
    * Contient la Frame du début pour le lancement
    * @see FrameDebut
    */
    private FrameDebut frmMenu;

    /**
    * Client du jeu
    * @see Client
    */
    private Client client;


    /**
    * Controleur du jeu
    * @param host
    */
    public Controleur(String host) {
        try {
            client = new Client(8000, host, this);
            Thread t = new Thread(client);
            t.start();
        } catch(Exception e){
          String rep = "<html> <body> <h1>ERREUR lors de la connexion au serveur.</h1>";
          rep += "Veuillez vérifier les informations. <br> (" +  e + ") </body> </html>";
          JOptionPane.showMessageDialog(null, String.format(rep, 175, 175));
        }
    }

    /**
    * Méthode prenant en charge le lancement du jeu
    * @param out
    */
    public void lancerJeu(PrintWriter out) {
        frmPrc = new FramePrincipale(this, out);
    }

    /**
    * Méthode prenant en charge le lancement du menu
    * @param c le client
    * @param out
    */
    public void lancerMenu(Client c, PrintWriter out) {
        frmMenu = new FrameDebut(c, out);
    }

	/**
	* Méthode permettant de fermer la JFrame
	*/
	public void fermerFenetre()
	{
		this.frmMenu.dispose();
		this.frmPrc.dispose();
	}

    /**
    * @return l'ensemble des joueurs
    */
    public synchronized ArrayList<JoueurD> getJoueurs() {
        ArrayList<JoueurD> lstJoueurs = new ArrayList<JoueurD>();

        for(Joueur j : client.getJoueurs())
            lstJoueurs.add(new JoueurD(j));

        return lstJoueurs;

    }

    /**
    * @return l'ensemble des items
    */
    public synchronized ArrayList<ItemD> getItems() {
        ArrayList<ItemD> lstItems = new ArrayList<ItemD>();

        for(Item i : client.getItems())
            lstItems.add(new ItemD(i));

        return lstItems;
    }

    /**
    * fait bouger le client dans une Direction
    * @param direction
    */
    public void bouger(char direction) {
        client.bouger(direction);
    }

    /**
    * Met à jour toute la frame
    */
    public void maj() { frmPrc.maj(); }

    /**
    * Main de la méthode
    * @param args
    */
    public static void main(String[] args)
    {
        String host = "";
        try {
            host = JOptionPane.showInputDialog("Entrez le host :");

            if(host.equals("localhost")){host = InetAddress.getLocalHost().getHostName();}

        } catch(Exception e){}


        new Controleur(host);
    }

}
