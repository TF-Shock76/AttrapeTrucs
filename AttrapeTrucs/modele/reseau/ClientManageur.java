package attrapeTruc.modele.reseau;

import attrapeTruc.modele.*;
import attrapeTruc.vue.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * classe ClientManageur.java
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */


public class ClientManageur implements Runnable {

	/**
	 * Instance de ClientManageur
	 * @see ClientManageur
	 */
	private ClientManageur cm;

	/**
	 * Permet de lire les valeurs d'un flux tamponné
	 * @see BufferedReader
	 */
	private BufferedReader in;

	/**
	 * Permet d'écrire dans un flux tamponné
	 * @see PrintWriter
	 */
	private PrintWriter out;

	/**
	 * Nom du Client
	 * @see String
	 */
	private String nom;

	/**
	 * Couleur du ClientManageur
	 * @see String
	 */
	private String couleur;

	/**
	 * Identifiant du ClientManageur
	 */
	private int    identifiant;

	/**
	 * Booléen permettant de savoir si un Client a quitté la partie
	 */
	private boolean quitter;

	/**
	 * Instance de Serveur
	 * @see Serveur
	 */
	private Serveur serv;
	/**
	 * Instance de tableEnleve
	 * @see Table
	 */
	private Table table;

	/**
	 * Instance de Socket
	 * @see Socket
	 */
	private Socket toClient;

	/**
	 * Constructeur
	 * @param id
	 * @param couleur
	 * @param s
	 * @param serv
	 */
	public ClientManageur(int id, String couleur, Socket s, Serveur serv) {

		this.serv          = serv;
		this.identifiant   = id;
		this.couleur       = couleur;
		this.cm = this;
		quitter = false;
		toClient = s;
		try{
			out = new PrintWriter(s.getOutputStream(), true);
			in  = new BufferedReader( new InputStreamReader(s.getInputStream()) );
		}catch(Exception e){e.printStackTrace();}

		// Méthode permettant de savoir si un client se déconnecte anormalement
		Runtime.getRuntime().addShutdownHook(new Thread() {
	     public void run() {
	         try {
	             Thread.sleep(200);
	             System.out.println("Shutting down ...");
	             serv.deconnexion(cm); // Déconnexion du client

	         } catch (InterruptedException e) {
	             Thread.currentThread().interrupt();
	             e.printStackTrace();
	         }
	     }
	 });

	}

	/**
    * Lancement du ClientManageur
    */
	public void run() {

		ecrire("requete;;;Vous vous êtes connecté au serveur. Veuillez entrer votre nom :");
		this.nom = attendreReponse();


		if(this.nom != null) {
			ecrire("lancerM;;; ");
			String message = "";
			String mode = "";
			String args = "";

			try {
			  while(!mode.equals("REPRENDRE")) {
				message = in.readLine();
				mode    = message.split(";;;")[0];
				args    = message.split(";;;")[1];
			  }

			} catch(Exception e){e.printStackTrace();}

			// Récupération de l'option de menu
			int val = Integer.parseInt(args);

			if(val == 1 || val == 2) {
				if(val == 1)
					selectionnTable();
				if(val == 2)
					nouvTable();

				ecrire("infoJ;;;" + identifiant);
				ecrire("console;;;<html><body>Bienvenue, " + nom + ". Vous avez rejoins un salon avec succès. <br> Veuillez patienter avant le début de la partie...</body></html>");
				table.ajouterJoueur( new Joueur(nom, couleur, identifiant, 100, 100) ); // ajoute un joueur à la table


				// CE THREAD CI DESSOUS PERMETS DE METTRE A JOUR LE PROTOCOLE DU TERRAIN TOUTES LES 10ms/
				ClientManageur c = this;
				Thread t = new Thread() {
					public void run() {
						while(true) {
							ecrire("protocole;;;" + table.getTerrainProtocole(c));
							try{Thread.sleep(10);}catch(Exception e){}
						}
					}
				};
				while(!this.table.tableLancer){
					try{Thread.sleep(30);}catch(Exception e){}
				} //On attends que la table se lance ou non

				//On lance la partie.
				t.start();
				ecrire("lancer;;; ");

				//On gère le protocole bouger
				String rep = "";
				while(!rep.equals("QUIT")) {
					rep = attendreReponse();
					if(!rep.equals("QUIT") && !rep.equals("")){
						String bouger = rep.split(";;;")[1];
						table.bouger( bouger.split("-")[0].charAt(0), Integer.parseInt(bouger.split("-")[1]) );
					}
				}

				t.stop();
			}
		}

		System.out.println("deconnexion");
		this.serv.deconnexion(this);
	}
	/**
	 * Vérification de la présence d'une Table inutilisée pour une partie
	 */
	private void selectionnTable() {
		boolean dejaExistante = false;
		String reponse, nomTable;

		if (!serv.tableLibre()) {
			ecrire("console;;;Aucun salon disponible. Création d'un nouveau salon...");
			nouvTable();
		}
		else {
			boolean ajouter = false;

			for (Table gt : serv.getTables()) {
				if (!gt.tableLancer && !ajouter) {
					gt.ajouterGc(this); // ajoute un ClientManageur à la Table
					this.table = gt;
					ajouter = true;
				}
			}

		}

	}

