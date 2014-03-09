/*
 * Copyright 2014 BeloTeam
 * 
 * This file is part of BeloteTime.
 *	
 * BeloteTime is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeloteTime is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeloteTime.  If not, see <http://www.gnu.org/licenses/>. 
 */

package noyau.classesMetier;

import java.util.Comparator;

import entite.Joueur;

public class Pli extends AbstractPaquetNonTrier {
	private boolean hasDixDeDer;
	private boolean hasCoupe;
	private CarteJouee carteMaitre;
	private CouleurEnum couleurDemandee;
	private CouleurEnum couleurAtout;
	
	public Pli(CouleurEnum couleurAtout, boolean hasDixDeDer) { 
		super(4);
		this.couleurAtout = couleurAtout;
		this.hasDixDeDer = hasDixDeDer;
		this.carteMaitre=null;
		this.couleurDemandee =null;
	}
	
	public boolean ajouter(Carte c, Joueur j){
		CarteJouee carteJouee = new CarteJouee(c,j);
		boolean ajoutOK = super.ajouter(carteJouee);
		//le cas ou c'est la 1ere carte du pli
		if(this.carteMaitre == null && this.couleurDemandee == null){
			this.carteMaitre = carteJouee;
			this.couleurDemandee = carteJouee.getCouleur();
		}else{
			//pour l'instant aucun atout n'a été joué
			if(this.carteMaitre.getCouleur() != couleurAtout){
				if(carteJouee.getCouleur() == this.couleurDemandee){
					if(this.carteMaitre.compareTo(carteJouee) < 0){
						this.carteMaitre = carteJouee;
					}
				}
				else if(carteJouee.getCouleur() == couleurAtout){
					this.carteMaitre = carteJouee;
				}else{
					//TODO controler si c'est la bonne carte, s'il a le droit de pisser
				}
			}
			//un atout a deja ete joue
			else{
				//comparer 2 atouts
				if(c.getCouleur() == couleurAtout){
					Comparator<Carte> compAtout = new Comparator<Carte>() {
						@Override
						public int compare(Carte o1, Carte o2) {
							int res = 0;
							if(o1.calculerValeurCarte(couleurAtout) > o2.calculerValeurCarte(couleurAtout)){
								res = 1;
							} else {
								if(o1.calculerValeurCarte(couleurAtout) < o2.calculerValeurCarte(couleurAtout)){
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
	
	public int calculerValeurPli(){
		int points = super.calculerValeurPaquet(couleurAtout);
		if(this.hasDixDeDer){
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