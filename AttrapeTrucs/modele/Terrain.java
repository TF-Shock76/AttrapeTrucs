package attrapeTruc.modele;

import attrapeTruc.controleur.*;

import java.util.*;

/**
 * classe Terrain.java
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */


public class Terrain {

    /**
     * Ensemble de tous les joueurs présents sur le terrain
     * @see Joueur
     */
    private ArrayList<Joueur>  lstJoueurs;

    /**
     * Ensemble de tous les Items présents sur le terrain
     * @see Item
     */
    private ArrayList<Item>    lstItems;

    /**
     * Ensemble de tous les scores
     * @see Integer
     */
    private ArrayList<Integer> lstScore;

    /**
     * Nombre d'items max
     */
    private int nbItemsMax;

    /**
     * Constructeur initialisant toutes les ArrayList
     * @param nbItemsMax
     */
    public Terrain(int nbItemsMax) {
        this.nbItemsMax = nbItemsMax;

        lstJoueurs = new ArrayList<Joueur>();
        lstItems   = new ArrayList<Item>();

        // Initialisation des items avec des coordonnées aléatoires
        for(int i = 0; i < nbItemsMax; i++) {
            int x = ((int) (Math.random() * 600)) + 1;
            int y = ((int) (Math.random() * 600)) + 1;

            lstItems.add(new Item(x, y));
        }
    }

    /**
     * permet de récupérer un item en fonction des coordonnées des joueurs et des items
     */
    public void recuperer() {
        for(Joueur j : lstJoueurs){
			int o = 0;
            for(Item i : lstItems) {
				if(o < 10) {
					if(j.getX() <= i.getX() + 10 && j.getX() + 30 >= i.getX() + 10 &&
					   j.getY() <= i.getY() + 10 && j.getY() + 30 >= i.getY() + 10)   {
						j.ajouterItem();
						lstItems.remove(i);
					}
				}
				o++;
            }
		}
    }

    /**
     * Ajouter un joueur au terrain
     * @param j
     */
    public void ajouterJoueur(Joueur j) { lstJoueurs.add(j) ;}

    /**
     * Ajouter un item au terrain
     * @param i
     */
    public void ajouterItem  (Item   i) { lstItems.add(i)   ;}


    /**
     * @return la liste des joueurs (Pour éviter les ConcurrentModificationException)
     */
    public ArrayList<Joueur> getJoueurs() {
      ArrayList<Joueur> temp = new ArrayList<Joueur>();

      for(Joueur j : lstJoueurs)
        temp.add(j);

      return temp;
    }

    /**
     * @return la liste des items (Pour éviter les ConcurrentModificationException)
     */
    public ArrayList<Item> getItems() {
      ArrayList<Item> temp = new ArrayList<Item>();

      for(Item i : lstItems)
        temp.add(i);

      return temp;
    }

    /**
     * @return le joueur en fonctione de l'id placé en paramètre
     * @param id
     */
    public Joueur getJoueurAvecId(int id) {
        for(Joueur j : lstJoueurs)
            if(j.getId() == id)
                return j;

        return null;
    }


    /**
     * Permet au joueur avec d'identifiant id de bouger en fonction de la direction
     * @param direction
     * @param id
     */
    public synchronized void bouger(char direction, int id) {
        Joueur j = getJoueurAvecId(id);

        int speed = 8;

        if(j == null)
            return; //Au cas ou l'id est invalide

        switch(direction) {
            case 'N' : j.setPos( 0,-speed); break;
            case 'S' : j.setPos( 0, speed); break;
            case 'E' : j.setPos( speed, 0); break;
            case 'O' : j.setPos(-speed, 0); break;
        }

    }

    /**
     * @return le protocole du terrain en donction des joueurs et items
     *         sous la forme : JOUEUR1||JOUEUR2//ITEM||ITEM||ITEM||ITEM
     *         avec "||" pour séparer 2 attributs de même classe et "//" pour séparer 2 types d'objet
     *         Pour plus de détail, voir la classe protocole
     */
    public synchronized String getProtocole() {

        String protoc = "";

        // Pour chaque joueur :
        // Nom;Couleur;id;x;y
        for(Joueur j : getJoueurs()) {
            protoc += j.getNom() + ";" + j.getCouleur() + ";" + j.getId() + ";" + j.getX() + ";" + j.getY();
            if(lstJoueurs.indexOf(j) != lstJoueurs.size()-1) protoc += "||";
        }

        //Séparation des dypes d'objets
        if(lstItems.size() != 0)
            protoc += "//";

        //Pour chaque item :
        //x;y
        int o = 0;
        for(Item i : getItems()) {
            if(i != null && o < 10) {
                protoc += i.getX() + ";" + i.getY();
                if(lstItems.indexOf(i) != lstItems.size()-1 && o < 10) protoc += "||";
            }
			o++;
        }
        return protoc;
    }

	/**
     * @return le protocole du terrain en donction du joueur et items
     *         sous la forme : JOUEUR//ITEM||ITEM||ITEM||ITEM
     *         avec "||" pour séparer 2 attributs de même classe et "//" pour séparer 2 types d'objet
     *         Pour plus de détail, voir la classe protocole
     */
    public String getProtocole(int indiceJoueur) {
		if(indiceJoueur == -1)
			return "";

        String protoc = "";

        // Pour le joueur :
        // Nom;Couleur;id;x;y
        Joueur j = lstJoueurs.get(indiceJoueur);
		protoc += j.getNom() + ";" + j.getCouleur() + ";" + j.getId() + ";" + j.getX() + ";" + j.getY();

        //Séparation des dypes d'objets
        if(lstItems.size() != 0)
            protoc += "//";

        //Pour chaque item :
        //x;y
		int o = 0;
        for(Item i : lstItems) {
            if(i != null && o < 10) {
                protoc += i.getX() + ";" + i.getY();
                if(lstItems.indexOf(i) != lstItems.size()-1 && o < 10) protoc += "||";
            }
			o++;
        }
        return protoc;
    }


    /**
     * Supprime le joueur de la liste de Joueur
     * @param indiceJoueur
     */
    public void supprimerJoueur(int indiceJoueur) {
        this.lstJoueurs.remove(indiceJoueur);
    }


    /**
     * @return true S'il n'y a plus d'item
     * @return false s'il reste des items
     */
    public boolean estFinPartie() {
        return (lstItems.size() == 0);
    }
}
