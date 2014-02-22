package ia;

import java.util.List;

import noyau.Carte;
import noyau.CouleurEnum;
import noyau.Main;
import noyau.Terminal;


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
	 * @resume
	 * */
	// renvois la valeur d'une carte
	public static int points(final Carte carte, final CouleurEnum couleurAtout) {
		int point = 0;
		
		switch (carte.getFigure()) {
		case Sept:
			point = 0;
			break;
		case Huit:
			point = 0;
			break;
		case Neuf:
			if(couleurAtout.equals(carte.getCouleur())) {
				point = 14;
			}else{
				point = 0;
			}
			break;
		case Dix:
			point = 10;
			break;
		case Valet:
			if(couleurAtout.equals(carte.getCouleur())) {
				point = 20;
			}else{
				point = 2;
			}
			break;
		case Dame:
			point = 3;
			break;
		case Roi:
			point = 4;
			break;
		default : // As
			point = 11;
			break;
		}
		return point;
	}
	
	
	/**
	 * compte les points d'une main
	 * @param
	 * @return
	 * */
	public static int pointsMain(final Main main, final CouleurEnum couleurAtout)
	{
		int pointTotal=0;
		for (Carte carte : main.getMain()) {
			pointTotal += points(carte, couleurAtout);
		}
		
		return pointTotal;
	}
	

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	// points sur une manche
	public static int pointsjeu(final Carte carte, final CouleurEnum couleurJoueur1, final CouleurEnum couleurJoueur2) {
		int point = 0;
		if (couleurJoueur1.equals(carte.getCouleur())) {
			switch (carte.getFigure()) {
			case Sept:
				if (couleurJoueur1.equals(carte.getCouleur())) {
					point = 9;
				} else {
					if (couleurJoueur2.equals(carte.getCouleur())) {
						point = 1;
					} else {
						point = 0;
					}
				}
				break;
			case Huit:
				if (couleurJoueur1.equals(carte.getCouleur())) {
					point = 10;
				} else {
					if (couleurJoueur2.equals(carte.getCouleur())) {
						point = 2;
					} else {
						point = 0;
					}
				}
				break;
			case Neuf:
				if (couleurJoueur1.equals(carte.getCouleur())) {
					point = 15;
				} else {
					if (couleurJoueur2.equals(carte.getCouleur())) {
						point = 3;
					} else {
						point = 0;
					}
				}
				break;
			case Dix:
				if (couleurJoueur1.equals(carte.getCouleur())) {
					point = 13;
				} else {
					if (couleurJoueur2.equals(carte.getCouleur())) {
						point = 7;
					} else {
						point = 0;
					}
				}
				break;
			case Valet:
				if (couleurJoueur1.equals(carte.getCouleur())) {
					point = 16;
				} else {
					if (couleurJoueur2.equals(carte.getCouleur())) {
						point = 4;
					} else {
						point = 0;
					}
				}
				break;
			case Dame:
				if (couleurJoueur1.equals(carte.getCouleur())) {
					point = 11;
				} else {
					if (couleurJoueur2.equals(carte.getCouleur())) {
						point = 5;
					} else {
						point = 0;
					}
				}
				break;
			case Roi:
				if (couleurJoueur1.equals(carte.getCouleur())) {
					point = 12;
				} else {
					if (couleurJoueur2.equals(carte.getCouleur())) {
						point = 6;
					} else {
						point = 0;
					}
				}
				break;
			default: // As
				if (couleurJoueur1.equals(carte.getCouleur())) {
					point = 14;
				} else {
					if (couleurJoueur2.equals(carte.getCouleur())) {
						point = 8;
					} else {
						point = 0;
					}
				}
				break;
			}
		} 
		return point;
	}

	/**
	 * @param
	 * @return
	 * @resume
	 * */
	public static boolean gagneManche(final int[] x, final int y) {
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
	 * @name testcartejouee
	 * @param Carte[] main représente la main du joueur
	 * @param CouleurEnum y représente la couleur ...
	 * @return boolean 
	 * @resume test carte jouer
	 * */
	public static boolean testcartejouee(final /*List<Carte>*/Main main,
			final CouleurEnum y, final Carte uv, final CouleurEnum couleurAtout) {
		boolean v = false;

		testc: if (y.equals(CouleurEnum.NotInitialized)) {
			v = true;
			break testc;
		} else {
			// test si la carte est de la couleur du jeux jouer
			if (uv.getCouleur().equals(y)) {
				v = true;
				break testc;
			} else {
				// test si une des cartes est de la couleur du jeux
				for (int i = 0; i < 8; i++) {
					if (main.get(i).getCouleur().equals(y)) {
						v = false;
						Terminal.ecrireStringln("Jouer une carte de la couleur demander "
								+ y);
						break testc;
					}
				}
				// Test si la carte est de la couleur de l'atout
				if (uv.getCouleur().equals(couleurAtout)) {
					v = true;
					break testc;
				} else {
					// test si au moins une carte est de la couleur de l'atout
					for (int i = 0; i < 8; i++) {
						if (main.get(i).getCouleur().equals(couleurAtout)) {
							v = false;
							Terminal.ecrireStringln("Jouer une carte d'atout  " + couleurAtout);
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
	 * @param une main de type Carte[] la carte retournee en string la couleur d'atout en string
	 * @return un int => la position de la carte a jouer dans la main
	 * @resume test pour jeux nieme
	 * */
	public static int testcartejouee2(final /*List<Carte>*/Main main, final CouleurEnum carteret, final CouleurEnum couleurAtout) {
		int indiceCarteAJouer = 0;
		Terminal.ecrireStringln("Carte demandee : " + carteret);

		/*
		 * A FAIRE changer la methode pour qu'elle prenne la bonne carte - jouer
		 * un atout si on est dans l'equipe qui a pris - le valet ou un petit
		 * pour faire tomber les autre atouts si le valet n'est pas passé -
		 * jouer un as si pas d'atouts ou de bons atouts - jouer une petite
		 * carte non atout si aucun des cas precedents
		 */

		testc: if (!carteret.equals(CouleurEnum.NotInitialized)) {
			for (int i = 0; i < 8; i++) {
				if (main.get(i).getCouleur().equals(carteret.toString())) {
					indiceCarteAJouer = i;
					break testc;
				}
			}
			for (int i = 0; i < 8; i++) {
				if (main.get(i).getCouleur().equals(couleurAtout)) {
					indiceCarteAJouer = i;
					break testc;
				}
			}
			indiceCarteAJouer = 0;
		}
		return indiceCarteAJouer;
	}
}