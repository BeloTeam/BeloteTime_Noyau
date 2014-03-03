package entite;
import java.util.ArrayList;

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.Pli;


public class Equipe {
	private ArrayList<Joueur> joueurs;
	private ArrayList<Integer> pointsDesManches;
	private ArrayList<Pli> plisRemportee;
	private int score;
	
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
	
	public void calculerScoreFinDeManche(CouleurEnum couleurAtout){
		int score = 0;
		for(Pli pli : this.plisRemportee){
			score += pli.calculerValeurPli(couleurAtout);
		}
		this.score += score;
		this.pointsDesManches.add(score);
	}
	

	public void calculerScoreFinDeManche(CouleurEnum couleurAtout,
			Equipe dixDeDer) {
		
		int score = 0;
		for(Pli pli : this.plisRemportee){
			score += pli.calculerValeurPli(couleurAtout);
		}
		if(this.equals(dixDeDer)){
			score+=10;
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
}
