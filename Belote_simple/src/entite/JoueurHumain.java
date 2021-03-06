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
import classesMetier.FigureEnum;
import classesMetier.Main;
import classesMetier.Paquet;
import classesMetier.PositionEnum;
import classesMetier.TableDeJeu;

public class JoueurHumain extends Joueur {

	/**
	 * Surcharge du constructeur Joueur, cr�ation d'un joueur humain.
	 * @param position du joueur sur la table
	 * @param nom du joueur humain
	 * @param table ou est assie le joueur humain
	 * */
	public JoueurHumain(PositionEnum position, String nom, TableDeJeu table) {
		super(position, nom, table);

	}

	/**
	 * Retourne si oui ou non le joueur humain prend au premier tour
	 * @return boolean
	 */
	public boolean prendPremiereDonne() {
		Terminal.ecrireStringln("Votre main :\n" + this.getMain()
				+ "\nPrenez vous pour l'atout : " + this.getTable().getCarteRetournee().toString()
				+ " (1-oui/0-non)");
		int rep = 0;
		rep = Terminal.lireInt();
		if (rep == 1) {
			this.getEquipeDuJoueur().setEquipeHasPris(true);
			return true;
		} else {
			this.getEquipeDuJoueur().setEquipeHasPris(false);
			return false;
		}
	}

	/**
	 * Retourne si oui ou non le joueur humain prend au deuxieme tour
	 * @return CouleurEnum
	 */
	@Override
	public CouleurEnum prendDeuxiemeDonne() {
		Terminal.ecrireStringln("Votre main :\n" + this.getMain()
				+ "Quelle couleur prenez vous : "
				+ " (1-Coeur/2-Pique/3-Carreau/4-Trefle/5-passe)");
		int rep = 0;
		rep = Terminal.lireInt();
		//TODO Ne pas proposer la couleur de la carte du paquet qui est retourn�e!
		switch (rep) {
		case 1:
			this.getEquipeDuJoueur().setEquipeHasPris(true);
			return CouleurEnum.Coeur;
		case 2:
			this.getEquipeDuJoueur().setEquipeHasPris(true);
			return CouleurEnum.Pique;
		case 3:
			this.getEquipeDuJoueur().setEquipeHasPris(true);
			return CouleurEnum.Carreau;
		case 4:
			this.getEquipeDuJoueur().setEquipeHasPris(true);
			return CouleurEnum.Trefle;
		case 5:
			this.getEquipeDuJoueur().setEquipeHasPris(false);
			return null;
		default : 
			this.getEquipeDuJoueur().setEquipeHasPris(false);
			return null;
		}
	}

	/**
	 * Afficher et pouvoir selectionner une carte parmis la liste des cartes retenues
	 * @param cartesPossibles
	 * @return
	 */
	public Carte selectionnerUneCarte(Main cartesPossibles) {
		int tailleEnsembleCartePropose;
		Carte carteSelectionnee = null;
		tailleEnsembleCartePropose = cartesPossibles.getTailleMain();
		
		Terminal.ecrireStringln("\nVous avez le choix entre : \n"+ cartesPossibles);		
		Terminal.ecrireStringln("\nChoisissez une carte : [entre 0 et "+ (cartesPossibles.getTailleMain() - 1) + "]");
		int rep = Terminal.lireInt();

		if (rep < tailleEnsembleCartePropose) {
			// Sur l'interface le mieux serait de r�cup�rer directement la carte selectionn�e 
			// comme �a on peut directement aller chercher la carte dans la main par le getteur adequat.			
			carteSelectionnee = cartesPossibles.hashtableToList().get(rep);		
			
			// Si le joueur poss�de la dame et le roi � l'atout... 
			if(super.hasBeloteEtRe()) {
				// ...alors il annonce la belote/rebelote lorsqu'il joue la dame / le roi.
				if (carteSelectionnee.getCouleur() == this.getTable().getCouleurAtout() && carteSelectionnee.getFigure() == FigureEnum.Dame) {
					this.getTable().setBeloteAnnoncee(true);
					Terminal.ecrireStringln("J'annonce (Re)Belote");
				}
				if (carteSelectionnee.getCouleur() == this.getTable().getCouleurAtout() && carteSelectionnee.getFigure() == FigureEnum.Roi) {
					this.getTable().setRebeloteAnnoncee(true);
					Terminal.ecrireStringln("J'annonce (Re)Belote");
				}
			}
						 
		} 
		else {
			Terminal.ecrireStringln("ERREUR");
		}	
		
		return carteSelectionnee;
	}
	
