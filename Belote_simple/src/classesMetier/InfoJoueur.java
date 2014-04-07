package classesMetier;


import java.util.HashMap;

import entite.Joueur;

public class InfoJoueur {
	private	Joueur joueur;
	private HashMap<CouleurEnum, Boolean> hasNotCouleur;
	private Main carteJouer;
	private boolean isAdversaire;
	
	public InfoJoueur(Joueur joueur, boolean isAdversaire) {
		this.joueur = joueur;
		this.hasNotCouleur = new HashMap<>();
		this.hasNotCouleur.put(CouleurEnum.Carreau, false);
		this.hasNotCouleur.put(CouleurEnum.Coeur, false);
		this.hasNotCouleur.put(CouleurEnum.Trefle, false);
		this.hasNotCouleur.put(CouleurEnum.Pique, false);
		carteJouer = new Main();
		this.isAdversaire = isAdversaire;
	}

	public boolean isAdversaire() {
		return isAdversaire;
	}

	public boolean HasNotCouleur(CouleurEnum couleur) {
		return this.hasNotCouleur.get(couleur);
	}

	public void setHasNotCouleur(CouleurEnum couleur) {
		this.hasNotCouleur.put(couleur, true);
	}

	public Joueur getJoueur() {
		return joueur;
	}

	public Main getCarteJouer() {
		return carteJouer;
	}
	
	public void ajouterCarteJouer(Carte carte, CouleurEnum couleurAtout){
		carteJouer.ajouter(carte, couleurAtout);
	}
}
