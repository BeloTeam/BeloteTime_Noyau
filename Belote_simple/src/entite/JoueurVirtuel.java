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

import java.util.SortedSet;

import control.Terminal;
import classesMetier.Carte;
import classesMetier.CouleurEnum;
import classesMetier.Main;
import classesMetier.Paquet;
import classesMetier.PositionEnum;
import classesMetier.TableDeJeu;

/**
 * JoueurVirtuel représante un joueur non humain, qui possède une intéligence artificielle
 * @author BeloTeam
 * @version 1.0
 **/
public class JoueurVirtuel extends Joueur {
	private Intelligence ia;
	public static int SEUIL = 42;

	/**
	 * Surcharge du constructeur Joueur, création d'un joueur virtuel.
	 * @param position du joueur sur la table
	 * @param nom du joueur humain
	 * @param table ou est assie le joueur humain
	 * */
	public JoueurVirtuel(PositionEnum position, String nom, TableDeJeu table) {
		super(position, nom, table);
	}

	public void initIA(){
		ia = new Intelligence(this);
	}
	
	/**
	 * Retourne si oui ou non le joueur virtuel prend au premier tour
	 * @return boolean
	 */
	@Override
	public boolean prendPremiereDonne() {
		CouleurEnum couleurAtout = this.getTable().getCarteRetournee().getCouleur();
		this.getMain().ajouter(this.getTable().getCarteRetournee(), couleurAtout);
		int scoreMain = this.getMain().calculerValeurMain(couleurAtout);
		this.getMain().supprimer(this.getTable().getCarteRetournee());
		if (scoreMain > SEUIL){
			this.getEquipeDuJoueur().setEquipeHasPris(true);
			return true;
		} else {
			this.getEquipeDuJoueur().setEquipeHasPris(false);
			return false;
		}
	}

