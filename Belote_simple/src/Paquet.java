/**
 * @class Paquet
 * @author lacertus, Nathan
 * @resume classe représentant un paquet de 32 cartes
 * */

public class Paquet {

	private Carte[] initial;
	private Carte[] jeuxdist;
	private Carte[] plisJ1J3;
	private Carte[] plisJ0J2;
	// 0=>joueur j0 et j2
	// 1=> joueur j1 et j3 nombre de carte dans le plis
	private int[] posplis = { 0, 0 };

	// 0=>point j0 et j2 1=> j1 et j3
	private int[] pointplis = { 0, 0 };

	// 0=>Coeur,1=>Pique,2=>Carreau,3=>Trefle
	private Carte maitre[];
	private Carte tapisjeux[];
	private Carte blanc;

	// couleur premier carte sur le tapis
	private CouleurEnum carteJoue;
	// longueur du jeux de donne "melange"
	private int longJeuxdist;

	public Paquet() {
		initial = new Carte[32];
		jeuxdist = new Carte[32];
		plisJ1J3 = new Carte[32];
		plisJ0J2 = new Carte[32];
		maitre = new Carte[4];
		tapisjeux = new Carte[4];
		blanc = new Carte(CouleurEnum.Blanc, Figure.Blanc);
		carteJoue = CouleurEnum.NotInitialized;
		longJeuxdist = 0;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// ===============================================================
	// cree le jeux de cartes
	public void creelejeux() {
		Carte c1 = new Carte(CouleurEnum.Pique, Figure.As);
		Carte c2 = new Carte(CouleurEnum.Pique, Figure.Roi);
		Carte c3 = new Carte(CouleurEnum.Pique, Figure.Dame);
		Carte c4 = new Carte(CouleurEnum.Pique, Figure.Valet);
		Carte c5 = new Carte(CouleurEnum.Pique, Figure.Dix);
		Carte c6 = new Carte(CouleurEnum.Pique, Figure.Neuf);
		Carte c7 = new Carte(CouleurEnum.Pique, Figure.Huit);
		Carte c8 = new Carte(CouleurEnum.Pique, Figure.Sept);
		Carte c9 = new Carte(CouleurEnum.Carreau, Figure.As);
		Carte c10 = new Carte(CouleurEnum.Carreau, Figure.Roi);
		Carte c11 = new Carte(CouleurEnum.Carreau, Figure.Dame);
		Carte c12 = new Carte(CouleurEnum.Carreau, Figure.Valet);
		Carte c13 = new Carte(CouleurEnum.Carreau, Figure.Dix);
		Carte c14 = new Carte(CouleurEnum.Carreau, Figure.Neuf);
		Carte c15 = new Carte(CouleurEnum.Carreau, Figure.Huit);
		Carte c16 = new Carte(CouleurEnum.Carreau, Figure.Sept);
		Carte c17 = new Carte(CouleurEnum.Trefle, Figure.As);
		Carte c18 = new Carte(CouleurEnum.Trefle, Figure.Roi);
		Carte c19 = new Carte(CouleurEnum.Trefle, Figure.Dame);
		Carte c20 = new Carte(CouleurEnum.Trefle, Figure.Valet);
		Carte c21 = new Carte(CouleurEnum.Trefle, Figure.Dix);
		Carte c22 = new Carte(CouleurEnum.Trefle, Figure.Neuf);
		Carte c23 = new Carte(CouleurEnum.Trefle, Figure.Huit);
		Carte c24 = new Carte(CouleurEnum.Trefle, Figure.Sept);
		Carte c25 = new Carte(CouleurEnum.Coeur, Figure.As);
		Carte c26 = new Carte(CouleurEnum.Coeur, Figure.Roi);
		Carte c27 = new Carte(CouleurEnum.Coeur, Figure.Dame);
		Carte c28 = new Carte(CouleurEnum.Coeur, Figure.Valet);
		Carte c29 = new Carte(CouleurEnum.Coeur, Figure.Dix);
		Carte c30 = new Carte(CouleurEnum.Coeur, Figure.Neuf);
		Carte c31 = new Carte(CouleurEnum.Coeur, Figure.Huit);
		Carte c32 = new Carte(CouleurEnum.Coeur, Figure.Sept);

		this.initial[0] = c1;
		this.initial[1] = c2;
		this.initial[2] = c3;
		this.initial[3] = c4;
		this.initial[4] = c5;
		this.initial[5] = c6;
		this.initial[6] = c7;
		this.initial[7] = c8;
		this.initial[8] = c9;
		this.initial[9] = c10;
		this.initial[10] = c11;
		this.initial[11] = c12;
		this.initial[12] = c13;
		this.initial[13] = c14;
		this.initial[14] = c15;
		this.initial[15] = c16;
		this.initial[16] = c17;
		this.initial[17] = c18;
		this.initial[18] = c19;
		this.initial[19] = c20;
		this.initial[20] = c21;
		this.initial[21] = c22;
		this.initial[22] = c23;
		this.initial[23] = c24;
		this.initial[24] = c25;
		this.initial[25] = c26;
		this.initial[26] = c27;
		this.initial[27] = c28;
		this.initial[28] = c29;
		this.initial[29] = c30;
		this.initial[30] = c31;
		this.initial[31] = c32;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// ===============================================================
	// affiche les paquet de carte
	public void affiche() {
		for (int i = 0; i < 32; i++) {
			Terminal.ecrireStringln("valeur jeux melange carte n" + i + " "
					+ jeuxdist[i].getFigure().getNom() + " "
					+ jeuxdist[i].getCouleur());

		}
		Terminal.ecrireStringln("-----------------------");
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// ===============================================================
	// melange le jeux
	public void donneJeuBeloteBattu() {
		int n = 32;
		int u = 0;
		int x = this.initial.length;
		int i;
		while (n != 0) {
			i = (int) (Math.random() * x);
			this.jeuxdist[u] = this.initial[i];
			for (int v = i; v < 32; v++) {
				if (i + 1 < 32) {
					this.initial[i] = this.initial[i + 1];
				} else {
					this.initial[i] = this.blanc;
				}
				i++;
			}
			x--;
			u++;
			n--;
		}
		this.longJeuxdist = u;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// ======================================================
	// remet les cartes
	public int remetjeux(Carte[] x, int y) {
		for (int u = 0; u < y; u++) {
			this.jeuxdist[this.longJeuxdist] = x[0];
			for (int v = 0; v < y; v++) {
				if (v + 1 < y) {
					x[v] = x[v + 1];
				} else {
					x[v] = this.blanc;
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
	// ======================================================
	// remet les cartes 2
	public void remetjeux2() {
		for (int u = 0; u < this.posplis[0]; u++) {
			this.jeuxdist[this.longJeuxdist] = this.plisJ0J2[0];
			for (int v = 0; v < this.posplis[0]; v++) {
				if (v + 1 < this.posplis[0]) {
					this.plisJ0J2[v] = this.plisJ0J2[v + 1];
				} else {
					this.plisJ0J2[v] = this.blanc;
				}
			}
			this.longJeuxdist++;
		}

		for (int u = 0; u < this.posplis[1]; u++) {
			this.jeuxdist[this.longJeuxdist] = this.plisJ1J3[0];
			for (int v = 0; v < this.posplis[1]; v++) {
				if (v + 1 < this.posplis[1]) {
					this.plisJ1J3[v] = this.plisJ1J3[v + 1];
				} else {
					this.plisJ1J3[v] = this.blanc;
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
	// ======================================
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
	// ==============================================
	// coupe le jeux de carte
	public void melange(int y) {
		for (int u = 0; u <= y; u++) {
			this.initial[u] = this.jeuxdist[0];
			for (int v = 0; v < 32; v++) {
				if (v + 1 < 32) {
					this.jeuxdist[v] = this.jeuxdist[v + 1];
				} else {
					this.jeuxdist[v] = this.blanc;
				}
			}
		}
		for (int z = 31 - y; z < 32; z++) {
			this.jeuxdist[z] = this.initial[0];
			for (int uv = 0; uv < 32; uv++) {
				if (uv + 1 < 32) {
					this.initial[uv] = this.initial[uv + 1];
				} else {
					this.initial[uv] = this.blanc;
				}
			}
		}
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// ============================================
	// qui remporte la manche
	public int remportemanche(CouleurEnum y, int u) {
		int pos = 0;
		int val;
		int val2 = 0;
		int points;
		for (int x = 0; x < 4; x++) {
			val = Arbitre.Pointsjeu(this.tapisjeux[x], y, this.carteJoue);
			if (val > val2) {
				val2 = val;
				pos = x;
			}
		}
		if (pos == 1 || pos == 3) {

			for (int x = 0; x < 4; x++) {
				this.plisJ1J3[this.posplis[1]] = this.tapisjeux[x];
				points = Arbitre.Points(this.tapisjeux[x], y);
				this.pointplis[1] = this.pointplis[1] + points;
				this.tapisjeux[x] = this.blanc;
				this.posplis[1] = this.posplis[1] + 1;
			}
			if (u == 7) {
				this.pointplis[1] = this.pointplis[1] + 10;
			}
		} else {
			for (int x = 0; x < 4; x++) {
				this.plisJ0J2[this.posplis[0]] = this.tapisjeux[x];
				points = Arbitre.Points(this.tapisjeux[x], y);
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
	// ===============================================================
	// affiche les paquet de carte
	public void affiche2() {
		for (int i = 0; i < this.posplis[0]; i++) {
			Terminal.ecrireStringln("valeur plis j0 et j2  carte n" + i + " "
					+ this.plisJ0J2[i].getFigure().getNom() + " "
					+ this.plisJ0J2[i].getCouleur());

		}
		Terminal.ecrireStringln("un total de point joueur 0 et 2 :"
				+ this.pointplis[0]);
		Terminal.ecrireStringln("-----------------------");
		for (int i = 0; i < this.posplis[1]; i++) {
			Terminal.ecrireStringln("valeur plis j1 et j3 carte n" + i + " "
					+ this.plisJ1J3[i].getFigure().getNom() + " "
					+ this.plisJ1J3[i].getCouleur());
		}
		Terminal.ecrireStringln("un total de point joueur 1 et 3 :"
				+ this.pointplis[1]);
		Terminal.ecrireStringln("-----------------------");
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// ========================================
	// modifie les cartemaitre
	public void modifiemaitre(Carte x, int y) {
		if (y == 0) {
			switch (Arbitre.coulrang(x)) {
			case 0:
				this.maitre[0] = new Carte(CouleurEnum.Coeur, Figure.Valet);
				break;
			case 1:
				this.maitre[1] = new Carte(CouleurEnum.Pique, Figure.Valet);
				break;
			case 2:
				this.maitre[2] = new Carte(CouleurEnum.Carreau, Figure.Valet);
				break;
			case 3:
				this.maitre[3] = new Carte(CouleurEnum.Trefle, Figure.Valet);
				break;
			}
		} else {

		}
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// ========================================================
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
		return jeuxdist;
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