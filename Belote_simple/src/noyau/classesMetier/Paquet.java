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
	private int pointEquipe1 = 0;
	private int pointEquipe2 = 0;

	// 0=>Coeur,1=>Pique,2=>Carreau,3=>Trefle
	private List<Carte> maitre;
	private List<Carte> tapisjeux;
	private Carte blanc;

	// couleur premier carte sur le tapis
	private CouleurEnum carteJoue;
	// longueur du jeux de donne "melange"
	private int longJeuxdist;

	public Paquet() {
		this.initial = new ArrayList<>(32);
		for (CouleurEnum c : CouleurEnum.values()) {
			for (FigureEnum f : FigureEnum.values()) {
				this.initial.add(new Carte(c, f));
			}
		}
		this.jeuxdistribue = new ArrayList<>(32);
		this.plisJ1J3 = new ArrayList<>(32);
		this.plisJ0J2 = new ArrayList<>(32);
		this.maitre = new ArrayList<>(4);
		this.tapisjeux = new ArrayList<>(4);
		for (int i = 0; i < 4 ; i++) {
			this.tapisjeux.add(new Carte(CouleurEnum.NotInitialized, FigureEnum.NotInitialized));
		}
		for (int i = 0; i < 32 ; i++) {
			this.plisJ1J3.add(new Carte(CouleurEnum.NotInitialized, FigureEnum.NotInitialized));
			this.plisJ0J2.add(new Carte(CouleurEnum.NotInitialized, FigureEnum.NotInitialized));
		}

		this.blanc = new Carte(CouleurEnum.NotInitialized, FigureEnum.NotInitialized);
		this.carteJoue = CouleurEnum.NotInitialized;
		this.longJeuxdist = 0;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// affiche les paquet de carte
	public void affiche() {
		for (int i = 0; i < 32; i++) {
			Terminal.ecrireStringln("valeur jeux melange carte n" + i + " " + this.jeuxdistribue.get(i).getFigure() + " " + this.jeuxdistribue.get(i).getCouleur());
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
			this.jeuxdistribue.add(this.initial.get(i));
			
			//rajout a voir si ça fait pas bugguer
			//this.initial.remove(i);
			//
			
			for (int v = i; v < 32; v++) {
				if (i + 1 < 32) {
					this.initial.set(i,this.initial.get(i+1)) ;
				} else {
					this.initial.set(i,this.blanc);
				}
				i++;
			}
			taillePaquet--;
			indice++;
			nbCartePaquet--;
		}
		/* Melange plus logique mais qui entraine une erreur par la suite
		int nbCartePaquet = this.initial.size();
		int i;
		Carte carteCourante;
		for(int j=nbCartePaquet; j > 0; j--){
			i = (int) (Math.random() * (j));
			carteCourante = this.initial.get(i);
			this.initial.remove(i);
			this.jeuxdistribue.add(carteCourante);
		}
		*/
		
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
				this.pointEquipe2 += points;
				this.tapisjeux.set(x,this.blanc);
				this.posplis[1] = this.posplis[1] + 1;
			}
			if (u == 7) {
				this.pointEquipe2 += 10;
			}
		} else {
			for (int x = 0; x < 4; x++) {
				this.plisJ0J2.set(this.posplis[0], this.tapisjeux.get(x));
				points = Arbitre.points(this.tapisjeux.get(x), y);
				this.pointEquipe1 += points;
				this.tapisjeux.set(x, this.blanc);
				this.posplis[0] = this.posplis[0] + 1;
			}
			if (u == 7) {
				this.pointEquipe1 += 10;
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
			Terminal.ecrireStringln("valeur plis j0 et j2  carte n" + i + " " + this.plisJ0J2.get(i).getFigure() + " " + this.plisJ0J2.get(i).getCouleur());
		}
		Terminal.ecrireStringln("un total de point joueur 0 et 2 :" + this.pointEquipe1);
		Terminal.ecrireStringln("-----------------------");
		for (int i = 0; i < this.posplis[1]; i++) {
			Terminal.ecrireStringln("valeur plis j1 et j3 carte n" + i + " " + this.plisJ1J3.get(i).getFigure() + " " + this.plisJ1J3.get(i).getCouleur());
		}
		Terminal.ecrireStringln("un total de point joueur 1 et 3 :" + this.pointEquipe2);
		Terminal.ecrireStringln("-----------------------");
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// modifie les cartemaitre
	public void modifiemaitre(Carte carte, int option) {
		if (option == 0) {
			switch (carte.getCouleur()) {
			case Coeur:
				this.maitre.add(new Carte(CouleurEnum.Coeur, FigureEnum.Valet));
				break;
			case Pique:
				this.maitre.add(new Carte(CouleurEnum.Pique, FigureEnum.Valet));
				break;
			case Carreau:
				this.maitre.add(new Carte(CouleurEnum.Carreau, FigureEnum.Valet));
				break;
			default: // Trefle
				this.maitre.add(new Carte(CouleurEnum.Trefle, FigureEnum.Valet));
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
		this.pointEquipe1 = 0;
		this.pointEquipe2 = 0;
		this.posplis[0] = 0;
		this.posplis[1] = 0;
	}

	public List<Carte> getInitial() {
		return this.initial;
	}

	public List<Carte> getJeuxdist() {
		return this.jeuxdistribue;
	}

	public List<Carte> getPlisJ1J3() {
		return this.plisJ1J3;
	}

	public List<Carte> getPlisJ0J2() {
		return this.plisJ0J2;
	}

	public int[] getPosplis() {
		return this.posplis;
	}


	public List<Carte> getMaitre() {
		return this.maitre;
	}

	public List<Carte> getTapisjeux() {
		return this.tapisjeux;
	}

	public Carte getBlanc() {
		return this.blanc;
	}

	public CouleurEnum getCarteJoue() {
		return this.carteJoue;
	}

	public void setCarteJoue(CouleurEnum carteJoue) {
		this.carteJoue = carteJoue;
	}

	public int getLongJeuxdist() {
		return this.longJeuxdist;
	}

	public void setLongJeuxdist(int longJeuxdist) {
		this.longJeuxdist = longJeuxdist;
	}

	public int getPointEquipe1() {
		return pointEquipe1;
	}

	public int getPointEquipe2() {
		return pointEquipe2;
	}
}