package noyau.classesMetier;

import java.util.ArrayList;

public class Tas extends AbstractPaquetNonTrier{
	public Tas() { 
		super(32); 
		//initialisation du tas
		for(CouleurEnum c : CouleurEnum.values()){
			for(FigureEnum f: FigureEnum.values()){
				this.ajouter(new Carte(c, f));
			}
		}
	}

	public boolean couper(int pourcentageCartesCoupees) {
		int nbCarteCoupees = 0;
		boolean estCoupe = false;
		if (pourcentageCartesCoupees >= 0 && pourcentageCartesCoupees <= 100) {
			estCoupe = true;
			nbCarteCoupees = (int) (31 * (pourcentageCartesCoupees / 100));
			for (int i = 0; i<nbCarteCoupees; i++) {
				Carte c = super.getCartes().remove(i);
				estCoupe &= super.getCartes().add(c);
			}
		}
		return estCoupe;
	}
	
	public void melanger(int nbTentativeSwitch) {
		for (int k = 0; k<nbTentativeSwitch; k++) {
			int i = (int) (Math.random() * (32-1));
			int j = (int) (Math.random() * (32-1));
			Carte c = super.getCartes().remove(i);
			super.getCartes().add(j, c);
		}
	}
	
	public Carte retirerCarteDessusPaquet(){
		Carte carteDuDessus;
		carteDuDessus = this.getCartes().get(0);
		this.supprimer(carteDuDessus);
		return carteDuDessus;
	}
	
	public void reposerDesCartes(ArrayList<Carte> cartes){
		for(Carte carte : cartes){
			this.ajouter(carte);
		}
	}
}
