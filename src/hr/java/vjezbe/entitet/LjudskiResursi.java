package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LjudskiResursi <T extends Osoba>
{
	 List<T> listaLjudskihResursa=new ArrayList<>();


	public T getListaLjudskihResursa(Integer i)
	{
		return listaLjudskihResursa.get(i);
	}
	
	public void add(T noviObjekt)
	{
		listaLjudskihResursa.add(noviObjekt);
	}
	public List<T> getSortedList()
	{
		Collections.sort(listaLjudskihResursa, (p1, p2) -> p1.getPrezime().compareTo(p2.getPrezime()));
		return listaLjudskihResursa;
	}
}
