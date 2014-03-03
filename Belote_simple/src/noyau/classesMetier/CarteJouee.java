package noyau.classesMetier;

import entite.Joueur;

public class CarteJouee extends Carte {

	private Joueur joueur;
	
	public CarteJouee(CouleurEnum couleur, FigureEnum figure, Joueur joueur) {
		super(couleur, figure);
	}
	
	public CarteJouee(Carte carte,Joueur joueur) {
		super(carte.getCouleur(), carte.getFigure());
		this.joueur = joueur;
	}

	public Joueur getJoueur() {
		return joueur;
	}
}
