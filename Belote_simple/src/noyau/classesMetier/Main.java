package noyau.classesMetier;



import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Classe representant une main de carte
 * @author BeloTeam
 * @version 1.0
 * */
public class Main {
	
	private Map<CouleurEnum, SortedSet<Carte>> main;
	private int nbCarte;
	private final int TAILLEMAX;
	
	
	/**
	 * Constructeur surcharge de Main
	 * @param int TAILLEMAX
	 * */
	public Main(final int TAILLEMAX) {
		this.main = new HashMap<>();
		this.nbCarte = 0;
		this.TAILLEMAX = TAILLEMAX;
		for(CouleurEnum c : CouleurEnum.values()) {
			this.main.put(c, new TreeSet<Carte>()); 
		}
	}
	
	/**
	 * Constructeur par defaut de Main
	 * */
	public Main() {
		this.main = new HashMap<>();
		this.nbCarte = 0;
		this.TAILLEMAX = 8;
		for(CouleurEnum c : CouleurEnum.values()) {
			this.main.put(c, new TreeSet<Carte>()); 
		}
	}
	
	/**
	 * Permet de recuperer la main triee
	 * @return Map<CouleurEnum, SortedSet<Carte>>
	 * */
	public Map<CouleurEnum, SortedSet<Carte>> getMain() {
		return main;
	}

	/**
	 * Permet de calculer le total de points d'une couleur donnee de la main
	 * @param atout CouleurEnum
	 * @return int total de points
	 * */
	public int calculerValeurMain(CouleurEnum atout) {
		int valeur = 0;
		Set<CouleurEnum> keys = this.main.keySet();
		for (Iterator<CouleurEnum> itPaquet = keys.iterator(); itPaquet.hasNext();) {
			SortedSet<Carte> paquetCouleur = new TreeSet<Carte>(main.get(itPaquet.next()));
			for (Iterator<Carte> itPaquetCouleur = paquetCouleur.iterator(); itPaquetCouleur.hasNext();) {
				valeur += itPaquetCouleur.next().calculerValeurCarte(atout);
			}
		}
		return valeur;
	}
	
	
	/**
	 * Permet d'ajouter une carte a la main
	 * @param c Carte
	 * @param atout CouleurEnum 
	 * @return boolean succes ou echec de l'ajout
	 * */
	public boolean ajouter(Carte c, final CouleurEnum atout) {
		boolean estAjoute = false;
		if (this.nbCarte < this.TAILLEMAX) {
			SortedSet<Carte> paquetCouleur;
			if (this.main.get(c.getCouleur()) != null) {
				paquetCouleur = new TreeSet<>(this.main.remove(c.getCouleur()));
			} else {
				if(c.getCouleur().equals(atout)){
					Comparator<Carte> compAtout = new Comparator<Carte>() {
						@Override
						public int compare(Carte o1, Carte o2) {
							int res = 0;
							if(o1.calculerValeurCarte(atout) > o2.calculerValeurCarte(atout)){
								res = 1;
							} else {
								if(o1.calculerValeurCarte(atout) < o2.calculerValeurCarte(atout)){
									res = -1;
								} else {
									res = o1.compareTo(o2);
								}
							}
							return res;
						}
					};
					paquetCouleur = new TreeSet<>(compAtout);
				} else {
					paquetCouleur = new TreeSet<>();
				}
			}
			estAjoute = paquetCouleur.add(c);
			if (estAjoute) {
				this.nbCarte++;
			}
			this.main.put(c.getCouleur(), paquetCouleur);
		}
		return estAjoute;
	}

	
	/**
	 * Permet de supprimer une carte a la main
	 * @param c Carte
	 * @return boolean succes ou echec de la supression
	 * */
	public boolean supprimer(Carte c) {
		boolean estSupprimer = false;
		if (this.nbCarte > 0) {
			SortedSet<Carte> paquetCouleur = new TreeSet<>(this.main.remove(c.getCouleur()));
			estSupprimer = paquetCouleur.remove(c);
			if (estSupprimer) {
				this.nbCarte--;
			}
			this.main.put(c.getCouleur(), paquetCouleur);
		}
		return estSupprimer;
	}
	
	
	/**
	 * Getter sur la taille de la main
	 * @return int taille de la main
	 * */
	public int getTailleMain() {
		return this.nbCarte;
	}
	
	
	/**
	 * Retourne la carte la plus forte en valeur d'une couleur donnee
	 * @param c CouleurEnum
	 * @return Carte carte la plus forte
	 * */
	public Carte getPlusForteCarteNormale(CouleurEnum c) {
		return this.main.get(c).first();
	}
	
	/**
	 * Retourne la carte la plus faible en valeur d'une couleur donnee
	 * @param c CouleurEnum
	 * @return Carte carte la plus faible
	 * */
	public Carte getPlusFaibleCarteNormale(CouleurEnum c) {
		return this.main.get(c).last();
	}
	
	/**
	 * Retourne le nombre de carte d'une couleur donnee
	 * @param couleur CouleurEnum
	 * @return int
	 * */
	public int getNbCarteCouleur(CouleurEnum couleur){
		return this.main.get(couleur).size();
	}
	
