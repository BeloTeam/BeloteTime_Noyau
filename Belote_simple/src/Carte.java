/**
 *  @class Carte
 *  @author lacertus, Nathan
 *  @resume classe repr�sentant un carte de belote compos�e d'une figure et d'une couleur
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