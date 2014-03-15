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

/**
 * La table de jeu contient toutes les informations nécessaire pour réaliser une partie de belote.
 * @author BeloTeam
 * @version 1.0
 *
 */
public class TableDeJeu {
	//TODO Tous les joueurs doivent pouvoir savoir qui est le donneur, qui a pris et quel est le joueur courant. Déplacer ces attributs de GameMaster ici?
	// (ce serait utilisé dans le todo suivant, cf attribuerCarteRetourneeA(Joueur) )
	private Joueur[] joueurs;
	private GameMaster gm;
	private Paquet paquet;
	private boolean sensDesAiguilleDuneMontre;
	private Pli pliCourant;
	private CouleurEnum couleurAtout;
	private Carte carteRetournee;

	/**
	 * Constructeur de la table de jeu.
	 */
	public TableDeJeu() {
		sensDesAiguilleDuneMontre = true;
		joueurs = new Joueur[4];
		joueurs[0] = new JoueurHumain(PositionEnum.Sud,"Humain",this);
		joueurs[1] = new JoueurHumain(PositionEnum.Nord,"Joueur Nord",this);
		joueurs[2] = new JoueurHumain(PositionEnum.Est,"Joueur Est",this);
		joueurs[3] = new JoueurHumain(PositionEnum.Ouest,"Joueur Ouest",this);
		gm = new GameMaster(joueurs,this);
		paquet = new Paquet();
		paquet.melanger(50);
		couleurAtout = null;
	}
	
	/**
	 * Retourne le tas de cartes.
	 * @return Paquet
	 */
	public Paquet getTas() {
		return paquet;
	}
	
	/**
	 * Retourne le pli courant.
	 * @return Pli
	 */
	public Pli getPliCourant(){
		return this.pliCourant;
	}
	
	/**
	 * Créé le nouveau pli courant.
	 * @param dixDeDer le dernier pli vaut 10 points supplémentaires
	 */
	public void nouveauPliCourant(boolean dixDeDer){
		this.pliCourant = new Pli(this.couleurAtout,dixDeDer);
	}
	
	/**
	 * Permet à un joueur de jouer une carte.
	 * @param carte
	 * @param joueur
	 */
	public void jouerCarte(Carte carte, Joueur joueur){
		if(this.pliCourant != null && this.couleurAtout != null){
			this.pliCourant.ajouter(carte, joueur);
		}
	}

	/**
	 * Retourne la couleur de l'atout.
	 * @return CouleurEnum
	 */
	public CouleurEnum getCouleurAtout() {
		return couleurAtout;
	}
	
	/**
	 * Défini la couleur à l'atout.
	 * @param couleurAtout
	 */
	public void setCouleurAtout(CouleurEnum couleurAtout) {
		this.couleurAtout = couleurAtout;
	}

	/**
	 * Retourne la carte du dessus du paquet.
	 */
	public void retournerUneCarte(){
		this.carteRetournee = this.paquet.retirerCarteDessusPaquet();
	}
	
	/**
	 * Renvoit la carte retournée.
	 * @return Carte
	 */
	public Carte getCarteRetournee() {
		return this.carteRetournee;
	}
	
	
	/**
	 * Ajouter la carte retournée au joueur qui a choisi l'atout.
	 * @param joueur
	 */
	public void attribuerCarteRetourneeA(Joueur joueur){
		//TODO En suivant le todo en haut de la page pas besoin d'indiquer le joueur qui récupère la carte retournée en paramètre
		joueur.getMain().ajouter(this.carteRetournee, this.couleurAtout);
		this.carteRetournee = null;
	}
	
	/**
	 * Remet la carte retournée dans le paquet.
	 */
	public void remettreCarteRetourneeDansLePaquet(){
		this.paquet.ajouter(carteRetournee);
		this.carteRetournee = null;
	}
	
	/**
	 * Indique le joueur suivant en fonction du sens de parcours
	 * @param j le joueur de référence
	 * @return le joueur suivant
	 */
	public Joueur joueurSuivant(Joueur j){
		if(sensDesAiguilleDuneMontre){
			return aGaucheDe(j);
		} else {
			return aDroiteDe(j);
		}
	}
	
	/**
	 * Indique le joueur se trouvant à gauche du joueur donné.
	 * @param j le joueur de référence
	 * @return le joueur à sa gauche
	 */
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
	
	/**
	 * Indique le joueur se trouvant à droite du joueur donné.
	 * @param j le joueur de référence
	 * @return le joueur à sa droite
	 */
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
	
	/**
	 * Indique le nombre de cartes dans la main d'un joueur donné.
	 * @param joueur
	 * @return le nombre de cartes
	 */
	public int nbCartesDe(Joueur joueur){
		//TODO La méthode ne semble pas utilisée (du moins pas dans GameMaster), c'est dommage! 
		int nbCartes = 0;
		for(Joueur j : this.joueurs){
			if(j == joueur){
				nbCartes = joueur.getMain().getTailleMain();
			}
		}
		return nbCartes;
	}
	
	/**
	 * Renvoit le maître du jeu de la partie
	 * @return GameMaster
	 */
	public GameMaster getGm() {
		return gm;
	}
	
	/**
	 * Renvoit les 4 joueurs de belote
	 * @return
	 */
	public Joueur[] getJoueurs() {
		return joueurs;
	}
}
