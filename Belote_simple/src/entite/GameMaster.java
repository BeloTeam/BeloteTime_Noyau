/*
 * Copyright 2014 BeloTeam
 * 
 * This file is part of BeloteTime.
 *	
 * BeloteTime is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeloteTime is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeloteTime.  If not, see <http://www.gnu.org/licenses/>. 
 */

package entite;

import java.util.ArrayList;

import noyau.classesMetier.Carte;
import noyau.classesMetier.EtatPartieEnum;
import noyau.classesMetier.TableDeJeu;

/**
 * Le maître du jeu s'occupe du déroulement de la partie.
 * @author BeloTeam
 * @version 1.0
 **/
public class GameMaster {
	private ArrayList<Equipe> equipes;
	private TableDeJeu table;
	private Joueur joueurCourant;
	private Joueur joueurDonneur;
	private Joueur joueurPrend;
	private EtatPartieEnum etat;

	/**
	 * Constructeur de GameMaster.
	 * @param joueurs Les 4 joueurs de belote
	 * @param table	La table de jeu	
	 */
	public GameMaster(Joueur joueurs[], TableDeJeu table) {
		this.equipes = new ArrayList<>();
		equipes.add(new Equipe(joueurs[0], joueurs[1]));
		equipes.add(new Equipe(joueurs[2], joueurs[3]));
		this.table = table;
		this.joueurDonneur = getDonneurRandom();
		etat = EtatPartieEnum.PremiereDistribution;
	}

	/**
	 * Lancement de la partie.
	 */
	public void debuterPartie() {
		joueurPrend = null;
		while (equipes.get(0).getScore() < 1000 && equipes.get(1).getScore() < 1000) {
			switch (etat) {
			case PremiereDistribution:
				System.out.println("Donneur : " + this.joueurDonneur);
				this.joueurCourant = table.joueurSuivant(this.joueurDonneur);
				distribuer(3);
				distribuer(2);
				this.table.montrerCarteDonne();
				etat = EtatPartieEnum.PremierTourDonne;
				break;
			case PremierTourDonne:
				joueurPrend = quiPrendPremiereDonne();
				if (joueurPrend == null) {
					etat = EtatPartieEnum.DeuxiemeTourDonne;
				} else {
					this.table.attribuerCarteDonneA(joueurPrend);
					etat = EtatPartieEnum.DeuxiemeDistribution;
				}
				break;
			case DeuxiemeTourDonne:
				joueurPrend = quiPrendDeuxiemeDonne();
				if (joueurPrend == null) {
					// Personne n'a pris, on re-distribue les cartes
					// on récupère les cartes
					recupererCartesMain();
					this.table.remettreCarteDonneDansLeTas();
					// on mélange
					this.table.getTas().melanger(50);
					etat = EtatPartieEnum.PremiereDistribution;
				} else {
					this.table.attribuerCarteDonneA(joueurPrend); //TODO ça devrait aller dans distribuerDeuxiemeTour ou au moins dans le case DeuxiemeDistribution
					System.out.println("----FIN DE LA DONNE-----\n"
							+ joueurPrend.toString() + " a pris à : "
							+ this.table.getCouleurAtout());
					etat = EtatPartieEnum.DeuxiemeDistribution;
				}
				break;
			case DeuxiemeDistribution:
				distribuerDeuxiemeTour(joueurPrend);
				this.joueurCourant = table.joueurSuivant(joueurDonneur);
				joueurPrend = null;
				etat = EtatPartieEnum.PhaseDePli;
				break;
			case PhaseDePli:
				jouerUnPli();
				if(this.joueurCourant.getMain().getTailleMain() == 0){
					calculerScoreDeLaManche();
					remettreLesPlisDansLeTas();
					this.joueurDonneur = table.joueurSuivant(this.joueurDonneur);
					etat = EtatPartieEnum.PremiereDistribution;
				} else {
					etat = EtatPartieEnum.PhaseDePli;
				}
				break;
			}
		}
	}

	/**
	 * Permet de calculer le score de la manche pour chaque équipe.
	 */
	private void calculerScoreDeLaManche(){
		System.out.println("-----FIN DE MANCHE-----\nRecapitualtif des scores:");
		for(Equipe equipe : this.equipes){
			equipe.calculerScoreFinDeManche();
			System.out.println(equipe+ "\n"+equipe.getPointsDesManches());
		}
	}

	
	/**
	 * On remet les cartes du plis dans le tas.
	 */
	private void remettreLesPlisDansLeTas(){
		for(Equipe equipe : this.equipes){
			table.getTas().reposerDesCartes(equipe.rendreLesCartesDesPlisRemporter());
		}
	}

	/**
	 * Selectionne un des joueurs pour être le donneur
	 * @return Le donneur
	 */
	private Joueur getDonneurRandom(){
		int indiceJoueurDonneur = ((int) (Math.random() * 4));
		return equipes.get(indiceJoueurDonneur % 2).getJoueurs().get(indiceJoueurDonneur / 2);
	}

