package hr.java.vjezbe.glavna;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.entitet.Alarm;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.KategorijaArtikla;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.Komunikacija;
import hr.java.vjezbe.entitet.MaloprodajnaTvrtka;
import hr.java.vjezbe.entitet.ProdajaArtikla;
import hr.java.vjezbe.entitet.Tvrtka;
import hr.java.vjezbe.entitet.VrstaUsluge;
import hr.java.vjezbe.entitet.Zaposlenik;
import hr.java.vjezbe.iznimke.AlarmIstekaoException;
import hr.java.vjezbe.iznimke.AlarmPredIstekomException;
import hr.java.vjezbe.sortiranje.KomunikacijaSorter;

public class GlavnaDatoteke
{
/////////OBSOLETE/////////
/////////OBSOLETE/////////
/////////OBSOLETE/////////
	
	/**
	 * Glavna metoda unutar koje se nalazi kod.
	 * 
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args)
	{
		GlavnaDatoteke gla=new GlavnaDatoteke("klijent");
		List<Klijent> klijenti=new ArrayList<>();
		List<Zaposlenik> zaposlenici=new ArrayList<>();
		List<Artikl> artikls=new ArrayList<>();
		MaloprodajnaTvrtka tvrtka = null;
		System.out.println("Ucitavanje klijenata...");
		for(int i=0;i<Klijent.brojKlijenata;i++)
		{
			klijenti.add(gla.getObject("klijent"));
		}
		System.out.println("Ucitavanje zaposlenika...");
		for(int i=0;i<Zaposlenik.brojZaposlenika;i++)
		{
			zaposlenici.add(gla.getObject("zaposlenik"));
		}
		System.out.println("Ucitavanje artikala...");
		for(int i=0;i<Artikl.brojArtikala;i++)
		{
			artikls.add(gla.getObject("artikl"));
		}
		System.out.println("Ucitavanje tvrtci...");
		for(int i=0;i<Tvrtka.brojTvrtci;i++)
		{
			tvrtka=gla.getObject("tvrtka");
		}
		
		
		
		Alarm al=gla.getObject("alarmm");
		System.out.println(al.getOpisAlarma());
		
		
		
		boolean petlja = true;

		Scanner scan = new Scanner(System.in);
		Logger logger = LoggerFactory.getLogger(Glavna.class);
		int brUsluga=0;
		do
		{
			try
			{
				System.out.println("Unesite broj usluga koje zelite izvrsiti");
				brUsluga = scan.nextInt();
				petlja = false;
			}
			catch (InputMismatchException ex)
			{
				System.out.println("Pogresan unos");
				scan.nextLine();
				petlja = true;
			}
		}
		while (petlja);
		logger.info("Pocetak unosenja " + brUsluga + "usluga");
		List<ProdajaArtikla> prodajaArtikla = new ArrayList<>();
		BigDecimal ukupno = BigDecimal.ZERO;
		List<Alarm> alarmi = new ArrayList<Alarm>();
		//int[] brojArtikalaZaProdaju = new int[brUsluga];
		int brojArtikalaZaProdaju=0;
		List<BigDecimal> ukupnoList=new ArrayList<>();
		for (int i = 0; i < brUsluga; i++)
		{
			LocalDate today = LocalDate.now();

			Klijent klijent;
			Artikl artikl;
			System.out.println("UNESITE " + (i + 1) + ". USLUGU:");
			System.out.println("UNESITE REDNI BROJ KLIJENTA:");
			System.out.println("1. KLIJENT:");
			Glavna.ispisKlijenta(klijenti.get(0));
			System.out.println("2. KLIJENT:");
			Glavna.ispisKlijenta(klijenti.get(1));
			System.out.println("Odabir:");
			int odabirKlijenta = 0;
			do
			{
				try
				{
					odabirKlijenta = scan.nextInt();
					petlja = false;
				}
				catch (InputMismatchException ex)
				{
					System.out.println("Krivi unos, unesite cijeli broj");
					scan.nextLine();
					petlja = true;
				}
			}
			while (petlja);
			if (odabirKlijenta == 1)
			{
				logger.info("Odabran klijent " + klijenti.get(0).getIme() + " " + klijenti.get(0).getPrezime());
				klijent = klijenti.get(0);
			}
			else
			{
				logger.info("Odabran klijent " + klijenti.get(1).getIme() + " " + klijenti.get(1).getPrezime());
				klijent = klijenti.get(1);
			}
			scan.nextLine();
			System.out.println("Vrsta usluge:");

			VrstaUsluge vrstaUsluge = null;
			for (int j = 0; j < VrstaUsluge.values().length - 1; j++)
				System.out.println((j + 1) + ". " + VrstaUsluge.values()[j]);
			Integer redniBrUsluge = null;
			while (true)
			{
				System.out.println("Odabir: ");
				try
				{
					redniBrUsluge = scan.nextInt();
					scan.nextLine();
					break;
				}
				catch (InputMismatchException ex)
				{
					System.out.println("Krivi unos");
					logger.error("Krivi unos vrste usluge", ex);
				}
			}
			if (redniBrUsluge >= 1 && redniBrUsluge < VrstaUsluge.values().length) vrstaUsluge = VrstaUsluge.values()[redniBrUsluge - 1];
			else vrstaUsluge = VrstaUsluge.OSTALO;

			logger.info("Odabrana vrsta usluge: " + vrstaUsluge);
			System.out.println("Opis usluge:");
			String opisUsluge = scan.nextLine();
			logger.info("Odabran opis usluge: " + opisUsluge);
			System.out.println("Cijena usluge:");
			BigDecimal cijena = null;
			do
			{
				try
				{
					cijena = scan.nextBigDecimal();
					petlja = false;
				}
				catch (InputMismatchException ex)
				{
					System.out.println("Unos krivog tipa, unesite cijenu usluge");
					logger.error("Unesen krivi tip", ex);
					scan.nextLine();
					System.out.println("Cijna usluge:");
					petlja = true;
				}
			}
			while (petlja);
			logger.info("Odabrana cijena usluge: " + cijena + " KN");
			System.out.println("ODABIR ARTIKLA:");
			Glavna.ispisArtikala(tvrtka);
			System.out.println("Odabir:");
			int br = 0;
			do
			{
				try
				{
					br = scan.nextInt();
					petlja = false;
				}
				catch (InputMismatchException ex)
				{
					logger.error("Unesen krivi tip", ex);
					System.out.println("Odabir");
					scan.nextLine();
					petlja = true;
				}
			}
			while (petlja);
			artikl = (tvrtka.getArtikl()).get(br-1);
			logger.info("Odabran artikl: " + artikl.getNaziv());
			prodajaArtikla.add(new ProdajaArtikla(klijent, vrstaUsluge, opisUsluge, today, cijena, false, false, artikl));

			LocalDateTime ldtToday = LocalDateTime.now().plusMinutes(1);
			alarmi.add(new Alarm(klijent, "Povratna informacija za obavljenu uslugu: " + opisUsluge, ldtToday, true));
			logger.info("Kreiran alarm za uslugu");

			System.out.println("Unesite broj artikala koje zelite prodati:");
			do
			{
				try
				{
					brojArtikalaZaProdaju = scan.nextInt();
					petlja = false;
				}
				catch (InputMismatchException ex)
				{
					logger.error("Unesen krivi tip", ex);
					scan.nextLine();
					System.out.println("Unesite broj artikala koj zelite prodati");
					petlja = true;
				}
			}
			while (petlja);
			logger.info("Unesen broj artikala za prodaju: " + brojArtikalaZaProdaju);
			ukupnoList.add(prodajaArtikla.get(i).prodaja(brojArtikalaZaProdaju));
		}
		ukupno=BigDecimal.valueOf((ukupnoList.stream().mapToDouble(n -> n.longValue()).sum()));
		tvrtka.setAlarmi(alarmi);
		System.out.println("Ukupno cijena prodanih artikala: " + ukupno + " KN");
		logger.info("Ukupna cijena svih artikala: " + ukupno);
		logger.info("Postavljeni alarmi za cijlu tvrtku");
		scan.nextLine();
		while (true)
		{
			int activeAlarms = 0;
			for (Alarm alarm : tvrtka.getAlarmi())
			{
				if (alarm.getAlarmAktiviran() == true) activeAlarms++;
			}
			for(Alarm alarm : tvrtka.getAlarmi())
			{
				if(alarm.getAlarmAktiviran()==true)
				{
					try
					{
						Glavna.predIstekom(alarm, tvrtka, scan);
					}
					catch (AlarmPredIstekomException ex)
					{
						logger.error("Alarm pred istekom", ex);
					}
					catch (AlarmIstekaoException ex)
					{
						logger.error("Alarm istekao", ex);
					}
				}
			}
			try
			{
				Thread.sleep(10000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			if (activeAlarms == 0) break;
		}
		try
		{
			PrintWriter out = new PrintWriter(new FileWriter(new File("dat\\komunikacija.txt")));
			for(Komunikacija kom : tvrtka.getKomunikacije())
			{
				out.println(kom.getKlijent().getOib());
				out.println(kom.getKlijent().getPrezime());
				out.println(kom.getKlijent().getIme());
				out.println(kom.getKlijent().getBrojTelefona());
				out.println(kom.getKlijent().getEmail());
				out.println(kom.getKlijent().getDatumRodjenja());
				out.println(kom.getZaposlenik().getKorisnickoIme());
				out.println(kom.getZaposlenik().getIme());
				out.println(kom.getZaposlenik().getPrezime());
				out.println(kom.getZaposlenik().getSifraZaposlenika());
				out.println(kom.getOznakaVrsteKomunikacije());
				out.println(kom.getSadrzajKomunikacije());
				out.println(kom.getVrijemeKomunikacije());
				out.println(kom.getVrijeme());
			}
			out.close();
			PrintWriter outt = new PrintWriter(new FileWriter(new File("dat\\alarm.txt")));
			for(Alarm alar : tvrtka.getAlarmi())
			{
				outt.println(alar.getKlijent().getOib());
				outt.println(alar.getKlijent().getPrezime());
				outt.println(alar.getKlijent().getIme());
				outt.println(alar.getKlijent().getBrojTelefona());
				outt.println(alar.getKlijent().getEmail());
				outt.println(alar.getKlijent().getDatumRodjenja());
				outt.println(alar.getOpisAlarma());
				outt.println(alar.getVrijemeAlarma());
				outt.println(alar.getAlarmAktiviran());
			}
			outt.close();
			PrintWriter outo = new PrintWriter(new FileWriter(new File("dat\\prodajaArtikla.txt")));
			for(ProdajaArtikla pro : prodajaArtikla)
			{
				outo.println(pro.getKlijent().getOib());
				outo.println(pro.getKlijent().getPrezime());
				outo.println(pro.getKlijent().getIme());
				outo.println(pro.getKlijent().getBrojTelefona());
				outo.println(pro.getKlijent().getEmail());
				outo.println(pro.getKlijent().getDatumRodjenja());
				outo.println(pro.getOznakaVrsteUsluge());
				outo.println(pro.getOznakaOpisaUsluge());
				outo.println(pro.getDatumUsluge());
				outo.println(pro.getCijenaUsluge());
				outo.println(pro.getUslugaObavljena());
				outo.println(pro.getUslugaNaplacena());
				outo.println(pro.getArtikl().getNaziv());
				outo.println(pro.getArtikl().getKategorija());
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dat\\komunikacije.dat")))
		{
			out.writeObject(tvrtka.getKomunikacije());
		}
		catch(IOException ex)
		{
			System.err.println(ex);
		}		
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dat\\alarmi.dat")))
		{
			out.writeObject(tvrtka.getAlarmi());
		}
		catch(IOException ex)
		{
			System.err.println(ex);
		}
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("dat\\prodajaArtikla.dat")))
		{
			out.writeObject(prodajaArtikla);
		}
		catch(IOException ex)
		{
			System.err.println(ex);
		}
		System.out.println("Ispis svih komunikacija:");
		Glavna.ispisKomunikacija(tvrtka);
		MaloprodajnaTvrtka tvrtkaTest=tvrtka;
		
		long pocetakSortiranja1 = System.currentTimeMillis();
		Collections.sort(tvrtka.getKomunikacije(), new KomunikacijaSorter());
		long krajSortiranja1 = System.currentTimeMillis();
		long rezultatSortiranja1=krajSortiranja1-pocetakSortiranja1;
		Glavna.ispisKomunikacija(tvrtka);
		
		long pocetakSortiranja2 = System.currentTimeMillis();
		Comparator<Komunikacija> poVremenu = (kom1, kom2) -> kom1.getVrijeme().compareTo(kom2.getVrijeme());
		Comparator<Komunikacija> poPrezimenu = (kom1, kom2) -> kom1.getKlijent().getPrezime().compareTo(kom2.getKlijent().getPrezime());
		tvrtkaTest.getKomunikacije().stream().sorted(poVremenu.thenComparing(poPrezimenu));
		long krajSortiranja2 = System.currentTimeMillis();
		long rezultatSortiranja2=krajSortiranja2-pocetakSortiranja2;
		
		System.out.println("Sortiranje bez lambdi je trajalo: "+rezultatSortiranja1+"ms");
		System.out.println("Sortiranje s lambdama i streamovima je trajalo: "+rezultatSortiranja2+"ms");
	}
	
	
	
	
	int readKlient;
	int readArtikl;
	int readTvrtka;
	int readZaposlenik;
	int readAlarm;
	String path,file;
	public GlavnaDatoteke(){}
	public GlavnaDatoteke(String file)
	{
		this.file=file;
		path="dat\\" + file + ".txt";
	}
	
	public void setPath(String file)
	{
		this.file=file;
		path="dat\\" + file + ".txt";
	}
	
	@SuppressWarnings({ "resource", "unchecked" })
	public <T> T getObject(String file)
	{
		if(this.file!=file)
		{
			setPath(file);
		}
		File myFile = new File(path);
		Scanner in;
		try
		{
			in = new Scanner(myFile, "UTF-8");
			if(file=="klijent" || file=="klijenti")
			{
				if(file=="klijent") for(int i=0;i<readKlient;i++) in.nextLine();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
				Klijent kl;
				kl=new Klijent(in.nextLine(),in.nextLine(),in.nextLine(),in.nextLine(),in.nextLine(),LocalDate.parse(in.nextLine(),formatter));
				readKlient+=6;
				return (T) kl;
			}
			else if(file=="zaposlenik" || file=="zaposlenici")
			{
				if(file=="zaposlenik") for(int i=0;i<readZaposlenik;i++) in.nextLine();
				Zaposlenik zap=new Zaposlenik(in.nextLine(),in.nextLine(),in.nextLine(),in.nextLine());
				readZaposlenik+=4;
				return (T) zap;
			}
			else if(file=="artikl" || file=="artikli")
			{
				if(file=="artikl") for(int i=0;i<readArtikl;i++) in.nextLine();
				Artikl ar=new Artikl(in.nextLine(), KategorijaArtikla.valueOf(in.nextLine()));
				readArtikl+=2;
				return (T) ar;
			}
			else if(file=="tvrtka")
			{
				for(int i=0;i<readTvrtka;i++) in.nextLine();
				List<Klijent> klijenti=new ArrayList<>();
				List<Zaposlenik> zaposlenici = new ArrayList<>();
				List<Artikl> artikl=new ArrayList<>();
				GlavnaDatoteke glav=new GlavnaDatoteke();
				for(int i=0;i<Klijent.brojKlijenata;i++)
				{
					klijenti.add(glav.getObject("klijent"));
				}
				for(int i=0;i<Zaposlenik.brojZaposlenika;i++)
				{
					zaposlenici.add(glav.getObject("zaposlenik"));
				}
				for(int i=0;i<Artikl.brojArtikala;i++)
				{
					artikl.add(glav.getObject("artikl"));
				}
				MaloprodajnaTvrtka tvr=new MaloprodajnaTvrtka(in.nextLine(), in.nextLine(), artikl);
				tvr.setArtikl(artikl);
				tvr.setKlijenti(klijenti);
				tvr.setZaposlenici(zaposlenici);
				readTvrtka+=2;
				return (T) tvr;
			}
			else if(file=="alarmm")
			{
				for(int i=0;i<readAlarm;i++) in.nextLine();
				List<Klijent> klijenti=new ArrayList<>();
				GlavnaDatoteke glav=new GlavnaDatoteke();
				klijenti.add(glav.getObject("klijent"));
				Alarm alarm=new Alarm(klijenti.get(0),in.nextLine(),LocalDateTime.parse(in.nextLine()),in.nextBoolean());
				return (T) alarm;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
