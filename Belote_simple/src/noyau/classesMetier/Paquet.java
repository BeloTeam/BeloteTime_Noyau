package noyau.classesMetier;


import java.util.ArrayList;
import java.util.List;

import noyau.exception.TerminalException;
import noyau.ia.Arbitre;



/**
 * @class Paquet
 * @author lacertus, Nathan
 * @resume classe représentant un paquet de 32 cartes
 * */

public class Paquet {

	private List<Carte> initial;
	private List<Carte> jeuxdistribue;
	private List<Carte> plisJ1J3;
	private List<Carte> plisJ0J2;
	// 0=>joueur j0 et j2
	// 1=> joueur j1 et j3 nombre de carte dans le plis
	private int[] posplis = { 0, 0 };

	// 0=>point j0 et j2 1=> j1 et j3
	private int[] pointplis = { 0, 0 };

	// 0=>Coeur,1=>Pique,2=>Carreau,3=>Trefle
	private List<Carte> maitre;
	private List<Carte> tapisjeux;
	private Carte blanc;

	// couleur premier carte sur le tapis
	private CouleurEnum carteJoue;
	// longueur du jeux de donne "melange"
	private int longJeuxdist;

	public Paquet() {
		initial = new ArrayList<>(32);
		jeuxdistribue = new ArrayList<>(32);
		plisJ1J3 = new ArrayList<>(32);
		plisJ0J2 = new ArrayList<>(32);
		maitre = new ArrayList<>(4);
		tapisjeux = new ArrayList<>(4);
		blanc = new Carte(CouleurEnum.NotInitialized, FigureEnum.NotInitialized);
		carteJoue = CouleurEnum.NotInitialized;
		longJeuxdist = 0;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// cree le jeux de cartes
	public void creelejeux() {
		Carte c1 = new Carte(CouleurEnum.Pique, FigureEnum.As);
		Carte c2 = new Carte(CouleurEnum.Pique, FigureEnum.Roi);
		Carte c3 = new Carte(CouleurEnum.Pique, FigureEnum.Dame);
		Carte c4 = new Carte(CouleurEnum.Pique, FigureEnum.Valet);
		Carte c5 = new Carte(CouleurEnum.Pique, FigureEnum.Dix);
		Carte c6 = new Carte(CouleurEnum.Pique, FigureEnum.Neuf);
		Carte c7 = new Carte(CouleurEnum.Pique, FigureEnum.Huit);
		Carte c8 = new Carte(CouleurEnum.Pique, FigureEnum.Sept);
		Carte c9 = new Carte(CouleurEnum.Carreau, FigureEnum.As);
		Carte c10 = new Carte(CouleurEnum.Carreau, FigureEnum.Roi);
		Carte c11 = new Carte(CouleurEnum.Carreau, FigureEnum.Dame);
		Carte c12 = new Carte(CouleurEnum.Carreau, FigureEnum.Valet);
		Carte c13 = new Carte(CouleurEnum.Carreau, FigureEnum.Dix);
		Carte c14 = new Carte(CouleurEnum.Carreau, FigureEnum.Neuf);
		Carte c15 = new Carte(CouleurEnum.Carreau, FigureEnum.Huit);
		Carte c16 = new Carte(CouleurEnum.Carreau, FigureEnum.Sept);
		Carte c17 = new Carte(CouleurEnum.Trefle, FigureEnum.As);
		Carte c18 = new Carte(CouleurEnum.Trefle, FigureEnum.Roi);
		Carte c19 = new Carte(CouleurEnum.Trefle, FigureEnum.Dame);
		Carte c20 = new Carte(CouleurEnum.Trefle, FigureEnum.Valet);
		Carte c21 = new Carte(CouleurEnum.Trefle, FigureEnum.Dix);
		Carte c22 = new Carte(CouleurEnum.Trefle, FigureEnum.Neuf);
		Carte c23 = new Carte(CouleurEnum.Trefle, FigureEnum.Huit);
		Carte c24 = new Carte(CouleurEnum.Trefle, FigureEnum.Sept);
		Carte c25 = new Carte(CouleurEnum.Coeur, FigureEnum.As);
		Carte c26 = new Carte(CouleurEnum.Coeur, FigureEnum.Roi);
		Carte c27 = new Carte(CouleurEnum.Coeur, FigureEnum.Dame);
		Carte c28 = new Carte(CouleurEnum.Coeur, FigureEnum.Valet);
		Carte c29 = new Carte(CouleurEnum.Coeur, FigureEnum.Dix);
		Carte c30 = new Carte(CouleurEnum.Coeur, FigureEnum.Neuf);
		Carte c31 = new Carte(CouleurEnum.Coeur, FigureEnum.Huit);
		Carte c32 = new Carte(CouleurEnum.Coeur, FigureEnum.Sept);

		this.initial.set(0, c1);
		this.initial.set(1, c2);
		this.initial.set(2, c3);
		this.initial.set(3, c4);
		this.initial.set(4, c5);
		this.initial.set(5, c6);
		this.initial.set(6, c7);
		this.initial.set(7, c8);
		this.initial.set(8, c9);
		this.initial.set(9, c10);
		this.initial.set(10, c11);
		this.initial.set(11, c12);
		this.initial.set(12, c13);
		this.initial.set(13, c14);
		this.initial.set(14, c15);
		this.initial.set(15, c16);
		this.initial.set(16, c17);
		this.initial.set(17, c18);
		this.initial.set(18, c19);
		this.initial.set(19, c20);
		this.initial.set(20, c21);
		this.initial.set(21, c22);
		this.initial.set(22, c23);
		this.initial.set(23, c24);
		this.initial.set(24, c25);
		this.initial.set(25, c26);
		this.initial.set(26, c27);
		this.initial.set(27, c28);
		this.initial.set(28, c29);
		this.initial.set(29, c30);
		this.initial.set(30, c31);
		this.initial.set(31, c32);
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// affiche les paquet de carte
	public void affiche() {
		for (int i = 0; i < 32; i++) {
			Terminal.ecrireStringln("valeur jeux melange carte n" + i + " " + jeuxdistribue.get(i).getFigure() + " " + jeuxdistribue.get(i).getCouleur());
		}
		Terminal.ecrireStringln("-----------------------");
	}

	/**
	 * IBhjbhjb
	 * @param 
	 * @return void
	 * */
	// melange le jeux
	public void donneJeuBeloteBattu() {
		int nbCartePaquet = 32;
		int indice = 0;
		int taillePaquet = this.initial.size();
		int i;
		while (nbCartePaquet != 0) {
			i = (int) (Math.random() * (taillePaquet-1));
			this.jeuxdistribue.set(indice,this.initial.get(i));
			
			//rajout a voir si ça fait pas bugguer
			this.initial.remove(i);
			//
			
			/*for (int v = i; v < 32; v++) {
				if (i + 1 < 32) {
					this.initial.set(i,this.initial.get(i+1)) ;
				} else {
					this.initial.set(i,this.blanc);
				}
				i++;
			}*/
			taillePaquet--;
			indice++;
			nbCartePaquet--;
		}
		this.longJeuxdist = indice;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// remet les cartes
	public int remetjeux(List<Carte> x, int y) {
		for (int u = 0; u < y; u++) {
			this.jeuxdistribue.set(this.longJeuxdist,x.get(0));
			for (int v = 0; v < y; v++) {
				if (v + 1 < y) {
					x.set(v,x.get(v+1)) ;
				} else {
					x.set(v,this.blanc);
				}
			}
			this.longJeuxdist++;
		}
		y = 0;
		return y;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// remet les cartes 2
	public void remetjeux2() {
		for (int u = 0; u < this.posplis[0]; u++) {
			this.jeuxdistribue.set(this.longJeuxdist,this.plisJ0J2.get(0));
			for (int v = 0; v < this.posplis[0]; v++) {
				if (v + 1 < this.posplis[0]) {
					this.plisJ0J2.set(v, this.plisJ0J2.get(v + 1));
				} else {
					this.plisJ0J2.set(v, this.blanc);
				}
			}
			this.longJeuxdist++;
		}

		for (int u = 0; u < this.posplis[1]; u++) {
			this.jeuxdistribue.set(this.longJeuxdist,this.plisJ1J3.get(0));
			for (int v = 0; v < this.posplis[1]; v++) {
				if (v + 1 < this.posplis[1]) {
					this.plisJ1J3.set(v,this.plisJ1J3.get(v + 1));
				} else {
					this.plisJ1J3.set(v, this.blanc);
				}
			}
			this.longJeuxdist++;
		}
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// on coupe le jeux
	public void coupe(int y) {
		int coupe;
		int n = 1;
		switch (y) {
		case 0:
			while (n != 0) {
				try {
					Terminal.ecrireStringln("Vous coupez donner une valeur entre 0 et 100.");
					coupe = Terminal.lireInt();
					if (coupe >= 0 && coupe <= 100) {
						coupe = (int) (31 * (coupe / 100));
						this.melange(coupe);
					}
					n = 0;
				} catch (TerminalException e) {
					Terminal.ecrireStringln("erreur de saisie");
				}
			}
			break;
		default:
			coupe = (int) (Math.random() * 32);
			this.melange(coupe);
		}
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// coupe le jeux de carte
	public void melange(int y) {
		for (int u = 0; u <= y; u++) {
			this.initial.set(u, this.jeuxdistribue.get(0));
			for (int v = 0; v < 32; v++) {
				if (v + 1 < 32) {
					this.jeuxdistribue.set(v, this.jeuxdistribue.get(v+1));
				} else {
					this.jeuxdistribue.set(v,this.blanc);
				}
			}
		}
		for (int z = 31 - y; z < 32; z++) {
			this.jeuxdistribue.set(z, this.initial.get(0)) ;
			for (int uv = 0; uv < 32; uv++) {
				if (uv + 1 < 32) {
					this.initial.set(uv,this.initial.get(uv + 1));
				} else {
					this.initial.set(uv,this.blanc);
				}
			}
		}
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// qui remporte la manche
	public int remportemanche(CouleurEnum y, int u) {
		int pos = 0;
		int val;
		int val2 = 0;
		int points;
		for (int x = 0; x < 4; x++) {
			val = Arbitre.pointsjeu(this.tapisjeux.get(x), y, this.carteJoue);
			if (val > val2) {
				val2 = val;
				pos = x;
			}
		}
		if (pos == 1 || pos == 3) {

			for (int x = 0; x < 4; x++) {
				this.plisJ1J3.set(this.posplis[1],this.tapisjeux.get(x));
				
				points = Arbitre.points(this.tapisjeux.get(x), y);
				this.pointplis[1] = this.pointplis[1] + points;
				this.tapisjeux.set(x,this.blanc);
				this.posplis[1] = this.posplis[1] + 1;
			}
			if (u == 7) {
				this.pointplis[1] = this.pointplis[1] + 10;
			}
		} else {
			for (int x = 0; x < 4; x++) {
				this.plisJ0J2[this.posplis[0]] = this.tapisjeux[x];
				points = Arbitre.points(this.tapisjeux[x], y);
				this.pointplis[0] = this.pointplis[0] + points;
				this.tapisjeux[x] = this.blanc;
				this.posplis[0] = this.posplis[0] + 1;
			}
			if (u == 7) {
				this.pointplis[0] = this.pointplis[0] + 10;
			}
		}
		return pos;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// affiche les paquet de carte
	public void affiche2() {
		for (int i = 0; i < this.posplis[0]; i++) {
			Terminal.ecrireStringln("valeur plis j0 et j2  carte n" + i + " " + this.plisJ0J2[i].getFigure() + " " + this.plisJ0J2[i].getCouleur());
		}
		Terminal.ecrireStringln("un total de point joueur 0 et 2 :" + this.pointplis[0]);
		Terminal.ecrireStringln("-----------------------");
		for (int i = 0; i < this.posplis[1]; i++) {
			Terminal.ecrireStringln("valeur plis j1 et j3 carte n" + i + " " + this.plisJ1J3[i].getFigure() + " " + this.plisJ1J3[i].getCouleur());
		}
		Terminal.ecrireStringln("un total de point joueur 1 et 3 :" + this.pointplis[1]);
		Terminal.ecrireStringln("-----------------------");
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// modifie les cartemaitre
	public void modifiemaitre(Carte x, int y) {
		if (y == 0) {
			switch (x.getCouleur()) {
			case Coeur:
				this.maitre[0] = new Carte(CouleurEnum.Coeur, FigureEnum.Valet);
				break;
			case Pique:
				this.maitre[1] = new Carte(CouleurEnum.Pique, FigureEnum.Valet);
				break;
			case Carreau:
				this.maitre[2] = new Carte(CouleurEnum.Carreau, FigureEnum.Valet);
				break;
			default: // Trefle
				this.maitre[3] = new Carte(CouleurEnum.Trefle, FigureEnum.Valet);
				break;
			}
		}
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// efface les references
	public void razpaquet() {
		this.pointplis[0] = 0;
		this.pointplis[1] = 0;
		this.posplis[0] = 0;
		this.posplis[1] = 0;
	}

	public Carte[] getInitial() {
		return initial;
	}

	public Carte[] getJeuxdist() {
		return jeuxdistribue;
	}

	public Carte[] getPlisJ1J3() {
		return plisJ1J3;
	}

	public Carte[] getPlisJ0J2() {
		return plisJ0J2;
	}

	public int[] getPosplis() {
		return posplis;
	}

	public int[] getPointplis() {
		return pointplis;
	}

	public Carte[] getMaitre() {
		return maitre;
	}

	public Carte[] getTapisjeux() {
		return tapisjeux;
	}

	public Carte getBlanc() {
		return blanc;
	}

	public CouleurEnum getCarteJoue() {
		return carteJoue;
	}

	public void setCarteJoue(CouleurEnum carteJoue) {
		this.carteJoue = carteJoue;
	}

	public int getLongJeuxdist() {
		return longJeuxdist;
	}

	public void setLongJeuxdist(int longJeuxdist) {
		this.longJeuxdist = longJeuxdist;
	}
}