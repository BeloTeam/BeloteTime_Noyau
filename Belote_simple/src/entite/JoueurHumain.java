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
		int tailleEnsembleCartePropose = 0;
		boolean peutJouerCouleurDuPli = true;
		List<Carte> cartesPossibles = null;
		boolean peutSeDefausser = false;

		System.out.println("-------------JEU--------------\n" + this.toString());
		while (carteJouee == null) {
			// S'il n'y a aucune carte sur la table (le cas ou le joueur commence)
			if (this.getTable().getPliCourant().getTaillePaquet() == 0) {
				tailleEnsembleCartePropose = this.getMain().getTailleMain();
				peutJouerCouleurDuPli = false;
				//System.out.println("Vous commencez, votre main :\n" + this.getMain());
				carteJouee = selectionnerUneCarte(this.getMain());
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
						cartesPossibles = this.getMain().getAtoutPlusFortListQue(this.getTable().getPliCourant().getCarteMaitre());
						tailleEnsembleCartePropose = cartesPossibles.size();
					}
					else {
						tailleEnsembleCartePropose = this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee()).size();
						cartesPossibles = this.getMain().getList(this.getTable().getPliCourant().getCouleurDemandee());
					}
					//System.out.println("\nVous avez le choix entre : "+ cartesPossibles);
					carteJouee = selectionnerUneCarte(cartesPossibles);

				} 
				// Sinon le joueur n'a pas la couleur demandee
				else { 
					peutJouerCouleurDuPli = false; // si on n'en en plus besoin variable à retirer
					// Si la couleur demandée est l'atout
					if (this.getTable().getPliCourant().getCouleurDemandee() == this.getTable().getCouleurAtout()){
						System.out.println("Vous n'avez pas d'atout, jouez une autre couleur.");
						//System.out.println("\nVous avez le choix entre : "+ cartesPossibles);
						carteJouee = selectionnerUneCarte(cartesPossibles);
					}	
					else {
						System.out.println("\nVous n'avez pas la couleur demande! ");															
						peutJouerCouleurDuPli = false;
						/********** on a peut-être le droit de se défausser! **********/
						// on regarde si le partenaire du joueur courant est maitre
						Joueur joueurMaitre = this.getTable().getPliCourant().getJoueurMaitre();
						Joueur joueurCoequipier = this.getTable().getGm().getEquipes().get(0).getPartenaire(this);
						if (joueurCoequipier == null) {
							joueurCoequipier = this.getTable().getGm().getEquipes().get(1).getPartenaire(this);
						}

						// si le partenaire est maitre il peut se défausser 
						if(joueurMaitre == joueurCoequipier){
							peutSeDefausser = true; // si on n'en en plus besoin variable à retirer
							System.out.println("Votre partenaire est maitre, vous avez le droit de vous défausser (pisser)");
							// Il peut jouer la carte qu'il veut
							//System.out.println("\nVous avez le choix entre : "+ cartesPossibles);
							carteJouee = selectionnerUneCarte(cartesPossibles);
							
						} 
						// le partenaire pas maître donc il ne peut pas se défausser 
						else{	
							// si le joueur a de l'atout il doit couper		
							SortedSet<Carte> cartesPossibleAtout = this.getMain().get(this.getTable().getCouleurAtout());
							if (cartesPossibleAtout != null && cartesPossibleAtout.size() > 0) {
								// si la carte maitre est un atout il doit surcouper si il le peut
								if(this.getTable().getPliCourant().getCarteMaitre().getCouleur() == this.getTable().getCouleurAtout()){
									cartesPossibleAtout = this.getMain().filtrerAtoutsPourSurcoupe(this.getTable().getPliCourant().getCarteMaitre());
								}								
								tailleEnsembleCartePropose = cartesPossibleAtout.size();
								System.out.println("\nVous devez jouer à l'atout");
								//System.out.println("\nVous avez le choix entre : "+ cartesPossibles);
								carteJouee = selectionnerUneCarte(cartesPossibleAtout);
							} 
							// sinon il doit jouer une autre carte.
							else {								
								tailleEnsembleCartePropose = this.getMain().getTailleMain();								
								System.out.println("Vous n'avez pas d'atout, vous devez vous défausser.\n");
								//System.out.println("\nVous avez le choix entre : "+ cartesPossibles);
								carteJouee = selectionnerUneCarte(cartesPossibles);
							}
						}
					}
				}
			} 
		}
		return carteJouee;
	}

	/**
	 * Afficher et pouvoir selectionner une carte parmis la liste des cartes retenues
	 * @param cartesPossibles
	 * @return
	 */
	private Carte selectionnerUneCarte(Main cartesPossibles) {
		
		System.out.println("\nVous avez le choix entre : "+ cartesPossibles);
		
		//TODO en profiter pour commencer à 0 pour ne pas se reprendre la tête avec cette histoire de -1?
		System.out.println("\nChoisissez une carte : [entre 0 et "
				+ (cartesPossibles.getTailleMain() - 1) + "]");
		int rep = saisieClavier();

		//TODO à continuer...
		/*
		if (rep < tailleEnsembleCartePropose) {
			// TODO Pour le moment la version avec rep est foireuse, ya des
			// chances que la carte choisie par l'utilisateur
			// ne corresponde pas, j'ai juste fais une conversion de map
			// vers list, l'ordre n'est pas assurement respecte.
			// Le mieux serait de demander a l'utilisateur de rentrer
			// exactement la couleur et la figure qu'il souhaite,
			// comme ça on peut directement aller chercher la carte dans la
			// main par le getteur adequat.

			if (!peutJouerCouleurDuPli) {
				if (!peutSeDefausser) {
					carteJouer = this.getMain().getList(this.getTable().getCouleurAtout()).get(rep);
				} 
				else {
					carteJouer = this.getMain().hashtableToList().get(rep);
				}
				this.getMain().supprimer(carteJouer);
			} 
			// Si on a la couleur demande on recupere parmis la liste des cartes de la couleur demandee
			else { 
				carteJouer = cartesPossibles.get(rep);
				this.getMain().supprimer(carteJouer);
			}
			this.getMain().supprimer(carteJouer);
		} 
		else {
			System.out.println("ERREUR");
		}
	*/
		return null;
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
