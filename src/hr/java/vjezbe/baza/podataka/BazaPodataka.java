package hr.java.vjezbe.baza.podataka;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import hr.java.vjezbe.entitet.Alarm;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.KategorijaArtikla;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.Komunikacija;
import hr.java.vjezbe.entitet.MaloprodajnaTvrtka;
import hr.java.vjezbe.entitet.Tvrtka;
import hr.java.vjezbe.entitet.Usluga;
import hr.java.vjezbe.entitet.VrstaKomunikacije;
import hr.java.vjezbe.entitet.VrstaUsluge;
import hr.java.vjezbe.entitet.Zaposlenik;

public class BazaPodataka
{

	private static Connection spajanjeNaBazuPodataka() throws SQLException, IOException
	{
		Properties svojstva = new Properties();
		svojstva.load(new FileReader("bazaPodataka.properties"));
		String urlBazePodataka = svojstva.getProperty("bazaPodatakaUrl");
		String korisnickoIme = svojstva.getProperty("korisnickoIme");
		String lozinka = svojstva.getProperty("lozinka");
		Connection veza = DriverManager.getConnection(urlBazePodataka, korisnickoIme, lozinka);
		return veza;
	}

	private static void zatvaranjeVezeNaBazuPodataka(Connection veza) throws SQLException
	{
		veza.close();
	}

	public static List<Klijent> dohvatiKlijente() throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementKlijenti = veza.createStatement();
		ResultSet resultSetKlijenti = statementKlijenti.executeQuery("SELECT * FROM CRM.KLIJENT");
		List<Klijent> listaKlijenata = new ArrayList<>();
		while (resultSetKlijenti.next())
		{
			Integer klijentId = resultSetKlijenti.getInt("ID");
			String oib = resultSetKlijenti.getString("OIB");
			String prezime = resultSetKlijenti.getString("PREZIME");
			String ime = resultSetKlijenti.getString("IME");
			String brojTelefona = resultSetKlijenti.getString("TELEFON");
			String email = resultSetKlijenti.getString("EMAIL");
			LocalDate datumRodjenja = resultSetKlijenti.getDate("DATUM_RODJENJA").toLocalDate();
			Klijent klijent = new Klijent(oib, prezime, ime, brojTelefona, email, datumRodjenja);
			klijent.setId(klijentId);
			listaKlijenata.add(klijent);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaKlijenata;
	}

	public static Klijent dohvatiKlijenta(Integer trazeniKlijent) throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementKlijenti = veza.createStatement();
		ResultSet resultSetKlijenti = statementKlijenti.executeQuery("SELECT * FROM CRM.KLIJENT");
		while (resultSetKlijenti.next())
		{
			if (resultSetKlijenti.getInt("ID") == trazeniKlijent)
			{
				Integer klijentId = resultSetKlijenti.getInt("ID");
				String oib = resultSetKlijenti.getString("OIB");
				String prezime = resultSetKlijenti.getString("PREZIME");
				String ime = resultSetKlijenti.getString("IME");
				String brojTelefona = resultSetKlijenti.getString("TELEFON");
				String email = resultSetKlijenti.getString("EMAIL");
				LocalDate datumRodjenja = resultSetKlijenti.getDate("DATUM_RODJENJA").toLocalDate();
				Klijent klijent = new Klijent(oib, prezime, ime, brojTelefona, email, datumRodjenja);
				klijent.setId(klijentId);
				return klijent;
			}
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return null;
	}

