package entite;

import java.util.ArrayList;

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.Pli;
import noyau.classesMetier.TableDeJeu;

public class GameMaster {
	private ArrayList<Equipe> equipes;
	private TableDeJeu table;
	private Joueur joueurCourant;
	private Joueur joueurDonneur;
	private Pli cartesDuPLiCourant;
	private CouleurEnum couleurAtout;

	public GameMaster(Joueur joueurs[], TableDeJeu table) {
		this.equipes = new ArrayList<>();
		equipes.add(new Equipe(joueurs[0], joueurs[1]));
		equipes.add(new Equipe(joueurs[2], joueurs[3]));
		this.table = table;
	}

	public void debuterPartie() {
		int indiceJoueurDonneur = ((int) (Math.random() * 4));
		this.joueurDonneur = equipes.get(indiceJoueurDonneur % 2).getJoueurs().get(indiceJoueurDonneur / 2);
		Joueur joueurPrend = null;
		while (equipes.get(0).getScore() < 1000 && equipes.get(1).getScore() < 1000) {
			Carte carteRetournee = null;
			// PHASE DE DONNE
			while (joueurPrend == null) {
				joueurPrend=null; // TODO redondance utile ?
				System.out.println("Donneur : " + this.joueurDonneur);
				this.joueurCourant = table.joueurSuivant(this.joueurDonneur);
				distribuer(3);
				distribuer(2);
				carteRetournee = table.getTas().retirerCarteDessusPaquet();
				// PremiereDonne
				joueurPrend = quiPrendPremiereDonne(carteRetournee);
				if (joueurPrend == null) {
					// DeuxiemeDonne
					joueurPrend = quiPrendDeuxiemeDonne();
				}
				// si personne n'a pris
				if (joueurPrend == null) {

					System.out.println("-----PERSONNE N'A PRIS-----\nAvant recuperation : "
									+ table.getTas().getTaillePaquet()
									+ " cartes dans le tas");
					// Personne n'a pris, on re-distribue les cartes
					// on récupère les cartes
					recupererCartesMain();
					this.table.getTas().ajouter(carteRetournee);
					// on mélange
					this.table.getTas().melanger(50);
					System.out.println("Apres recuperation : "
							+ table.getTas().getTaillePaquet()
							+ " cartes dans le tas");
				}
			}

			System.out.println("----FIN DE LA DONNE-----\n"
					+ joueurPrend.toString() + " a pris à : "
					+ this.couleurAtout);
			joueurPrend.getMain().ajouter(carteRetournee, carteRetournee.getCouleur());
			
			/*this.joueurCourant = joueurPrend;
			joueurPrend = null;
			distribuerDeuxiemeTour();*/
			
			distribuerDeuxiemeTour(joueurPrend);
			this.joueurCourant = table.joueurSuivant(joueurDonneur);
			joueurPrend = null;

			// PHASE DES PLIS
			while (this.joueurCourant.getMain().getTaillePaquet() > 0) {
				if(this.joueurCourant.getMain().getTaillePaquet() == 1){
					this.cartesDuPLiCourant = new Pli(true, false, false);
				} else {
					this.cartesDuPLiCourant = new Pli();
				}
				Carte carteJouer = null;
				while (this.cartesDuPLiCourant.getTaillePaquet() < 4) {
					carteJouer = joueurCourant.jouerPli(this.cartesDuPLiCourant);
					this.cartesDuPLiCourant.ajouter(carteJouer,joueurCourant, couleurAtout);
					joueurCourant = table.joueurSuivant(joueurCourant);
				}
				System.out.println("-------PLI FINI------\nJoueurMaitre : " + this.cartesDuPLiCourant.getJoueurMaitre());
				getEquipe(this.cartesDuPLiCourant.getJoueurMaitre()).ajouterUnpliRemportee(this.cartesDuPLiCourant);
				joueurCourant = this.cartesDuPLiCourant.getJoueurMaitre();
			}
			//MancheFini
			System.out.println("-----FIN DE MANCHE-----\nRecapitualtif des scores:");
			for(Equipe equipe : this.equipes){
				equipe.calculerScoreFinDeManche(couleurAtout);
				System.out.println(equipe+ "\n"+equipe.getPointsDesManches());
				table.getTas().reposerDesCartes(equipe.rendreLesCartesDesPlisRemporter());
			}
			this.joueurDonneur = table.joueurSuivant(this.joueurDonneur);
		}
	}
	private void recupererCartesMain() {
		Joueur premierJoueur = this.joueurCourant;
		do {
			for (Carte carte : joueurCourant.getMain().hashtableToList()) {
				this.table.getTas().ajouter(carte);
				joueurCourant.getMain().supprimer(carte);
			}
			this.joueurCourant = table.joueurSuivant(this.joueurCourant);
		} while (this.joueurCourant != premierJoueur);
	}

