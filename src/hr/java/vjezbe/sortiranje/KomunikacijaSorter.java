package hr.java.vjezbe.sortiranje;

import java.util.Comparator;

import hr.java.vjezbe.entitet.Komunikacija;

public class KomunikacijaSorter implements Comparator<Komunikacija>
{

	@Override
	public int compare(Komunikacija k1, Komunikacija k2)
	{
		if(k1.getVrijeme().isBefore(k2.getVrijeme())) return 1;
		else if(k1.getVrijeme().isAfter(k2.getVrijeme())) return -1;
		else if(k1.getKlijent().getPrezime().compareTo(k2.getKlijent().getPrezime())==1) return 1;
		else return 0;
	}
}
