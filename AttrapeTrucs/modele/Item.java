package attrapeTruc.modele;

/**
 * classe Item.java
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */

public class Item {

    /**
     * Coordonée en x
     */
    private int x;

    /**
     * Coordonée en y
     */
    private int y;


    /**
     * Constructeur
     * @param x
     * @param y
     */
    public Item(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return x
     */
    public int getX() { return x; }

    /**
     * @return y
     */
    public int getY() { return y; }


    /**
     * @return l'item et ses coordonnées
     */
    public String toString() {
        return "ITEM [" + x + ";" + y + "]";
    }

}