	private Joueur quiPrendPremiereDonne(Carte carteRetournee) {
		Joueur joueurPrend = null;
		int i = 0;
		// tend que personne n'a pris ET que tout le monde n'as pas Ã©tÃ©
		// interroger
		while (joueurPrend == null && i < 4) {
			System.out.println("---------PREMIERE DONNE----------\n"
					+ joueurCourant + "\nAtout:" + carteRetournee);
			if (joueurCourant.prendPremiereDonne(carteRetournee)) {
				joueurPrend = joueurCourant;
				this.couleurAtout = carteRetournee.getCouleur();
				System.out.println(joueurPrend + " PREND");
			} else {
				System.out.println(joueurPrend + " PASSE");
			}
			i++;
			joueurCourant = table.joueurSuivant(joueurCourant);
		}
		return joueurPrend;
	}

	private Joueur quiPrendDeuxiemeDonne() {
		couleurAtout = null;
		Joueur joueurPrend = null;
		int i = 0;
		while (couleurAtout == null && i < 4) {
			System.out.println("---------DEUXIEME DONNE----------\n"
					+ joueurCourant);
			couleurAtout = joueurCourant.prendDeuxiemeDonne();
			System.out.println("\ncouleur Choisie :" + couleurAtout);
			if (couleurAtout == null) {
				joueurCourant = table.joueurSuivant(joueurCourant);
			} else {
				joueurPrend = joueurCourant;
			}
			i++;
		}
		return joueurPrend;
	}

	private Equipe getEquipe(Joueur joueur) {
		for (Equipe equipe : equipes) {
			if (equipe.estDansLEquipe(joueur)) {
				return equipe;
			}
		}
		return null;
	}

	private void distribuer(int nbCarte) {
		Joueur premierJoueur = this.joueurCourant;
		do {
			for (int i = 0; i < nbCarte; i++) {
				this.joueurCourant.getMain().ajouter(
						table.getTas().retirerCarteDessusPaquet(),
						this.couleurAtout);
			}
			this.joueurCourant = table.joueurSuivant(this.joueurCourant);
		} while (this.joueurCourant != premierJoueur);
	}


	private void distribuerDeuxiemeTour(Joueur joueurPrend) {
		Joueur premierJoueur = table.joueurSuivant(joueurDonneur);
		this.joueurCourant = table.joueurSuivant(joueurDonneur);
		int nbCarte = 2;
		do {
			for (int i = 0; i < nbCarte; i++) {
				this.joueurCourant.getMain().ajouter(
						table.getTas().retirerCarteDessusPaquet(),
						this.couleurAtout);
			}
			if (!this.joueurCourant.equals(joueurPrend)) { // voir ==
				this.joueurCourant.getMain().ajouter(table.getTas().retirerCarteDessusPaquet(), this.couleurAtout);
			}
			this.joueurCourant = table.joueurSuivant(this.joueurCourant);
		} while (this.joueurCourant != premierJoueur);
	}
}
