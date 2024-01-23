package attrapeTruc.modele.reseau;

import attrapeTruc.modele.*;

import java.util.ArrayList;

/**
 * classe Table.java
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */

class Table implements Runnable{

	/**
     * Nombre d'item affiché sur la frame
     */
	int nbItems;

	/**
	 * Nombre d'item maximum
	 */
	int nbItemsMax;

	/**
	 * Nombre de joueur
	 */
	int nbJoueur;

	/**
	 * true si la table et visible, false dans le cas contraire
	 */
	boolean estVisible;

	/**
	 * Le Terrain
	 * @see Terrain
	 */
	Terrain terrain;

	/**
	 * Ensemble de ClientManageur
	 * @see ClientManageur
	 */
	ArrayList<ClientManageur> alClient;

	/**
	 * true si la table est lancée, cad que la partie est lancée
	 */
	boolean tableLancer;

	/**
	 * Constructeur
	 * @param nbItemsMax
	 * @param nbJoueur
	 * @param estVisible
	 */
	public Table(int nbItemsMax, int nbJoueur, boolean estVisible) {
		this.alClient    = new ArrayList<ClientManageur>();
		this.estVisible  = estVisible;
		this.terrain     = new Terrain(nbItemsMax);

		this.nbJoueur    = nbJoueur;
		this.tableLancer = false;

		Thread t = new Thread(this);
		t.start();
	}

	/**
	 * Lancement de la table
	 */
	public void run() {
		while(alClient.size() <= 0) {} // Tant qu'il n'y a pas de client il ne se passe rien
		lancerTable(); // Lancement

		// Tant qu'il reste des items le terrain les récupères
		while(terrain.getItems().size() > 0) {
			try {
				Thread.sleep(30);
				terrain.recuperer();
			} catch(Exception e) {}
		}
		//à partir de ce moment, la partie est finies
		String rep = "<html><body><h1>PARTIE FINIE!</h1><br>";

		rep += "Scores :<br>";
		for(Joueur j : terrain.getJoueurs())
			rep += " - " + j.getNom() +  " : " + j.getScore() + " points <br>";

		rep += "</body> </html>";
		messagePourTous(rep);

		for(ClientManageur gc : alClient)
			gc.ecrire("QUIT");
	}

	/**
	 * Permet d'ajouter un joueur
	 * @param j Joueur
	 */
	public void ajouterJoueur(Joueur j) {
		this.terrain.ajouterJoueur(j);
	}

	/**
	 * @return le protocole du terrain
	 */
	public synchronized String getTerrainProtocole(ClientManageur c) {
		if(estVisible)
			return this.terrain.getProtocole();
		return this.terrain.getProtocole(alClient.indexOf(c));
	}

	/**
	 * Permet d'écrire le message pour tous les joueurs
	 * @param message
	 */
	public void messagePourTous(String message) {
		for(ClientManageur gc : alClient)
			gc.ecrire("console;;;" + message);
	}

	/**
	 * Permet de faire bouger dans la direction souhaitée le joueur d'identifiant id
	 * @param direction
	 * @param id
	 */
	public synchronized void bouger(char direction, int id) {
		terrain.bouger(direction, id);
	}

	/**
	 * Permet de lancer le jeu de cette table
	 */
	private void lancerTable() {
		while(this.alClient.size() < this.nbJoueur)
			try { Thread.sleep(30); } catch(Exception e) {}

		tableLancer = true;
	}

	/**
	 * @return l'ensemble des joueurs
	 */
	public ArrayList<ClientManageur> getalClient(){
		return alClient;
	}

	/**
	 * Permet de supprimer un client du terrain
	 * @param indiceJoueur
	 */
	public void supprimerClient(int indiceJoueur) {
		System.out.println("Joueur " + indiceJoueur + " déconnecté");
		terrain.supprimerJoueur(indiceJoueur);
	}

	/**
	 * Permet d'ajouter un ClientManageur
	 * @param gc
	 */
	public void ajouterGc(ClientManageur gc) {
		System.out.println("Client ajouté a la table.");
		alClient.add(gc);
	}
}
