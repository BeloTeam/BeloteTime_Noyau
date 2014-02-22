package noyau.classesMetier;
/**
 * @class Carte
 * @author lacertus, Nathan,Loic
 * @resume classe représentant une carte de belote composée d'une figure, d'une
 *         couleur, et d'une valeur.
 * 
 * */

public class Carte {
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
		return this.figure + " de " + this.couleur;
	}
	
	public boolean equals(Carte carte)
	{
		return this.couleur == carte.couleur && this.figure == carte.figure;
	}

	public void setCouleur(CouleurEnum couleur) {
		this.couleur = couleur;
	}

	public void setFigure(FigureEnum figure) {
		this.figure = figure;
	}
	
}