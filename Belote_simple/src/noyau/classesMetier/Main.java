package noyau.classesMetier;

import java.util.ArrayList;
import java.util.List;

public class Main {

	private List<Carte> main;

	public Main() {
		main = new ArrayList<Carte>(8);
	}

	public void add(Carte newCarte) {
		if(this.main.size() < 9){
			this.main.add(newCarte);
		}		
	}

	public void remove(Carte carte) {
		this.main.remove(find(carte));
	}

	private List<Carte> getListCouleur(CouleurEnum couleurAtout) {
		List<Carte> liste = new ArrayList<>();
		for (Carte carte : this.main) {
			if(carte.getCouleur() == couleurAtout){
				liste.add(carte);
			}
		}
		return liste;
	}

	public int getNbAtout(CouleurEnum couleurAtout) {
		return this.getListCouleur(couleurAtout).size();
	}

	public boolean hasNeuf(CouleurEnum couleurAtout) {
		for (Carte carte : this.getListCouleur(couleurAtout)) {
			if(carte.getFigure() == FigureEnum.Neuf)
				return true;
		}
		return false;
	}

	public boolean hasValet(CouleurEnum couleurAtout) {
		for (Carte carte : this.getListCouleur(couleurAtout)) {
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
	 * @param carte
	 *            une carte que l'on veut trouver
	 * @return retourne l'indice de la carte dans la main
	 * */
	private int find(Carte carte) {
		for (Carte c : this.main) {
			if (c.equals(carte)) {
				return this.main.indexOf(carte);
			}
		}
		return -1;
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
	}

	public void set(Carte carte, CouleurEnum c) {
		this.main.get(find(carte)).setCouleur(c);
	}

	public void set(Carte carte, FigureEnum f) {
		this.main.get(find(carte)).setFigure(f);
	}

	public void set(int indice, Carte carte) {
		this.main.set(indice, carte);
	}

	/* Accesseurs */
	public List<Carte> getMain() {
		return main;
	}

	public List<Carte> getMainPique() {
		return this.getListCouleur(CouleurEnum.Pique);
	}

	public List<Carte> getMainCoeur() {
		return this.getListCouleur(CouleurEnum.Coeur);
	}

	public List<Carte> getMainTrefle() {
		return this.getListCouleur(CouleurEnum.Trefle);
	}

	public List<Carte> getMainCarreau() {
		return this.getListCouleur(CouleurEnum.Carreau);
	}

	public String toString(){
		String main="";
		for (Carte c : this.main) {
			main += c.toString();
			main +="\n";
		}
		return main;
	}
	
	
	public void afficherAll() {
		System.out.println("-------------------------------------------------------------------");
		System.out.println("-----Main principale");
		for (Carte c : this.main) {
			System.out.println(c.toString());
		}
		
		System.out.println("\n-----Main carreau");
		for (Carte c : this.getMainCarreau()) {
			System.out.println(c.toString());
		}
		
		System.out.println("\n-----Main coeur");
		for (Carte c : this.getMainCoeur()) {
			System.out.println(c.toString());
		}
		
		System.out.println("\n-----Main pique");
		for (Carte c : this.getMainPique()) {
			System.out.println(c.toString());
		}
		System.out.println("\n-----Main trefle");
		for (Carte c : this.getMainTrefle()) {
			System.out.println(c.toString());
		}
		System.out.println("-------------------------------------------------------------------");
	}
}
