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

import gui.Terminal;

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
		this.joueurPrend = null;
		while (equipes.get(0).getScorePartie() < 1000 && equipes.get(1).getScorePartie() < 1000) {
			switch (etat) {
			case PremiereDistribution:
				Terminal.ecrireStringln("Donneur : " + this.joueurDonneur);
				this.joueurCourant = table.joueurSuivant(this.joueurDonneur);
				distribuer(3);
				distribuer(2);
				this.table.retournerUneCarte();
				etat = EtatPartieEnum.PremierTourDonne;
				break;
			case PremierTourDonne:
				this.joueurPrend = quiPrendPremiereDonne();
				if (this.joueurPrend == null) {
					etat = EtatPartieEnum.DeuxiemeTourDonne;
				} else {
					this.table.attribuerCarteRetourneeA(this.joueurPrend);
					etat = EtatPartieEnum.DeuxiemeDistribution;
				}
				break;
			case DeuxiemeTourDonne:
				this.joueurPrend = quiPrendDeuxiemeDonne();
				if (this.joueurPrend == null) {
					// Personne n'a pris, on re-distribue les cartes
					// on récupère les cartes
					recupererCartesMain();
					this.table.remettreCarteRetourneeDansLePaquet();
					// on mélange
					this.table.getTas().melanger(50);
					etat = EtatPartieEnum.PremiereDistribution;
				} else {
					this.table.attribuerCarteRetourneeA(this.joueurPrend); //TODO ça devrait aller dans distribuerDeuxiemeTour ou au moins dans le case DeuxiemeDistribution
					Terminal.ecrireStringln("----FIN DE LA DONNE-----\n"
							+ this.joueurPrend.toString() + " a pris à : "
							+ this.table.getCouleurAtout());
					etat = EtatPartieEnum.DeuxiemeDistribution;
				}
				break;
			case DeuxiemeDistribution:
				distribuerDeuxiemeTour(this.joueurPrend);
				this.joueurCourant = table.joueurSuivant(joueurDonneur);
				etat = EtatPartieEnum.PhaseDePli;
				break;
			case PhaseDePli:
				jouerUnPli();
				if(this.joueurCourant.getMain().getTailleMain() == 0){
					/***************** FIN DE LA MANCHE *****************/
					determinerScoreMancheCourante();
					this.joueurPrend = null;
					remettreLesPlisDansLeTas();
					this.table.reinitialiserLesAnnonces();
					this.joueurDonneur.coupe(this.table.getTas());
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
	//TODO gros copier coller, ya moyen d'optimiser ça mais ça marche :)
	private void determinerScoreMancheCourante(){
		Terminal.ecrireStringln("-----FIN DE MANCHE-----\nRecapitualtif des scores:");
		Terminal.ecrireStringln("Joueur prenant: " + this.joueurPrend);
		Equipe e1 = this.equipes.get(0);
		Equipe e2 = this.equipes.get(1);
		
		for (Joueur joueur : e1.getJoueurs()) {
			// voir si capo
			if(e1.getScoreManche() == 0) {
				e1.setScoreManche(0);
				e2.setScoreManche(250);
			}
			
			// voir si dedans
			if(joueur.equals(this.joueurPrend) && e1.getScoreManche() < 81 && e1.getScoreManche() > 0) {
				e1.setScoreManche(0);
				e2.setScoreManche(162);
			}
			
			// voir si belote et re et si annoncées
			if (this.table.isBeloteAnnoncee() && this.table.isRebeloteAnnoncee() && joueur.hasBeloteEtRe()){
				// pour l'équipe qui a annoncé la belote : +20 points 	
				e1.setScoreManche(e1.getScoreManche() + 20);		
			}
		}
		for (Joueur joueur : e2.getJoueurs()) {
			// voir si capo
			if(e2.getScoreManche() == 0) {
				e2.setScoreManche(0);
				e1.setScoreManche(250);
			}
						
			// voir si le contrat est fait
			if(joueur.equals(this.joueurPrend) && e2.getScoreManche() < 81  && e2.getScoreManche() > 0) {
				e2.setScoreManche(0);
				e1.setScoreManche(162);
			}
			
			// voir si belote et re et si annoncées
			if (this.table.isBeloteAnnoncee() && this.table.isRebeloteAnnoncee() && joueur.hasBeloteEtRe()){
				// pour l'équipe qui a annoncé la belote : +20 points 	
				e2.setScoreManche(e2.getScoreManche() + 20);		
			}
		}
		
		// ajouter le score dans l'historique des score de manches de l'equipe
		e1.addScoreMancheToHistorique(e1.getScoreManche());
		e2.addScoreMancheToHistorique(e2.getScoreManche());
		
		// maj du score de la partie pour l'equipe
		e1.setScorePartie(e1.getScorePartie() + e1.getScoreManche());
		Terminal.ecrireStringln(e1 
				+ "\n   Score manche: " + e1.getScoreManche() 
				+ "\n   Score partie: " + e1.getScorePartie());
		e2.setScorePartie(e2.getScorePartie() + e2.getScoreManche());
		Terminal.ecrireStringln(e2 
				+ "\n   Score manche: " + e2.getScoreManche() 
				+ "\n   Score partie: " + e2.getScorePartie());
		
		// reset le score de la manche courante pour l'equipe
		e1.setScoreManche(0);
		e2.setScoreManche(0);
	}

	
	/**
	 * On remet les cartes du plis dans le tas.
	 */
	private void remettreLesPlisDansLeTas(){
		for(Equipe equipe : this.equipes){
			table.getTas().reposerListeCartes(equipe.rendreLesCartesDesPlisRemporter());
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
		Carte carteJouee = null;
		while (this.table.getPliCourant().size() < 4) {
			// Au premier tour avant de jouer quoi que ce soit, on analyse son jeu (juste pour voir les annonces belote et re pour le moment)
			if(this.joueurCourant.getMain().getTailleMain() == 8) this.joueurCourant.analyserSonJeu();
			carteJouee = joueurCourant.jouerPli();

			this.table.jouerCarte(carteJouee,joueurCourant);
			joueurCourant = table.joueurSuivant(joueurCourant);
		}
		Terminal.ecrireStringln("-------PLI FINI------\nJoueurMaitre : " + this.table.getPliCourant().getJoueurMaitre());
		getEquipe(this.table.getPliCourant().getJoueurMaitre()).ajouterPliRemporte(this.table.getPliCourant());
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
			Terminal.ecrireStringln("---------PREMIERE DONNE----------\nJoueur courant : "
					+ this.joueurCourant + "\nCarte retournée:" + this.table.getCarteRetournee());
			if (joueurCourant.prendPremiereDonne()) {
				joueurPrend = joueurCourant;
				this.table.setCouleurAtout(this.table.getCarteRetournee().getCouleur());
				Terminal.ecrireStringln(joueurCourant + " PREND");
			} else {
				Terminal.ecrireStringln(joueurCourant + " PASSE");
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
			Terminal.ecrireStringln("---------DEUXIEME DONNE----------\nJoueur courant : "
					+ joueurCourant);
			this.table.setCouleurAtout(joueurCourant.prendDeuxiemeDonne());
			Terminal.ecrireStringln("\ncouleur Choisie :" + this.table.getCouleurAtout());
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
			if (equipe.estDansEquipe(joueur)) {
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
				this.joueurCourant.getMain().ajouter(table.getTas().retirerPremiereCarte(), this.table.getCouleurAtout());
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
						table.getTas().retirerPremiereCarte(),
						this.table.getCouleurAtout());
			}
			if (this.joueurCourant != joueurPrend) { // voir .equals()
				this.joueurCourant.getMain().ajouter(table.getTas().retirerPremiereCarte(), this.table.getCouleurAtout());
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
