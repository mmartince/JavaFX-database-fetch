package hr.java.vjezbe.glavna;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;

import hr.java.vjezbe.entitet.Alarm;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.Komunikacija;
import hr.java.vjezbe.entitet.MaloprodajnaTvrtka;
import hr.java.vjezbe.entitet.VrstaKomunikacije;
import hr.java.vjezbe.entitet.Zaposlenik;
import hr.java.vjezbe.iznimke.AlarmIstekaoException;
import hr.java.vjezbe.iznimke.AlarmPredIstekomException;

public class Glavna
{
/////////OBSOLETE/////////
/////////OBSOLETE/////////
/////////OBSOLETE/////////


	/**
	 * 
	 * @param tvrtka tvrtka cije se komunikacije ispisuju
	 */
	public static void ispisKomunikacija(MaloprodajnaTvrtka tvrtka)
	{
		int i = 0;
		for (Komunikacija kom : tvrtka.getKomunikacije())
		{
			System.out.println("Podatci za " + (++i) + ". komunikaciju");
			System.out.println("Prezime i ime klijenta: " + kom.getKlijent().getPrezime() + " " + kom.getKlijent().getIme());
			System.out.println("Prezime i ime zaposlenika: " + kom.getZaposlenik().getIme() + " " + kom.getZaposlenik().getPrezime());
			System.out.println("Vrsta komunikacija: "+kom.getOznakaVrsteKomunikacije());
			System.out.println("Sadrzaj komunikacije: "+kom.getSadrzajKomunikacije());
			System.out.println("Vrijeme komunikacije: "+kom.getVrijeme());
		}
	}

	/**
	 * Provjerava da li je alarm pred istekom (manje od 60 sekundi do isteka).
	 * 
	 * @param alarm  - Alarm kojem se provjerava vrijeme do isteka.
	 * @param tvrtka - Tvrtka kojoj se pristupa
	 * @param  scan - scanner za upis;
	 * @throws AlarmPredIstekomException
	 * @throws AlarmIstekaoException
	 */
	public static void predIstekom(Alarm alarm, MaloprodajnaTvrtka tvrtka, Scanner scan) throws AlarmPredIstekomException, AlarmIstekaoException
	{

		LocalDateTime fromDateTime = LocalDateTime.now();

		LocalDateTime toDateTime = alarm.getVrijemeAlarma();

		LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

		long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
		tempDateTime = tempDateTime.plusYears(years);

		long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
		tempDateTime = tempDateTime.plusMonths(months);

		long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
		tempDateTime = tempDateTime.plusDays(days);

		long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
		tempDateTime = tempDateTime.plusHours(hours);

		long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
		tempDateTime = tempDateTime.plusMinutes(minutes);

		long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);

