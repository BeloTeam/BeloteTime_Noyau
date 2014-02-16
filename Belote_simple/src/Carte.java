/**
 *  @class Carte
 *  @author lacertus, Nathan
 *  @resume classe représentant un carte de belote composée d'une figure et d'une couleur
 * 
 * */


public class Carte {
	private CouleurEnum couleur ;
	private Figure figure;
	
	public Carte (CouleurEnum couleur, Figure figure){
		this.couleur = couleur;
		this.figure = figure;
	}

	public CouleurEnum getCouleur() {
		return couleur;
	}

	public Figure getFigure() {
		return figure;
	}
	
	public  String toString() {
		return this.figure.getNom() + " de " + this.couleur;
	}
	
	
}