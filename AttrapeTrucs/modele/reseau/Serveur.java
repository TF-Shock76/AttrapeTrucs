package attrapeTruc.modele.reseau;

import attrapeTruc.modele.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/**
 * classe Serveur.java
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */


public class Serveur {
	/**
	 * ArrayList de ClientManageurs
	 * @see ClientManageur
	 */
	private ArrayList<ClientManageur> alClient;

	/**
	 * ArrayList de Tables
	 * @see Table
	 */
	private ArrayList<Table> alTable;

	/**
	 * ArrayList de Joueurs
	 * @see Joueur
	 */
	private ArrayList<Joueur> alJoueur;

	/**
	 * Instance de ServerSocket
	 * @see ServerSocket
	 */
	private ServerSocket serverSocket;

	/**
	 * Définition d'un port constant
	 */
	private final int PORT = 8000;

	/**
	 * Constructeur de la classe Serveur
	 */
	public Serveur() {
		int idIncrement = 0;
		int idCouleur = 0;
		String[] tableCouleur = new String[]{
			"Rouge",
			"Jaune",
			"Bleu",
			"Rose",
			"Vert",
			"Violet"
		};


		System.out.println("Serveur créé avec succès");

		// Initialisation des attributs
		try {
			alClient      = new ArrayList<ClientManageur>();
			alTable       = new ArrayList<Table>();
			serverSocket  = new ServerSocket(PORT);

			Thread t = new Thread(){
				  public void run() {
					while(true){
						tableEnleve();
						try{this.sleep(2000);}catch(Exception e){}
					}
				  }
			};
			t.start();

			while (true) {
				System.out.println("Attente de client");
				Socket toClient  = serverSocket.accept();

				ClientManageur gdc = new ClientManageur(idIncrement, tableCouleur[idCouleur], toClient, this);
				alClient.add(gdc);

				Thread tgdc =  new Thread(gdc);
				tgdc.start();

				idIncrement++;
				idCouleur++;

				if(idCouleur >= tableCouleur.length) idCouleur = 0;

			}
		} catch(Exception e) {System.out.println("Client pas cool"); e.printStackTrace();}
	}

	/**
	 * Suppression d'une table de l'ArrayList lorsque celle-ci ne possède plus de Client
	 */
	public void tableEnleve() {
		try {
			for(Table gt : alTable) {
				if(gt.alClient.size() == 0){
					alTable.remove(gt);
					System.out.println("Table enlevée");
				}
			}
		} catch(Exception ignored) {}
	}

	/**
	 * Création d'une nouvelle Table
	 * @param nbItems
	 * @param nbJoueur
	 * @param estVisible
	 * @return une nouvelle Table
	 */
	public Table nouvelleTable(int nbItems, int nbJoueur, boolean estVisible) {
		Table t = new Table(nbItems, nbJoueur, estVisible);
		alTable.add(t);

		System.out.println("Table ajoutée. -- " + alTable.size());
		return t;
	}

	/**
	 * Retourne l'ArrayList de Tables
	 * @return l'ensemble des Tables se trouvant dans l'ArrayList
	 */
	public ArrayList<Table> getTables() {
		return alTable;
	}

	/**
	 * Vérification de l'ajout possible de nouveaux Client
	 * @return vrai si une table est libre, faux dans le cas où aucun client ne peut entrer
	 */
	public boolean tableLibre() {
		System.out.println("Parcours de la table...");
		for(Table gt : alTable) {
			System.out.println("Table -- Size = " + gt.getalClient().size());
			if(gt.getalClient().size() < 8 && !gt.tableLancer)
				return true;
		}

		return false;
	}

	/**
	 * Suppression d'un joueur lorsque le client correspondant se déconnecte
	 * @param Client
	 */
	public void deconnexion(ClientManageur Client) {
		if(alClient.contains(Client))
			alClient.remove(Client);

		System.out.println("Client deco");

		for(Table gt : alTable) {
			if(gt.alClient.contains(Client)) {
				gt.supprimerClient(gt.alClient.indexOf(Client));
				gt.alClient.remove(Client);
			}
		}
		Client = null;
	}

	public static void main(String[] args){
		new Serveur();
	}


}
