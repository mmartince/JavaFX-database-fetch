package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Amalah
 * @param nazivTvrtke naziv tvrtke
 * @param oibTvrtke osobni identifikacijski broj tvrtke
 * @param klijenti klijenti koji se nalaze unutar tvrtke
 * @param zaposlenici zaposlenici koji se nalaze unutar tvrtke
 * @param komunikacije komunikacije koje se provode unutar tvrtke
 * @param usluge usluge koje pruza tvrtka
 * @param alarmi alarmi unutar tvrtke
 */
public class Tvrtka
{

	public static final Integer brojKlijenata = 2;
	public static final Integer brojZaposlenika = 2;
	public static final Integer brojKomunikacija = 2;
	public static final Integer brojUsluga = 2;
	public static final Integer brojAlarma = 2;
	public static final int brojTvrtci=1;
	
	private Integer id;
	private String nazivTvrtke;
	private String oibTvrtke;
	private List<Klijent> klijenti=new ArrayList<Klijent>();
	private List<Zaposlenik> zaposlenici = new ArrayList<Zaposlenik>();
	private List<Komunikacija> komunikacije = new ArrayList<Komunikacija>();
	private List<Usluga> usluge = new ArrayList<Usluga>();
	private List<Alarm> alarmi = new ArrayList<Alarm>();

	public Tvrtka(String nazivTvrtke, String oibTvrtke)
	{
		super();
		this.nazivTvrtke = nazivTvrtke;
		this.oibTvrtke = oibTvrtke;

	}

	public String getNazivTvrtke()
	{
		return nazivTvrtke;
	}

	public void setNazivTvrtke(String nazivTvrtke)
	{
		this.nazivTvrtke = nazivTvrtke;
	}

	public String getOibTvrtke()
	{
		return oibTvrtke;
	}

	public void setOibTvrtke(String oibTvrtke)
	{
		this.oibTvrtke = oibTvrtke;
	}

	public List<Klijent> getKlijenti()
	{
		return klijenti;
	}

	public void setKlijenti(List<Klijent> klijenti)
	{
		this.klijenti = klijenti;
	}

	public void setKlijenti(Klijent klijent)
	{
		this.klijenti.add(klijent);
	}

	public List<Zaposlenik> getZaposlenici()
	{
		return zaposlenici;
	}

	public void setZaposlenici(List<Zaposlenik> zaposlenici)
	{
		this.zaposlenici = zaposlenici;
	}

	public void setZaposlenici(Zaposlenik zaposlenik)
	{
		this.zaposlenici.add(zaposlenik);
	}

	public List<Komunikacija> getKomunikacije()
	{
		return komunikacije;
	}

	public void setKomunikacije(List<Komunikacija> komunikacije)
	{
		this.komunikacije = komunikacije;
	}

	public void setKomunikacije(Komunikacija komunikacija, int pos)
	{
		this.komunikacije.add(pos, komunikacija);
	}
	
	public void addKomunikacije(Komunikacija komunikacija)
	{
		this.komunikacije.add(komunikacija);
	}

	public List<Usluga> getUsluge()
	{
		return usluge;
	}

	public void setUsluge(List<Usluga> usluge)
	{
		this.usluge = usluge;
	}

	public void setUsluge(Usluga usluga, int pos)
	{
		this.usluge.add(pos, usluga);
	}

	public List<Alarm> getAlarmi()
	{
		return alarmi;
	}

	public void setAlarmi(List<Alarm> alarmi)
	{
		this.alarmi = alarmi;
	}
	
	public void addAlarmi(Alarm alarm)
	{
		this.alarmi.add(alarm);
	}
	
	public void setAlarmi(Alarm alarm, int pos)
	{
		this.alarmi.add(pos, alarm);
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
