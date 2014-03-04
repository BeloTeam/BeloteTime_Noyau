package entite;

import java.io.IOException;

import noyau.classesMetier.Carte;
import noyau.classesMetier.CouleurEnum;
import noyau.classesMetier.PositionEnum;
import noyau.classesMetier.TableDeJeu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class JoueurHumain extends Joueur {

	static BufferedReader in = new BufferedReader(new InputStreamReader(
			System.in));

	public JoueurHumain(PositionEnum position, String nom, TableDeJeu table) {
		super(position, nom, table);

	}

	public boolean prendPremiereDonne() {
		System.out.println("Votre main :\n" + this.getMain()
				+ "\nPrenez vous pour l'atout : " + this.getTable().getCarteDonne().toString()
				+ " (1-oui/0-non)");
		int rep = 0;
		try {
			rep = Integer.parseInt(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		try {
			rep = Integer.parseInt(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		System.out
				.println("-------------JEU--------------\n" + this.toString());
		while (carteJouer == null) {
			if (this.getTable().getPliCourant().getTaillePaquet() != 0) {
				System.out.println("Pli actuel :" + this.getTable().getPliCourant());
				System.out.println("Couleur demande : " + this.getTable().getPliCourant().getCouleurDemandee());
				System.out.println("Votre main :\n" + this.getMain().toString());
				if (this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee()) != null && !this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee()).isEmpty()) {
					tailleEnsembleCartePropose = this.getMain()
							.get(this.getTable().getPliCourant().getCouleurDemandee()).size();
					System.out.println("\nVous avez le choix entre : "
							+ this.getMain().get(this.getTable().getPliCourant().getCouleurDemandee()));

				} else {
					tailleEnsembleCartePropose = this.getMain()
							.getTaillePaquet();
					peutJouerCouleurDuPli = false;
					System.out
							.println("\nVous n'avez pas la couleur demande! ");
				}
			} else {
				tailleEnsembleCartePropose = this.getMain().getTaillePaquet();
				peutJouerCouleurDuPli = false;
				System.out.println("Vous commencez, votre main :\n"
						+ this.getMain());
			}
			System.out.println("\nQuelle carte jouer : [0 a "
					+ (tailleEnsembleCartePropose - 1) + "]");
			int rep = 0;
			try {
				// CouleurEnum couleur = ...; cf. le pavet en dessous
				// FigureEnum figure = ...; cf. le pavet en dessous
				rep = Integer.parseInt(in.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
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
					carteJouer = this.getMain().hashtableToList().get(rep);
					this.getMain().supprimer(carteJouer);
				} else {
					carteJouer = this.getMain().getList(this.getTable().getPliCourant().getCouleurDemandee()).get(rep);
					this.getMain().supprimer(carteJouer);
				}
				this.getMain().supprimer(carteJouer);
			} else {
				System.out.println("ERREUR");
			}
		}
		return carteJouer;
	}
}
