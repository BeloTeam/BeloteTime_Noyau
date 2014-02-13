/**
 *  @class Figure
 *  @author lacertus, Nathan
 *  @resume Définition des cartes de 7 .. As
 * 
 * */

class Figure {
	
	private String nom;

	public static Figure Sept = new Figure("Sept");
	public static Figure Huit = new Figure("Huit");
	public static Figure Neuf = new Figure("Neuf");
	public static Figure Dix = new Figure("Dix");
	public static Figure Valet = new Figure("Valet");
	public static Figure Dame = new Figure("Dame");
	public static Figure Roi = new Figure("Roi");
	public static Figure As = new Figure("As");
	public static Figure Blanc = new Figure("");
	
	
	public Figure ( String nomFigure ){
		this.nom = nomFigure ;
	}
	
	public String getNom() {
		return nom;
	}
}