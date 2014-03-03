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
		
		m.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.Neuf), atout);
		m.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.Huit), atout);
		m.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.Dix), atout);
		m.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.Valet), atout);
		m.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.Dame), atout);
		m.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.As), atout);
		m.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.Sept), atout);
		m.ajouter(new Carte(CouleurEnum.Carreau, FigureEnum.Neuf), atout);
		
		int valeur = m.calculerValeurPaquet(atout);
		
		System.out.println(m);
		System.out.println(valeur);
		System.out.println(m.hasNeuf(atout));
		
		m.supprimer(new Carte(CouleurEnum.Pique, FigureEnum.Neuf));
		valeur = m.calculerValeurPaquet(atout);
		System.out.println(m);
		System.out.println(valeur);
		System.out.println(m.hasNeuf(atout));
		
		m.ajouter(new Carte(CouleurEnum.Pique, FigureEnum.Neuf), atout);
		valeur = m.calculerValeurPaquet(atout);
		System.out.println(m);
		System.out.println(valeur);
		System.out.println(m.hasNeuf(atout));
	}

}
