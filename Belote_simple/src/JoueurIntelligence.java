/**
 * @class JoueurProgramme
 * @author lacertus, Nathan
 * @resume represente l'IA d'un joueur de belote
 * 
 * */

public class JoueurIntelligence {

	private Carte monPaquet[] = new Carte[8];
	private int recoitVal = 0;
	private CouleurEnum choixAtout;


	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public void affichejoueur(int x) {
		for (int i = 0; i < Fenetre.nbcartej[x]; i++) {
			Terminal.ecrireStringln("valeur jeux joueur " + x + " carte n " + i + " valeur " + this.monPaquet[i].getFigure() + " " + this.monPaquet[i].getCouleur());
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
		this.monPaquet[y] = tabCards[0];
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
		boolean hasValet = false, hasNeuf = false;
		int totalpoint = 0, nbAtout = 0;

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
			for (int i = 0; i < nbcarte; i++) {

				/*
				 * on regarde le nombre d'atout qu'il possede et s'il a le valet
				 * et 9
				 */
				if (this.monPaquet[i].getCouleur().equals(carteRetournee.getCouleur())) {
					if (this.monPaquet[i].getFigure().equals(FigureEnum.Valet) || carteRetournee.getFigure().equals(FigureEnum.Valet)) {
						hasValet = true;
					}
					if (this.monPaquet[i].getFigure().equals(FigureEnum.Neuf) || carteRetournee.getFigure().equals(FigureEnum.Neuf)) {
						hasNeuf = true;
					}
					nbAtout++;
				}
				totalpoint += Arbitre.Points(this.monPaquet[i], carteRetournee.getCouleur());
			}

			// ajout des points de la carte du milieu
			totalpoint += Arbitre.Points(carteRetournee, carteRetournee.getCouleur());
			// on ajoute la carte retournee a notre nombre d'atouts
			nbAtout++;
			/*
			 * on prend si : - on a 40 pts et moins de 4 atouts avec le valet et
			 * le neuf - on a 40pts et plus de 3 atouts avec le valet - on a
			 * 40pts et plus de 5 atouts
			 */
			Terminal.ecrireStringln("Joueur " + numeroJoueur + " : ");
			Terminal.ecrireStringln("Nombre d'atouts : " + nbAtout + ", Valet : " + hasValet + " Neuf : " + hasNeuf + ", points : " + totalpoint);
			if (totalpoint >= 40 && ((nbAtout < 4 && hasValet && hasNeuf) || (nbAtout > 3 && nbAtout < 5 && hasValet) || (nbAtout >= 5))) {
				Terminal.ecrireStringln("Jeu du joueur " + numeroJoueur + " qui a pris : ");
				for (int i = 0; i < 7; i++) {
					Terminal.ecrireStringln("carte : " + monPaquet[i]);
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

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// gestion pour prendre au deuxieme tour
	public void prenddeuxieme(int numeroJoueur, Carte carteRetournee, int nbcarte) {
		String rep = new String();
		CouleurEnum atoutret = CouleurEnum.NotInitialized;
		
		Object[] coulAtout = new Object[4];
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
				hasValetCouleurCourante = false;
				hasNeufCouleurCourante = false;

				for (int j = 0; j < nbcarte; j++) {
					// on regarde les atouts
					if (this.monPaquet[j].getCouleur().equals(((CouleurEnum) coulAtout[i]))) {
						if (this.monPaquet[j].getFigure().equals(FigureEnum.Valet)) {
							hasValetCouleurCourante = true;
						}
						if (this.monPaquet[j].getFigure().equals(FigureEnum.Neuf)) {
							hasNeufCouleurCourante = true;
						}
						nbAtoutCouleurCourante++;
					}
					totalpoint += Arbitre.Points(this.monPaquet[j], (CouleurEnum) coulAtout[i]);
				}
				// ajout des points de la carte du milieu
				totalpoint += Arbitre.Points(carteRetournee, (CouleurEnum) coulAtout[i]);

				if (pointInt < totalpoint 
						&& ((nbAtoutCouleurCourante < 4 && hasValetCouleurCourante && hasNeufCouleurCourante)
								|| (nbAtoutCouleurCourante > 3 && nbAtoutCouleurCourante < 5 && hasValetCouleurCourante) 
								|| (nbAtoutCouleurCourante >= 5))) {
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
					System.out.println("carte : " + monPaquet[i]);
				}

				Terminal.ecrireStringln("Le joueur N" + numeroJoueur + " a pris a la couleur " + atoutret);
			} else {
				this.recoitVal = 0;
			}
			break;
		}
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
				if (couleurtri[x].equals(this.monPaquet[y].getCouleur().toString())) {
					Paquettampon1[pos] = this.monPaquet[y];
					pos++;
				}
			}
		}
		for (int y = 0; y < 8; y++) {
			this.monPaquet[y] = Paquettampon1[y];
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
		for (int i = 0; i < monPaquet.length; i++) {
			System.out.println(monPaquet[i]);
		}
		/* FIN AFFICHAGE */

		Carte renvoijouee = this.monPaquet[0];
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
						renvoijouee = this.monPaquet[rep];
						if (Arbitre.testcartejouee(this.monPaquet, carteret, renvoijouee, coulatout)) {
							for (int v = rep; v < 8; v++) {
								if (v + 1 < 8) {
									this.monPaquet[v] = this.monPaquet[v + 1];
								} else {
									this.monPaquet[v] = card;
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
			rep = Arbitre.testcartejouee2(this.monPaquet, carteret, coulatout);
			Terminal.ecrireStringln("Le joueur choisi de jouer la carte n�"
					+ rep + " : " + this.monPaquet[rep]);
			renvoijouee = this.monPaquet[rep];

			for (int i = rep; i < 8; i++) {
				if (i + 1 < 8) {
					this.monPaquet[i] = this.monPaquet[i + 1];
				} else {
					this.monPaquet[i] = card;
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
		for (int i = 0; i < monPaquet.length; i++) {
			Terminal.ecrireStringln(monPaquet[i].toString());
		}
	}

	public Carte[] getMonPaquet() {
		return monPaquet;
	}

	public int getRecoitval() {
		return recoitVal;
	}

	public CouleurEnum getChoixatout() {
		return choixAtout;
	}
}