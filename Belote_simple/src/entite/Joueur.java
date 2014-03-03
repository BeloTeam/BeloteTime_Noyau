package entite;

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.Main;
import noyau.classesMetier.Pli;
import noyau.classesMetier.PositionEnum;

public abstract class Joueur {
	private Main main;
	private PositionEnum position;
	private String nom;

	public Main getMain() {
		return main;
	}

	public Joueur(PositionEnum position, String nom) {
		this.main = new Main();
		this.position = position;
		this.nom = nom;
	}

	public PositionEnum getPosition() {
		return position;
	}

	public String toString() {
		return "Joueur : " + this.nom;
	}

	public abstract boolean prendPremiereDonne(Carte atout);

	public abstract CouleurEnum prendDeuxiemeDonne();

	public abstract Carte jouerPli(Pli carteDuPliCourant);

	public boolean equals(Object joueur) {
		if (joueur instanceof Joueur) {
			if (((Joueur) joueur).nom.equals(this.nom) && ((Joueur) joueur).position.equals(this.position)) {
				return true;
			}
			return false;
		}
		return false;
	}
}
