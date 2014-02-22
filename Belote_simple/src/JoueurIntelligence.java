import java.util.ArrayList;
import java.util.List;

/**
 * @class JoueurProgramme
 * @author lacertus, Nathan,Loic
 * @resume represente l'IA d'un joueur de belote
 * 
 * */

public class JoueurIntelligence {

	//private Carte monPaquet[] = new Carte[8];
	//private List<Carte> main;
	private Main main;
	
	private int recoitVal;
	private CouleurEnum choixAtout;
	
	public JoueurIntelligence(){
		//main = new ArrayList<Carte>(8);
		main = new Main();
		for (int i = 0; i < 8; i++) {
			main.add(new Carte(CouleurEnum.NotInitialized,FigureEnum.NotInitialized));
		}
		recoitVal = 0;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public void affichejoueur(int x) {
		for (int i = 0; i < Fenetre.nbcartej[x]; i++) {
			Terminal.ecrireStringln("valeur jeux joueur " + x + " carte n " + i + " valeur " + this.main.get(i).getFigure() + " " + this.main.get(i).getCouleur());
		}
		Terminal.ecrireStringln("-----------------------");
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// recoit la carte sur le paquet
	public int recoit(Carte[] tabCards, int y, Carte u) {
		System.out.println("TAILLE : "+this.main.size()+ " Elem "+y);
		this.main.set(y, tabCards[0]);
		y++;
		for (int v = 0; v < 32; v++) {
			if (v + 1 < 32) {
				tabCards[v] = tabCards[v + 1];
			} else {
				tabCards[v] = u;
			}
		}
		return y;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// prend premier tour
	public void prendpremier(int numeroJoueur, Carte carteRetournee, int nbcarte) {
		String rep = new String();
		boolean hasValet = this.main.hasValet(carteRetournee.getCouleur());
		boolean hasNeuf = this.main.hasNeuf(carteRetournee.getCouleur());
		int totalpoint,nbAtout;

		switch (numeroJoueur) {
		case 0:
			Terminal.ecrireStringln("Vous voulez prendre aux premier tour ? (o,n)");
			rep = Terminal.lireString();
			if (rep.equals("o")) {
				this.recoitVal = 1;
			} else {
				this.recoitVal = 0;
			}
			break;
		default:
			// on compte le nombre d'atout et on ajoute la carte retournee
			nbAtout = this.main.getNbAtout(carteRetournee.getCouleur())+1;
			
			//on compte le nombre de points de la main du joueur
			totalpoint = Arbitre.pointsMain(main, carteRetournee.getCouleur());
			
			// ajout des points de la carte du milieu
			totalpoint += Arbitre.points(carteRetournee, carteRetournee.getCouleur());
			
			
			/*
			 * on prend si : - on a 40 pts et moins de 4 atouts avec le valet et
			 * le neuf - on a 40pts et plus de 3 atouts avec le valet - on a
			 * 40pts et plus de 5 atouts
			 */
			Terminal.ecrireStringln("Joueur " + numeroJoueur + " : ");
			Terminal.ecrireStringln("Nombre d'atouts : " + nbAtout + ", Valet : " + hasValet + " Neuf : " + hasNeuf + ", points : " + totalpoint);
			
			if (prendrePremierTour(hasValet, hasNeuf, totalpoint, nbAtout)) {
				Terminal.ecrireStringln("Jeu du joueur " + numeroJoueur + " qui a pris : ");
				for (int i = 0; i < 7; i++) {
					Terminal.ecrireStringln("carte : " + main.get(i));
				}
				this.recoitVal = 1;
			} else {
				this.recoitVal = 0;
			}
			// affiche la valeur de toute les cartes
			// Terminal.ecrireStringln("Valeur des cartes "+totalpoint);
			break;
		}

	}

	private boolean prendrePremierTour(boolean hasValet, boolean hasNeuf,
			int totalpoint, int nbAtout) {
		return totalpoint >= 40 && ((nbAtout < 4 && hasValet && hasNeuf) || (nbAtout > 3 && nbAtout < 5 && hasValet) || (nbAtout >= 5));
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// gestion pour prendre au deuxieme tour
	public void prenddeuxieme(int numeroJoueur, Carte carteRetournee, int nbcarte) {
		String rep = new String();
		CouleurEnum atoutret = CouleurEnum.NotInitialized;
		
		System.out.println("===============DEUXIEME TOUR ===============" );
		System.out.println(carteRetournee);
		
		CouleurEnum[] coulAtout = new CouleurEnum[4];
		coulAtout[0] = CouleurEnum.Coeur;
		coulAtout[1] = CouleurEnum.Pique;
		coulAtout[2] = CouleurEnum.Trefle;
		coulAtout[3] = CouleurEnum.Carreau;

		boolean hasValetCouleurCourante = false, hasNeufCouleurCourante = false;
		boolean hasValetCouleurPrecedente = false, hasNeufCouleurPrecedente = false;
		int totalpoint = 0, pointInt = 0, nbAtoutCouleurPrecedente = 0, nbAtoutCouleurCourante = 0;

		Terminal.ecrireStringln("Deuxieme tour : joueur " + numeroJoueur + " reflechi ...");
		
		// En fonction du joueur
		switch (numeroJoueur) {
		case 0:
			Terminal.ecrireStringln("Vous voulez prendre aux Deuxi�me tour ? (o,n)");
			rep = Terminal.lireString();
			
			if (rep.equals("o")) {
				Terminal.ecrireStringln("Vous voulez prendre a quel couleur ? (Coeur,Pique,Trefle,Carreau)");
				rep = Terminal.lireString();
				this.recoitVal = 1;
				if (rep.equals(CouleurEnum.Coeur.toString())) {
					this.choixAtout = CouleurEnum.Coeur;
				}
				if (rep.equals(CouleurEnum.Carreau.toString())) {
					this.choixAtout = CouleurEnum.Carreau;
				}
				if (rep.equals(CouleurEnum.Trefle.toString())) {
					this.choixAtout = CouleurEnum.Trefle;
				}
				if (rep.equals(CouleurEnum.Pique.toString())) {
					this.choixAtout = CouleurEnum.Pique;
				}
			} else {
				this.recoitVal = 0;
			}
			break;
		default:
			// boucle de teste sur les 4 getCouleur()s pour l'ordinateur
			for (int i = 0; i < 4; i++) {
				totalpoint = 0;
				nbAtoutCouleurPrecedente = nbAtoutCouleurCourante;
				nbAtoutCouleurCourante = 0;
				hasValetCouleurPrecedente = hasValetCouleurCourante;
				hasNeufCouleurPrecedente = hasNeufCouleurCourante;
				hasValetCouleurCourante = this.main.hasValet(coulAtout[i]);
				hasNeufCouleurCourante = this.main.hasNeuf(coulAtout[i]);

				
				// on compte le nombre d'atout et on ajoute la carte retournee
				nbAtoutCouleurCourante = this.main.getNbAtout(coulAtout[i])+1;
				
				//on compte le nombre de points de la main du joueur
				totalpoint = Arbitre.pointsMain(main, coulAtout[i]);
				
				// ajout des points de la carte du milieu
				totalpoint += Arbitre.points(carteRetournee, carteRetournee.getCouleur());
				
				// ajout des points de la carte du milieu
				totalpoint += Arbitre.points(carteRetournee, carteRetournee.getCouleur());

				if (prendreDeuxiemeTour(hasValetCouleurCourante, hasNeufCouleurCourante,totalpoint, pointInt, nbAtoutCouleurCourante)) {
					pointInt = totalpoint;
					atoutret = (CouleurEnum) coulAtout[i];
				}

				Terminal.ecrireStringln("Joueur " + numeroJoueur + " pour couleur " + ((CouleurEnum) coulAtout[i]).toString() + " : ");
				Terminal.ecrireStringln("Nombre d'atouts : "
						+ nbAtoutCouleurCourante + ", Valet : "
						+ hasValetCouleurCourante + " Neuf : "
						+ hasNeufCouleurCourante + ", points : " + totalpoint);
			}
			
			
			// test pour la prise
			if (pointInt >= 40) {
				this.recoitVal = 1;
				this.choixAtout = atoutret;
				for (int i = 0; i < 7; i++) {
					System.out.println("carte : " + this.main.get(i));
				}

				Terminal.ecrireStringln("Le joueur N" + numeroJoueur + " a pris a la couleur " + atoutret);
			} else {
				this.recoitVal = 0;
			}
			break;
		}
	}

	private boolean prendreDeuxiemeTour(boolean hasValetCouleurCourante,
			boolean hasNeufCouleurCourante, int totalpoint, int pointInt,
			int nbAtoutCouleurCourante) {
		return pointInt < totalpoint 
				&& ((nbAtoutCouleurCourante < 4 && hasValetCouleurCourante && hasNeufCouleurCourante)
						|| (nbAtoutCouleurCourante > 3 && nbAtoutCouleurCourante < 5 && hasValetCouleurCourante) 
						|| (nbAtoutCouleurCourante >= 5));
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// Trie le jeux de carte
	public void trijeux(CouleurEnum jeuxAtout) {
		int pos = 0;
		Carte Paquettampon1[] = new Carte[8];

		String[] couleurtri = { "Coeur", "Trefle", "Carreau", "Pique" };
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 8; y++) {
				if (couleurtri[x].equals(this.main.get(y).getCouleur().toString())) {
					Paquettampon1[pos] = this.main.get(y);
					pos++;
				}
			}
		}
		for (int y = 0; y < 8; y++) {
			this.main.set(y, Paquettampon1[y]);
		}
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// joue premier
	public Carte jouepremier(int numeroJoueur, Carte card, CouleurEnum carteret, CouleurEnum coulatout) {

		/* AFFICHAGE */
		System.out.println("Methode jouepremier : \nParametres :");
		System.out.println(" carte : " + card + ", couleur demandee : " + carteret + ", couleur atout : " + coulatout);
		System.out.println("Affichage de la main du joueur " + numeroJoueur);
		for (int i = 0; i < this.main.size(); i++) {
			System.out.println(this.main.get(i));
		}
		/* FIN AFFICHAGE */

		Carte renvoijouee = this.main.get(0);
		int rep;
		switch (numeroJoueur) {
		case 0:
			int n = 20;
			while (n != 0) {
				Terminal.ecrireStringln("Quel Numero de carte jouez vous?");
				try {
					rep = Terminal.lireInt();
					rep--;
					if (rep >= 0 && rep <= 7) {
						renvoijouee = this.main.get(rep);
						if (Arbitre.testcartejouee(this.main, carteret, renvoijouee, coulatout)) {
							for (int v = rep; v < 8; v++) {
								if (v + 1 < 8) {
									//TODO risque de lecture/ecriture sur la meme liste
									this.main.set(v, this.main.get(v+1));
								} else {
									this.main.set(v, card);
								}
							}
							n = 0;
						}
					}
				} catch (TerminalException e) {
					Terminal.ecrireStringln("erreur de saisie");
				}
			}
			break;
		default:
			rep = Arbitre.testcartejouee2(this.main, carteret, coulatout);
			Terminal.ecrireStringln("Le joueur choisi de jouer la carte n�"
					+ rep + " : " + this.main.get(rep));
			renvoijouee = this.main.get(rep);

			for (int i = rep; i < 8; i++) {
				if (i + 1 < 8) {
					//TODO risque de lecture/ecriture sur la meme liste
					this.main.set(i, this.main.get(i+1));
				} else {
					this.main.set(i,card);
				}

			}
			break;
		}
		return renvoijouee;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public void afficherMain() {
		Terminal.ecrireStringln("Affichage main : ");
		for (int i = 0; i < this.main.size(); i++) {
			Terminal.ecrireStringln(this.main.get(i).toString());
		}
	}


	public int getRecoitval() {
		return this.recoitVal;
	}

	public CouleurEnum getChoixatout() {
		return choixAtout;
	}

	public List<Carte> getMain() {
		//return main;
		return main.getMain();
	}
}