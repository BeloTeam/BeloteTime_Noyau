package entite;

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.Pli;
import noyau.classesMetier.PositionEnum;

public class JoueurVirtuel extends Joueur{
	Intelligence ia;

	public JoueurVirtuel(PositionEnum position,String nom) {
		super(position,nom);
		ia = new Intelligence();
	}

	public boolean prendPremiereDonne(Carte atout) {
		//TODO a faire
		return false;
	}

	@Override
	public CouleurEnum prendDeuxiemeDonne() {
		//TODO a faire
		return null;
	}

	@Override
	public Carte jouerPli(Pli carteDuPliCourant) {
		return null;
	}

}