	public static void spremiKlijenta(Klijent klijent, Tvrtka tvrtka) throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try
		{
			PreparedStatement insertKlijent = veza.prepareStatement(
					"INSERT INTO CRM.KLIJENT (OIB, PREZIME, IME, TELEFON, EMAIL, DATUM_RODJENJA) VALUES (?, ?, ?, ?, ?, ?);");
			insertKlijent.setString(1, klijent.getOib());
			insertKlijent.setString(2, klijent.getPrezime());
			insertKlijent.setString(3, klijent.getIme());
			insertKlijent.setString(4, klijent.getBrojTelefona());
			insertKlijent.setString(5, klijent.getEmail());
			insertKlijent.setDate(6, Date.valueOf(klijent.getDatumRodjenja()));
			insertKlijent.executeUpdate();
			ResultSet generatedKeys = insertKlijent.getGeneratedKeys();
			if (generatedKeys.next())
			{
				klijent.setId(generatedKeys.getInt(1));
			}
			PreparedStatement insertTvrtkaKlijent = veza
					.prepareStatement("INSERT INTO CRM.TVRTKA_KLIJENT VALUES (?, ?)");
			insertTvrtkaKlijent.setInt(1, 1);
			insertTvrtkaKlijent.setInt(2, klijent.getId());
			insertTvrtkaKlijent.executeUpdate();
			veza.commit();
		}
		catch (Throwable ex)
		{
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static List<Alarm> dohvatiAlarme()
	{
		try
		{
			Connection veza = spajanjeNaBazuPodataka();
			Statement statementAlarma = veza.createStatement();
			ResultSet resultSetAlarma = statementAlarma.executeQuery("SELECT * FROM CRM.ALARM");
			List<Alarm> listaAlarma = new ArrayList<>();
			while (resultSetAlarma.next())
			{
				Integer alarmId = resultSetAlarma.getInt("ID");
				LocalDateTime datum = resultSetAlarma.getTimestamp("VRIJEME").toLocalDateTime();
				String opis = resultSetAlarma.getString("OPIS");
				Integer klijentId = resultSetAlarma.getInt("KLIJENT_ID");
				boolean aktivan = resultSetAlarma.getBoolean("AKTIVAN");
				Klijent klijent = BazaPodataka.dohvatiKlijenta(klijentId);
				Alarm alarm = new Alarm(klijent, opis, datum, aktivan);
				alarm.setId(alarmId);
				listaAlarma.add(alarm);
			}
			zatvaranjeVezeNaBazuPodataka(veza);
			return listaAlarma;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	public static List<Alarm> dohvatiAlarmePredIstekom()
	{
		try
		{
			Connection veza = spajanjeNaBazuPodataka();
			Statement statementAlarma = veza.createStatement();
			ResultSet resultSetAlarma = statementAlarma.executeQuery("SELECT * FROM CRM.ALARM WHERE TIMESTAMPDIFF('SECOND', VRIJEME, NOW()) <= 60 ORDER BY VRIJEME ASC;");
			List<Alarm> listaAlarma = new ArrayList<>();
			while (resultSetAlarma.next())
			{
				Integer alarmId = resultSetAlarma.getInt("ID");
				LocalDateTime datum = resultSetAlarma.getTimestamp("VRIJEME").toLocalDateTime();
				String opis = resultSetAlarma.getString("OPIS");
				Integer klijentId = resultSetAlarma.getInt("KLIJENT_ID");
				boolean aktivan = resultSetAlarma.getBoolean("AKTIVAN");
				Klijent klijent = BazaPodataka.dohvatiKlijenta(klijentId);
				Alarm alarm = new Alarm(klijent, opis, datum, aktivan);
				alarm.setId(alarmId);
				listaAlarma.add(alarm);
			}
			zatvaranjeVezeNaBazuPodataka(veza);
			return listaAlarma;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return new ArrayList<>();
	}

	public static void spremiAlarm(Alarm alarm) throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try
		{
			PreparedStatement insertAlarm = veza.prepareStatement(
					"INSERT INTO CRM.ALARM (OPIS, KLIJENT_ID, VRIJEME, AKTIVAN,) VALUES (?, ?, ?, ?);");
			insertAlarm.setString(1, alarm.getOpisAlarma());
			insertAlarm.setInt(2, alarm.getKlijent().getId());
			insertAlarm.setTimestamp(3, Timestamp.valueOf(alarm.getVrijemeAlarma()));
			insertAlarm.setBoolean(4, alarm.getAlarmAktiviran());
			insertAlarm.executeUpdate();
			ResultSet generatedKeys = insertAlarm.getGeneratedKeys();
			if (generatedKeys.next())
			{
				alarm.setId(generatedKeys.getInt(1));
			}
			veza.commit();
		}
		catch (Throwable ex)
		{
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static void deleteAlarmi() throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try
		{
			PreparedStatement insertAlarm = veza.prepareStatement(
					"DELETE FROM CRM.ALARM");
			insertAlarm.executeUpdate();
			veza.commit();
		}
		catch (Throwable ex)
		{
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static List<Zaposlenik> dohvatiZaposlenike() throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementZaposlenici = veza.createStatement();
		ResultSet resultSetZaposlenici = statementZaposlenici.executeQuery("SELECT * FROM CRM.ZAPOSLENIK");
		List<Zaposlenik> listaZaposlenika = new ArrayList<>();
		while (resultSetZaposlenici.next())
		{
			Integer zaposlenikId = resultSetZaposlenici.getInt("ID");
			String sifra = resultSetZaposlenici.getString("SIFRA");
			String prezime = resultSetZaposlenici.getString("PREZIME");
			String ime = resultSetZaposlenici.getString("IME");
			String korisnickoIme = resultSetZaposlenici.getString("KORISNICKO_IME");
			Zaposlenik zaposlenik = new Zaposlenik(korisnickoIme, ime, prezime, sifra);
			zaposlenik.setId(zaposlenikId);
			listaZaposlenika.add(zaposlenik);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaZaposlenika;
	}

	public static void spremiZaposlenika(Zaposlenik zaposlenik) throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try
		{
			PreparedStatement insertZaposlenik = veza.prepareStatement(
					"INSERT INTO CRM.ZAPOSLENIK (KORISNICKO_IME, PREZIME, IME, SIFRA) VALUES (?, ?, ?, ?);");
			insertZaposlenik.setString(1, zaposlenik.getKorisnickoIme());
			insertZaposlenik.setString(2, zaposlenik.getPrezime());
			insertZaposlenik.setString(3, zaposlenik.getIme());
			insertZaposlenik.setString(4, zaposlenik.getSifraZaposlenika());
			insertZaposlenik.executeUpdate();
			ResultSet generatedKeys = insertZaposlenik.getGeneratedKeys();
			if (generatedKeys.next())
			{
				zaposlenik.setId(generatedKeys.getInt(1));
			}
			PreparedStatement insertTvrtkaZaposlenik = veza
					.prepareStatement("INSERT INTO CRM.TVRTKA_ZAPOSLENIK VALUES (?, ?)");
			insertTvrtkaZaposlenik.setInt(1, 1);
			insertTvrtkaZaposlenik.setInt(2, zaposlenik.getId());
			insertTvrtkaZaposlenik.executeUpdate();
			veza.commit();
		}
		catch (Throwable ex)
		{
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static List<Artikl> dohvatiArtikle() throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementArtikli = veza.createStatement();
		ResultSet resultSetArtikli = statementArtikli.executeQuery("SELECT * FROM CRM.ARTIKL");
		List<Artikl> listaArtikala = new ArrayList<>();
		while (resultSetArtikli.next())
		{
			Integer artiklId = resultSetArtikli.getInt("ID");
			Integer kategorijaId = resultSetArtikli.getInt("KATEGORIJA_ID");
			String naziv = resultSetArtikli.getString("NAZIV");
			Artikl artil = new Artikl(naziv, KategorijaArtikla.values()[kategorijaId - 1]);
			artil.setId(artiklId);
			listaArtikala.add(artil);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaArtikala;
	}

	public static void spremiArtikl(Artikl artikl) throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try
		{
			PreparedStatement insertArtikla = veza
					.prepareStatement("INSERT INTO CRM.ARTIKL (NAZIV, KATEGORIJA_ID) VALUES (?, ?);");
			insertArtikla.setString(1, artikl.getNaziv());
			insertArtikla.setInt(2, artikl.getKategorija().ordinal() + 1);
			insertArtikla.executeUpdate();
			ResultSet generatedKeys = insertArtikla.getGeneratedKeys();
			if (generatedKeys.next())
			{
				artikl.setId(generatedKeys.getInt(1));
			}
			PreparedStatement insertTvrtkaArtikl = veza.prepareStatement("INSERT INTO CRM.TVRTKA_ARTIKL VALUES (?, ?)");
			insertTvrtkaArtikl.setInt(1, 1);
			insertTvrtkaArtikl.setInt(2, artikl.getId());
			insertTvrtkaArtikl.executeUpdate();
			veza.commit();
		}
		catch (Throwable ex)
		{
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	public static List<MaloprodajnaTvrtka> dohvatiTvrtke() throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementTvrtke = veza.createStatement();
		Statement statementTvrtkeArtikli = veza.createStatement();
		Statement statementTvrtkeKlijenti = veza.createStatement();
		Statement statementTvrtkeZaoslenici = veza.createStatement();
		ResultSet resultSetTvrtke = statementTvrtke.executeQuery("SELECT * FROM CRM.TVRTKA");
		ResultSet resultSetTvrtkeArtikala = statementTvrtkeArtikli.executeQuery("SELECT * FROM CRM.TVRTKA_ARTIKL");
		ResultSet resultSetTvrtkeKlijenata = statementTvrtkeKlijenti.executeQuery("SELECT * FROM CRM.TVRTKA_KLIJENT");
		ResultSet resultSetTvrtkeZaposlenika = statementTvrtkeZaoslenici
				.executeQuery("SELECT * FROM CRM.TVRTKA_ZAPOSLENIK");
		List<MaloprodajnaTvrtka> listaTvrtci = new ArrayList<>();
		while (resultSetTvrtke.next())
		{
			List<Artikl> artikli = BazaPodataka.dohvatiArtikle();
			List<Klijent> klijenti = BazaPodataka.dohvatiKlijente();
			List<Zaposlenik> zaposlenici = BazaPodataka.dohvatiZaposlenike();
			List<Artikl> filtriraniArtikli = new ArrayList<>();
			List<Klijent> filtriraniKlijenti = new ArrayList<>();
			List<Zaposlenik> filtriraniZaposlenici = new ArrayList<>();
			Integer tvrtkaId = resultSetTvrtke.getInt("ID");
			String oib = resultSetTvrtke.getString("OIB");
			String naziv = resultSetTvrtke.getString("NAZIV");
			while (resultSetTvrtkeArtikala.next())
			{
				if (resultSetTvrtkeArtikala.getInt("TVRTKA_ID") == tvrtkaId)
				{
					for (Artikl art : artikli)
					{
						if (art.getId() == resultSetTvrtkeArtikala.getInt("ARTIKL_ID")) filtriraniArtikli.add(art);
					}
				}
			}
			while (resultSetTvrtkeKlijenata.next())
			{
				if (resultSetTvrtkeKlijenata.getInt("TVRTKA_ID") == tvrtkaId)
				{
					for (Klijent kl : klijenti)
					{
						if (kl.getId() == resultSetTvrtkeKlijenata.getInt("KLIJENT_ID")) filtriraniKlijenti.add(kl);
					}
				}
			}
			while (resultSetTvrtkeZaposlenika.next())
			{
				if (resultSetTvrtkeZaposlenika.getInt("TVRTKA_ID") == tvrtkaId)
				{
					for (Zaposlenik zap : zaposlenici)
					{
						if (zap.getId() == resultSetTvrtkeZaposlenika.getInt("ZAPOSLENIK_ID"))
							filtriraniZaposlenici.add(zap);
					}
				}
			}
			MaloprodajnaTvrtka tvrtka = new MaloprodajnaTvrtka(naziv, oib, filtriraniArtikli);
			tvrtka.setKlijenti(filtriraniKlijenti);
			tvrtka.setZaposlenici(filtriraniZaposlenici);
			tvrtka.setId(tvrtkaId);
			listaTvrtci.add(tvrtka);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaTvrtci;
	}

	public static void spremiTvrtku(MaloprodajnaTvrtka tvrtka) throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try
		{
			PreparedStatement insertTvrtka = veza.prepareStatement("INSERT INTO CRM.TVRTKA (NAZIV, OIB) VALUES (?, ?);");
			insertTvrtka.setString(1, tvrtka.getNazivTvrtke());
			insertTvrtka.setString(2, tvrtka.getOibTvrtke());
			insertTvrtka.executeUpdate();
			ResultSet generatedKeys = insertTvrtka.getGeneratedKeys();
			if (generatedKeys.next())
			{
				tvrtka.setId(generatedKeys.getInt(1));
			}
			
			PreparedStatement insertTvrtkaArtikl = veza
					.prepareStatement("INSERT INTO CRM.TVRTKA_ARTIKL VALUES (?, ?)");
			insertTvrtkaArtikl.setInt(1, tvrtka.getId());
			for(Artikl ar:tvrtka.getArtikl())
			{
				insertTvrtkaArtikl.setInt(2, ar.getId() );
			}
			insertTvrtkaArtikl.executeUpdate();
			
			PreparedStatement insertTvrtkaKlijent = veza
					.prepareStatement("INSERT INTO CRM.TVRTKA_KLIJENT VALUES (?, ?)");
			insertTvrtkaKlijent.setInt(1, tvrtka.getId());
			for(Klijent kl:tvrtka.getKlijenti())
			{
				insertTvrtkaKlijent.setInt(2, kl.getId() );
			}
			insertTvrtkaKlijent.executeUpdate();
			
			PreparedStatement insertTvrtkaZaposlenik = veza
					.prepareStatement("INSERT INTO CRM.TVRTKA_ZAPOSLENIK VALUES (?, ?)");
			insertTvrtkaZaposlenik.setInt(1, tvrtka.getId());
			for(Zaposlenik zap:tvrtka.getZaposlenici())
			{
				insertTvrtkaZaposlenik.setInt(2, zap.getId() );
			}
			insertTvrtkaZaposlenik.executeUpdate();
			veza.commit();
		}
		catch (Throwable ex)
		{
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}

	@SuppressWarnings("unused")
	public static List<Komunikacija> dohvatiKomunikacije() throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementKomunikacije = veza.createStatement();
		Statement statementKomunikacijeKlijenti = veza.createStatement();
		Statement statementKomunikacijeZaposlenika = veza.createStatement();
		Statement statementKomunikacijeKomunikacije = veza.createStatement();
		ResultSet resultSetKomunikacije = statementKomunikacije.executeQuery("SELECT * FROM CRM.KOMUNIKACIJA");
		ResultSet resultSetKomunikacijeKomunikacije = statementKomunikacijeKomunikacije
				.executeQuery("SELECT * FROM CRM.VRSTA_KOMUNIKACIJE");
		List<Komunikacija> listaKomunikacija = new ArrayList<>();
		List<Zaposlenik> listaZaposlenika = new ArrayList<>();
		while (resultSetKomunikacije.next())
		{
			ResultSet resultSetKomunikacijeKlijenti = statementKomunikacijeKlijenti
					.executeQuery("SELECT * FROM CRM.TVRTKA_KLIJENT");
			ResultSet resultSetKomunikacijeZaposlenika = statementKomunikacijeZaposlenika
					.executeQuery("SELECT * FROM CRM.TVRTKA_ZAPOSLENIK");
			List<Klijent> klijenti = BazaPodataka.dohvatiKlijente();
			List<Zaposlenik> zaposlenici = BazaPodataka.dohvatiZaposlenike();
			List<Klijent> filtriraniKlijenti = new ArrayList<>();
			List<Zaposlenik> filtriraniZaposlenici = new ArrayList<>();
			Integer Id = resultSetKomunikacije.getInt("ID");
			Integer klijentId = resultSetKomunikacije.getInt("KLIJENT_ID");
			Integer zaposlenikId = resultSetKomunikacije.getInt("ZAPOSLENIK_ID");
			Integer vrstaId = resultSetKomunikacije.getInt("VRSTA_KOMUNIKACIJE_ID");
			String sadrzaj = resultSetKomunikacije.getString("SADRZAJ");
			LocalDateTime vrijeme = resultSetKomunikacije.getTimestamp("VRIJEME").toLocalDateTime();
			while (resultSetKomunikacijeKlijenti.next())
			{
					for (Klijent kl : klijenti)
					{
						if (kl.getId() == resultSetKomunikacijeKlijenti.getInt("KLIJENT_ID"))
							filtriraniKlijenti.add(kl);
					}
			}
			while (resultSetKomunikacijeZaposlenika.next())
			{
					for (Zaposlenik zap : zaposlenici)
					{
						if (zap.getId() == resultSetKomunikacijeZaposlenika.getInt("ZAPOSLENIK_ID"))
							filtriraniZaposlenici.add(zap);
					}
			}
			Komunikacija komunikacija = new Komunikacija(filtriraniKlijenti.get(0), filtriraniZaposlenici.get(0),
					VrstaKomunikacije.values()[vrstaId - 1], sadrzaj, vrijeme);
			komunikacija.setId(klijentId);
			listaKomunikacija.add(komunikacija);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaKomunikacija;
	}

	public static void spremiKomunikaciju(Komunikacija komunikacija) throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try
		{
			PreparedStatement insertKomunikacija = veza.prepareStatement(
					"INSERT INTO CRM.KOMUNIKACIJA (KLIJENT_ID, ZAPOSLENIK_ID, VRSTA_KOMUNIKACIJE_ID, SADRZAJ, VRIJEME) VALUES (?, ?, ?, ?, ?);");
			insertKomunikacija.setInt(1, komunikacija.getKlijent().getId());
			insertKomunikacija.setInt(2, komunikacija.getZaposlenik().getId());
			insertKomunikacija.setInt(3, komunikacija.getOznakaVrsteKomunikacije().ordinal() +1);
			insertKomunikacija.setString(4, komunikacija.getSadrzajKomunikacije());
			insertKomunikacija.setTimestamp(5, Timestamp.valueOf(komunikacija.getVrijemeKomunikacije()));
			insertKomunikacija.executeUpdate();
			ResultSet generatedKeys = insertKomunikacija.getGeneratedKeys();
			if (generatedKeys.next())
			{
				komunikacija.setId(generatedKeys.getInt(1));
			}
			veza.commit();
		}
		catch (Throwable ex)
		{
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
	public static List<Usluga> dohvatiUsluge() throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		Statement statementUsluga = veza.createStatement();
		Statement statementKlijent = veza.createStatement();
		ResultSet resultSetUsluga = statementUsluga.executeQuery("SELECT * FROM CRM.USLUGA");
		ResultSet resultSetKlijent = statementKlijent.executeQuery("SELECT * FROM CRM.KLIJENT");
		List<Usluga> listaUsluga = new ArrayList<>();
		while (resultSetUsluga.next())
		{
			Integer id = resultSetUsluga.getInt("ID");
			String opis = resultSetUsluga.getString("OPIS");
			Integer klijentId = resultSetUsluga.getInt("KLIJENT_ID");
			Integer vrstaUslugeId = resultSetUsluga.getInt("VRSTA_USLUGE_ID");
			boolean placena = resultSetUsluga.getBoolean("PLACENA");
			boolean obavljena = resultSetUsluga.getBoolean("OBAVLJENA");
			BigDecimal cijena = resultSetUsluga.getBigDecimal("CIJENA");
			LocalDate datum = resultSetUsluga.getDate("DATUM").toLocalDate();
			Klijent klijent = null;
			while (resultSetKlijent.next())
			{
				if (resultSetKlijent.getInt("ID") == klijentId)
				{
					klijent=BazaPodataka.dohvatiKlijenta(klijentId);
				}
			}
			
			Usluga usluga = new Usluga(klijent, VrstaUsluge.values()[vrstaUslugeId-1], opis, datum, cijena, obavljena, placena);
			usluga.setId(id);
			listaUsluga.add(usluga);
		}
		zatvaranjeVezeNaBazuPodataka(veza);
		return listaUsluga;
	}

	public static void spremiUslugu(Usluga usluga) throws SQLException, IOException
	{
		Connection veza = spajanjeNaBazuPodataka();
		veza.setAutoCommit(false);
		try
		{
			PreparedStatement insertUsluge = veza.prepareStatement(
					"INSERT INTO CRM.USLUGA (OPIS, KLIJENT_ID, VRSTA_USLUGE_ID, DATUM, CIJENA, PLACENA, OBAVLJENA) VALUES (?, ?, ?, ?, ?, ?, ?);");
			insertUsluge.setString(1, usluga.getOznakaOpisaUsluge());
			insertUsluge.setInt(2, usluga.getKlijent().getId());
			insertUsluge.setInt(3, usluga.getOznakaVrsteUsluge().ordinal());
			insertUsluge.setDate(4, Date.valueOf(usluga.getDatumUsluge()));
			insertUsluge.setBigDecimal(5, usluga.getCijenaUsluge());
			insertUsluge.setBoolean(6, usluga.getUslugaNaplacena());
			insertUsluge.setBoolean(7, usluga.getUslugaObavljena());
			insertUsluge.executeUpdate();
			ResultSet generatedKeys = insertUsluge.getGeneratedKeys();
			if (generatedKeys.next())
			{
				usluga.setId(generatedKeys.getInt(1));
			}
			veza.commit();
		}
		catch (Throwable ex)
		{
			veza.rollback();
			throw ex;
		}
		zatvaranjeVezeNaBazuPodataka(veza);
	}
	
}
