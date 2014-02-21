import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

	private List<Carte> main;
	private List<Carte> mainPique;
	private List<Carte> mainCoeur;
	private List<Carte> mainTrefle;
	private List<Carte> mainCarreau;

	public Main() {
		main = new ArrayList<Carte>(8);
		mainPique = new ArrayList<Carte>(8);
		mainCoeur = new ArrayList<Carte>(8);
		mainTrefle = new ArrayList<Carte>(8);
		mainCarreau = new ArrayList<Carte>(8);

		for (int i = 0; i < 8; i++) {
			main.add(new Carte(CouleurEnum.NotInitialized,
					FigureEnum.NotInitialized));
			mainPique.add(new Carte(CouleurEnum.NotInitialized,
					FigureEnum.NotInitialized));
			mainCoeur.add(new Carte(CouleurEnum.NotInitialized,
					FigureEnum.NotInitialized));
			mainTrefle.add(new Carte(CouleurEnum.NotInitialized,
					FigureEnum.NotInitialized));
			mainCarreau.add(new Carte(CouleurEnum.NotInitialized,
					FigureEnum.NotInitialized));
		}
	}

	/*
	 * TODO methodes a realiser
	 * 
	 * Ajouter carte à la main - fait 
	 * Modifier un carte de la main - fait
	 * Enlever un carte a la main - fait 
	 * mettre a jour directement tte les listes - fait
	 */

	public void addCarte(Carte c) {
		this.main.add(c);
		this.synchronizeMain();
	}

	public void removeCarte(Carte c) {
		this.main.remove(trouverCarte(c));
		synchronizeMain();
	}

	private int trouverCarte(Carte carte) {
		for (Carte c : this.main) {
			if (c.equals(carte)) {
				return this.main.indexOf(carte);
			}
		}
		return -1;
	}

	private void synchronizeMain() {
		/*
		 * TODO il faut trouver mieux, pas du tout optimisé
		 * */
		//on supprime toutes les cartes
		this.mainCarreau.removeAll(this.mainCarreau);
		this.mainCoeur.removeAll(this.mainCoeur);
		this.mainPique.removeAll(this.mainPique);
		this.mainTrefle.removeAll(this.mainTrefle);
		
		//on remet les nouvelles cartes
		for (Carte c : main) {
			switch (c.getCouleur()) {
			case Carreau:
				this.mainCarreau.add(c);
				break;
			case Coeur:
				this.mainCoeur.add(c);
				break;
			case Pique:
				this.mainPique.add(c);
				break;
			case Trefle:
				this.mainTrefle.add(c);
				break;
			default:
				//cas de carte en NotInitialized
				break;
			}
		}
	}

	public void modifCarte(Carte carte, FigureEnum f, CouleurEnum c) {
		this.main.get(trouverCarte(carte)).setCouleur(c);
		this.main.get(trouverCarte(carte)).setFigure(f);
		this.synchronizeMain();
	}

	public void modifCarte(Carte carte, CouleurEnum c) {
		this.main.get(trouverCarte(carte)).setCouleur(c);
		this.synchronizeMain();
	}

	public void modifCarte(Carte carte, FigureEnum f) {
		this.main.get(trouverCarte(carte)).setFigure(f);
		this.synchronizeMain();
	}

	/* Accesseurs */
	public List<Carte> getMain() {
		return main;
	}

	public List<Carte> getMainPique() {
		return mainPique;
	}

	public List<Carte> getMainCoeur() {
		return mainCoeur;
	}

	public List<Carte> getMainTrefle() {
		return mainTrefle;
	}

	public List<Carte> getMainCarreau() {
		return mainCarreau;
	}

}
