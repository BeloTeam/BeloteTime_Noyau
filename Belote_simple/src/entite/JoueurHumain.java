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

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.Main;
import noyau.classesMetier.Paquet;
import noyau.classesMetier.PositionEnum;
import noyau.classesMetier.TableDeJeu;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;

public class JoueurHumain extends Joueur {

	static BufferedReader in = new BufferedReader(new InputStreamReader(
			System.in));

	public JoueurHumain(PositionEnum position, String nom, TableDeJeu table) {
		super(position, nom, table);

	}

	public boolean prendPremiereDonne() {
		System.out.println("Votre main :\n" + this.getMain()
				+ "\nPrenez vous pour l'atout : " + this.getTable().getCarteRetournee().toString()
				+ " (1-oui/0-non)");
		int rep = 0;
		rep = saisieClavier();
		if (rep == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public CouleurEnum prendDeuxiemeDonne() {
		System.out.println("Votre main :\n" + this.getMain()
				+ "Quelle couleur prenez vous : "
				+ " (1-Coeur/2-Pique/3-Carreau/4-Trefle/5-passe)");
		int rep = 0;
		rep = saisieClavier();
		//TODO Ne pas proposer la couleur de la carte du paquet qui est retournée!
		switch (rep) {
		case 1:
			return CouleurEnum.Coeur;
		case 2:
			return CouleurEnum.Pique;
		case 3:
			return CouleurEnum.Carreau;
		case 4:
			return CouleurEnum.Trefle;
		case 5:
			return null;
		default : return null;
		}
	}

	@Override
	public Carte jouerPli() {
		Carte carteJouee = null;
		SortedSet<Carte> cartesPossibles = null;
		Main mainTemp = new Main();
		
		System.out.println("-------------JEU--------------\n" + this.toString());
		while (carteJouee == null) {
			// S'il n'y a aucune carte sur la table (le cas ou le joueur commence)
			if (this.getTable().getPliCourant().getTaillePaquet() == 0) {	
				mainTemp = this.getMain();
				System.out.println("Vous commencez.");
			} 
			// S'il y a au moins une carte sur la table
			else {				
				System.out.println("Pli actuel : "+ this.getTable().getPliCourant());
				System.out.println("Joueur maitre : "+ this.getTable().getPliCourant().getJoueurMaitre());
				System.out.println("Couleur demande : "+ this.getTable().getPliCourant().getCouleurDemandee());
				System.out.println("Votre main :\n" + this.getMain().toString());


				// Si nous avons la couleur demandee
				if (this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee()) != null
						&& !this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee()).isEmpty()) {

					//si c'est de l'atout
					if(this.getTable().getPliCourant().getCouleurDemandee() == this.getTable().getCouleurAtout()){
						cartesPossibles = this.getMain().filtrerAtoutsPourSurcoupe(this.getTable().getPliCourant().getCarteMaitre());
						mainTemp.getMain().put(this.getTable().getCouleurAtout(), cartesPossibles);
						mainTemp.setSize(cartesPossibles.size());
						System.out.println("Vous devez jouer de l'atout.");
						// n'a pas obligé le joueur à surcouper alors qu'il le pouvait ?!
					}
					else {
						cartesPossibles = this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee());
						mainTemp.getMain().put(this.getTable().getPliCourant().getCouleurDemandee(), cartesPossibles);
						mainTemp.setSize(cartesPossibles.size());
						System.out.println("Vous devez jouer la couleur demandée.");
					}					
					//System.out.println("\nVous avez le choix entre : "+ cartesPossibles);
					//carteJouee = selectionnerUneCarte(cartesPossibles);
				} 
				// Sinon le joueur n'a pas la couleur demandee
				else { 
					// Si la couleur demandée est l'atout
					if (this.getTable().getPliCourant().getCouleurDemandee() == this.getTable().getCouleurAtout()){
						mainTemp = this.getMain();
						System.out.println("Vous n'avez pas d'atout, jouez une autre couleur.");
					}	
					else {
						System.out.println("\nVous n'avez pas la couleur demandée! ");	
						/********** on a peut-être le droit de se défausser! **********/
						// on regarde si le partenaire du joueur courant est maitre
						Joueur joueurMaitre = this.getTable().getPliCourant().getJoueurMaitre();
						Joueur joueurCoequipier = this.getTable().getGm().getEquipes().get(0).getPartenaire(this);
						if (joueurCoequipier == null) {
							joueurCoequipier = this.getTable().getGm().getEquipes().get(1).getPartenaire(this);
						}

						// si le partenaire est maitre il peut se défausser 
						if(joueurMaitre == joueurCoequipier){
							// Il peut jouer la carte qu'il veut
							mainTemp = this.getMain();	
							System.out.println("Votre partenaire est maitre, vous avez le droit de vous défausser (pisser)");	
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
								System.out.println("\nVous devez jouer à l'atout");
							} 
							// sinon il doit jouer une autre carte.
							else {								
								mainTemp = this.getMain();								
								System.out.println("Vous n'avez pas d'atout, vous devez vous défausser.\n");
							}
						}
					}
				}
			} 
			carteJouee = selectionnerUneCarte(mainTemp);
		}		
		return carteJouee;
	}

	/**
	 * Afficher et pouvoir selectionner une carte parmis la liste des cartes retenues
	 * @param cartesPossibles
	 * @return
	 */
	private Carte selectionnerUneCarte(Main cartesPossibles) {
		int tailleEnsembleCartePropose;
		Carte carteSelectionnee = null;
		tailleEnsembleCartePropose = cartesPossibles.getTailleMain();
		
		System.out.println("\nVous avez le choix entre : \n"+ cartesPossibles);		
		System.out.println("\nChoisissez une carte : [entre 0 et "+ (cartesPossibles.getTailleMain() - 1) + "]");
		int rep = saisieClavier();
		
		if (rep < tailleEnsembleCartePropose) {
			// Sur l'interface le mieux serait de récupérer directement la carte selectionnée 
			// comme ça on peut directement aller chercher la carte dans la main par le getteur adequat.			
			carteSelectionnee = cartesPossibles.hashtableToList().get(rep);			
			this.getMain().supprimer(carteSelectionnee);			 
		} 
		else {
			System.out.println("ERREUR");
		}	
		
		return carteSelectionnee;
	}

	public int saisieClavier() {
		int rep = 0;
		try {
			rep = Integer.parseInt(in.readLine());
		} catch (Exception e) {
			System.out.println("Erreur de saisie, veuillez resaisir  :");
			rep = saisieClavier();
		}
		return rep;
	}
}
