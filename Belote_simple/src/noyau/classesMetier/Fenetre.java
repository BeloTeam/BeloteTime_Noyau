package noyau.classesMetier;
/**
 *  @class fenetre
 *  @author lacertus, Nathan
 *  @resume classe d'IHM
 * */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


// la classe formulaire
public class Fenetre extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// le constructeur
	public static int[] nbcartej = { 0, 0, 0, 0 };
	public static int pointj1j3 = 0;
	public static int pointj0j2 = 0;
	public static String gagnant = new String("");
	public static String joueurpris = new String("");
	public static ImageIcon carter, cartepl, atout;
	public static ImageIcon[] cartej0 = new ImageIcon[8];
	public static ImageIcon[] cartej = new ImageIcon[4];
	JPanel paneau;

	public void initFenetre() {

		this.setTitle("Belote");
		this.setSize(615, 660);
		paneau = new Superpanel();
		paneau.setBackground(Color.green);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(paneau);
		barreMenus = new JMenuBar();
		setJMenuBar(barreMenus);
		fermer = new JMenuItem("Fermer");
		barreMenus.add(fermer);
		fermer.addActionListener(this);
	}

	public void actionPerformed(ActionEvent ev) {
		System.exit(0);
	}

	private JMenuBar barreMenus;
	private JMenuItem fermer;

	// Initialise le plateau
	public void affiche() {
		for (int v = 0; v < 8; v++) {
			Fenetre.cartej0[v] = new ImageIcon("");
		}
		for (int v = 0; v < 4; v++) {
			Fenetre.cartej[v] = new ImageIcon("");
		}
		Fenetre.cartepl = new ImageIcon("image/back-90.gif");
		Fenetre.atout = new ImageIcon("");
		this.setVisible(true);
	}

	// affiche le jeux du joueur 0
	public void affichejeux(List<Carte> valpq) {
		int x = Fenetre.nbcartej[0];
		for (int v = 0; v < x; v++) {
			Fenetre.cartej0[v] = new ImageIcon("image/" + valpq.get(v).getFigure() + valpq.get(v).getCouleur() + ".gif");
		}
		paneau.repaint();
	}

	// affiche la carte retourne
	public void affichecarter(Carte valpq, CouleurEnum jeuxatout) {
		Fenetre.carter = new ImageIcon("image/" + valpq.getFigure() + valpq.getCouleur() + ".gif");
		Fenetre.atout = new ImageIcon("image/" + jeuxatout + ".gif");
		paneau.repaint();
	}

	// efface le paquet du centre
	public void effacecarteplis() {
		Fenetre.cartepl = new ImageIcon("");
		paneau.repaint();
	}

	// afiche carte jouee
	public void affichej(Carte y, int u) {
		Fenetre.cartej[u] = new ImageIcon("image/" + y.getFigure() + y.getCouleur() + ".gif");
		paneau.repaint();
	}

	public void raffiche() {
		paneau.repaint();
	}
}

class Superpanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public Superpanel() {
		cartel = new ImageIcon("image/back-90.gif");
		carteh = new ImageIcon("image/back.gif");
		tapiscarte = new ImageIcon("image/Tapis Belotte.gif");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// jeux joueur 3
		int valposx3 = 5;
		int valposy3 = 125;
		g.drawImage(tapiscarte.getImage(), 0, 0, null);
		for (int v = 0; v < Fenetre.nbcartej[3]; v++) {
			g.drawImage(cartel.getImage(), valposx3, valposy3, null);
			valposy3 = valposy3 + 40;
		}
		// jeux joueur 0
		int x = Fenetre.nbcartej[0];
		int posx = 125;
		for (int v = 0; v < x; v++) {
			g.drawImage(Fenetre.cartej0[v].getImage(), posx, 475, null);
			posx = posx + 40;
		}
		// jeux joueur 2
		int valposx2 = 130;
		int valposy2 = 25;
		for (int v = 0; v < Fenetre.nbcartej[2]; v++) {
			g.drawImage(carteh.getImage(), valposx2, valposy2, null);
			valposx2 = valposx2 + 40;
		}
		// affiche les n° des cartes pour le joueur 0
		g.setColor(Color.white);
		Font f = new Font("Dialog", Font.BOLD, 18);
		g.setFont(f);
		int valposx1 = 145;
		int uvx = 1;
		String affnum = new String("");
		for (int uv = 0; uv < Fenetre.nbcartej[0]; uv++) {
			affnum = ("" + uvx);
			g.drawString(affnum, valposx1, 590);
			valposx1 = valposx1 + 40;
			uvx++;
		}
		// affiche tableau resultat
		String affresult = new String("");
		Font f2 = new Font("Dialog", Font.BOLD, 12);
		g.setFont(f2);
		g.setColor(Color.cyan);
		affresult = ("" + Fenetre.pointj1j3);
		g.drawString(affresult, 40, 600);
		affresult = ("" + Fenetre.pointj0j2);
		g.drawString(affresult, 560, 600);
		// affiche le joueur
		String affresult2 = new String("");
		g.setFont(f2);
		affresult2 = ("" + Fenetre.joueurpris);
		g.drawString(affresult2, 20, 35);
		// affiche le gagnant
		g.setColor(Color.cyan);
		Font f3 = new Font("Dialog", Font.BOLD, 50);
		g.setFont(f3);
		g.drawString(Fenetre.gagnant, 50, 300);
		// jeux joueur 1
		int valposx = 480;
		int valposy = 125;
		for (int v = 0; v < Fenetre.nbcartej[1]; v++) {
			g.drawImage(cartel.getImage(), valposx, valposy, null);
			valposy = valposy + 40;
		}
		// paquet de carte
		g.drawImage(Fenetre.cartepl.getImage(), 240, 240, null);
		g.drawImage(Fenetre.carter.getImage(), 240, 240, null);
		// jeux
		g.drawImage(Fenetre.cartej[2].getImage(), 260, 130, null);
		g.drawImage(Fenetre.cartej[3].getImage(), 146, 240, null);
		g.drawImage(Fenetre.cartej[0].getImage(), 260, 340, null);
		g.drawImage(Fenetre.cartej[1].getImage(), 380, 240, null);
		g.drawImage(Fenetre.atout.getImage(), 500, 5, null);
	}

	private ImageIcon cartel, carteh, tapiscarte;
}