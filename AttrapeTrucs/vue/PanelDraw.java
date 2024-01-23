package attrapeTruc.vue;

import attrapeTruc.controleur.*;
import attrapeTruc.vue.draw.*;

import java.awt.*;
import javax.swing.*;

/**
 * classe PanelDraw.java
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */

public class PanelDraw extends JPanel implements Runnable {

    /**
     * Accès au Controleur
     * @see Controleur
     */
    private Controleur ctrl;


    /**
     * Constructeur
     * @param ctrl
     */
    public PanelDraw(Controleur ctrl) {
        this.ctrl = ctrl;

        this.repaint();
    }


    /**
     * Effectue une mise à jour toutes les 30ms
     */
    public void run() {
        try {
            while(true) {
                Thread.sleep(30);
                this.repaint();
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Permet de dessiner les joueur et items
     * @param g
     */
    public void paint(Graphics g) {
        g.clearRect(0,0,10000,10000);

        g.setColor(Color.BLACK);

        //On dessine chaque joueur
        for(JoueurD j : ctrl.getJoueurs() )
        j.draw(g);
        
        //On dessine chaque item
        for(ItemD i : ctrl.getItems() )
        i.draw(g);
    }
}
