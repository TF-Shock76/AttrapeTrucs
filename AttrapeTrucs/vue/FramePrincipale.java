package attrapeTruc.vue;

import attrapeTruc.controleur.*;

import java.io.*;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


/**
 * classe FramePrincipale.java
 * Frame du jeu
 * @author Théo Crauffon
 * @author Anne-Laure Drouard
 * @author Swan Remacle
 * @author Thibault Fouchet
 * @version 1.0
 */

public class FramePrincipale extends JFrame  implements WindowListener {

    /**
     * Le Controleur
     * @see Controleur
     */
    private Controleur ctrl;

    /**
     * Permet d'écrire
     * @see PrintWriter
     */
    private PrintWriter out;

    /**
     * Permet de dessiner les items et joueurs
     * @see PanelDraw
     */
    private PanelDraw pnlDraw;

    /**
     * Pour les évènement du clavier
     * @see ControleManager
     */
    private ControleManager ctrlManager;

    /**
     * Le Constructeur
     * @param ctrl
     * @param out
     */
    public FramePrincipale(Controleur ctrl, PrintWriter out) {
        this.out = out;
        this.ctrl = ctrl;

        //Initialisation de la Frame
        this.setTitle("AttrapeTrucs");
        this.setSize(700, 700);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(this);

        //Panels
        this.pnlDraw = new PanelDraw(ctrl);
        Thread t = new Thread(pnlDraw);
        t.start();
        this.add(pnlDraw);

        //Listeners
        this.ctrlManager = new ControleManager(ctrl);
        this.addKeyListener(ctrlManager);

        //On lance
        this.setVisible(true);

    }

    /**
     * Pour mettre à jour la Frame
     */
    public void maj() {
        pnlDraw.repaint();
    }

    public void windowClosed     (WindowEvent arg0) {                      }
    public void windowClosing    (WindowEvent e   ) { out.println("QUIT"); }
    public void windowActivated  (WindowEvent arg0) {                      }
    public void windowDeactivated(WindowEvent arg0) {                      }
    public void windowDeiconified(WindowEvent arg0) {                      }
    public void windowIconified  (WindowEvent arg0) {                      }
    public void windowOpened     (WindowEvent arg0) {                      }
}
