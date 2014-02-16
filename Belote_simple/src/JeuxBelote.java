/**
 * @class JeuxBelote
 * @author lacertus, Nathan
 * @resume classe principale permettant de lancer la partie
 * 
 * */

public class JeuxBelote {

	public static void main(String[] arguments) {
		Paquet jeux = new Paquet();
		JoueurIntelligence[] joueur = new JoueurIntelligence[4];
		Fenetre tapis = new Fenetre();
		int prend = 4; // variable pour savoir qui prend
		CouleurEnum jeuxAtout = CouleurEnum.NotInitialized;// variable pour la couleur
													// d'atout
		// qui donne le jeux
		int donne = 0;
		int n = 0;
		int n1 = 0;
		int n2 = 0;
		int gagne = 0;
		Carte retourne;
		for (int i = 0; i < 4; i++) {
			joueur[i] = new JoueurIntelligence();
		}
		// ========================================================
		jeux.creelejeux();
		// affiche le nombre de carte dans le paquet initial
		Terminal.ecrireStringln("valeur " + jeux.getInitial().length);
		// ========================================================
		// melange le jeux
		jeux.donneJeuBeloteBattu();
		tapis.initFenetre();
		for (int pmax = 0; pmax < 500;) {
			jeux.razpaquet();
			// affiche sur le terminal le jeux du paquet
			// jeux.affiche();
			// affiche le jeux des joueurs
			for (int y = 0; y < 4; y++) {
				joueur[y].affichejoueur(y);
			}
			// =====================
			// donne premier tour
			n = 2;
			n1 = donne;
			n2 = 0;
			// donne 3 cartes
			while (n != 0) {
				n1++;
				if (n1 > 3) {
					n1 = 0;
				}
				for (int i = 0; i < 3; i++) {
					tapis.nbcartej[n1] = joueur[n1].recoit(jeux.getJeuxdist(), tapis.nbcartej[n1], jeux.getBlanc());
				}
				jeux.setLongJeuxdist(jeux.getLongJeuxdist() - 3);
				n2++;
				if (n2 == 4) {
					n = 0;
				}
			}
			// donne 2 cartes
			n = 2;
			n1 = donne;
			n2 = 0;
			while (n != 0) {
				n1++;
				if (n1 > 3) {
					n1 = 0;
				}
				for (int i = 0; i < 2; i++) {
					tapis.nbcartej[n1] = joueur[n1].recoit(jeux.getJeuxdist(), tapis.nbcartej[n1], jeux.getBlanc());
				}
				jeux.setLongJeuxdist(jeux.getLongJeuxdist() - 2);
				n2++;
				if (n2 == 4) {
					n = 0;
				}
			}
			// retourne la carte pour atout
			retourne = jeux.getJeuxdist()[0];
			for (int v = 0; v < 32; v++) {
				if (v + 1 < 32) {
					jeux.getJeuxdist()[v] = jeux.getJeuxdist()[v + 1];
				} else {
					jeux.getJeuxdist()[v] = jeux.getBlanc();
				}
			}
			jeux.setLongJeuxdist(jeux.getLongJeuxdist() - 1);
			jeuxAtout = retourne.getCouleur();
			tapis.affiche();
			// affiche le jeux des joueurs
			for (int y = 0; y < 4; y++) {
				joueur[y].affichejoueur(y);
			}
			tapis.affichecarter(retourne, jeuxAtout);
			// =========================================
			// affiche sur le terminal le jeux du paquet
			// jeux.affiche();
			// affiche sur l'interface graphique le jeux du joueur 0
			
			tapis.affichejeux(joueur[0].getMonPaquet());
			// prend premier tour
			n = 2;
			n1 = donne;
			n2 = 0;
			while (n != 0) {
				n1++;
				if (n1 > 3) {
					n1 = 0;
				}
				joueur[n1].prendpremier(n1, retourne, tapis.nbcartej[n1]);
				if (joueur[n1].getRecoitval() == 1) {
					prend = n1;
					jeux.modifiemaitre(retourne, 0);
					n = 0;
				}
				n2++;
				if (n2 == 4) {
					n = 0;
				}
			}
			// prend deuxieme tour
			if (prend == 4) {
				n = 2;
				n1 = donne;
				n2 = 0;
				while (n != 0) {
					n1++;
					if (n1 > 3) {
						n1 = 0;
					}
					joueur[n1].prenddeuxieme(n1, retourne, tapis.nbcartej[n1]);
					if (joueur[n1].getRecoitval() == 1) {
						prend = n1;
						jeuxAtout = joueur[n1].getChoixatout();
						n = 0;
					}
					n2++;
					if (n2 == 4) {
						n = 0;
					}
				}
			}
			if (prend == 4) {
				// remet les cartes dans le paquet
				for (int i = 0; i < 4; i++) {
					tapis.nbcartej[i] = jeux.remetjeux(
							joueur[i].getMonPaquet(), tapis.nbcartej[i]);
				}
				jeux.getJeuxdist()[jeux.getLongJeuxdist()] = retourne;
				retourne = jeux.getBlanc();
				jeux.setLongJeuxdist(jeux.getLongJeuxdist() + 1);
			} else {
				tapis.joueurpris = ("Joueur " + prend);
				tapis.affichecarter(retourne, jeuxAtout);
				// distribution du reste des cartes
				n = 2;
				n1 = donne;
				n2 = 0;
				while (n != 0) {
					n1++;
					if (n1 > 3) {
						n1 = 0;
					}
					if (prend == n1) {
						for (int i = 0; i < 2; i++) {
							tapis.nbcartej[n1] = joueur[n1].recoit(
									jeux.getJeuxdist(), tapis.nbcartej[n1],
									jeux.getBlanc());
						}
						joueur[n1].getMonPaquet()[7] = retourne;
						retourne = jeux.getBlanc();
						jeux.setLongJeuxdist(jeux.getLongJeuxdist() - 2);
						tapis.nbcartej[n1] = tapis.nbcartej[n1] + 1;
					} else {
						for (int i = 0; i < 3; i++) {
							tapis.nbcartej[n1] = joueur[n1].recoit(
									jeux.getJeuxdist(), tapis.nbcartej[n1],
									jeux.getBlanc());
						}
						jeux.setLongJeuxdist(jeux.getLongJeuxdist() - 3);
					}
					n2++;
					if (n2 == 4) {
						n = 0;
					}
				}
				tapis.affichecarter(retourne, jeuxAtout);
				joueur[0].trijeux(jeuxAtout);
				
				System.out.println(joueur[0].getMonPaquet());
				
				tapis.affichejeux(joueur[0].getMonPaquet());
				tapis.effacecarteplis();

				// joue
				for (int y = 0; y < 8; y++) {
					if (y == 0) {
						n1 = donne + 1;
					} else {
						n1 = gagne;
					}
					n2 = 0;
					jeux.setCarteJoue(CouleurEnum.NotInitialized);
					while (n2 != 4) {

						if (n1 > 3) {
							n1 = 0;
						}
						Terminal.ecrireStringln("le joueur " + n1 + " joue");
						jeux.getTapisjeux()[n1] = joueur[n1].jouepremier(n1, jeux.getBlanc(), jeux.getCarteJoue(), jeuxAtout);
						if (n2 == 0) {
							jeux.setCarteJoue(jeux.getTapisjeux()[n1].getCouleur());
						}
						tapis.nbcartej[n1] = tapis.nbcartej[n1] - 1;
						tapis.affichej(jeux.getTapisjeux()[n1], n1);
						tapis.affichejeux(joueur[0].getMonPaquet());
						n2++;
						n1++;
					}
					Terminal.ecrireStringln("Pause appuyer sur la touche entrer pour continuer");
					Terminal.lireString();
					gagne = jeux.remportemanche(jeuxAtout, y);
					// affiche la joue sur l'interface graphique
					for (int yy = 0; yy < 4; yy++) {
						tapis.affichej(jeux.getTapisjeux()[yy], yy);
					}
					Terminal.ecrireStringln("le joueur " + gagne
							+ " gagne le plis");
					// affiche les plis
					jeux.affiche2();
				}
				// affiche le jeux des joueur
				for (int y = 0; y < 4; y++) {
					joueur[y].affichejoueur(y);
				}
				// =========================
				// Qui gagne la manche
				if (jeux.getPosplis()[0] == 0 || jeux.getPosplis()[1] == 0) {
					if (jeux.getPosplis()[0] == 0) {
						tapis.pointj1j3 = tapis.pointj1j3 + 250;
					} else {
						tapis.pointj0j2 = tapis.pointj0j2 + 250;
					}
				} else {
					if (Arbitre.Gagnemanche(jeux.getPointplis(), prend)) {
						tapis.pointj1j3 = tapis.pointj1j3
								+ jeux.getPointplis()[1];
						tapis.pointj0j2 = tapis.pointj0j2
								+ jeux.getPointplis()[0];
					} else {
						if (prend == 1 || prend == 3) {
							tapis.pointj0j2 = tapis.pointj0j2 + 162;
						} else {
							tapis.pointj1j3 = tapis.pointj1j3 + 162;
						}
					}
				}
				// =========================
				// remet les cartes dans le paquet
				jeux.remetjeux2();
				jeuxAtout = CouleurEnum.NotInitialized;
				tapis.affichecarter(retourne, jeuxAtout);
				tapis.affichejeux(joueur[0].getMonPaquet());
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