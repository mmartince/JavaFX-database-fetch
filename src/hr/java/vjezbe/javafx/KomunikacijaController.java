package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.Komunikacija;
import hr.java.vjezbe.entitet.VrstaKomunikacije;
import hr.java.vjezbe.entitet.Zaposlenik;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class KomunikacijaController
{
	@FXML
	private List<Komunikacija> listaKomunikacija;
	@FXML
	private TextField komunikacijaFilterTextField;
	@FXML
	private TableView<Komunikacija> komunikacijaTableView;
	@FXML
	private TableColumn<Klijent, String> klijentColumn;
	@FXML
	private TableColumn<Zaposlenik, String> zaposlenikColumn;
	@FXML
	private TableColumn<VrstaKomunikacije, String> vrstaKomunikacijeColumn;
	@FXML
	private TableColumn<Komunikacija, String> sadrzajColumn;
	@FXML
	private TableColumn<Komunikacija, String> vrijemeAlarmaColumn;
	
	public static ObservableList<Komunikacija> observableListaKomunikacija=FXCollections.observableArrayList();
	public static ObservableList<Komunikacija> filteredObservableListaKomunikacija=FXCollections.observableArrayList();
	
	@FXML
	public void initialize() throws SQLException, IOException
	{
		klijentColumn.setCellValueFactory(new PropertyValueFactory<Klijent, String>("klijenti"));
		zaposlenikColumn.setCellValueFactory(new PropertyValueFactory<Zaposlenik, String>("zaposlenici"));
		vrstaKomunikacijeColumn.setCellValueFactory(new PropertyValueFactory<VrstaKomunikacije, String>("oznakaVrsteKomunikacije"));
		sadrzajColumn.setCellValueFactory(new PropertyValueFactory<Komunikacija, String>("sadrzajKomunikacije"));
		vrijemeAlarmaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Komunikacija, String>, ObservableValue<String>>()
		{
			@Override
			public ObservableValue<String> call(CellDataFeatures<Komunikacija, String> param)
			{
				return new ReadOnlyObjectWrapper<String>(Main.dateTimeFormatter.format(param.getValue().getVrijemeKomunikacije()));
			}
		});
		
		listaKomunikacija=PocetniEkranController.getKomunikacije();
		observableListaKomunikacija=FXCollections.observableArrayList(listaKomunikacija);
	}
	
	public static void dodajNovuKomunikaciju(Komunikacija novaKomunikacijua)
	{
		observableListaKomunikacija.add(novaKomunikacijua);
		filteredObservableListaKomunikacija.add(novaKomunikacijua);
	}
	
	public void prikaziKomunikacije()
	{
		List<Komunikacija> filtriraneKomunikacije = new ArrayList<>();
		listaKomunikacija=observableListaKomunikacija;
		komunikacijaFilterTextField.getText();
		if (komunikacijaFilterTextField.getText().isEmpty() == false)
		{
			filtriraneKomunikacije = listaKomunikacija.stream().filter(p -> p.getKlijent().getPrezime().contains(komunikacijaFilterTextField.getText())).collect(Collectors.toList());
		}
		else
		{
			filtriraneKomunikacije = listaKomunikacija;
		}
		filteredObservableListaKomunikacija = FXCollections.observableArrayList(filtriraneKomunikacije);
		komunikacijaTableView.setItems(filteredObservableListaKomunikacija);
	}
}
