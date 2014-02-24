package noyau.ia;

import java.util.ArrayList;
import java.util.List;

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.Fenetre;
import noyau.classesMetier.Paquet;
import noyau.classesMetier.Terminal;


/**
 * @class JeuxBelote
 * @author lacertus, Nathan,Loic
 * @resume classe principale permettant de lancer la partie
 * 
 * */

public class JeuxBelote {

	public static void main(String[] arguments) {
		Paquet jeux = new Paquet();
		List<JoueurIntelligence> joueurs = new ArrayList<>(4);
		Fenetre tapis = new Fenetre();
		int prend = 4; // variable pour savoir qui prend
		CouleurEnum couleurAtout = CouleurEnum.NotInitialized;// variable pour la couleur
													// d'atout
		// qui donne le jeux
		int donne = 0;
		int n = 0;
		int numeroJoueur = 0;
		int indice = 0;
		int gagne = 0;
		Carte retourne;
		
		for (int i = 0; i < 4; i++) {
			joueurs.add(new JoueurIntelligence());
		}
		// affiche le nombre de carte dans le paquet initial
		Terminal.ecrireStringln("valeur " + jeux.getInitial().size());
		// melange le jeux
		jeux.donneJeuBeloteBattu();
		tapis.initFenetre();
		for (int pmax = 0; pmax < 500;) {
			jeux.razpaquet();
			// affiche sur le terminal le jeux du paquet
			// jeux.affiche();
			// affiche le jeux des joueurs
			for (int y = 0; y < 4; y++) {
				joueurs.get(y).affichejoueur(y);
			}
			// donne premier tour
			n = 2;
			numeroJoueur = donne;
			indice = 0;
			// donne 3 cartes
			while (n != 0) {
				numeroJoueur++;
				if (numeroJoueur > 3) {
					numeroJoueur = 0;
				}
				for (int i = 0; i < 3; i++) {
					tapis.nbcartej[numeroJoueur] = joueurs.get(numeroJoueur).recoit(jeux.getJeuxdist(), tapis.nbcartej[numeroJoueur], jeux.getBlanc());
				}
				jeux.setLongJeuxdist(jeux.getLongJeuxdist() - 3);
				indice++;
				if (indice == 4) {
					n = 0;
				}
			}
			// donne 2 cartes
			n = 2;
			numeroJoueur = donne;
			indice = 0;
			while (n != 0) {
				numeroJoueur++;
				if (numeroJoueur > 3) {
					numeroJoueur = 0;
				}
				for (int i = 0; i < 2; i++) {
					tapis.nbcartej[numeroJoueur] = joueurs.get(numeroJoueur).recoit(jeux.getJeuxdist(), tapis.nbcartej[numeroJoueur], jeux.getBlanc());
				}
				jeux.setLongJeuxdist(jeux.getLongJeuxdist() - 2);
				indice++;
				if (indice == 4) {
					n = 0;
				}
			}
			// retourne la carte pour atout
			retourne = jeux.getJeuxdist().get(0);
			for (int v = 0; v < 32; v++) {
				if (v + 1 < 32) {
					jeux.getJeuxdist().set(v, jeux.getJeuxdist().get(v + 1));
				} else {
					jeux.getJeuxdist().set(v, jeux.getBlanc());
				}
			}
			jeux.setLongJeuxdist(jeux.getLongJeuxdist() - 1);
			couleurAtout = retourne.getCouleur();
			tapis.affiche();
			
			// affiche le jeux des joueurs
			for (int y = 0; y < 4; y++) {
				joueurs.get(y).affichejoueur(y);
			}
			tapis.affichecarter(retourne, couleurAtout);
			// affiche sur le terminal le jeux du paquet
			// jeux.affiche();
			// affiche sur l'interface graphique le jeux du joueur 0
			tapis.affichejeux(joueurs.get(0).getMain());
			
			// prend premier tour
			n = 2;
			numeroJoueur = donne;
			indice = 0;
			while (n != 0) {
				numeroJoueur++;
				if (numeroJoueur > 3) {
					numeroJoueur = 0;
				}
				joueurs.get(numeroJoueur).prendpremier(numeroJoueur, retourne, tapis.nbcartej[numeroJoueur]);
				if (joueurs.get(numeroJoueur).getRecoitval() == 1) {
					prend = numeroJoueur;
					jeux.modifiemaitre(retourne, 0);
					n = 0;
				}
				indice++;
				if (indice == 4) {
					n = 0;
				}
			}
			
			// prend deuxieme tour
			if (prend == 4) {
				n = 2;
				numeroJoueur = donne;
				indice = 0;
				while (n != 0) {
					numeroJoueur++;
					if (numeroJoueur > 3) {
						numeroJoueur = 0;
					}
					joueurs.get(numeroJoueur).prenddeuxieme(numeroJoueur, retourne, tapis.nbcartej[numeroJoueur]);
					if (joueurs.get(numeroJoueur).getRecoitval() == 1) {
						prend = numeroJoueur;
						couleurAtout = joueurs.get(numeroJoueur).getChoixatout();
						n = 0;
					}
					indice++;
					if (indice == 4) {
						n = 0;
					}
				}
			}
			
			if (prend == 4) {
				// remet les cartes dans le paquet
				for (int i = 0; i < 4; i++) {
					tapis.nbcartej[i] = jeux.remetjeux(
							joueurs.get(i).getMain(), tapis.nbcartej[i]);
				}
				jeux.getJeuxdist().set(jeux.getLongJeuxdist(), retourne);
				retourne = jeux.getBlanc();
				jeux.setLongJeuxdist(jeux.getLongJeuxdist() + 1);
			} else {
				tapis.joueurpris = ("Joueur " + prend);
				tapis.affichecarter(retourne, couleurAtout);
				// distribution du reste des cartes
				n = 2;
				numeroJoueur = donne;
				indice = 0;
				while (n != 0) {
					numeroJoueur++;
					if (numeroJoueur > 3) {
						numeroJoueur = 0;
					}
					if (prend == numeroJoueur) {
						for (int i = 0; i < 2; i++) {
							tapis.nbcartej[numeroJoueur] = joueurs.get(numeroJoueur).recoit(
									jeux.getJeuxdist(), tapis.nbcartej[numeroJoueur],
									jeux.getBlanc());
						}
						joueurs.get(numeroJoueur).getMain().set(7, retourne);
						retourne = jeux.getBlanc();
						jeux.setLongJeuxdist(jeux.getLongJeuxdist() - 2);
						tapis.nbcartej[numeroJoueur] = tapis.nbcartej[numeroJoueur] + 1;
					} else {
						for (int i = 0; i < 3; i++) {
							tapis.nbcartej[numeroJoueur] = joueurs.get(numeroJoueur).recoit(
									jeux.getJeuxdist(), tapis.nbcartej[numeroJoueur],
									jeux.getBlanc());
						}
						jeux.setLongJeuxdist(jeux.getLongJeuxdist() - 3);
					}
					indice++;
					if (indice == 4) {
						n = 0;
					}
				}
				tapis.affichecarter(retourne, couleurAtout);
				joueurs.get(0).trijeux(couleurAtout);	
				tapis.affichejeux(joueurs.get(0).getMain() );
				tapis.effacecarteplis();

				// joue
				for (int y = 0; y < 8; y++) {
					if (y == 0) {
						numeroJoueur = donne + 1;
					} else {
						numeroJoueur = gagne;
					}
					
					jeux.setCarteJoue(CouleurEnum.NotInitialized);
					indice = 0;
					while (indice != 4) {

						if (numeroJoueur > 3) {
							numeroJoueur = 0;
						}
						
						Terminal.ecrireStringln("le joueur " + numeroJoueur + " joue");
						System.out.println("JeuxBelote avant-----------------------------------------------");
						System.out.println("getTapisJEux : " + jeux.getTapisjeux() + "Joueur : " + numeroJoueur);
						System.out.println("JeuxBelote FIN---------------------------------------------");
						

						jeux.getTapisjeux().set(numeroJoueur, joueurs.get(numeroJoueur).jouepremier(numeroJoueur, jeux.getBlanc(), jeux.getCarteJoue(), couleurAtout));
						
						System.out.println("JeuxBelote après-----------------------------------------------");
						System.out.println("getTapisJEux : " + jeux.getTapisjeux() + "Joueur : " + numeroJoueur);
						System.out.println("JeuxBelote FIN---------------------------------------------");
						
						if (indice == 0) {
							jeux.setCarteJoue(jeux.getTapisjeux().get(numeroJoueur).getCouleur());
						}
						tapis.nbcartej[numeroJoueur] = tapis.nbcartej[numeroJoueur] - 1;
						tapis.affichej(jeux.getTapisjeux().get(numeroJoueur), numeroJoueur);
						tapis.affichejeux(joueurs.get(0).getMain());
						indice++;
						numeroJoueur++;
					}
					
					Terminal.ecrireStringln("Pause appuyer sur la touche entrer pour continuer");
					Terminal.lireString();
					gagne = jeux.remportemanche(couleurAtout, y);
					// affiche la joue sur l'interface graphique
					for (int yy = 0; yy < 4; yy++) {
						tapis.affichej(jeux.getTapisjeux().get(yy), yy);
					}
					Terminal.ecrireStringln("le joueur " + gagne
							+ " gagne le plis");
					// affiche les plis
					jeux.affiche2();
				}
				// affiche le jeux des joueur
				for (int y = 0; y < 4; y++) {
					joueurs.get(y).affichejoueur(y);
				}
				// Qui gagne la manche
				if (jeux.getPosplis()[0] == 0 || jeux.getPosplis()[1] == 0) {
					if (jeux.getPosplis()[0] == 0) {
						tapis.pointj1j3 = tapis.pointj1j3 + 250;
					} else {
						tapis.pointj0j2 = tapis.pointj0j2 + 250;
					}
				} else {
					if (Arbitre.gagneManche(jeux.getPointEquipe1(),jeux.getPointEquipe2(), prend)) {
						tapis.pointj1j3 = tapis.pointj1j3
								+ jeux.getPointEquipe2();
						tapis.pointj0j2 = tapis.pointj0j2
								+ jeux.getPointEquipe1();
					} else {
						if (prend == 1 || prend == 3) {
							tapis.pointj0j2 = tapis.pointj0j2 + 162;
						} else {
							tapis.pointj1j3 = tapis.pointj1j3 + 162;
						}
					}
				}
				// remet les cartes dans le paquet
				jeux.remetjeux2();
				couleurAtout = CouleurEnum.NotInitialized;
				tapis.affichecarter(retourne, couleurAtout);
				tapis.affichejeux(joueurs.get(0).getMain());
			}
			// on coupe
			jeux.coupe(donne);
			// change de donneur
			donne = Arbitre.quidonne(donne);
			if (tapis.pointj1j3 > 500 && tapis.pointj1j3 > tapis.pointj0j2) {
				tapis.gagnant = ("Joueur 1 et joueur 3 gagne je jeux");
				// tapis.setVisible(false);
				// System.exit(0);
			}

			if (tapis.pointj0j2 > 500 && tapis.pointj0j2 > tapis.pointj1j3) {
				tapis.gagnant = ("Joueur 0 et joueur 2 gagne je jeux");
				// tapis.setVisible(false);
				// System.exit(0);
			}

			tapis.raffiche();

			pmax = Arbitre.testfinmanche(tapis.pointj1j3, tapis.pointj0j2);

		}
	}
}