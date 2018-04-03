package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.util.List;

public class TestClass implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4254669235072360351L;

	List<Klijent> klijenti;
	KategorijaArtikla kategorija;
	String ime;
	
	public TestClass(List<Klijent> klijenti, KategorijaArtikla kategorija, String ime)
	{
		this.klijenti = klijenti;
		this.kategorija = kategorija;
		this.ime = ime;
	}

	public List<Klijent> getKlijenti()
	{
		return klijenti;
	}

	public void setKlijenti(List<Klijent> klijenti)
	{
		this.klijenti = klijenti;
	}

	public KategorijaArtikla getKategorija()
	{
		return kategorija;
	}

	public void setKategorija(KategorijaArtikla kategorija)
	{
		this.kategorija = kategorija;
	}

	public String getIme()
	{
		return ime;
	}

	public void setIme(String ime)
	{
		this.ime = ime;
	}
	
}
