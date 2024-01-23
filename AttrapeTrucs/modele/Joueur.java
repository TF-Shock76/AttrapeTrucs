/**
 * Coordonée en x
 *//**
  * Joueur est la classe modèle qui sera à la fois la base pour le dessin et les calculs.
  * Ce n'est pas le client, elle sert juste à construire un objet pour le client, pour
  * faciliter le traîtement. (Même base pour les trucs à attraper.)
**/

package attrapeTruc.modele;

/**
 * classe Joueur.java
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */


public class Joueur {

	/**
     * Coordonée en x
     */
	private int x;

	/**
	 * Coordonée en y
	 */
	private int y;

	/**
     * Nom du Joueur
	 * @see String
     */
	private String nom;

	/**
	 * Couleur du Joueur
	 * @see String
	 */
	private String couleur;

	/**
	 * Identifiant du joueur
	 */
	private int    identifiant;

	/**
	 * Score du joueur
	 */
	private int    score;

	/**
     * Constructeur
     * @param nom
	 * @param couleur
	 * @param id
	 * @param x
	 * @param y
     */
	public Joueur(String nom, String couleur, int id, int x, int y) {
		this.x   = x;
		this.y   = y;
		this.nom = nom;
		this.couleur = couleur;
		this.identifiant = id;

		this.score = 0;
	}

	/**
     * @return x
     */
	public int    getX()       {return this.x;}

	/**
	 * @return y
	 */
	public int    getY()       {return this.y;}

	/**
	 * @return le nom du joueur
	 */
	public String getNom()     {return this.nom;}

	/**
	 * @return la couleur du joueur
	 */
	public String getCouleur() {return this.couleur;}

	/**
	 * @return l'identifiant du joueur
	 */
	public int    getId()      {return this.identifiant;}

	/**
	 * @return le score du joueur
	 */
	 public int  getScore() {return this.score++;}

	/**
	 * affecte le nom au joueur
     * @param nom
     */
	public void setNom(String nom) {this.nom = nom;}

	/**
	 * affecte les coordonnées au joueur
	 * @param x
	 * @param y
	 */
	public void setPos(int x, int y) {
		this.x += x;
		this.y += y;
	}

	/**
	 * Ajoute un point si un item est attrapé
	 */
	public void ajouterItem() { this.score++; }

	/**
	 * Affichage des coordonnéesdu joueur
	 */
	public String toString() {
		return this.nom + "\t-- [" + this.x + ";" + this.y + "]";
	}

}
