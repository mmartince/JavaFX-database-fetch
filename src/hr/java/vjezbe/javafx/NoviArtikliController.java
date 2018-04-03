package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Artikl;
import hr.java.vjezbe.entitet.KategorijaArtikla;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NoviArtikliController
{
	@FXML
	private TextField naziv;
	@FXML
	private ComboBox<KategorijaArtikla> kategorija;
	@FXML
	private Button spremiButton;
	
	@FXML
	private void initialize()
	{
		kategorija.setValue(KategorijaArtikla.SOFTVER);
		kategorija.getItems().setAll(KategorijaArtikla.values());	
	}
	
	public void noviArtikl() throws SQLException, IOException
	{
		Logger logger = LoggerFactory.getLogger(NoviArtikliController.class);
		try
		{
			checkEmptyFields();
		}
		catch (Exception e)
		{
			logger.error("Pogreška kod spremanja podataka!", e);
			e.printStackTrace();
			return;
		}
		Artikl artikl = new Artikl(naziv.getText(), kategorija.getValue());
		BazaPodataka.spremiArtikl(artikl);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uspješno spremanje artikla!");
		alert.setHeaderText("Uspješno spremanje artikla!");
		alert.setContentText("Uneseni podaci za artikla su uspješno spremljeni.");
		alert.showAndWait();
		Stage stage = (Stage) spremiButton.getScene().getWindow();
		stage.close();
		ArtikliController.dodajNovogArtikla(artikl);
	}
	private void checkEmptyFields() throws IOException, IllegalArgumentException
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Neuspjesno spremanje klijenta!");
		alert.setHeaderText("Potrebno je ispraviti sljedece pogreske:");
		int err=0;
		if(naziv.getText().isEmpty())
		{
			err=1;
			alert.setContentText("Morate unjeti naziv!\n");
		}
		if(err==1)
		{
			alert.showAndWait();
			throw new IOException();
		}
	}
}
