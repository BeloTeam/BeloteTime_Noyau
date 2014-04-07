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

import classesMetier.Carte;
import classesMetier.CarteJouee;
import classesMetier.CouleurEnum;
import classesMetier.FigureEnum;
import classesMetier.InfoJoueur;
import classesMetier.Pli;

public class Intelligence {
	ArrayList<InfoJoueur> infoSurAutresJoueurs;
	InfoJoueur infoSurMoi;
	Joueur moi;
	
	
	
	public Intelligence(Joueur moi) {
		this.moi = moi;
		this.infoSurAutresJoueurs = new ArrayList<InfoJoueur>();
		for(Joueur joueur : moi.getTable().getJoueurs()){
			if(joueur != moi){
				if(joueur.getEquipeDuJoueur() == moi.getEquipeDuJoueur()){
					infoSurAutresJoueurs.add(new InfoJoueur(joueur, false));
				} else {
					infoSurAutresJoueurs.add(new InfoJoueur(joueur, true));
				}
			} else {
				infoSurMoi = new InfoJoueur(moi, false);
			}
		}
	}


	public void AnalyserPliPrecedent(){
		CouleurEnum couleurAtout = moi.getTable().getCouleurAtout();
		Pli pli = moi.getTable().getPliPrecedent();
		Carte annonce = pli.getCartes().get(0);
		for(Carte carte : pli.getCartes()){
			for(InfoJoueur infoJoueur : infoSurAutresJoueurs){
				if(((CarteJouee)carte).getJoueur() == infoJoueur.getJoueur()){
					infoJoueur.ajouterCarteJouer(carte, couleurAtout);
					if(annonce.getCouleur() == carte.getCouleur()){
						infoJoueur.setHasNotCouleur(annonce.getCouleur());
					}/*
					if(annonce.getCouleur() == couleurAtout && carte.getCouleur() != couleurAtout && pli.getJoueurMaitre() != infoJoueur.getJoueur().getPartenaire()){
						infoJoueur.setHasNotAtout(true);
					}*/
				}
			}
				
		}
	}
	
	public boolean adversaireHasNotCouleur(CouleurEnum couleur){
		boolean res = true;
		for (InfoJoueur infoJoueur : this.infoSurAutresJoueurs){
			if(infoJoueur.isAdversaire()){
				res = res && infoJoueur.HasNotCouleur(couleur);
			}
		}
		return res;
	}
	
	public boolean UnAdversaireHasNotCouleur(CouleurEnum couleur){
		boolean res = false;
		for (InfoJoueur infoJoueur : this.infoSurAutresJoueurs){
			if(infoJoueur.isAdversaire()){
				res = res || infoJoueur.HasNotCouleur(couleur);
			}
		}
		return res;
	}
	
	public boolean carteDejaJouer(Carte carte){
		boolean carteIsJouer = false;
		for(InfoJoueur info : infoSurAutresJoueurs){
			carteIsJouer = carteIsJouer || info.getCarteJouer().get(carte.getCouleur()).contains(carte);
		}
		carteIsJouer = carteIsJouer || infoSurMoi.getCarteJouer().get(carte.getCouleur()).contains(carte);
		return carteIsJouer;
	}
	
	public boolean isPlusForteCarteRestanteDansSaCouleur(Carte carte, CouleurEnum couleurAtout){
		for(FigureEnum f : FigureEnum.values()){
			Carte carteATester = new Carte(carte.getCouleur(), f);
			if(carteATester.numeroOrdre(couleurAtout) > carte.numeroOrdre(couleurAtout)){
				if(!carteDejaJouer(carteATester)){
					return false;
				}
			}
		}
		return true;
	}
}
