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


public class Main {
	private Map<CouleurEnum, SortedSet<Carte>> main;
	private int nbCarte;
	private final int TAILLEMAX;

	public Main(final int TAILLEMAX) {
		this.main = new HashMap<>();
		this.nbCarte = 0;
		this.TAILLEMAX = TAILLEMAX;
	}
	public Main() {
		this.main = new HashMap<>();
		this.nbCarte = 0;
		this.TAILLEMAX = 8;
	}

	public Map<CouleurEnum, SortedSet<Carte>> getMain() {
		return main;
	}

	public int calculerValeurPaquet(CouleurEnum atout) {
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
	
	public int getTaillePaquet() {
		return this.nbCarte;
	}
	
	public Carte getPlusForteCarteNormale(CouleurEnum c) {
		return this.main.get(c).first();
	}
	
	public Carte getPlusFaibleCarteNormale(CouleurEnum c) {
		return this.main.get(c).last();
	}
	
	public int getNbCarteCouleur(CouleurEnum couleur){
		return this.main.get(couleur).size();
	}
	
	public boolean hasValet(CouleurEnum couleur){
		return this.main.get(couleur).contains(new Carte(couleur, FigureEnum.Valet));
	}
	
	public boolean hasNeuf(CouleurEnum couleur){
		return this.main.get(couleur).contains(new Carte(couleur, FigureEnum.Neuf));
	}
	
	public boolean hasCarte(CouleurEnum couleur, FigureEnum figure){
		return this.main.get(couleur).contains(new Carte(couleur, figure));
	}
	
	public boolean hasBeloteRebolote(CouleurEnum couleurAtout){
		return this.hasCarte(couleurAtout, FigureEnum.Dame) 
				&& this.hasCarte(couleurAtout, FigureEnum.Roi);
	}
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
	public SortedSet<Carte> get(CouleurEnum c){
		return this.main.get(c);
	}
	public List<Carte> getList(CouleurEnum c){
		List<Carte> l = new ArrayList<>(this.main.get(c));
		return l;
	}
	
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
}
