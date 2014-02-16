/**
 * @class Arbitre
 * @author lacertus, Nathan
 * @resume classe représentant l'arbitre, il choisit le donneur, compte les
 *         points ...
 * */

public class Arbitre {
	/**
	 * @param int donne ; Contient le numero du joueur courant qui donne.
	 * @return int donne ; Contient le numero du joueur qui donne a la partie
	 *         suivant
	 * @resume Fonction qui retourne le numero du joueur devant donner a la
	 *         partie suivante. Cette fonction doit etre appele apres la fin
	 *         d'une partie.
	 * */
	public static int quidonne(int donne) {
		donne = (donne + 1) % 3;
		return donne;
	}

	/**
	 * @param
	 * @return
	 * @resume Identifie
	 * */
	public static int fig2(final Carte x) {
		int vaaff = 0;
		if (x.getFigure() == Figure.Sept) {
			vaaff = 1;
		}
		if (x.getFigure() == Figure.Huit) {
			vaaff = 2;
		}
		if (x.getFigure() == Figure.Neuf) {
			vaaff = 3;
		}
		if (x.getFigure() == Figure.Dix) {
			vaaff = 4;
		}
		if (x.getFigure() == Figure.Valet) {
			vaaff = 5;
		}
		if (x.getFigure() == Figure.Dame) {
			vaaff = 6;
		}
		if (x.getFigure() == Figure.Roi) {
			vaaff = 7;
		}
		if (x.getFigure() == Figure.As) {
			vaaff = 8;
		}
		return vaaff;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// ===================================
	// le rang d'une couleur de la carte
	public static int coulrang(final Carte x) {
		int vaaff2 = 0;
		if (x.getCouleur() == CouleurEnum.Coeur) {
			vaaff2 = 0;
		}
		if (x.getCouleur() == CouleurEnum.Pique) {
			vaaff2 = 1;
		}
		if (x.getCouleur() == CouleurEnum.Carreau) {
			vaaff2 = 2;
		}
		if (x.getCouleur() == CouleurEnum.Trefle) {
			vaaff2 = 3;
		}
		return vaaff2;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// ======================================
	// renvois la valeur d'une carte
	public static int Points(final Carte carte, final CouleurEnum couleurAtout) {
		int point1 = 0;
		CouleurEnum vcouleur;
		int vfigure;
		vcouleur = carte.getCouleur();
		vfigure = fig2(carte);
		if (couleurAtout == vcouleur) {
			switch (vfigure) {
			case 1:
				point1 = 0;
				break;
			case 2:
				point1 = 0;
				break;
			case 3:
				point1 = 14;
				break;
			case 4:
				point1 = 10;
				break;
			case 5:
				point1 = 20;
				break;
			case 6:
				point1 = 3;
				break;
			case 7:
				point1 = 4;
				break;
			case 8:
				point1 = 11;
				break;
			default:
				break;
			}
		} else {
			switch (vfigure) {
			case 1:
				point1 = 0;
				break;
			case 2:
				point1 = 0;
				break;
			case 3:
				point1 = 0;
				break;
			case 4:
				point1 = 10;
				break;
			case 5:
				point1 = 2;
				break;
			case 6:
				point1 = 3;
				break;
			case 7:
				point1 = 4;
				break;
			case 8:
				point1 = 11;
				break;
			default:
				break;
			}

		}
		return point1;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// =====================================================
	// points sur une manche
	public static int Pointsjeu(final Carte carte,
			final CouleurEnum couleurJoueur1, final CouleurEnum couleurJoueur2) {
		int point1 = 0;
		int vfigure;
		vfigure = fig2(carte);
		if (couleurJoueur1 == carte.getCouleur()) {
			switch (vfigure) {
			case 1:
				point1 = 9;
				break;
			case 2:
				point1 = 10;
				break;
			case 3:
				point1 = 15;
				break;
			case 4:
				point1 = 13;
				break;
			case 5:
				point1 = 16;
				break;
			case 6:
				point1 = 11;
				break;
			case 7:
				point1 = 12;
				break;
			case 8:
				point1 = 14;
				break;
			default:
				break;
			}
		} else {

			if (couleurJoueur2 == carte.getCouleur()) {
				switch (vfigure) {
				case 1:
					point1 = 1;
					break;
				case 2:
					point1 = 2;
					break;
				case 3:
					point1 = 3;
					break;
				case 4:
					point1 = 7;
					break;
				case 5:
					point1 = 4;
					break;
				case 6:
					point1 = 5;
					break;
				case 7:
					point1 = 6;
					break;
				case 8:
					point1 = 8;
					break;
				default:
					break;
				}
			} else {

				switch (vfigure) {
				case 0:
					point1 = 0;
					break;
				case 1:
					point1 = 0;
					break;
				case 2:
					point1 = 0;
					break;
				case 3:
					point1 = 0;
					break;
				case 4:
					point1 = 0;
					break;
				case 5:
					point1 = 0;
					break;
				case 6:
					point1 = 0;
					break;
				case 7:
					point1 = 0;
					break;
				case 8:
					point1 = 0;
					break;
				default:
					break;
				}

			}
		}
		return point1;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static boolean Gagnemanche(final int[] x, final int y) {
		boolean v = false;
		if (y == 1 || y == 3) {
			if (x[0] < x[1]) {
				v = true;
			} else {
				v = false;
			}
		} else {
			if (x[0] > x[1]) {
				v = true;
			} else {
				v = false;
			}
		}
		return v;
	}

	/**
	 * @param
	 * @return
	 * @resume test fijn manche pour savoir si la partie est fini
	 * */
	public static int testfinmanche(final int pointsEquipe1, int pointsEquipe2) {

		int pointsEquipeGagnante = 0;
		if (pointsEquipe1 < pointsEquipe2) {
			pointsEquipeGagnante = pointsEquipe2;
		} else {
			pointsEquipe2 = pointsEquipeGagnante;
		}
		return pointsEquipeGagnante;
	}

	/**
	 * @param
	 * @return
	 * @resume test carte jouer
	 * */
	public static boolean testcartejouee(final Carte[] main,
			final CouleurEnum y, final Carte uv, final CouleurEnum couleurAtout) {
		boolean v = false;
		boolean test;
		test = y.equals("");
		testc: if (test == true) {
			v = true;
			break testc;
		} else {
			// test si la carte est de la couleur du jeux jouer
			test = uv.getCouleur().equals(y);
			if (test == true) {
				v = true;
				break testc;
			} else {
				// test si une des cartes est de la couleur du jeux
				for (int i = 0; i < 8; i++) {
					test = main[i].getCouleur().equals(y);
					if (test == true) {
						v = false;
						Terminal.ecrireStringln("Jouer une carte de la couleur demander "
								+ y);
						break testc;
					}
				}
				// Test si la carte est de la couleur de l'atout
				test = uv.getCouleur().equals(couleurAtout);
				if (test == true) {
					v = true;
					break testc;
				} else {
					// test si au moins une carte est de la couleur de l'atout
					for (int i = 0; i < 8; i++) {
						test = main[i].getCouleur().equals(couleurAtout);
						if (test == true) {
							v = false;
							Terminal.ecrireStringln("Jouer une carte d'atout  "
									+ couleurAtout);
							break testc;
						}
					}
					v = true;
					break testc;
				}
			}
		}
		return v;
	}

	/**
	 * @param une
	 *            main de type Carte[], la carte retournee en string, la couleur
	 *            d'atout en string
	 * @return un int => la position de la carte a jouer dans la main
	 * @resume test pour jeux nieme
	 * */
	public static int testcartejouee2(final Carte[] main,
			final CouleurEnum carteret, final CouleurEnum couleurAtout) {
		int indiceCarteAJouer = 0;
		Terminal.ecrireStringln("Carte demandee : " + carteret);
		boolean test = carteret.equals("");

		/*
		 * A FAIRE changer la methode pour qu'elle prenne la bonne carte - jouer
		 * un atout si on est dans l'equipe qui a pris - le valet ou un petit
		 * pour faire tomber les autre atouts si le valet n'est pas passé -
		 * jouer un as si pas d'atouts ou de bons atouts - jouer une petite
		 * carte non atout si aucun des cas precedents
		 */

		testc: if (!test) {
			for (int i = 0; i < 8; i++) {
				test = main[i].getCouleur().equals(carteret);
				if (test) {
					indiceCarteAJouer = i;
					break testc;
				}
			}
			for (int i = 0; i < 8; i++) {
				test = main[i].getCouleur().equals(couleurAtout);
				if (test) {
					indiceCarteAJouer = i;
					break testc;
				}
			}
			indiceCarteAJouer = 0;
		}

		return indiceCarteAJouer;
	}
}