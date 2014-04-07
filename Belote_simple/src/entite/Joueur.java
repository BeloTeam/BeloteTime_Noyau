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

package entite;

import java.util.SortedSet;

import control.Terminal;
import classesMetier.Carte;
import classesMetier.CouleurEnum;
import classesMetier.Main;
import classesMetier.Paquet;
import classesMetier.PositionEnum;
import classesMetier.TableDeJeu;

public abstract class Joueur {
	private Main main;
	private PositionEnum position;
	private String nom;
	private TableDeJeu table;
	private boolean hasBeloteEtRe;

	/**
	 * Constructeur Joueur, création d'un joueur.
	 * @param position du joueur sur la table
	 * @param nom du joueur humain
	 * @param table ou est assie le joueur humain
	 * */
	public Joueur(PositionEnum position, String nom, TableDeJeu table) {
		this.main = new Main();
		this.position = position;
		this.nom = nom;
		this.table = table;
		this.hasBeloteEtRe = false;
	}
	
	/**
	 * Surcharge de l'opérateur toString d'Object
	 * @return String
	 */
	@Override
	public String toString() {
		return this.nom;
	}
	
	/**
	 * Surcharge de l'opérateur equals d'Object
	 * @param joueur
	 * @return boolean
	 */
	@Override
	public boolean equals(Object joueur) {
		if (joueur instanceof Joueur) {
			if (((Joueur) joueur).nom.equals(this.nom) && ((Joueur) joueur).position.equals(this.position)) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * maj de la variable, vrai si le joueur a la belote et rebelote dans sa main de départ, sinon faux.
	 * @param b boolean
	 */
	public void setHasBeloteEtRe(boolean b) {
		this.hasBeloteEtRe = b;
	}

	/**
	 * Retourne vrai si le joueur a la belote et rebelote dans sa main de départ, sinon faux.
	 * @return boolean
	 */
	public boolean hasBeloteEtRe() {
		return this.hasBeloteEtRe;
	}
	
	/**
	 * Retourne la position du joueur sur la table
	 * @return PositionEnum
	 */
	public PositionEnum getPosition() {
		return position;
	}

	/**
	 * Retourne la main courante du joueur
	 * @return Main
	 */
	public Main getMain() {
		return main;
	}
	
	/**
	 * Retourne la table de jeu où est assis le joueur.
	 * @return TableDeJeu
	 */
	public TableDeJeu getTable() {
		return table;
	}
	
	/**
	 * Retourne si oui ou non le joueur prend au premier tour
	 * @return boolean
	 */
	public abstract boolean prendPremiereDonne();

	/**
	 * Retourne si oui ou non le joueur prend au deuxieme tour
	 * @return CouleurEnum
	 */
	public abstract CouleurEnum prendDeuxiemeDonne();

	/**
	 * Action permettant de jouer une carte lors d'un pli.
	 * @return Carte
	 */
	public abstract Carte jouerPli();

	/**
	 * Action permettant de couper un tas de cartes
	 * @return boolean
	 */
	public abstract boolean coupe(Paquet tas);
	

	/**
	 * Action permettant d'analyser la main courante (belotes?)
	 * @return boolean
	 */
	public abstract void analyserSonJeu();
	
	/**
	 * Permet de retourner l'equipe dans lequel est le joueur
	 * @return Equipe
	 */
	public Equipe getEquipeDuJoueur(){
		
		if( this.table.getEquipes().get(0).estDansEquipe(this)){
			return this.table.getEquipes().get(0);
		}
		else{
			return this.table.getEquipes().get(1);
		}
	}
	
	public Joueur getPartenaire(){
		return this.getEquipeDuJoueur().getPartenaire(this);
	}
}
