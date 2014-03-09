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
import noyau.classesMetier.PositionEnum;
import noyau.classesMetier.TableDeJeu;

public class JoueurVirtuel extends Joueur{
	Intelligence ia;

	public JoueurVirtuel(PositionEnum position,String nom, TableDeJeu table) {
		super(position,nom, table);
		ia = new Intelligence();
	}

	public boolean prendPremiereDonne() {
		//TODO a faire
		return false;
	}

	@Override
	public CouleurEnum prendDeuxiemeDonne() {
		//TODO a faire
		return null;
	}

	@Override
	public Carte jouerPli() {
		return null;
	}

}
