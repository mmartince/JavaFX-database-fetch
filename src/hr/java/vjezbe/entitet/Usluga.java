package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 
 * @author Amalah
 * @param klijent klijent kojem se obavlja usluga
 * @param oznakaVrsteUsluge ozunaka vrste usluge
 * @param oznakaOpisaUsluge oznaka opisa usluge
 * @param datumUsluge datum kada je usluga obavljena usluge
 * @param cijenaUsluge cijena usluge
 * @param uslugaObavljena da li je usluga obavljena
 * @param uslugaNaplacena da li je usluga naplacena
 */
public class Usluga implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6742475920660350652L;
	
	private Integer id;
	private Klijent klijent;
	private VrstaUsluge oznakaVrsteUsluge;
	private String oznakaOpisaUsluge;
	private LocalDate datumUsluge;
	private BigDecimal cijenaUsluge;
	private Boolean uslugaObavljena = false;
	private Boolean uslugaNaplacena = false;

	public Usluga(Klijent klijent, VrstaUsluge oznakaVrsteUsluge, String oznakaOpisaUsluge, LocalDate datumUsluge,
			BigDecimal cijenaUsluge, Boolean uslugaObavljena, Boolean uslugaNaplacena)
	{
		super();
		this.klijent = klijent;
		this.oznakaVrsteUsluge = oznakaVrsteUsluge;
		this.oznakaOpisaUsluge = oznakaOpisaUsluge;
		this.datumUsluge = datumUsluge;
		this.cijenaUsluge = cijenaUsluge;
		this.uslugaObavljena = uslugaObavljena;
		this.uslugaNaplacena = uslugaNaplacena;
	}

	public Klijent getKlijent()
	{
		return klijent;
	}

	public void setKlijent(Klijent klijent)
	{
		this.klijent = klijent;
	}

	public VrstaUsluge getOznakaVrsteUsluge()
	{
		return oznakaVrsteUsluge;
	}

	public void setOznakaVrsteUsluge(VrstaUsluge oznakaVrsteUsluge)
	{
		this.oznakaVrsteUsluge = oznakaVrsteUsluge;
	}

	public String getOznakaOpisaUsluge()
	{
		return oznakaOpisaUsluge;
	}

	public void setOznakaOpisaUsluge(String oznakaOpisaUsluge)
	{
		this.oznakaOpisaUsluge = oznakaOpisaUsluge;
	}

	public LocalDate getDatumUsluge()
	{
		return datumUsluge;
	}

	public void setDatumUsluge(LocalDate datumUsluge)
	{
		this.datumUsluge = datumUsluge;
	}

	public BigDecimal getCijenaUsluge()
	{
		return cijenaUsluge;
	}

	public void setCijenaUsluge(BigDecimal cijenaUsluge)
	{
		this.cijenaUsluge = cijenaUsluge;
	}

	public Boolean getUslugaObavljena()
	{
		return uslugaObavljena;
	}

	public void setUslugaObavljena(Boolean uslugaObavljena)
	{
		this.uslugaObavljena = uslugaObavljena;
	}

	public Boolean getUslugaNaplacena()
	{
		return uslugaNaplacena;
	}

	public void setUslugaNaplacena(Boolean uslugaNaplacena)
	{
		this.uslugaNaplacena = uslugaNaplacena;
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
