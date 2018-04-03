package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Alarm;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.MaloprodajnaTvrtka;
import hr.java.vjezbe.entitet.Tvrtka;
import hr.java.vjezbe.entitet.Zaposlenik;
import hr.java.vjezbe.glavna.GlavnaDatoteke;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application
{
	
	public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. hh.mm");
	public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
	private static BorderPane root;
	private Stage primaryStage;
	
	@Override
	public void start(Stage stage) throws SQLException, IOException
	{
		BazaPodataka.deleteAlarmi();
		BazaPodataka.spremiAlarm(new Alarm(BazaPodataka.dohvatiKlijenta(1),"Neki alarm",LocalDateTime.now().plusSeconds(50),true));
		BazaPodataka.spremiAlarm(new Alarm(BazaPodataka.dohvatiKlijenta(1),"Drugi alarm",LocalDateTime.now().plusSeconds(90),true));
		primaryStage = stage;
		try
		{
			root=(BorderPane)FXMLLoader.load(getClass().getResource( "PocetniEkran.fxml"));
			Scene scene = new Scene(root, 600, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
	
	public static void setCenterPane(BorderPane centerPane)
	{
		root.setCenter(centerPane);
	}
	

	
	
	public static ObservableList<Klijent> ucitavanjeKlijenata()
	{
		GlavnaDatoteke glav = new GlavnaDatoteke();
		ObservableList<Klijent> klijenti=FXCollections.observableArrayList();
		for(int i=0;i<Klijent.brojKlijenata;i++)
		{
			klijenti.add(glav.getObject("klijent"));
		}
		return klijenti;
	}
	public static ObservableList<Zaposlenik> ucitavanjeZaposlenika()
	{
		GlavnaDatoteke glav = new GlavnaDatoteke();
		ObservableList<Zaposlenik> zaposlenici=FXCollections.observableArrayList();
		for(int i=0;i<Zaposlenik.brojZaposlenika;i++)
		{
			zaposlenici.add(glav.getObject("zaposlenik"));
		}
		return zaposlenici;
	}
	public static List<Alarm> ucitavanjeAlarma()
	{
		GlavnaDatoteke glav = new GlavnaDatoteke();
		List<Alarm> alarmi=new ArrayList<>();
		for(int i=0;i<Alarm.brojAlarma;i++)
		{
			alarmi.add(glav.getObject("alarmm"));
		}
		return alarmi;
	}
	public static MaloprodajnaTvrtka ucitavanjeTvrtke()
	{
		GlavnaDatoteke glav = new GlavnaDatoteke();
		MaloprodajnaTvrtka tvrtka=null;
		for(int i=0;i<Tvrtka.brojTvrtci;i++)
		{
			tvrtka=glav.getObject("tvrtka");
		}
		return tvrtka;
	}

	public static ObservableList<Artikl> ucitavanjeArtikala()
	{
		GlavnaDatoteke glav = new GlavnaDatoteke();
		ObservableList<Artikl> artikli=FXCollections.observableArrayList();
		for(int i=0;i<3;i++)
		{
			artikli.add(glav.getObject("artikl"));
		}
		return artikli;
	}
}
