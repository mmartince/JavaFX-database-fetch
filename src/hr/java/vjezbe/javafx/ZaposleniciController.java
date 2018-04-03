package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Zaposlenik;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ZaposleniciController
{
	private static PocetniEkranController pocetni;
	
	public Zaposlenik selected;
	
	private List<Zaposlenik> listaZaposlenika;
	@FXML
	private TextField zaposleniciFilterTextField;
	@FXML
	private TableView<Zaposlenik> zaposleniciTableView;
	@FXML
	private TableColumn<Zaposlenik, String> korisnickoImeColumn;
	@FXML
	private TableColumn<Zaposlenik, String> prezimeColumn;
	@FXML
	private TableColumn<Zaposlenik, String> imeColumn;
	@FXML
	private TableColumn<Zaposlenik, String> sifraZeposlenikaColumn;
	
	
	public static ObservableList<Zaposlenik> observableListaZaposlenika=FXCollections.observableArrayList();
	public static ObservableList<Zaposlenik> filteredObservableListaZaposlenika=FXCollections.observableArrayList();
	
	

	@FXML
	public void initialize() throws SQLException, IOException
	{
		korisnickoImeColumn.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("korisnickoIme"));
		prezimeColumn.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("prezime"));
		imeColumn.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("ime"));
		sifraZeposlenikaColumn.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("sifraZaposlenika"));

		listaZaposlenika = PocetniEkranController.getZaposlenike();
		observableListaZaposlenika=FXCollections.observableArrayList(listaZaposlenika);
		zaposleniciTableView.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if(event.getClickCount() > 1)
				{
		            Node node = ((Node) event.getTarget()).getParent();
		            TableRow<?> row;
		            if (node instanceof TableRow)
		            {
		                row = (TableRow<?>) node;
		            }
		            else
		            {
		                row = (TableRow<?>) node.getParent();
		            }
		            
		            selected=(Zaposlenik)row.getItem();
		            NovaKomunikacijaController.selectedZaposlenik=selected;
		            pocetni.prikaziErkanZaNvuKomunikaciju();
				}
			}
		}); 
	}
	
	public static void init(PocetniEkranController pocetniEkranController)
	{
		pocetni = pocetniEkranController;
	}
	
	public void ekranNoveKomunikacije(Zaposlenik zap)
	 {
		 try
		 {
			 Stage stage = new Stage();
			 BorderPane root=(BorderPane)FXMLLoader.load(getClass().getResource( "NovaKomunikacija.fxml"));
			 Scene scene = new Scene(root, 600, 400);
			// klijent.setText(alarm.getKlijent().getIme() + " " + alarm.getKlijent().getPrezime());
			 //zaposlenik.setText(zap.getIme() + " " + zap.getPrezime());
			 stage.setScene(scene);
			 stage.show();
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
	 }
	
	public static void dodajNovogZaposlenika(Zaposlenik noviZaposlenik)
	{
		observableListaZaposlenika.add(noviZaposlenik);
		filteredObservableListaZaposlenika.add(noviZaposlenik);
	}
	
	public void prikaziAlarme()
	{
		List<Zaposlenik> filtriraniZaposlenici = new ArrayList<>();
		listaZaposlenika=observableListaZaposlenika;
		zaposleniciFilterTextField.getText();
		if (zaposleniciFilterTextField.getText().isEmpty() == false)
		{
			filtriraniZaposlenici = listaZaposlenika.stream().filter(p -> p.getPrezime().contains(zaposleniciFilterTextField.getText())).collect(Collectors.toList());
		}
		else
		{
			filtriraniZaposlenici = listaZaposlenika;
		}
		filteredObservableListaZaposlenika = FXCollections.observableArrayList(filtriraniZaposlenici);
		zaposleniciTableView.setItems(filteredObservableListaZaposlenika);
	}
}
