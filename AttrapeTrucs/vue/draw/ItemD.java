package attrapeTruc.vue.draw;

import attrapeTruc.modele.*;

import java.awt.*;
import java.util.*;

/**
 * classe ItemD.java
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */


public class ItemD {

    /**
     * Item à dessiner
     * @see Item
     */
    private Item i;

    /**
     * Constructeur
     * @param i
     */
    public ItemD(Item i) {
        this.i = i;
    }

    /**
     * permet de dessiner l'item
     * @param g
     */
    public void draw(Graphics g) {

        int taille = 10; //Taille provisoire

        int x = i.getX();
        int y = i.getY();

        g.setColor(Color.BLACK);
        g.fillOval(x-taille/2, y-taille/2, taille, taille);

    }
}