	/**
	 * Permet de savoir si la main possede le 9 a l'atout
	 * @param couleur CouleurEnum
	 * @return boolean true si le 9 est dans la main
	 * */
	public boolean hasValet(CouleurEnum couleur){
		return this.main.get(couleur).contains(new Carte(couleur, FigureEnum.Valet));
	}
	/**
	 * Permet de savoir si la main possede le valet a l'atout
	 * @param couleur CouleurEnum
	 * @return boolean true si le valet est dans la main
	 * */
	public boolean hasNeuf(CouleurEnum couleur){
		return this.main.get(couleur).contains(new Carte(couleur, FigureEnum.Neuf));
	}
	
	/**
	 * Permet de savoir si la main possede une carte donnee
	 * @param couleur CouleurEnum
	 * @param figure FigureEnum
	 * @return boolean true si le valet est dans la main
	 * */
	public boolean hasCarte(CouleurEnum couleur, FigureEnum figure){
		return this.main.get(couleur).contains(new Carte(couleur, figure));
	}
	
	/**
	 * Permet de savoir si la main possede la dame et le roi a l'atout
	 * @param couleurAtout CouleurEnum
	 * @return boolean true si le dame et roi sont dans la main
	 * */
	public boolean hasBeloteRebolote(CouleurEnum couleurAtout){
		return this.hasCarte(couleurAtout, FigureEnum.Dame) 
				&& this.hasCarte(couleurAtout, FigureEnum.Roi);
	}
	
	/**
	 * Surcharge de la methode toString() d'Object
	 * @return string 
	 * */
	public String toString(){
		int numCarte = 0;
		String res = "(";
		Set<CouleurEnum> keys = this.main.keySet();
		for (Iterator<CouleurEnum> it = keys.iterator(); it.hasNext();) {
			CouleurEnum key = (CouleurEnum) it.next();
			for (Iterator<Carte> it2 = this.main.get(key).iterator(); it2.hasNext();) {
				Carte carte = (Carte) it2.next();
				res += "\t"+ numCarte + " - " + carte + "\n";
				numCarte++;
			}
		}
		return res + ")";
	}
	
	/**
	 * Permet de savoir si la main possede la dame et le roi a l'atout
	 * @param c CouleurEnum
	 * @return SortedSet<Carte>
	 * */
	public SortedSet<Carte> get(CouleurEnum c){
		return this.main.get(c);
	}
	/**
	 * Retourne une liste de carte de la couleur donnee
	 * @param c CouleurEnum
	 * @return List<Carte>
	 * */
	public List<Carte> getList(CouleurEnum c){
		List<Carte> l = new ArrayList<>(this.main.get(c));
		return l;
	}
	/**
	 * Retourne une liste de carte representant la main
	 * @return List<Carte>
	 * */
	public List<Carte> hashtableToList(){
		List<Carte> newMain = new ArrayList<>();
		Set<CouleurEnum> keys = this.main.keySet();
		for (Iterator<CouleurEnum> it = keys.iterator(); it.hasNext();) {
			CouleurEnum key = (CouleurEnum) it.next();
			for (Iterator<Carte> it2 = this.main.get(key).iterator(); it2.hasNext();) {
				Carte carte = (Carte) it2.next();
				newMain.add(carte);
			}
		}
		return newMain;
	}
	
	/**
	 * Retourne les atouts plus fort que la carte donnee
	 * @param carteMaitre CarteJouee
	 * @return SortedSet<Carte>
	 * */
	public SortedSet<Carte> getAtoutPlusFortQue(CarteJouee carteMaitre) {
		SortedSet<Carte> setCarteAtoutPlusForte = new TreeSet<Carte>();

		for (Carte carte : this.get(carteMaitre.getCouleur())) {
			if(carte.compareTo(carteMaitre) > 0){
				setCarteAtoutPlusForte.add(carte);
			}
		}
		
		//si setCarteAtoutPlusForte est vide alors le joueur n'a pas d'atout plus forte
		if(setCarteAtoutPlusForte.size() == 0){
			return this.get(carteMaitre.getCouleur());
		}
		else{
			//affichage temporaire
			System.out.println("Vous devez monter a l'atout");
			return setCarteAtoutPlusForte;
		}
		
	}
	
	/**
	 * Retourne les atouts plus fort que la carte donnee
	 * @param carteMaitre CarteJouee
	 * @return List<Carte>
	 * */
	public List<Carte> getAtoutPlusFortListQue(final CarteJouee carteMaitre) {
		List<Carte> listCarteAtoutPlusForte = new ArrayList<Carte>();
		Comparator<Carte> compAtout = new Comparator<Carte>() {
			@Override
			public int compare(Carte o1, Carte o2) {
				int res = 0;
				if(o1.calculerValeurCarte(carteMaitre.getCouleur()) > o2.calculerValeurCarte(carteMaitre.getCouleur())){
					res = 1;
				} else {
					if(o1.calculerValeurCarte(carteMaitre.getCouleur()) < o2.calculerValeurCarte(carteMaitre.getCouleur())){
						res = -1;
					} else {
						res = o1.compareTo(o2);
					}
				}
				return res;
			}
		};
		

		for (Carte carte : this.get(carteMaitre.getCouleur())) {
			if(compAtout.compare(carteMaitre, carte)<0){
				listCarteAtoutPlusForte.add(carte);
			}
		}
		
		//si setCarteAtoutPlusForte est vide alors le joueur n'a pas d'atout plus forte
		if(listCarteAtoutPlusForte.size() == 0){
			return this.getList(carteMaitre.getCouleur());
		}
		else{
			//affichage temporaire
			System.out.println("Vous devez monter a l'atout");
			return listCarteAtoutPlusForte;
		}
		
	}
}
