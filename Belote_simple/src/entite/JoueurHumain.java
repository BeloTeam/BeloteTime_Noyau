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
		//TODO Ne pas proposer la couleur de la carte du paquet qui est retourn�e!
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
		Carte carteJouer = null;
		int tailleEnsembleCartePropose = 0;
		boolean peutJouerCouleurDuPli = true;
		boolean coupeObligatoirement = false;
		List<Carte> cartesPossibles = null;
		
		System.out.println("-------------JEU--------------\n" + this.toString());
		while (carteJouer == null) {
			// S'il y a au moins une carte sur la table
			if (this.getTable().getPliCourant().getTaillePaquet() != 0) {
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
					System.out.println("\nVous avez le choix entre : "+ cartesPossibles);
					
				} 
				// Sinon le joueur n'a pas la couleur demandee
				else { 
					peutJouerCouleurDuPli = false;
					System.out.println("\nVous n'avez pas la couleur demande! ");

					// on regarde si le partenaire du joueur courant est maitre
					Joueur joueurMaitre = this.getTable().getPliCourant().getJoueurMaitre();
					Joueur joueurCoequipier = this.getTable().getGm().getEquipes().get(0).getPartenaire(this);
					if (joueurCoequipier == null) {
						joueurCoequipier = this.getTable().getGm().getEquipes().get(1).getPartenaire(this);
					}
					if((joueurMaitre == joueurCoequipier)){
						System.out.println("Votre partenaire est maitre");
					}
					else{
						System.out.println("Votre partenaire n'est pas maitre, vous devez couper si vous avez de l'atout");
					}
					boolean peutSeDefausser = false;

					if (joueurMaitre == joueurCoequipier) {
						peutSeDefausser = true;
					}

					SortedSet<Carte> listCarteCouleurAtout = this.getMain().get(this.getTable().getCouleurAtout());
					//si le joueur doit couper
					if (listCarteCouleurAtout != null && listCarteCouleurAtout.size() > 0 && !peutSeDefausser) {
						coupeObligatoirement = true;
						
						//regarder si carte maitre est un atout, si oui on doit monter si possible
						if(this.getTable().getPliCourant().getCarteMaitre().getCouleur() == this.getTable().getCouleurAtout()){
							listCarteCouleurAtout = this.getMain().getAtoutPlusFortQue(this.getTable().getPliCourant().getCarteMaitre());
						}
						
						tailleEnsembleCartePropose = listCarteCouleurAtout.size();
						System.out.println("\nVous devez couper :\nVous avez le choix entre : "+ listCarteCouleurAtout);
					} 
					else {
						tailleEnsembleCartePropose = this.getMain().getTailleMain();
						peutJouerCouleurDuPli = false;
						System.out.println("Vous pouvez jouer la carte que vous voulez.\n");
					}
				}
			} 
			// S'il n'y a aucune carte sur la table (le cas ou le joueur commence)
			else 
			{ 
				tailleEnsembleCartePropose = this.getMain().getTailleMain();
				peutJouerCouleurDuPli = false;
				System.out.println("Vous commencez, votre main :\n"
						+ this.getMain());
			}

			System.out.println("\nQuelle carte jouer : [0 a "
					+ (tailleEnsembleCartePropose - 1) + "]");
			int rep = saisieClavier();

			if (rep < tailleEnsembleCartePropose) {
				// TODO Pour le moment la version avec rep est foireuse, ya des
				// chances que la carte choisie par l'utilisateur
				// ne corresponde pas, j'ai juste fais une conversion de map
				// vers list, l'ordre n'est pas assurement respecte.
				// Le mieux serait de demander a l'utilisateur de rentrer
				// exactement la couleur et la figure qu'il souhaite,
				// comme �a on peut directement aller chercher la carte dans la
				// main par le getteur adequat.

				if (!peutJouerCouleurDuPli) {
					if (coupeObligatoirement) {
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
		}
		return carteJouer;
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
