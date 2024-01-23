package attrapeTruc.vue;

import java.awt.event.*;
import attrapeTruc.controleur.*;

/**
 * classe ControleManager.java
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */

public class ControleManager implements KeyListener {

    /**
     * Le Controleur
     * @see Controleur
     */
    private Controleur ctrl;

    /**
     * true si le bouton est appuyé
     * false sinon
     */
    private boolean  estAppuye;

    /**
     * Evenement
     * @see KeyEvent
     */
    private KeyEvent e;

    /**
     * Le Constructeur
     * @param ctrl
     */
    public ControleManager(Controleur ctrl) {
        this.ctrl = ctrl;
        this.estAppuye = false;
    }

    /**
     * Bouge en fonction de la touche appuyée
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT)	{ctrl.bouger('E');}
        if(e.getKeyCode() == KeyEvent.VK_LEFT)	{ctrl.bouger('O');}
        if(e.getKeyCode() == KeyEvent.VK_UP)	{ctrl.bouger('N');}
        if(e.getKeyCode() == KeyEvent.VK_DOWN)	{ctrl.bouger('S');}
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
