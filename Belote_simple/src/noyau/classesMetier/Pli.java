package noyau.classesMetier;

import java.util.Comparator;

import entite.Joueur;

public class Pli extends AbstractPaquetNonTrier {
	private boolean hasDixDeDer;
	private boolean hasBelote;
	private boolean hasRebelote;
	private boolean hasCoupe;
	private CarteJouee carteMaitre;
	private CouleurEnum couleurDemandee;
	
	public Pli() { 
		super(4); 
		this.hasDixDeDer = false;
		this.hasBelote =false;
		this.hasRebelote=false;
		this.carteMaitre =null;
		this.couleurDemandee =null;
	}
	
	public Pli(boolean hasDixDeDer, boolean hasBelote, boolean hasRebelote) { 
		super(4); 
		this.hasDixDeDer = hasDixDeDer;
		this.hasBelote = hasBelote;
		this.hasRebelote = hasRebelote;
		this.carteMaitre=null;
		this.couleurDemandee =null;
	}
	
	public boolean ajouter(Carte c, Joueur j,final CouleurEnum atout){
		CarteJouee carteJouee = new CarteJouee(c,j);
		boolean ajoutOK = super.ajouter(carteJouee);
		//le cas ou c'est la 1ere carte du pli
		if(this.carteMaitre == null && this.couleurDemandee == null){
			this.carteMaitre = carteJouee;
			this.couleurDemandee = carteJouee.getCouleur();
		}else{
			//pour l'instant aucun atout n'a été joué
			if(this.carteMaitre.getCouleur() != atout){
				if(carteJouee.getCouleur() == this.couleurDemandee){
					if(this.carteMaitre.compareTo(carteJouee) < 0){
						this.carteMaitre = carteJouee;
					}
				}
				else if(carteJouee.getCouleur() == atout){
					this.carteMaitre = carteJouee;
				}else{
					//TODO controler si c'est la bonne carte, s'il a le droit de pisser
				}
			}
			//un atout a deja ete joue
			else{
				//comparer 2 atouts
				if(c.getCouleur() == atout){
					Comparator<Carte> compAtout = new Comparator<Carte>() {
						@Override
						public int compare(Carte o1, Carte o2) {
							int res = 0;
							if(o1.calculerValeurCarte(atout) > o2.calculerValeurCarte(atout)){
								res = 1;
							} else {
								if(o1.calculerValeurCarte(atout) < o2.calculerValeurCarte(atout)){
									res = -1;
								} else {
									res = o1.compareTo(o2);
								}
							}
							return res;
						}
					};

					if(compAtout.compare(this.carteMaitre, carteJouee)<0){
						this.carteMaitre = carteJouee;
					}
				}
			}
		}

		return ajoutOK;
	}
	
	public int calculerValeurPli(CouleurEnum atout){
		int points = super.calculerValeurPaquet(atout);
		if(this.hasDixDeDer){
			points += 10;
		}
		if(this.hasBelote){
			points += 10;
		}
		if(this.hasRebelote){
			points += 10;
		}
		return points;
	}
	
	public CarteJouee getCarteJoueeMaitre(){
		if(hasCoupe){
			//TODO ici faut prendre en compte qu'on a coupé a l'atout;
		}
		return null;
	}

	public CarteJouee getCarteMaitre() {
		return carteMaitre;
	}
	public Joueur getJoueurMaitre() {
		return carteMaitre.getJoueur();
	}

	public CouleurEnum getCouleurDemandee() {
		return this.couleurDemandee;
	}
}