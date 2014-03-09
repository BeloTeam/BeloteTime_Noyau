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

import entite.GameMaster;
import entite.Joueur;
import entite.JoueurHumain;

public class TableDeJeu {
	private Joueur[] joueurs;
	private GameMaster gm;
	private Tas tas;
	private boolean sensDesAiguilleDuneMontre;
	private Pli pliCourant;
	private CouleurEnum couleurAtout;
	private Carte carteDonne;

	public TableDeJeu() {
		sensDesAiguilleDuneMontre = true;
		joueurs = new Joueur[4];
		joueurs[0] = new JoueurHumain(PositionEnum.Sud,"Humain",this);
		joueurs[1] = new JoueurHumain(PositionEnum.Nord,"Joueur Nord",this);
		joueurs[2] = new JoueurHumain(PositionEnum.Est,"Joueur Est",this);
		joueurs[3] = new JoueurHumain(PositionEnum.Ouest,"Joueur Ouest",this);
		gm = new GameMaster(joueurs,this);
		tas = new Tas();
		tas.melanger(50);
	}
	public Tas getTas() {
		return tas;
	}
	
	public Pli getPliCourant(){
		return this.pliCourant;
	}
	
	public void nouveauPliCourant(boolean dixDeDer){
		this.pliCourant = new Pli(this.couleurAtout,dixDeDer);
	}
	
	public void jouerCarte(Carte carte, Joueur joueur){
		if(this.pliCourant != null && this.couleurAtout != null){
			this.pliCourant.ajouter(carte, joueur);
		}
	}

	public CouleurEnum getCouleurAtout() {
		return couleurAtout;
	}
	public void setCouleurAtout(CouleurEnum couleurAtout) {
		this.couleurAtout = couleurAtout;
	}
	
	public Carte getCarteDonne() {
		return this.carteDonne;
	}

	public void montrerCarteDonne(){
		this.carteDonne = this.tas.retirerCarteDessusPaquet();
	}
	
	public void attribuerCarteDonneA(Joueur joueur){
		joueur.getMain().ajouter(this.carteDonne, this.couleurAtout);
		this.carteDonne = null;
	}
	
	public void remettreCarteDonneDansLeTas(){
		this.tas.ajouter(carteDonne);
		this.carteDonne = null;
	}
	
	public Joueur joueurSuivant(Joueur j){
		if(sensDesAiguilleDuneMontre){
			return aGaucheDe(j);
		} else {
			return aDroiteDe(j);
		}
	}
	
	public Joueur aGaucheDe(Joueur j){
		PositionEnum aGauche = null;
		switch(j.getPosition()){
		case Nord:
			aGauche = PositionEnum.Est;
			break;
		case Est:
			aGauche = PositionEnum.Sud;
			break;
		case Sud:
			aGauche = PositionEnum.Ouest;
			break;
		case Ouest:
			aGauche = PositionEnum.Nord;
			break;
		}
		for(Joueur joueur : joueurs){
			if(joueur.getPosition() == aGauche){
				return joueur;
			}
		}
		return null;
	}
	
	public Joueur aDroiteDe(Joueur j){
		PositionEnum aGauche = null;
		switch(j.getPosition()){
		case Nord:
			aGauche = PositionEnum.Ouest;
			break;
		case Ouest:
			aGauche = PositionEnum.Sud;
			break;
		case Sud:
			aGauche = PositionEnum.Est;
			break;
		case Est:
			aGauche = PositionEnum.Nord;
			break;
		}
		for(Joueur joueur : joueurs){
			if(joueur.getPosition() == aGauche){
				return joueur;
			}
		}
		return null;
	}
	
	public int nbCartesDe(Joueur joueur){
		int nbCartes = 0;
		for(Joueur j : this.joueurs){
			if(j == joueur){
				nbCartes = joueur.getMain().getMain().size();
			}
		}
		return nbCartes;
	}
	public GameMaster getGm() {
		return gm;
	}
}
