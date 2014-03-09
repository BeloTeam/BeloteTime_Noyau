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
import noyau.classesMetier.Main;
import noyau.classesMetier.PositionEnum;
import noyau.classesMetier.TableDeJeu;

public abstract class Joueur {
	private Main main;
	private PositionEnum position;
	private String nom;
	private TableDeJeu table;

	public Main getMain() {
		return main;
	}

	public Joueur(PositionEnum position, String nom, TableDeJeu table) {
		this.main = new Main();
		this.position = position;
		this.nom = nom;
		this.table = table;
	}

	public PositionEnum getPosition() {
		return position;
	}

	public String toString() {
		return "Joueur : " + this.nom;
	}

	public TableDeJeu getTable() {
		return table;
	}

	public abstract boolean prendPremiereDonne();

	public abstract CouleurEnum prendDeuxiemeDonne();

	public abstract Carte jouerPli();

	public boolean equals(Object joueur) {
		if (joueur instanceof Joueur) {
			if (((Joueur) joueur).nom.equals(this.nom) && ((Joueur) joueur).position.equals(this.position)) {
				return true;
			}
			return false;
		}
		return false;
	}
}
