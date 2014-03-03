package noyau.classesMetier;


import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPaquetNonTrier {
	private List<Carte> paquet;
	private int nbCarte;
	private final int TAILLEMAX;

	public AbstractPaquetNonTrier(final int TAILLEMAX) {
		this.paquet = new ArrayList<>(TAILLEMAX);
		this.nbCarte = 0;
		this.TAILLEMAX = TAILLEMAX;
	}

	public List<Carte> getCartes() {
		return paquet;
	}
	
	public boolean ajouter(Carte c){
		boolean estAjoute = false;
		if(this.nbCarte < this.TAILLEMAX){
			estAjoute = paquet.add(c);
			if(estAjoute) nbCarte++;
		}
		return estAjoute;
	}
	
	public boolean supprimer(Carte c){
		boolean estSupprime = false;
		if(this.nbCarte > 0){
			estSupprime = paquet.remove(c);
			if(estSupprime) nbCarte--;
		}
		return estSupprime;
	}
	
	public int getTaillePaquet() {
		return this.nbCarte;
	}

	public int calculerValeurPaquet(CouleurEnum atout) {
		int valeur = 0;
		for (Carte c : this.paquet) {
			valeur += c.calculerValeurCarte(atout);
		}
		return valeur;
	}
	
	public String toString(){
		return paquet.toString();
	}
}