	/**
	 * Action permettant de jouer une carte lors d'un pli.
	 * @return Carte
	 */
	public Carte jouerPli() {
		Carte carteJouee = null;
		SortedSet<Carte> cartesPossibles = null;
		Main mainTemp = new Main();
		CouleurEnum couleurAtout = this.getTable().getCouleurAtout();
		
		Terminal.ecrireStringln("-------------JEU--------------\n Joueur courant : " + this.toString() 
				+ " ("+this.getEquipeDuJoueur()+")");
		while (carteJouee == null) {
			// S'il n'y a aucune carte sur la table (le cas ou le joueur commence)
			if (this.getTable().getPliCourant().size() == 0) {	
				mainTemp = this.getMain();
				Terminal.ecrireStringln("Vous commencez.");
				carteJouee = getCarteForAppel(mainTemp);
			} 
			// S'il y a au moins une carte sur la table
			else {				
				Terminal.ecrireStringln("Pli actuel : "+ this.getTable().getPliCourant());
				Terminal.ecrireStringln("Joueur maitre : "+ this.getTable().getPliCourant().getJoueurMaitre());
				Terminal.ecrireStringln("Couleur demande : "+ this.getTable().getPliCourant().getCouleurDemandee());
				Terminal.ecrireStringln("Votre main :\n" + this.getMain().toString());


				// Si nous avons la couleur demandee
				if (this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee()) != null
						&& !this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee()).isEmpty()) {

					//si c'est de l'atout
					if(this.getTable().getPliCourant().getCouleurDemandee() == this.getTable().getCouleurAtout()){
						cartesPossibles = this.getMain().filtrerAtoutsPourSurcoupe(this.getTable().getPliCourant().getCarteMaitre());
						mainTemp.getMain().put(this.getTable().getCouleurAtout(), cartesPossibles);
						mainTemp.setSize(cartesPossibles.size());
						Terminal.ecrireStringln("Vous devez jouer de l'atout.");
						carteJouee = getCarteForSurCoupe(mainTemp);
						// n'a pas obligé le joueur à surcouper alors qu'il le pouvait ?!
					}
					else {
						cartesPossibles = this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee());
						mainTemp.getMain().put(this.getTable().getPliCourant().getCouleurDemandee(), cartesPossibles);
						mainTemp.setSize(cartesPossibles.size());
						Terminal.ecrireStringln("Vous devez jouer la couleur demandée.");
						carteJouee = getCarteForJouerCouleur(mainTemp, this.getTable().getPliCourant().getCouleurDemandee());
					}					
					//Terminal.ecrireStringln("\nVous avez le choix entre : "+ cartesPossibles);
					//carteJouee = selectionnerUneCarte(cartesPossibles);
				} 
				// Sinon le joueur n'a pas la couleur demandee
				else { 
					// Si la couleur demandée est l'atout
					if (this.getTable().getPliCourant().getCouleurDemandee() == this.getTable().getCouleurAtout()){
						mainTemp = this.getMain();
						Terminal.ecrireStringln("Vous n'avez pas d'atout, jouez une autre couleur.");
						carteJouee = getCarteForLimiterLaCasse(mainTemp);
					}	
					else {
						Terminal.ecrireStringln("\nVous n'avez pas la couleur demandée! ");	
						/********** on a peut-être le droit de se défausser! **********/
						// on regarde si le partenaire du joueur courant est maitre
						Joueur joueurMaitre = this.getTable().getPliCourant().getJoueurMaitre();
						Joueur joueurCoequipier = this.getTable().getEquipeDuJoueur(this).getPartenaire(this);

						// si le partenaire est maitre il peut se défausser 
						if(joueurMaitre == joueurCoequipier){
							// Il peut jouer la carte qu'il veut
							mainTemp = this.getMain();	
							Terminal.ecrireStringln("Votre partenaire est maitre, vous avez le droit de vous défausser (pisser)");
							carteJouee = getCarteForLimiterLaCasse(mainTemp);
							//TODO cas de test pas encore atteint : il a le droit de se défausser mais il choisit de prendre la main 
						} 
						// le partenaire n'est pas maître donc il ne peut pas se défausser 
						else{	
							// si le joueur a de l'atout il doit couper		
							cartesPossibles = this.getMain().get(this.getTable().getCouleurAtout());
							if (cartesPossibles != null && cartesPossibles.size() > 0) {
								// si la carte maitre est un atout il doit surcouper si il le peut
								if(this.getTable().getPliCourant().getCarteMaitre().getCouleur() == this.getTable().getCouleurAtout()){
									cartesPossibles = this.getMain().filtrerAtoutsPourSurcoupe(this.getTable().getPliCourant().getCarteMaitre());
								}									
								mainTemp.getMain().put(this.getTable().getCouleurAtout(), cartesPossibles);
								mainTemp.setSize(cartesPossibles.size());
								Terminal.ecrireStringln("\nVous devez jouer à l'atout");
								carteJouee = getCarteForSurCoupe(mainTemp);
							} 
							// sinon il doit jouer une autre carte.
							else {								
								mainTemp = this.getMain();		
								carteJouee = getCarteForLimiterLaCasse(mainTemp);
								Terminal.ecrireStringln("Vous n'avez pas d'atout, vous devez vous défausser.\n");
							}
						}
					}
				}
			} 
			System.out.println(carteJouee);
			if(carteJouee == null){
				int rep = Terminal.lireInt();
			}
		}		

		this.getMain().supprimer(carteJouee);
		return carteJouee;
	}
	
	/**
	 * Retourne la couleur choisie ou null si le joueur virtuel ne prend pas au deuxieme tour
	 * @return CouleurEnum
	 */
	@Override
	public CouleurEnum prendDeuxiemeDonne() {
		CouleurEnum couleurChoisie = null;
		int scoreMaxMain = 0;
		int score;

		System.out.println(this.getMain());
		
		for(CouleurEnum couleur : CouleurEnum.values()){
			this.getMain().ajouter(this.getTable().getCarteRetournee(), couleur);
			score = this.getMain().calculerValeurMain(couleur);
			this.getMain().supprimer(this.getTable().getCarteRetournee());
		
			if (score > SEUIL && score > scoreMaxMain){
				couleurChoisie = couleur;
			}
		}
		if (couleurChoisie != null){
			this.getEquipeDuJoueur().setEquipeHasPris(true);
		} else {
			this.getEquipeDuJoueur().setEquipeHasPris(false);
		}
		return couleurChoisie;
	}

	/**
	 * Action permettant de couper un tas de cartes
	 * @return boolean
	 */
	@Override
	public boolean coupe(Paquet tas) {
		int pourcentageCoupe = ((int) (Math.random() * 100));
		return tas.couper(pourcentageCoupe);
	}

	/**
	 * Action permettant d'analyser la main courante (belotes?)
	 * @return void
	 */
	@Override
	public void analyserSonJeu() {
		if(this.getMain().hasBeloteRebelote(this.getTable().getCouleurAtout())) {
			super.setHasBeloteEtRe(true);
			this.getEquipeDuJoueur().setEquipeHasBeloteEtRe(true);
		} 
	}
	
	private Carte getCarteForAppel(Main mainTemp){
		
		//raccourci
		if(mainTemp.getTailleMain() == 1){
			return mainTemp.hashtableToList().get(0);
		}
		
		CouleurEnum couleurAtout = this.getTable().getCouleurAtout();

		//Stratégie Preneur
		if(this.getEquipeDuJoueur().isEquipeHasPris()){
		//si on peut faire tomber les atouts
			if (mainTemp.get(couleurAtout).size() > 1 && !ia.adversaireHasNotCouleur(couleurAtout)){
				return mainTemp.getPlusForteCarteAtout(couleurAtout);
			} else {
				int nbMinCarteMemeCouleur = 9;
				int nbCourant;
				CouleurEnum couleurMin = null;
				for(CouleurEnum couleur : CouleurEnum.values()){
					nbCourant = mainTemp.getNbCarteCouleur(couleur);
					if(nbCourant > 0 && nbCourant < nbMinCarteMemeCouleur && !couleur.equals(couleurAtout)){
						couleurMin = couleur;
						nbMinCarteMemeCouleur = nbCourant;
					}
				}
				return mainTemp.getPlusFaibleCarteNormale(couleurMin,couleurAtout);
			}
		//stratégie défenseur
		} else {
			int nbMinCarteMemeCouleur = 9;
			int nbCourant;
			CouleurEnum couleurMin = null;
			for(CouleurEnum couleur : CouleurEnum.values()){
				nbCourant = mainTemp.getNbCarteCouleur(couleur);
				if (nbCourant > 0 && nbCourant < nbMinCarteMemeCouleur && !couleur.equals(couleurAtout)){
					couleurMin = couleur;
					nbMinCarteMemeCouleur = nbCourant;
				}
			}
			//cas particulier ou il ne reste plus que de l'atout
			if(couleurMin == null){
				return mainTemp.getPlusFaibleCarteAtout(couleurAtout);
			}
			
		return mainTemp.getPlusFaibleCarteNormale(couleurMin,couleurAtout);
		}
	}
	
	//TODO peut être perfectible
	private Carte getCarteForLimiterLaCasse(Main mainTemp){
		
		//raccourci
		if(mainTemp.getTailleMain() == 1){
			return mainTemp.hashtableToList().get(0);
		}
		
		CouleurEnum couleurAtout = this.getTable().getCouleurAtout();
		CouleurEnum couleurMax = null;
		//si le joueurMaitre est notre partenaire et qu'on est le dernier a jouer
		if(this.getTable().getPliCourant().getJoueurMaitre().getEquipeDuJoueur() == this.getEquipeDuJoueur() && this.getTable().getPliCourant().size() == 3){
			int maxScore = -1;
			int score;
			for(CouleurEnum couleur : CouleurEnum.values()){
				if(couleur != couleurAtout && mainTemp.get(couleur).size()>0){
					score = mainTemp.getPlusForteCarteNormale(couleur,couleurAtout).calculerValeurCarte(couleurAtout);
					if(score > maxScore){
						maxScore = score;
						couleurMax = couleur;
					}
				}
			}
			//cas particulier ou il ne reste plus que de l'atout
			if(couleurMax == null){
				return mainTemp.getPlusFaibleCarteAtout(couleurAtout);
			}
			return mainTemp.getPlusForteCarteNormale(couleurMax,couleurAtout);
		} else {
			int nbMaxCarteMemeCouleur = 0;
			int nbCourant;
			for(CouleurEnum couleur : CouleurEnum.values()){
				nbCourant = mainTemp.getNbCarteCouleur(couleur);
				if(nbCourant > nbMaxCarteMemeCouleur){
					couleurMax = couleur;
					nbMaxCarteMemeCouleur = nbCourant;
				}
			}
			return mainTemp.getPlusFaibleCarteNormale(couleurMax,couleurAtout);
		}
	}
	
	private Carte getCarteForSurCoupe(Main mainTemp){
		
		//raccourci
		if(mainTemp.getTailleMain() == 1){
			return mainTemp.hashtableToList().get(0);
		}
		
		Carte carteJouee;
		CouleurEnum couleurAtout = this.getTable().getCouleurAtout();
		Carte cartePlusForte = mainTemp.getPlusForteCarteAtout(couleurAtout);
		//si ma meilleur carte est meilleur que la carte maitre
		if(cartePlusForte.calculerValeurCarte(couleurAtout) > this.getTable().getPliCourant().getCarteMaitre().calculerValeurCarte(couleurAtout)){
			//si on est le dernier ou si aucun adversaire n'est en mesure de surcouper 
			if(this.getTable().getPliCourant().size() == 3 || ia.isPlusForteCarteRestanteDansSaCouleur(mainTemp.getPlusForteCarteAtout(couleurAtout), couleurAtout) || ia.adversaireHasNotCouleur(couleurAtout)){
				carteJouee = mainTemp.getPlusForteCarteAtout(couleurAtout);
			} else {
				carteJouee = mainTemp.getPlusFaibleCarteAtout(couleurAtout);
			}
		} else {
			carteJouee = mainTemp.getPlusFaibleCarteAtout(couleurAtout);
		}
		return carteJouee;
	}
	
	private Carte getCarteForJouerCouleur(Main mainTemp, CouleurEnum couleur){
		
		//raccourci
		if(mainTemp.getTailleMain() == 1){
			return mainTemp.hashtableToList().get(0);
		}
		
		Carte carteJouee;
		CouleurEnum couleurAtout = this.getTable().getCouleurAtout();
		//si on a une carte plus forte que la carte maitre
		if(mainTemp.getPlusForteCarteNormale(couleur,couleurAtout).calculerValeurCarte(couleurAtout) > this.getTable().getPliCourant().getCarteMaitre().calculerValeurCarte(couleurAtout)){
				//si on est le dernier ou si aucun adversaire n'est en mesure de couper 
				if(this.getTable().getPliCourant().size() == 3 || (ia.isPlusForteCarteRestanteDansSaCouleur(mainTemp.getPlusForteCarteNormale(couleur,couleurAtout), couleurAtout) || ia.adversaireHasNotCouleur(couleur)) && ia.adversaireHasNotCouleur(couleurAtout)){
					carteJouee = mainTemp.getPlusForteCarteNormale(couleur,couleurAtout);
				} else {
					carteJouee = mainTemp.getPlusFaibleCarteNormale(couleur,couleurAtout);
				}
		} else {
			//si le maitre du pli est de notre equipe
			if(this.getTable().getPliCourant().getJoueurMaitre().getEquipeDuJoueur() == this.getEquipeDuJoueur()){
				//si on est le dernier ou si aucun adversaire n'est en mesure de couper 
				if(this.getTable().getPliCourant().size() == 3 || ia.isPlusForteCarteRestanteDansSaCouleur(mainTemp.getPlusForteCarteNormale(couleur,couleurAtout), couleurAtout)){
					carteJouee = mainTemp.getPlusForteCarteNormale(couleur,couleurAtout);
				} else {
					carteJouee = mainTemp.getPlusFaibleCarteNormale(couleur,couleurAtout);
				}
			} else {
				carteJouee = mainTemp.getPlusFaibleCarteNormale(couleur,couleurAtout);
			}
		}
		return carteJouee;
	}
	
}