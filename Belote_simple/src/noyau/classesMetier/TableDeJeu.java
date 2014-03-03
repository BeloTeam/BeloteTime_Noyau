package noyau.classesMetier;

import entite.GameMaster;
import entite.Joueur;
import entite.JoueurHumain;

public class TableDeJeu {
	private Joueur[] joueurs;
	private GameMaster gm;
	private Tas tas;
	private boolean sensDesAiguilleDuneMontre;
	
	public TableDeJeu() {
		sensDesAiguilleDuneMontre = true;
		joueurs = new Joueur[4];
		joueurs[0] = new JoueurHumain(PositionEnum.Sud,"Humain");
		joueurs[1] = new JoueurHumain(PositionEnum.Nord,"Joueur Nord");
		joueurs[2] = new JoueurHumain(PositionEnum.Est,"Joueur Est");
		joueurs[3] = new JoueurHumain(PositionEnum.Ouest,"Joueur Ouest");
		gm = new GameMaster(joueurs,this);
		tas = new Tas();
		tas.melanger(50);
	}
	public Tas getTas() {
		return tas;
	}
	
	public Joueur joueurSuivant(Joueur j){
		if(sensDesAiguilleDuneMontre){
			return aGaucheDe(j);
		} else {
			return aDroiteDe(j);
		}
	}
	
	public Joueur aGaucheDe(Joueur j){
		PositionEnum aGauche = null;
		switch(j.getPosition()){
		case Nord:
			aGauche = PositionEnum.Est;
			break;
		case Est:
			aGauche = PositionEnum.Sud;
			break;
		case Sud:
			aGauche = PositionEnum.Ouest;
			break;
		case Ouest:
			aGauche = PositionEnum.Nord;
			break;
		}
		for(Joueur joueur : joueurs){
			if(joueur.getPosition() == aGauche){
				return joueur;
			}
		}
		return null;
	}
	
	public Joueur aDroiteDe(Joueur j){
		PositionEnum aGauche = null;
		switch(j.getPosition()){
		case Nord:
			aGauche = PositionEnum.Ouest;
			break;
		case Ouest:
			aGauche = PositionEnum.Sud;
			break;
		case Sud:
			aGauche = PositionEnum.Est;
			break;
		case Est:
			aGauche = PositionEnum.Nord;
			break;
		}
		for(Joueur joueur : joueurs){
			if(joueur.getPosition() == aGauche){
				return joueur;
			}
		}
		return null;
	}
	
	public int nbCartesDe(Joueur joueur){
		int nbCartes = 0;
		for(Joueur j : this.joueurs){
			if(j == joueur){
				nbCartes = joueur.getMain().getMain().size();
			}
		}
		return nbCartes;
	}
	public GameMaster getGm() {
		return gm;
	}
}
