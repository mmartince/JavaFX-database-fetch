package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.KategorijaArtikla;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ArtikliController
{
	private List<Artikl> listaArtikala;
	@FXML
	private TextField artikliFilterTextField;
	@FXML
	private TableView<Artikl> artikliTableView;
	@FXML
	private TableColumn<Artikl, String> nazivArtiklaColumn;
	@FXML
	private TableColumn<KategorijaArtikla, String> kategorijaArtiklaColumn;
	
	public static ObservableList<Artikl> observableListaArtikala=FXCollections.observableArrayList();
	public static ObservableList<Artikl> filteredObservableListaArtikala=FXCollections.observableArrayList();

	@FXML
	public void initialize() throws SQLException, IOException
	{
		nazivArtiklaColumn.setCellValueFactory(new PropertyValueFactory<Artikl, String>("naziv"));
		kategorijaArtiklaColumn.setCellValueFactory(new PropertyValueFactory<KategorijaArtikla, String>("kategorija"));
		
		listaArtikala=PocetniEkranController.getArtikli();
		observableListaArtikala=FXCollections.observableArrayList(listaArtikala);
	}
	
	public static void dodajNovogArtikla(Artikl noviArtikl)
	{
		observableListaArtikala.add(noviArtikl);
		filteredObservableListaArtikala.add(noviArtikl);
	}
	
	public void prikaziArtikle()
	{
		List<Artikl> filtriraniArtikli = new ArrayList<>();
		listaArtikala=observableListaArtikala;
		artikliFilterTextField.getText();
		if (artikliFilterTextField.getText().isEmpty() == false)
		{
			filtriraniArtikli = listaArtikala.stream().filter(p -> p.getNaziv().contains(artikliFilterTextField.getText())).collect(Collectors.toList());
		}
		else
		{
			filtriraniArtikli = listaArtikala;
		}
		filteredObservableListaArtikala = FXCollections.observableArrayList(filtriraniArtikli);
		artikliTableView.setItems(filteredObservableListaArtikala);
	}
}