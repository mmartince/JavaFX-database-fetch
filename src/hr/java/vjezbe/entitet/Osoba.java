package hr.java.vjezbe.entitet;

/**
 * 
 * @author Amalah
 * @param ime ime osobe
 * @param prezime prezime osobe
 */
public abstract class Osoba
{
	private String ime;
	private String prezime;
	
	public Osoba(String ime, String prezime)
	{
		super();
		this.ime = ime;
		this.prezime = prezime;
	}
	
	public String getIme()
	{
		return ime;
	}
	public void setIme(String ime)
	{
		this.ime = ime;
	}
	public String getPrezime()
	{
		return prezime;
	}
	public void setPrezime(String prezime)
	{
		this.prezime = prezime;
	}
}
