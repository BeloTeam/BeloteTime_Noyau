package entite;

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.Main;
import noyau.classesMetier.PositionEnum;
import noyau.classesMetier.TableDeJeu;

public abstract class Joueur {
	private Main main;
	private PositionEnum position;
	private String nom;
	private TableDeJeu table;

	public Main getMain() {
		return main;
	}

	public Joueur(PositionEnum position, String nom, TableDeJeu table) {
		this.main = new Main();
		this.position = position;
		this.nom = nom;
		this.table = table;
	}

	public PositionEnum getPosition() {
		return position;
	}

	public String toString() {
		return "Joueur : " + this.nom;
	}

	public TableDeJeu getTable() {
		return table;
	}

	public abstract boolean prendPremiereDonne();

	public abstract CouleurEnum prendDeuxiemeDonne();

	public abstract Carte jouerPli();

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