	/**
	 * Action permettant de jouer une carte lors d'un pli.
	 * @return Carte
	 */
	public Carte jouerPli() {
		Carte carteJouee = null;
		SortedSet<Carte> cartesPossibles = null;
		Main mainTemp = new Main();
		
		Terminal.ecrireStringln("-------------JEU--------------\n Joueur courant : " + this.toString() 
				+ " ("+this.getEquipeDuJoueur()+")");
		while (carteJouee == null) {
			// S'il n'y a aucune carte sur la table (le cas ou le joueur commence)
			if (this.getTable().getPliCourant().size() == 0) {	
				mainTemp = this.getMain();
				Terminal.ecrireStringln("Vous commencez.");
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
						// n'a pas oblig� le joueur � surcouper alors qu'il le pouvait ?!
					}
					else {
						cartesPossibles = this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee());
						mainTemp.getMain().put(this.getTable().getPliCourant().getCouleurDemandee(), cartesPossibles);
						mainTemp.setSize(cartesPossibles.size());
						Terminal.ecrireStringln("Vous devez jouer la couleur demand�e.");
					}					
					//Terminal.ecrireStringln("\nVous avez le choix entre : "+ cartesPossibles);
					//carteJouee = selectionnerUneCarte(cartesPossibles);
				} 
				// Sinon le joueur n'a pas la couleur demandee
				else { 
					// Si la couleur demand�e est l'atout
					if (this.getTable().getPliCourant().getCouleurDemandee() == this.getTable().getCouleurAtout()){
						mainTemp = this.getMain();
						Terminal.ecrireStringln("Vous n'avez pas d'atout, jouez une autre couleur.");
					}	
					else {
						Terminal.ecrireStringln("\nVous n'avez pas la couleur demand�e! ");	
						/********** on a peut-�tre le droit de se d�fausser! **********/
						// on regarde si le partenaire du joueur courant est maitre
						Joueur joueurMaitre = this.getTable().getPliCourant().getJoueurMaitre();
						Joueur joueurCoequipier = this.getTable().getEquipeDuJoueur(this).getPartenaire(this);

						// si le partenaire est maitre il peut se d�fausser 
						if(joueurMaitre == joueurCoequipier){
							// Il peut jouer la carte qu'il veut
							mainTemp = this.getMain();	
							Terminal.ecrireStringln("Votre partenaire est maitre, vous avez le droit de vous d�fausser (pisser)");	
							//TODO cas de test pas encore atteint : il a le droit de se d�fausser mais il choisit de prendre la main 
						} 
						// le partenaire n'est pas ma�tre donc il ne peut pas se d�fausser 
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
								Terminal.ecrireStringln("\nVous devez jouer � l'atout");
							} 
							// sinon il doit jouer une autre carte.
							else {								
								mainTemp = this.getMain();								
								Terminal.ecrireStringln("Vous n'avez pas d'atout, vous devez vous d�fausser.\n");
							}
						}
					}
				}
			} 
			carteJouee = selectionnerUneCarte(mainTemp);
		}		

		this.getMain().supprimer(carteJouee);
		return carteJouee;
	}
	
	
	
	/**
	 * Action permettant de couper un tas de cartes
	 * @param tas Paquet
	 * @return boolean
	 */
	public boolean coupe(Paquet tas) {
		int pourcentageCoupe = 0;
		Terminal.ecrireString("Veuillez entrer une valeur de coupe(poucentage): ");
		pourcentageCoupe = Terminal.lireInt();
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
}
