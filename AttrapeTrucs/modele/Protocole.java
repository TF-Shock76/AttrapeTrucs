package attrapeTruc.modele;

import java.util.*;


/**
 * classe Protocole.java
 *
 * Un protocole est sous la forme :
 * JOUEUR1||JOUEUR2//ITEM||ITEM||ITEM||ITEM
 *
 * Avec plus de détail :
 * 		Pour chaque joueur : Nom;Couleur;identifiant;x;y
 * 		Pour chaque item   : x;y
 *
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */

public class Protocole {

	/**
     * Ensemble de tous les Items présents sur le terrain
     * @see Item
     */
	private ArrayList<Item>   alItem;

	/**
	 * Ensemble de tous les joueurs présents sur le terrain
	 * @see Joueur
	 */
	private	ArrayList<Joueur> alJoueurs;


	/**
     * Constructeur initialisant toutes les ArrayList
     */
	public Protocole() {
		this.alItem    = new ArrayList<Item>();
		this.alJoueurs = new ArrayList<Joueur>();
	}

	/**
     * Récupère tous les objets en fonction du protocole placé en paramètre
     * @param objets protocole
     */
	public synchronized void getObj(String objets) {
		// Efface les tableaux de joueur et items
		this.alJoueurs.clear();
		this.alItem.clear();

		int cpt = 0;
		String[] itm;
		String[] jou;
		String[] tabElt;
		String[] tabSepar;
		String[] tabJoueur;
		String[] tabItem;

		// Comptage d'occurrence des | pour savoir s'il y a plusieurs objets
		for(int i = 0; i < objets.length(); i++)
			if(objets.charAt(i) == '|')
				cpt++;
		cpt /= 2; // Car il y a deux '|' à chaque fois

		// Vérification du nombre
		if(cpt == 0) { // Il n'y a qu'un seul objet passé
			int cpt2 = 0;
			for(int i = 0; i < objets.length(); i++)
				if(objets.charAt(i) == ';')
					cpt2++;

			if(cpt2 == 1) { // Il n'y a que 2 elt donc c'est un item
				itm = objets.split(";");
				ajouterItem(itm[0], itm[1]);
			}

			else if(cpt2 == 4) { // Il y a 5 elt donc c'est un joueur
				jou = objets.split(";");
				ajouterJoueur(jou[0], jou[1], jou[2], jou[3], jou[4]);
			}

			else if (cpt2 == 5) {
				if(objets.contains("//")) { // Il y a un joueur et un item
					tabSepar  = objets.split("//"); // On sépare le joueur de l'item
					tabJoueur = tabSepar[0].split(";");
					tabItem   = tabSepar[1].split(";");
					ajouterJoueur(tabJoueur[0], tabJoueur[1], tabJoueur[2], tabJoueur[3], tabJoueur[4]);
					ajouterItem(tabItem[0], tabItem[1]);
				}
			}

			else {
				System.out.println("Il y a un problème dans le protocole");
				System.exit(1);
			}
		}

		else { // Il y a au moins 2 objets
			tabElt = objets.split("\\|\\|"); // On regroupe tous les objets
			for(int i = 0; i < tabElt.length; i++) { // on vérifie la longueur de chaque objet
				itm = tabElt[i].split(";");
				if(itm.length == 2)
					ajouterItem(itm[0], itm[1]);

				if(itm.length == 5)
					ajouterJoueur(itm[0], itm[1], itm[2], itm[3], itm[4]);

				if(tabElt[i].contains("//")) { // Il y a un joueur et un item
					tabSepar  = tabElt[i].split("//"); // On sépare le joueur de l'item
					tabJoueur = tabSepar[0].split(";");
					tabItem   = tabSepar[1].split(";");
					ajouterJoueur(tabJoueur[0], tabJoueur[1], tabJoueur[2], tabJoueur[3], tabJoueur[4]);
					ajouterItem(tabItem[0], tabItem[1]);
				}
			}
		}

	}

	/**
     * Ajouter un item à la liste
     * @param s1 x
	 * @param s2 y
     */
	public synchronized void ajouterItem(String s1, String s2) {
		this.alItem.add(new Item(Integer.parseInt(s1), Integer.parseInt(s2)));
	}

	/**
     * Ajouter un joueur à la liste
     * @param s1 Nom
	 * @param s2 Couleur
	 * @param s3 Identifiant
	 * @param s4 x
	 * @param s5 y
     */
	public synchronized void ajouterJoueur(String s1, String s2, String s3, String s4, String s5) {
		this.alJoueurs.add(new Joueur(s1, s2, Integer.parseInt(s3), Integer.parseInt(s4), Integer.parseInt(s5)));
	}

	/**
	 * On crée une copie pour éviter les erreurs ConcurrentModificationException.
     * @return la liste d'items'
     */
	public synchronized  ArrayList<Item> getItems() {
		ArrayList<Item> temp = new ArrayList<Item>();

		for(Item i : alItem)
			temp.add(i);

		return temp;
	}
	/**
	 * On crée une copie pour éviter les erreurs ConcurrentModificationException.
     * @return la liste de joueurs'
     */
	public synchronized  ArrayList<Joueur> getJoueurs() {
		ArrayList<Joueur> temp = new ArrayList<Joueur>();

		for(Joueur j : alJoueurs)
			temp.add(j);

		return temp;
	}

}
