package attrapeTruc.vue;


import attrapeTruc.modele.*;
import attrapeTruc.modele.reseau.*;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;

/**
* classe FrameDebut.java
* @author Théo Crauffon
* @author Anne-Laure Drouard
* @author Swan Remacle
* @author Thibault Fouchet
* @version 1.0
*/


public class FrameDebut extends JFrame implements InternalFrameListener {

   /**
	 * Instance de Client
	 * @see Client
	 */
   private Client c;

   /**
	 * Instance de FrameDebut
	 * @see FrameDebut
	 */
   private FrameDebut f;

   /**
	 * Valeur
	 */
   private int val = 0;

   /**
	 * Instance de PrintWriter
	 * @see PrintWriter
	 */
   private PrintWriter out;

   /**
	 * Constructeur de la classe FrameDebut
    * @param c
    * @param out
	 */
   public FrameDebut(Client c, PrintWriter out) {
      // Initialisation des attributs
      this.c   = c;
      this.f   = this;
      this.out = out;

      // Initialisation de la Frame
      this.setTitle("AttrapeTrucs");
      this.setSize(700, 700);
      this.setLocationRelativeTo(null);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setLayout(new GridLayout(4,1));

      JLabel image = new JLabel( new ImageIcon("mon_image.jpg"));
      this.add(image);

      // Création et ajout de boutons dynamiques
      JButton button = new JButton("Creer une partie");
      button.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
               val = 2;
               f.dispose();
               out.println("REPRENDRE;;;" + val);
            }
         }
      );
      this.add(button);

      button = new JButton("Rejoindre une partie");
      button.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
               val = 1;
               f.dispose();
               out.println("REPRENDRE;;;" + val);
            }
         }
      );
      this.add(button);

      button = new JButton("Quitter");
      button.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
               f.dispose();
               out.println("REPRENDRE;;;" + val);

            }
         }
      );
      this.add(button);

      //On lance
      this.setVisible(true);
      }
      /**
   	 * Evenement "la fenêtre est fermée"
   	 */
      public void internalFrameClosed(InternalFrameEvent e) {
      out.println("REPRENDRE;;;" + val);
   }

   public void internalFrameClosing(InternalFrameEvent e) {}

   public void internalFrameOpened(InternalFrameEvent e) {}

   public void internalFrameIconified(InternalFrameEvent e) {}

   public void internalFrameDeiconified(InternalFrameEvent e) {}

   public void internalFrameActivated(InternalFrameEvent e) {}

   public void internalFrameDeactivated(InternalFrameEvent e) {}

}
