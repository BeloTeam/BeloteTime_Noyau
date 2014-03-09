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

import junit.framework.Assert;
import junit.framework.TestCase;

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.FigureEnum;
import noyau.classesMetier.Main;

import org.junit.Test;


public class MainTest extends TestCase{

	@Test
	public void test() {
		Main main =  new Main();
		main.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.As), CouleurEnum.Pique);
		main.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.As), CouleurEnum.Pique);
		main.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.Dame), CouleurEnum.Pique);
		main.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.Valet), CouleurEnum.Pique);
		main.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.Huit), CouleurEnum.Pique);
		main.ajouter(new Carte(CouleurEnum.Trefle, FigureEnum.Huit), CouleurEnum.Pique);
		main.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.Dame), CouleurEnum.Pique);
		main.ajouter(new Carte(CouleurEnum.Coeur, FigureEnum.Valet), CouleurEnum.Pique);
		
		Assert.assertEquals(8, main.getTailleMain());
		Assert.assertEquals(4, main.getNbCarteCouleur(CouleurEnum.Pique));
		Assert.assertEquals(2, main.getNbCarteCouleur(CouleurEnum.Carreau));
		
	}

}
