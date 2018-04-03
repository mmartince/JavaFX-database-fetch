package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Klijent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class KlijentiController
{
	private List<Klijent> listaKlijenata;
	@FXML
	private TextField klijentFilterTextField;
	@FXML
	private TableView<Klijent> alarmiTableView;
	@FXML
	private TableColumn<Klijent, String> oibColumn;
	@FXML
	private TableColumn<Klijent, String> prezimeColumn;
	@FXML
	private TableColumn<Klijent, String> imeColumn;
	@FXML
	private TableColumn<Klijent, String> brojTelefonaColumn;
	@FXML
	private TableColumn<Klijent, String> emailColumn;
	@FXML
	private TableColumn<Klijent, String> datumRodjenjaColumn;
	
	public static ObservableList<Klijent> observableListaKlijenata=FXCollections.observableArrayList();
	public static ObservableList<Klijent> filteredObservableListaKlijenata=FXCollections.observableArrayList();

	@FXML
	public void initialize() throws SQLException, IOException
	{
		oibColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("oib"));
		prezimeColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("prezime"));
		imeColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("ime"));
		brojTelefonaColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("brojTelefona"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("email"));
		datumRodjenjaColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("datumRodjenja"));
		
		listaKlijenata = PocetniEkranController.getKlijenti();
		observableListaKlijenata=FXCollections.observableArrayList(listaKlijenata);
	}
	
	public static void dodajNovogKlijenta(Klijent noviKlijent)
	{
		observableListaKlijenata.add(noviKlijent);
		filteredObservableListaKlijenata.add(noviKlijent);
	}
	
	public void prikaziKljente()
	{
		List<Klijent> filtriraniAlarmi = new ArrayList<>();
		listaKlijenata=observableListaKlijenata;
		if (klijentFilterTextField.getText().isEmpty() == false)
		{
			filtriraniAlarmi = listaKlijenata.stream().filter(p -> p.getPrezime().contains(klijentFilterTextField.getText())).collect(Collectors.toList());
		}
		else
		{
			filtriraniAlarmi = listaKlijenata;
		}
		filteredObservableListaKlijenata = FXCollections.observableArrayList(filtriraniAlarmi);
		alarmiTableView.setItems(filteredObservableListaKlijenata);
	}
}