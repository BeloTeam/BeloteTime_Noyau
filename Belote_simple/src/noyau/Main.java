package noyau;

import java.util.ArrayList;
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

		/*for (int i = 0; i < 8; i++) {
			main.add(new Carte(CouleurEnum.Carreau,
					FigureEnum.As));
			mainPique.add(new Carte(CouleurEnum.NotInitialized,
					FigureEnum.NotInitialized));
			mainCoeur.add(new Carte(CouleurEnum.NotInitialized,
					FigureEnum.NotInitialized));
			mainTrefle.add(new Carte(CouleurEnum.NotInitialized,
					FigureEnum.NotInitialized));
			mainCarreau.add(new Carte(CouleurEnum.NotInitialized,
					FigureEnum.NotInitialized));
		}*/
	}

	public void add(Carte c) {
		if(this.main.size() < 9){
			this.main.add(c);
			this.synchronizeMain();
		}		
	}

	public void remove(Carte c) {
		this.main.remove(find(c));
		synchronizeMain();
	}

	private List<Carte> getListCouleur(CouleurEnum c) {
		switch (c) {
		case Carreau:
			return mainCarreau;
		case Coeur:
			return mainCoeur;
		case Pique:
			return mainPique;
		case Trefle:
			return mainTrefle;
		default:
			//lever un exception
			return null;
		}
	}

	public List<Carte> getListAtout(CouleurEnum c) {
		return this.getListAtout(c);
	}

	public int getNbAtout(CouleurEnum c) {
		return this.getListCouleur(c).size();
	}

	public boolean hasNeuf(CouleurEnum c) {
		for (Carte carte : this.getListCouleur(c)) {
			if(carte.getFigure() == FigureEnum.Neuf)
				return true;
		}
		return false;
	}
	
	public void afficher() {
		System.out.println("-------------------------------------------------------------------");
		System.out.println("-----Main principale");
		for (Carte c : this.main) {
			System.out.println(c.toString());
		}
		
		System.out.println("\n-----Main carreau");
		for (Carte c : this.mainCarreau) {
			System.out.println(c.toString());
		}
		
		System.out.println("\n-----Main coeur");
		for (Carte c : this.mainCoeur) {
			System.out.println(c.toString());
		}
		
		System.out.println("\n-----Main pique");
		for (Carte c : this.mainPique) {
			System.out.println(c.toString());
		}
		System.out.println("\n-----Main trefle");
		for (Carte c : this.mainTrefle) {
			System.out.println(c.toString());
		}
		System.out.println("-------------------------------------------------------------------");
	}

	public boolean hasValet(CouleurEnum c) {
		for (Carte carte : this.getListCouleur(c)) {
			if(carte.getFigure() == FigureEnum.Valet)
				return true;
		}
		return false;
	} 

	public Carte get(int i) {
		return this.main.get(i);
	}

	public int size() {
		return this.main.size();
	}

	/**
	 * Permet de trouver une carte dans la main
	 * 
	 * @param Carte
	 *            une carte que l'on veut trouver
	 * @return retourne l'indice de la carte dans la main
	 * */
	public int find(Carte carte) {
		for (Carte c : this.main) {
			if (c.equals(carte)) {
				return this.main.indexOf(carte);
			}
		}
		return -1;
	}

	/**
	 * Permet de sychroniser la mains de couleur avec la main principale qui
	 * contient toutes les cartes
	 * 
	 * @param void
	 * @return void
	 * */
	private void synchronizeMain() {
		/*
		 * TODO il faut trouver mieux, pas du tout optimisé
		 */
		// on supprime toutes les cartes
		this.mainCarreau.removeAll(this.mainCarreau);
		this.mainCoeur.removeAll(this.mainCoeur);
		this.mainPique.removeAll(this.mainPique);
		this.mainTrefle.removeAll(this.mainTrefle);

		// on remet les nouvelles cartes
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
				// cas de carte en NotInitialized
				break;
			}
		}
	}

	/**
	 * Permet de modifier la couleur ou la figure d'une carte qui se trouve dans
	 * la main
	 * 
	 * @param carte
	 *            carte que l'on veut modifier
	 * @param figure
	 *            nouvelle figure
	 * @param couleur
	 *            nouvelle couleur
	 * @return void
	 * */
	public void set(Carte carte, FigureEnum figure, CouleurEnum couleur) {
		this.main.get(find(carte)).setCouleur(couleur);
		this.main.get(find(carte)).setFigure(figure);
		this.synchronizeMain();
	}

	public void set(Carte carte, CouleurEnum c) {
		this.main.get(find(carte)).setCouleur(c);
		this.synchronizeMain();
	}

	public void set(Carte carte, FigureEnum f) {
		this.main.get(find(carte)).setFigure(f);
		this.synchronizeMain();
	}

	public void set(int indice, Carte carte) {
		this.main.set(indice, carte);
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

	public String toString(){
		String main="";
		for (Carte c : this.main) {
			main += c.toString();
			main +="\n";
		}
		return main;
	}
}
