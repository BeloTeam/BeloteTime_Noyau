/**
 * @class Carte
 * @author lacertus, Nathan
 * @resume classe repr�sentant une carte de belote compos�e d'une figure, d'une
 *         couleur, et d'une valeur.
 * 
 * */

public class Carte {
	private CouleurEnum couleur;
	private Figure figure;

	public Carte(CouleurEnum couleur, Figure figure) {
		this.couleur = couleur;
		this.figure = figure;
	}

	public CouleurEnum getCouleur() {
		return couleur;
	}

	public Figure getFigure() {
		return figure;
	}

	public String toString() {
		return this.figure.getNom() + " de " + this.couleur;
	}
}