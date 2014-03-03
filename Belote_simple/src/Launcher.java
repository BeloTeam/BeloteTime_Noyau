
import noyau.classesMetier.TableDeJeu;


public class Launcher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TableDeJeu t = new TableDeJeu();
		//t.getTas().melanger(200); Pour les tests, evitons de melanger les cartes au debut
		t.getGm().debuterPartie();
	}

}