		if (seconds <= 0)
		{
			alarm.setAlarmAktiviran(false);
			throw new AlarmIstekaoException("Sljedeci alarm je istekao: '" + alarm.getOpisAlarma() + "'");
		}
		else if (seconds < 60)
		{
			System.out.println("Za manje od jednu minutu ce biti vrijeme alarma: " + alarm.getOpisAlarma());
			System.out.println("Zelite li obaviti komunikaciju s klijentom (DA/NE)?");
			if (scan.nextLine().equals("DA"))
			{
				System.out.println("Odaberite zaposlenika za komunikaciju");
				int i = 0;
				Zaposlenik zaposlenik;
				VrstaKomunikacije vrstaKomunikacije;
				int[] intAr=new int[1];
				intAr[0]=0;
				tvrtka.getZaposlenici().forEach(zap ->{
					System.out.println(++intAr[0] + ". ZAPOSLENIK");
					System.out.println("PREZIME: " + zap.getPrezime());
					System.out.println("IME: " + zap.getIme());
					System.out.println("SIFRA: " + zap.getSifraZaposlenika());
				});
				while (true)
				{
					try
					{
						i = scan.nextInt();
						scan.nextLine();
						break;
					}
					catch (InputMismatchException ex)
					{
						System.out.println("Krivi unos, unesite broj");
						scan.nextLine();
					}
				}
				zaposlenik = tvrtka.getZaposlenici().get(i - 1);
				System.out.println("Unesite vrstu komunikacije: ");
				System.out.println("1. VERBALNA");
				System.out.println("2. PISMENA");
				System.out.println("3. ELEKTRONICKA");
				while (true)
				{
					try
					{
						i = scan.nextInt();
						scan.nextLine();
						break;
					}
					catch (InputMismatchException ex)
					{
						System.out.println("Krivi unos, unesite broj");
						scan.nextLine();
					}
				}
				vrstaKomunikacije = VrstaKomunikacije.values()[i - 1];
				Komunikacija komunikacija = new Komunikacija(alarm.getKlijent(), zaposlenik, vrstaKomunikacije, "Povratna informacija o obavljenoj usluzi.", LocalDateTime.now());
				tvrtka.addKomunikacije(komunikacija);
			}
			throw new AlarmPredIstekomException("Za manje od 1 minutu ce biti vrjeme alarma " + alarm.getOpisAlarma());
		}
	}

	/**
	 * Ispisuje artikle unutar tvrtke
	 * 
	 * @param tvrtka
	 *            - Tvrtka ciji se artikli ispisuju
	 * @param brArtikala
	 *            - broj artikala unutar te tvrtke
	 */
	public static void ispisArtikala(MaloprodajnaTvrtka tvrtka)
	{
		int[] intAr=new int[1];
		intAr[0]=0;
		tvrtka.getArtikl().stream().forEach(tvr ->{
			System.out.println(++intAr[0]+". ARTIKL");
			System.out.println("Naziv artikla: "+tvr.getNaziv());
			System.out.println("Kategorija artikla: "+tvr.getKategorija());
		});
	}

	/**
	 * Ispisuje informacije o klijentu.
	 * 
	 * @param klijent
	 *            - Klijent cije se informacije ispisuju.
	 */
	public static void ispisKlijenta(Klijent klijent)
	{
		System.out.println("Prezime klijenta: " + klijent.getPrezime());
		System.out.println("Ime Klijenta: " + klijent.getIme());
		System.out.println("OIB klijenta: " + klijent.getOib());
		System.out.println("Broj telefona klijenta: " + klijent.getBrojTelefona());
		System.out.println("E-mail klijenta: " + klijent.getEmail());
		System.out.println("Datum rodjenja klijenta: " + klijent.getDatumRodjenja());
	}

	/**
	 * Unos informacija o novom klijentu i njegova kreacija.
	 * 
	 * @param scan
	 *            - skener kojim se unose podatci.
	 * @param logger
	 *            - Logger
	 * @return Klijent
	 */
	public static Klijent noviKlijent(Scanner scan, Logger logger)
	{
		System.out.println("Unesite OIB klijenta:");
		String oib = scan.nextLine();
		System.out.println("Unesite prezime klijenta:");
		String prezime = scan.nextLine();
		System.out.println("Unesite ime klijenta:");
		String ime = scan.nextLine();
		System.out.println("Unesite broj telefona klijenta:");
		String brojTelefona = scan.nextLine();
		System.out.println("Unesite e-mail adresu klijenta:");
		String eMail = scan.nextLine();
		LocalDate localDate = null;
		String datumRodjenja = null;
		boolean petlja;
		do
		{
			try
			{
				System.out.println("Datum rodj1enja klijenta (dd.MM.yyyy.):");
				datumRodjenja = scan.nextLine();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				localDate = LocalDate.parse(datumRodjenja, formatter);
				petlja = false;
			}
			catch (DateTimeParseException ex)
			{
				logger.error("Unesen neispravan format datuma: " + datumRodjenja, ex);
				System.out.println("Unesen neispravan format datuma: " + datumRodjenja);
				petlja = true;
			}
		}
		while (petlja);
		Klijent klijent = new Klijent(oib, prezime, ime, brojTelefona, eMail, localDate);
		return klijent;
	}

	/**
	 * Unos novog zaposlenika i njegova kreacija.
	 * 
	 * @param scan
	 *            - Skener kojim se unose podatci.
	 * @param logger
	 *            - logger.
	 * @return Zaposlenik
	 */
	public static Zaposlenik noviZaposlenik(Scanner scan, Logger logger)
	{
		System.out.println("Unesite korisnicko ime zaposlenika:");
		String korisnickoIme = scan.nextLine();
		System.out.println("Unesite ime zaposlenika:");
		String ime = scan.nextLine();
		System.out.println("Unesite prezime zaposlenika:");
		String prezime = scan.nextLine();
		System.out.println("Unesite sifru zaposlenika:");
		String sifra = scan.nextLine();
		Zaposlenik zaposlenik = new Zaposlenik(korisnickoIme, ime, prezime, sifra);
		return zaposlenik;
	}

	/**
	 * Unos nove tvrtke i njena kreacija omggm.
	 * 
	 * @param scan
	 *            - skener kojim se unose podatci
	 * @param artikli
	 *            - artikli koji ce biti spremljeni unutar te tvrtke
	 * @return MaloprodajnaTvrtka
	 */
	public static MaloprodajnaTvrtka novaTvrtka(Scanner scan, List<Artikl> artikli)
	{
		System.out.println("Unesite naziv tvrtke:");
		String naziv = scan.nextLine();
		System.out.println("Unesite OIB tvrtke:");
		String oib = scan.nextLine();
		MaloprodajnaTvrtka tvrtka = new MaloprodajnaTvrtka(naziv, oib, artikli);
		return tvrtka;
	}
}
