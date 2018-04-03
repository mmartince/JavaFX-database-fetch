package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 
 * @author Amalah
 * @param oib
 *            osobni identifikacijski broj klijenta
 * @param brojTelefona
 *            broj telefona klijenta
 * @param eMail
 *            email klijenta
 * @param datumRodjenja
 *            datum rodjenja klijenta
 */
public class Klijent extends Osoba implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1021989922861275901L;

	public static final int brojKlijenata = 2;

	private Integer id;
	private String oib;
	private String brojTelefona;
	private String email;
	private LocalDate datumRodjenja;

	public Klijent(String oib, String prezime, String ime, String brojTelefona, String eMail, LocalDate datumRodjenja)
	{
		super(ime, prezime);
		this.oib = oib;
		this.brojTelefona = brojTelefona;
		this.email = eMail;
		this.datumRodjenja = datumRodjenja;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getOib()
	{
		return oib;
	}

	public void setOib(String oib)
	{
		this.oib = oib;
	}

	public String getBrojTelefona()
	{
		return brojTelefona;
	}

	public void setBrojTelefona(String brojTelefona)
	{
		this.brojTelefona = brojTelefona;
	}

	public String getEmail()
	{
		return email;
	}

	public void seteMail(String eMail)
	{
		this.email = eMail;
	}

	public LocalDate getDatumRodjenja()
	{
		return datumRodjenja;
	}

	public void setDatumRodjenja(LocalDate datumRodjenja)
	{
		this.datumRodjenja = datumRodjenja;
	}
}
