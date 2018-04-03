package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * 
 * @author Amalah
 * @param klijent klijent kojem je alarm pripisan
 * @param opisAlarm opis cemu sluzi taj alarm
 * @param vrijemeAlarma kada ce alarm zvoniti
 * @param alarmAktiviran da li je alarm aktiviran
 */
public class Alarm implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7357666504619949361L;
	public static final int brojAlarma=1;
	
	Integer id;
	Klijent klijent;
	String opisAlarma;
	LocalDateTime vrijemeAlarma;
	Boolean alarmAktiviran;
	protected String klijentIme;

	public Alarm(Klijent klijent, String opisAlarma, LocalDateTime vrijemeAlarma, Boolean alarmAktiviran)
	{
		super();
		this.klijent = klijent;
		this.opisAlarma = opisAlarma;
		this.vrijemeAlarma = vrijemeAlarma;
		this.alarmAktiviran = alarmAktiviran;
	}

	public Klijent getKlijent()
	{
		return klijent;
	}
	
	public String getKlijentIme()
	{
		return klijent.getIme() + " "+ klijent.getPrezime();
	}

	public void setKlijent(Klijent klijent)
	{
		this.klijent = klijent;
	}

	public String getOpisAlarma()
	{
		return opisAlarma;
	}

	public void setOpisAlarma(String opisAlarma)
	{
		this.opisAlarma = opisAlarma;
	}

	public LocalDateTime getVrijemeAlarma()
	{
		return vrijemeAlarma;
	}

	public void setVrijemeAlarma(LocalDateTime vrijemeAlarma)
	{
		this.vrijemeAlarma = vrijemeAlarma;
	}

	public Boolean getAlarmAktiviran()
	{
		return alarmAktiviran;
	}

	public void setAlarmAktiviran(Boolean alarmAktiviran)
	{
		this.alarmAktiviran = alarmAktiviran;
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
