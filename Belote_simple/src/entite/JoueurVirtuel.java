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

import classesMetier.Carte;
import classesMetier.CouleurEnum;
import classesMetier.Paquet;
import classesMetier.PositionEnum;
import classesMetier.TableDeJeu;

/**
 * JoueurVirtuel repr�sante un joueur non humain, qui poss�de une int�ligence artificielle
 * @author BeloTeam
 * @version 1.0
 **/
public class JoueurVirtuel extends Joueur {
	Intelligence ia;

	/**
	 * Surcharge du constructeur Joueur, cr�ation d'un joueur virtuel.
	 * @param position du joueur sur la table
	 * @param nom du joueur humain
	 * @param table ou est assie le joueur humain
	 * */
	public JoueurVirtuel(PositionEnum position, String nom, TableDeJeu table) {
		super(position, nom, table);
		ia = new Intelligence();
	}

	/**
	 * Retourne si oui ou non le joueur virtuel prend au premier tour
	 * @return boolean
	 */
	@Override
	public boolean prendPremiereDonne() {
		// TODO a faire
		return false;
	}

	/**
	 * Retourne si oui ou non le joueur virtuel prend au deuxieme tour
	 * @return boolean
	 */
	@Override
	public CouleurEnum prendDeuxiemeDonne() {
		// TODO a faire
		return null;
	}

	/**
	 * Action permettant de jouer une carte lors d'un pli.
	 * @return Carte
	 */
	@Override
	public Carte jouerPli() {
		return null;
	}

	/**
	 * Action permettant de couper un tas de cartes
	 * @return boolean
	 */
	@Override
	public boolean coupe(Paquet tas) {
		return false;
	}

	/**
	 * Action permettant d'analyser la main courante (belotes?)
	 * @return void
	 */
	@Override
	public void analyserSonJeu() {
		
	}

}
