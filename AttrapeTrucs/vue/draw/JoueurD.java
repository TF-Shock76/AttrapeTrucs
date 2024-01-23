package attrapeTruc.vue.draw;

import attrapeTruc.modele.*;

import java.awt.*;
import java.util.*;

/**
 * classe JoueurD.java
 *
 * JoueurD (JoueurDraw) prends le joueur qui lui est lié et rends son dessin
 * plus facile à lire/écrire. Si on veut modifier la manière dont le Joueur
 * est dessiné, c'est dans cette classe
 *
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */


public class JoueurD{

    /**
     * Joueur à dessiner
     * @Joueur Item
     */
    private Joueur j;

    /**
     * Constructeur
     * @param j
     */
    public JoueurD(Joueur j) {
        this.j = j;
    }

    /**
     * permet de dessiner le joueur en fonction de sa couleur
     * @param g
     */
    public void draw(Graphics g) {

        int taille = 30; //Taille provisoire

        int x = j.getX();
        int y = j.getY();

        switch(j.getCouleur())
        {
            case "Rouge" :
                g.setColor(Color.RED);    break;

            case "Jaune" :
                g.setColor(Color.YELLOW);  break;

            case "Bleu" :
                g.setColor(Color.BLUE);    break;

            case "Rose" :
                g.setColor(Color.PINK);    break;

            case "Vert" :
                g.setColor(Color.GREEN);   break;

            case "Violet" :
                g.setColor(Color.MAGENTA); break;

            default :
                g.setColor(Color.GREEN);   break;
        }

        //Affiche la boule du joueur
        g.fillOval(x-taille/2, y-taille/2, taille, taille);

        // Et son nom en noir
        g.setColor(Color.BLACK);
        g.drawString(j.getNom(), x -(taille/2), y+(taille/2)+15);

    }

}