	/**
	 * Jouer un pli.
	 */
	private void jouerUnPli(){
		if(this.joueurCourant.getMain().getTailleMain() == 1){
			this.table.nouveauPliCourant(true);
		} else {
			this.table.nouveauPliCourant(false);
		}
		Carte carteJouer = null;
		while (this.table.getPliCourant().getTaillePaquet() < 4) {
			carteJouer = joueurCourant.jouerPli();
			this.table.jouerCarte(carteJouer,joueurCourant);
			joueurCourant = table.joueurSuivant(joueurCourant);
		}
		System.out.println("-------PLI FINI------\nJoueurMaitre : " + this.table.getPliCourant().getJoueurMaitre());
		getEquipe(this.table.getPliCourant().getJoueurMaitre()).ajouterUnpliRemportee(this.table.getPliCourant());
		joueurCourant = this.table.getPliCourant().getJoueurMaitre();
	}

	/**
	 * Permet de récupérer les cartes dans la main des joueurs.
	 */
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

	/**
	 * Premier tour de table pour proposer aux joueurs de prendre à l'atout la carte retournée.
	 * @return Renvoit le jouer qui a pris, null le cas échéant.
	 */
	private Joueur quiPrendPremiereDonne() {
		Joueur joueurPrend = null;
		int i = 0;
		// tend que personne n'a pris ET que tout le monde n'as pas été interrogé
		while (joueurPrend == null && i < 4) {
			System.out.println("---------PREMIERE DONNE----------\n"
					+ this.joueurCourant + "\nAtout:" + this.table.getCarteDonne());
			if (joueurCourant.prendPremiereDonne()) {
				joueurPrend = joueurCourant;
				this.table.setCouleurAtout(this.table.getCarteDonne().getCouleur());
				System.out.println(joueurCourant + " PREND");
			} else {
				System.out.println(joueurCourant + " PASSE");
			}
			i++;
			joueurCourant = table.joueurSuivant(joueurCourant);
		}
		return joueurPrend;
	}

	/**
	 * Deuxieme tour de table pour proposer aux joueurs de prendre à l'atout à une autre couleur.
	 * @return Renvoit le jouer qui a pris, null le cas échéant.
	 */
	private Joueur quiPrendDeuxiemeDonne() {
		this.table.setCouleurAtout(null);
		Joueur joueurPrend = null;
		int i = 0;
		while (this.table.getCouleurAtout() == null && i < 4) {
			System.out.println("---------DEUXIEME DONNE----------\n"
					+ joueurCourant);
			this.table.setCouleurAtout(joueurCourant.prendDeuxiemeDonne());
			System.out.println("\ncouleur Choisie :" + this.table.getCouleurAtout());
			if (this.table.getCouleurAtout() == null) {
				joueurCourant = table.joueurSuivant(joueurCourant);
			} else {
				joueurPrend = joueurCourant;
			}
			i++;
		}
		return joueurPrend;
	}

	/**
	 * Retourne l'équipe d'un joueur donné.
	 * @param joueur
	 * @return L'équipe du joueur donné
	 */
	private Equipe getEquipe(Joueur joueur) {
		for (Equipe equipe : equipes) {
			if (equipe.estDansLEquipe(joueur)) {
				return equipe;
			}
		}
		return null;
	}

	/** 
	 * Permet de distribuer les cartes du paquet aux joueurs.
	 * @param nbCarte nombre de cartes à distribuer
	 */
	private void distribuer(int nbCarte) {
		Joueur premierJoueur = this.joueurCourant;
		do {
			for (int i = 0; i < nbCarte; i++) {
				this.joueurCourant.getMain().ajouter(
						table.getTas().retirerCarteDessusPaquet(),
						this.table.getCouleurAtout());
			}
			this.joueurCourant = table.joueurSuivant(this.joueurCourant);
		} while (this.joueurCourant != premierJoueur);
	}

	/**
	 * Distribue le reste des cartes. 2 cartes pour celui qui a pris à l'atout et 3 cartes pour les autres. 
	 * @param joueurPrend joueur ayant pris a l'atout
	 */
	private void distribuerDeuxiemeTour(Joueur joueurPrend) {
		Joueur premierJoueur = table.joueurSuivant(joueurDonneur);
		this.joueurCourant = table.joueurSuivant(joueurDonneur);
		int nbCarte = 2;
		do {
			for (int i = 0; i < nbCarte; i++) {
				this.joueurCourant.getMain().ajouter(
						table.getTas().retirerCarteDessusPaquet(),
						this.table.getCouleurAtout());
			}
			if (this.joueurCourant != joueurPrend) { // voir .equals()
				this.joueurCourant.getMain().ajouter(table.getTas().retirerCarteDessusPaquet(), this.table.getCouleurAtout());
			}
			this.joueurCourant = table.joueurSuivant(this.joueurCourant);
		} while (this.joueurCourant != premierJoueur);
	}

	/**
	 * Retourne les 2 équipes de la partie.
	 * @return ArrayList<Equipe>
	 */
	public ArrayList<Equipe> getEquipes() {
		return equipes;
	}
}
