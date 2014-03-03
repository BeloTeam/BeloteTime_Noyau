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
		
		Assert.assertEquals(8, main.getTaillePaquet());
		Assert.assertEquals(4, main.getNbCarteCouleur(CouleurEnum.Pique));
		Assert.assertEquals(2, main.getNbCarteCouleur(CouleurEnum.Carreau));
		
	}

}
