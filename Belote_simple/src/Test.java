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

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.FigureEnum;
import noyau.classesMetier.Main;




public class Test{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Main m = new Main(8);
		CouleurEnum atout = CouleurEnum.Pique;

		m.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.As), atout);
		m.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.Dix), atout);
		m.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.Roi), atout);
		m.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.Dame), atout);
		m.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.Valet), atout);
		m.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.Neuf), atout);
		m.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.Huit), atout);
		m.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.Sept), atout);

		int valeur = m.calculerValeurMain(atout);
		
		System.out.println(m);
		System.out.println(valeur);
		System.out.println(m.hasNeuf(atout));
		
		/*m.supprimer(new Carte(CouleurEnum.Pique, FigureEnum.Dix));
		valeur = m.calculerValeurPaquet(atout);
		System.out.println(m);
		System.out.println(valeur);
		System.out.println(m.hasNeuf(atout));
		
		m.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.Dix), atout);
		valeur = m.calculerValeurPaquet(atout);
		System.out.println(m);
		System.out.println(valeur);
		System.out.println(m.hasNeuf(atout));*/
	}

}
