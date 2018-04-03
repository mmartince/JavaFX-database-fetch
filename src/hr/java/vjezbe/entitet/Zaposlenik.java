package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * 
 * @author Amalah
 * @param korisnickoIme korisnicko ime zaposlenika
 * @param sifraZaposlenika sifra zaposlenika
 */
public class Zaposlenik extends Osoba implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7793545409318923673L;

	public static final int brojZaposlenika=2;
	
	private Integer id;
	private String korisnickoIme;
	private String sifraZaposlenika;

	public Zaposlenik(String korisnickoIme, String ime, String prezime, String sifraZaposlenika)
	{
		super(ime,prezime);
		this.korisnickoIme = korisnickoIme;
		this.sifraZaposlenika = sifraZaposlenika;
	}

	public String getKorisnickoIme()
	{
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme)
	{
		this.korisnickoIme = korisnickoIme;
	}

	public String getSifraZaposlenika()
	{
		return sifraZaposlenika;
	}

	public void setSifraZaposlenika(String sifraZaposlenika)
	{
		this.sifraZaposlenika = sifraZaposlenika;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}
}
