package noyau.classesMetier;

import java.util.Comparator;

public class CompAtout implements Comparator<Carte>{
	private CouleurEnum atout;

	public CompAtout(CouleurEnum atout){
		this.atout = atout;
	}

	@Override
	public int compare(Carte o1, Carte o2) {
		int res = 0;
		if(o1.calculerValeurCarte(this.atout) > o2.calculerValeurCarte(this.atout)){
			res = 1;
		} else {
			if(o1.calculerValeurCarte(this.atout) < o2.calculerValeurCarte(this.atout)){
				res = -1;
			} else {
				res = o1.compareTo(o2);
			}
		}
		return res;
	}
}