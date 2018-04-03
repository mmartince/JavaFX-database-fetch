package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Alarm;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.Komunikacija;
import hr.java.vjezbe.entitet.MaloprodajnaTvrtka;
import hr.java.vjezbe.entitet.Zaposlenik;
import hr.java.vjezbe.niti.AlarmiNit;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class PocetniEkranController
{
	
	@FXML AlarmiNit alarmiNitG;
	@FXML AlarmiController alarmiController;
	@FXML ArtikliController artikliController;
	@FXML KlijentiController klijentiController;
	@FXML ZaposleniciController zaposleniciController;
	@FXML KomunikacijaController komunikacijaController;
	@FXML NovaKomunikacijaController novaKomunikacijaController;
	
	private List<Alarm> listaAlarma;
	@SuppressWarnings("unused")
	private List<MaloprodajnaTvrtka> tvrtka;
	@FXML
	private TextField alarmiFilterTextField;
	@FXML
	private TableView<Alarm> alarmiTableView;
	@FXML
	private TableColumn<Klijent, String> klijentColumn;
	@FXML
	private TableColumn<Alarm, String> opisColumn;
	@FXML
	private TableColumn<Alarm, String> vrijemeColumn;

	@SuppressWarnings("static-access")
	@FXML
	public void initialize() throws SQLException, IOException
	{
		klijentColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("klijentIme"));
		opisColumn.setCellValueFactory(new PropertyValueFactory<Alarm, String>("opisAlarma"));
		vrijemeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alarm, String>, ObservableValue<String>>()
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Alarm, String> param)
			{
				return new ReadOnlyObjectWrapper<String>(Main.dateTimeFormatter.format(param.getValue().getVrijemeAlarma()));
			}
		});
		listaAlarma = BazaPodataka.dohvatiAlarme();
		tvrtka = BazaPodataka.dohvatiTvrtke();
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		AlarmiNit alarmiNit = new AlarmiNit();
		scheduler.scheduleAtFixedRate(alarmiNit, 0, 10, TimeUnit.SECONDS);
		
		alarmiNit.giveAlarm();
		alarmiNitG.init(this);
		zaposleniciController.init(this);
	}

	
	
	
	
	public static List<Komunikacija> getKomunikacije() throws SQLException, IOException
	{
		return BazaPodataka.dohvatiKomunikacije();
	}
	
	public static List<Alarm> getAlarmi()
	{
		return BazaPodataka.dohvatiAlarme();
	}
	
	public static List<MaloprodajnaTvrtka> getTvrtke() throws SQLException, IOException
	{
		return BazaPodataka.dohvatiTvrtke();
	}
	
	public static List<Zaposlenik> getZaposlenike() throws SQLException, IOException
	{
		return BazaPodataka.dohvatiZaposlenike();
	}
	
	public static List<Klijent> getKlijenti() throws SQLException, IOException
	{
		return BazaPodataka.dohvatiKlijente();
	}
	
	public static List<Artikl> getArtikli() throws SQLException, IOException
	{
		return BazaPodataka.dohvatiArtikle();
	}
	
	public void prikaziEkranKomunikacija()
	{
		try
		{
			BorderPane zaposlenikPane = FXMLLoader.load(Main.class.getResource("Komunikacija.fxml"));
			Main.setCenterPane(zaposlenikPane);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziErkanZaNvuKomunikaciju()
	{
		try
		{
			BorderPane novaKomunikacijaPane = FXMLLoader.load(Main.class .getResource("NovaKomunikacija.fxml"));
			Scene scene = new Scene(novaKomunikacijaPane,600,400);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNovogKorisnika()
	{
		try
		{
			BorderPane noviKlijentPane = FXMLLoader.load(Main.class .getResource("NoviKlijent.fxml"));
			Scene scene = new Scene(noviKlijentPane,600,400);
			scene.getStylesheets().add(getClass().getResource( "application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNovogZaposlenika()
	{
		try
		{
			BorderPane noviKlijentPane = FXMLLoader.load(Main.class .getResource("NoviZaposlenik.fxml"));
			Scene scene = new Scene(noviKlijentPane,600,400);
			scene.getStylesheets().add(getClass().getResource( "application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaNovogArtikla()
	{
		try
		{
			BorderPane noviKlijentPane = FXMLLoader.load(Main.class .getResource("NoviArtikl.fxml"));
			Scene scene = new Scene(noviKlijentPane,600,400);
			scene.getStylesheets().add(getClass().getResource( "application.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranZaposlenika()
	{
		try
		{
			BorderPane zaposlenikPane = FXMLLoader.load(Main.class.getResource("Zaposlenici.fxml"));
			Main.setCenterPane(zaposlenikPane);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranArtikla()
	{
		try
		{
			BorderPane artiklPane = FXMLLoader.load(Main.class.getResource("Artikli.fxml"));
			Main.setCenterPane(artiklPane);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranAlarma()
	{
		try
		{
			BorderPane alarmPane = FXMLLoader.load(Main.class .getResource("Alarmi.fxml"));
			Main.setCenterPane(alarmPane);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziEkranKlijenata()
	{
		try
		{
			BorderPane klijentiPane = FXMLLoader.load(Main.class.getResource("Klijenti.fxml"));
			Main.setCenterPane(klijentiPane);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void prikaziAlarme()
	{
		List<Alarm> filtriraniAlarmi = new ArrayList<Alarm>();
		alarmiFilterTextField.getText();
		if (alarmiFilterTextField.getText().isEmpty() == false)
		{
			filtriraniAlarmi = listaAlarma.stream().filter(p -> p.getKlijent().getOib().contains(alarmiFilterTextField.getText())).collect(Collectors.toList());
		}
		else
		{
			filtriraniAlarmi = listaAlarma;
		}
		ObservableList<Alarm> listaAlarma = FXCollections.observableArrayList(filtriraniAlarmi);
		alarmiTableView.setItems(listaAlarma);
	}
}