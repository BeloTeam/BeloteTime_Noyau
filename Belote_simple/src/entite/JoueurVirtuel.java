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

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.Paquet;
import noyau.classesMetier.PositionEnum;
import noyau.classesMetier.TableDeJeu;

/**
 * JoueurVirtuel représante un joueur non humain, qui possède une intéligence artificielle
 * @author BeloTeam
 * @version 1.0
 **/
public class JoueurVirtuel extends Joueur {
	Intelligence ia;

	/**
	 * Surcharge du constructeur Joueur, création d'un joueur virtuel.
	 * @param PositionEnum Position du joueur sur la table
	 * @param String Nom du joueur virtuel
	 * @param String Table ou est assie le joueur virtuel
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
	 * @return boolean
	 */
	@Override
	public void analyserSonJeu() {
		
	}

}
