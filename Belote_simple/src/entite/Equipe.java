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
import noyau.classesMetier.Pli;

/**
 * Une equipe est composée de deux joueurs, d'un historique de score de manche
 * et d'un score de partie.
 * 
 * @author BeloTeam
 * @version 1.0
 **/
public class Equipe {
	private ArrayList<Joueur> joueurs;
	private ArrayList<Integer> historiqueScoreManches;
	private ArrayList<Pli> plisRemportee;
	private int scoreManche;
	private int scorePartie;
	private int nbManche;
	
	/**
	 * Constructeur d'une equipe
	 * @param Joueur le joueur 1
	 * @param Joueur le joueur 2
	 * */
	public Equipe(Joueur joueur1, Joueur joueur2) {
		this.joueurs = new ArrayList<>();
		this.joueurs.add(joueur1);
		this.joueurs.add(joueur2);
		this.historiqueScoreManches = new ArrayList<>();
		this.plisRemportee = new ArrayList<Pli>();
		this.scorePartie = 0;
		this.scoreManche = 0;
		this.nbManche = 0;
	}

	/**
	 * Surchage de la méthode equals d'Object
	 * @return boolean
	 */
	public boolean equals(Object equipe) {
		if (equipe.getClass() == this.getClass()) {
			if (this.joueurs.get(0).equals(((Equipe) equipe).joueurs.get(0))
					&& this.joueurs.get(1).equals(
							((Equipe) equipe).joueurs.get(1))) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * Surcharge de la méthode toString d'Object
	 * @return String
	 */
	public String toString() {
		return "Equipe de : " + this.getJoueurs();
	}
	
	/* **** Méthodes manipulant les joueurs de l'équipe **** **/
	/**
	 * Retourne la liste des membres de l'équipe
	 * @return ArrayList<Joueur>
	 */
	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * Retourne si oui ou non un joueur est membre de l'équipe
	 * @param Joueur le joueur à tester
	 * @return ArrayList<Joueur>
	 */
	public boolean estDansEquipe(Joueur joueur) {
		return joueurs.contains(joueur);
		/*for (Joueur j : joueurs) {
			if (j.equals(joueur)) {
				return true;
			}
		}
		return false;*/
	}

	/**
	 * Retourne le partenaire d'un joueur de l'equipe
	 * @return Joueur
	 */
	public Joueur getPartenaire(Joueur j) {
		if (this.estDansEquipe(j)) {
			if (this.joueurs.get(0) != j) {
				return this.joueurs.get(0);
			} else {
				return this.joueurs.get(1);
			}
		}
		return null;
	}
	/* ***************************************************** **/
	
	/* **** Méthodes manipulant les scores de l'équipe **** **/
	/**
	 * Retourne le score de la manche dont le numero est passé en paramètre
	 * @param int numéro de la manche
	 * @return int
	 */
	 public int getScoreMancheOfHistorique(int numeroManche) {
		 return this.historiqueScoreManches.get(numeroManche);
	 }
	 
	 /**
	 * Ajoute le score d'une manche à l'historique des scores de chaque manches de la partie
	 * @return int un score
	 */
	 public boolean addScoreMancheToHistorique(int score) {
		 this.nbManche++;
		 return this.historiqueScoreManches.add(score);
	 }
	 
	 /**
	 * Retourne le score courant de la manche
	 * @return int
	 */
	 public int getScoreManche() {
		 return this.scoreManche;
	 }
	 
	 /**
	 * Maj la score courant de la manche
	 */
	 public void setScoreManche(int scoreManche) {
		 this.scoreManche = scoreManche;
	 }
	 
	/**
	 * Retourne le score de la partie
	 * @return int
	 */
	public int getScorePartie() {
		return this.scorePartie;
	}
	
	/**
	 * Maj le score de la partie
	 * @param int nouveau score de la partie
	 */
	public void setScorePartie(int scorePartie) {
		this.scorePartie = scorePartie;
	}
	
	/**
	 * Retourne le numero de la manche courante (ou le nombre de manche effectué)
	 * @return int
	 */
	public int getNbManche() {
		return nbManche;
	}

	/**
	 * Maj le numero de la manche courante (ou le nombre de manche effectué)
	 * @param int nombre de manche
	 */
	public void setNbManche(int nbManche) {
		this.nbManche = nbManche;
	}
	
	/** 
	 * Reset de tous les attributs score de la partie
	 */
	public void resetScorePartie() {
		this.historiqueScoreManches.clear();
		this.historiqueScoreManches = new ArrayList<>();
		this.scoreManche = 0;
		this.scorePartie = 0;
		this.nbManche = 0;
	}
	/* **************************************************** **/
	
	/* ***** Méthodes manipulant les plis de l'équipe ***** **/
	/**
	 * Ajoute un nouveau pli remporter à l'historique des plis et maj le score courant de la manche
	 * @param Pli le pli remporté
	 * @return boolean
	 */
	public boolean ajouterPliRemporte(Pli pli) {
		this.scoreManche += pli.getValeurPli();
		return this.plisRemportee.add(pli);
	}
	
	/**
	 * Retourne une liste de cartes contenues dans les plis remportés 
	 * @return boolean
	 */
	public ArrayList<Carte> rendreLesCartesDesPlisRemporter() {
		ArrayList<Carte> cartesARendre = new ArrayList<>();
		for (Pli pli : plisRemportee) {
			cartesARendre.addAll(pli.getCartes());
		}
		plisRemportee.clear();
		plisRemportee = new ArrayList<>();
		return cartesARendre;
	}
	/* **************************************************** **/
}
