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


import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite representant un paquet de carte
 * @author BeloTeam
 * @version 1.0
**/
public abstract class AbstractPaquetNonTrier {
	private List<Carte> paquet;
	private int nbCarte;
	private final int TAILLEMAX;

	/**
	 * Constructeur par defaut de AbstractPaquetNonTrier
	 * @param TAILLEMAX int
	 * */
	public AbstractPaquetNonTrier(final int TAILLEMAX) {
		this.paquet = new ArrayList<>(TAILLEMAX);
		this.nbCarte = 0;
		this.TAILLEMAX = TAILLEMAX;
	}

	
	/**
	 * Retourne la liste des cartes
	 * @return List<Carte> 
	 * */
	public List<Carte> getCartes() {
		return paquet;
	}
	
	/**
	 * Permet d'ajouter une carte a la liste des cartes
	 * @param c Carte
	 * @return boolean true si l'ajout a ete effectue
	 * */
	public boolean ajouter(Carte c){
		boolean estAjoute = false;
		if(this.nbCarte < this.TAILLEMAX){
			estAjoute = paquet.add(c);
			if(estAjoute) nbCarte++;
		}
		return estAjoute;
	}
	
	/**
	 * Permet de supprimer une carte a la liste des cartes
	 * @param c Carte
	 * @return boolean true si la suppression a ete effectue
	 * */
	public boolean supprimer(Carte c){
		boolean estSupprime = false;
		if(this.nbCarte > 0){
			estSupprime = paquet.remove(c);
			if(estSupprime) nbCarte--;
		}
		return estSupprime;
	}
	
	/**
	 * Retourne la taille du paquet
	 * @return int
	 * */
	public int getTaillePaquet() {
		return this.nbCarte;
	}

	/**
	 * Permet de calculer la valeur en points du paquet
	 * @param atout CouleurEnum
	 * @return int
	 * */
	public int calculerValeurPaquet(CouleurEnum atout) {
		int valeur = 0;
		for (Carte c : this.paquet) {
			valeur += c.calculerValeurCarte(atout);
		}
		return valeur;
	}
	
	/**
	 * Surcharge de la methode toString() d'Object
	 * @return String 
	 * */
	public String toString(){
		return paquet.toString();
	}
}