	/**
	 * Création une nouvelle table
	 */
	private void nouvTable() {
		ecrire("requete;;;Quel est le nombre d'items à ramasser dans la partie ? ");
		String nbItem = attendreReponse();

		while(nbItem.equals("") || !estNombre(nbItem)) {
			ecrire("requete;;;Quel est le nombre d'items à ramasser dans la partie ? ");
			nbItem = attendreReponse();
		}

		String typePartie = "";
		while(!typePartie.equals("visible") && !typePartie.equals("invisible")) {
			ecrire("requete;;;Quel est le type de partie que vous voulez ? (visible/invisible) ");
			typePartie = attendreReponse();
		}
		boolean estVisible = typePartie.equals("visible");

		boolean valeurValide = false;

		String nbJoueur    = "";
		int    nbJoueurInt = 0;

		while(!valeurValide) {
			ecrire("requete;;;Quel est le nombre de joueurs de la partie ? (2-8) ");
			nbJoueur = attendreReponse();

			if(estNombre(nbJoueur)){nbJoueurInt = Integer.parseInt(nbJoueur);}
			valeurValide = (nbJoueurInt >= 2 && nbJoueurInt <= 8);
		}



		this.table = serv.nouvelleTable(Integer.parseInt(nbItem), nbJoueurInt, estVisible);
		this.table.ajouterGc(this);
	}

	/**
	 * Vérifie que la chaine de caractères passée en paramètres est un nombre
	 * @param s
	 * @return vrai si la chaine de caractères est un nombre, faux dans le cas contraire
	 */
	public boolean estNombre(String s) {
		try {
			Integer.parseInt(s);
		} catch(Exception e) {
			return false;
		}

		return true;
	}

	/**
	 * Attend qu'une saisie soit faite
	 * @return la chaine saisie par le Client
	 */
	public String attendreReponse(){
		String rep = "";
		try {
			while(rep.equals("") && toClient.isConnected()){
				try{
					rep = in.readLine();
				}catch(IOException ignored){}
			}
		} catch(NullPointerException npe) {this.serv.deconnexion(this);}
		return rep;
	}

	/**
	 * Affiche le message passé en paramètres
	 * @param message
	 */
	public void ecrire(String message){
		out.println(message);
	}

	/**
	 * Change la valeur du booléen "quitter"
	 */
	 public void quitter() {
	     quitter = true;
	 }

	 /**
	  * Vérifie l'occurrence d'une chaine de caractère parmi un tableau de chaines
	  * @param attendu
	  * @param private
	  * @return vrai si le motif se trouve dans une des cases du tableau
	  */
	private boolean contient(String[] attendu, String s) {
		for(String att : attendu) {
			if(att.equalsIgnoreCase(s)) return true;
		}
		return false;
	}

}
