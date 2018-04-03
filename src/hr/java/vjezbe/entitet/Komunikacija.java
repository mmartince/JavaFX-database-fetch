package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 
 * @author Amalah
 * @param klijetn Klijent s kojim se komunicira
 * @param zaposlenik zaposlenik koji komunicira s klijentom
 * @param oznakaVrsteKomunikacije oznaka vrste komunikacije
 * @param sadrzajKomunikacije sadrzaj komunikacija
 * @param vrijemeKomunikacije vrijeme komunikacije
 */
public class Komunikacija implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2435461746625896373L;
	
	private Integer id;
	private Klijent klijent;
	private Zaposlenik zaposlenik;
	private VrstaKomunikacije oznakaVrsteKomunikacije;
	private String sadrzajKomunikacije;
	private LocalDateTime vrijemeKomunikacije;
	private LocalTime vrijeme;

	public Komunikacija(Klijent klijent, Zaposlenik zaposlenik, VrstaKomunikacije oznakaVrsteKomunikacije,
			String sadrzajKomunikacije, LocalDateTime vrijemeKomunikacije)
	{
		super();
		this.klijent = klijent;
		this.zaposlenik = zaposlenik;
		this.oznakaVrsteKomunikacije = oznakaVrsteKomunikacije;
		this.sadrzajKomunikacije = sadrzajKomunikacije;
		this.vrijemeKomunikacije = vrijemeKomunikacije;
		vrijeme=LocalTime.now();
	}

	public String getKlijenti()
	{
		return klijent.getIme() + " " + klijent.getPrezime();
	}
	
	public String getZaposlenici()
	{
		return zaposlenik.getIme() + " " + zaposlenik.getPrezime();
	}
	
	public Klijent getKlijent()
	{
		return klijent;
	}

	public void setKlijent(Klijent klijent)
	{
		this.klijent = klijent;
	}

	public Zaposlenik getZaposlenik()
	{
		return zaposlenik;
	}

	public void setZaposlenik(Zaposlenik zaposlenik)
	{
		this.zaposlenik = zaposlenik;
	}

	public VrstaKomunikacije getOznakaVrsteKomunikacije()
	{
		return oznakaVrsteKomunikacije;
	}

	public void setOznakaVrsteKomunikacije(VrstaKomunikacije oznakaVrsteKomunikacije)
	{
		this.oznakaVrsteKomunikacije = oznakaVrsteKomunikacije;
	}

	public String getSadrzajKomunikacije()
	{
		return sadrzajKomunikacije;
	}

	public void setSadrzajKomunikacije(String sadrzajKomunikacije)
	{
		this.sadrzajKomunikacije = sadrzajKomunikacije;
	}

	public LocalDateTime getVrijemeKomunikacije()
	{
		return vrijemeKomunikacije;
	}

	public void setVrijemeKomunikacije(LocalDateTime crijemeKomunikacije)
	{
		this.vrijemeKomunikacije = crijemeKomunikacije;
	}
	
	public LocalTime getVrijeme()
	{
		return vrijeme;
	}
	
	public void setVrijeme(LocalTime vrijeme)
	{
		this.vrijeme=vrijeme;
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
