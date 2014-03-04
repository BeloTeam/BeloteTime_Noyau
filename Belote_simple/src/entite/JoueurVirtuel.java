package entite;

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.PositionEnum;
import noyau.classesMetier.TableDeJeu;

public class JoueurVirtuel extends Joueur{
	Intelligence ia;

	public JoueurVirtuel(PositionEnum position,String nom, TableDeJeu table) {
		super(position,nom, table);
		ia = new Intelligence();
	}

	public boolean prendPremiereDonne() {
		//TODO a faire
		return false;
	}

	@Override
	public CouleurEnum prendDeuxiemeDonne() {
		//TODO a faire
		return null;
	}

	@Override
	public Carte jouerPli() {
		return null;
	}

}
