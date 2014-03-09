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
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.FigureEnum;
import noyau.classesMetier.Pli;


public class Equipe {
	private ArrayList<Joueur> joueurs;
	private ArrayList<Integer> pointsDesManches;
	private ArrayList<Pli> plisRemportee;
	private int score;
	private boolean hasBelote;
	private boolean hasRebelote;//TODO prendre en compte belote / rebelote
	
	public Equipe(Joueur joueur1, Joueur joueur2) {
		this.joueurs = new ArrayList<>();
		this.joueurs.add(joueur1);
		this.joueurs.add(joueur2);
		this.pointsDesManches = new ArrayList<>();
		this.plisRemportee = new ArrayList<Pli>();
		this.score = 0;
	}
	
	public void ajouterUnpliRemportee(Pli pli){
		this.plisRemportee.add(pli);
	}

	public ArrayList<Integer> getPointsDesManches() {
		return pointsDesManches;
	}

	public void setPointsDesManches(ArrayList<Integer> pointsDesManches) {
		this.pointsDesManches = pointsDesManches;
	}

	public ArrayList<Joueur> getJoueurs() {
		return joueurs;
	}

	public boolean estDansLEquipe(Joueur joueur) {
		for(Joueur j : joueurs){
			if(j.equals(joueur)){
				return true;
			}
		}
		return false;
	}
	
	public void calculerScoreFinDeManche(){
		int score = 0;
		for(Pli pli : this.plisRemportee){
			score += pli.calculerValeurPli();
		}
		this.score += score;
		this.pointsDesManches.add(score);
	}
	

	public int getScore() {
		return score;
	}


	public boolean equals(Object equipe) {
		if (equipe.getClass() == this.getClass()) {
			if(this.joueurs.get(0).equals(((Equipe)equipe).joueurs.get(0)) &&
			   this.joueurs.get(1).equals(((Equipe)equipe).joueurs.get(1))	){
				return true;
			}
			return false;
		}
		return false;
	}

	public ArrayList<Carte> rendreLesCartesDesPlisRemporter(){
		ArrayList<Carte> cartesARendre = new ArrayList<>();
		for(Pli pli : plisRemportee){
			cartesARendre.addAll(pli.getCartes());
		}
		plisRemportee = new ArrayList<>();
		return cartesARendre;
	}
	
	public String toString(){
		return "Equipe de : "+this.getJoueurs();
	}
	
	public Joueur hasBeloteRebelote(CouleurEnum couleurAtout){
		boolean hasBe=false;
		boolean hasRe=false;
		
		for (Joueur j : this.joueurs) {
			for (Carte c : j.getMain().get(couleurAtout)) {
				if(c.equals(new Carte(couleurAtout,FigureEnum.Dame))){
					hasBe=true;
				}
				if(c.equals(new Carte(couleurAtout,FigureEnum.Dame))){
					hasRe=true;
				}
				if(hasBe && hasRe){
					return j;
				}
			}
			hasBe=false;
			hasRe=false;
		}
		
		return null;
	}
	
	public Joueur getPartenaire(Joueur j){
		if(this.estDansLEquipe(j)){
			if(this.joueurs.get(0) != j){
				return this.joueurs.get(0);
			} else {
				return this.joueurs.get(1);
			}
		}
		return null;
	}
}
