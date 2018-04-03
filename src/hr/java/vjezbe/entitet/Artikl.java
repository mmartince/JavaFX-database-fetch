package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * 
 * @author Amalah
 * @param  naziv naziv artikla
 * @param kategorija kategorija u koju spada artikl
 */
public class Artikl implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9105225840089368203L;

	public static final int brojArtikala=3;
	
	Integer id;
	private String naziv;
	private KategorijaArtikla kategorija;
	
	
	public Artikl(String naziv, KategorijaArtikla kategorija)
	{
		super();
		this.naziv = naziv;
		this.kategorija = kategorija;
	}


	public String getNaziv()
	{
		return naziv;
	}


	public void setNaziv(String naziv)
	{
		this.naziv = naziv;
	}


	public KategorijaArtikla getKategorija()
	{
		return kategorija;
	}


	public void setKategorija(KategorijaArtikla kategorija)
	{
		this.kategorija = kategorija;
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
