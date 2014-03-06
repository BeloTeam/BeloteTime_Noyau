//<<<<<<< HEAD:Belote_simple/src/noyau/classesMetier/Carte.java
package noyau.classesMetier;

/**
 * @class Carte
 * @author lacertus, Nathan, Loic
 * @resume classe représentant une carte de belote composée d'une figure, d'une
**/



public class Carte implements Comparable<Carte>{
	private CouleurEnum couleur;
	private FigureEnum figure;

	public Carte(CouleurEnum couleur, FigureEnum figure) {
		this.couleur = couleur;
		this.figure = figure;
	}

	public CouleurEnum getCouleur() {
		return couleur;
	}

	public FigureEnum getFigure() {
		return figure;
	}

	public String toString() {
		return "(" + this.figure + " de " + this.couleur + ")";
	}
	
	public boolean equals(Carte carte) {
		return (this.couleur.equals(carte.couleur) && this.figure.equals(carte.figure));
	}

	@Override
	public int compareTo(Carte c) {
		int res = this.getCouleur().compareTo(c.getCouleur()) + this.getFigure().compareTo(c.getFigure());
		//System.out.println("Carte " + this + " compareTo carte " + c + " = " + res);
		return res;
	}
	
	public int calculerValeurCarte(CouleurEnum atout){
		int point = 0;
		switch (this.figure) {
		case Neuf:
			if(atout.equals(this.getCouleur())) {
				point = 14;
			}else{
				point = 0;
			}
			break;
		case Dix:
			point = 10;
			break;
		case Valet:
			if(atout.equals(this.getCouleur())) {
				point = 20;
			}else{
				point = 2;
			}
			break;
		case Dame:
			point = 3;
			break;
		case Roi:
			point = 4;
			break;
		case As:
			point = 11;
			break;
		default: // Sept  et huit
			point = 0;
			break;
		}
		return point;
	}
}