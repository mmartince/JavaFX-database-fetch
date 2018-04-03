package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Zaposlenik;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NoviZaposlenikController
{
	@FXML
	private TextField prezime;
	@FXML
	private TextField ime;
	@FXML
	private TextField korisnickoIme;
	@FXML
	private TextField sifra;
	@FXML
	private Button spremiButton;
	
	public void noviZaposlenik() throws SQLException, IOException
	{
		Logger logger = LoggerFactory.getLogger(NoviZaposlenikController.class);
		Zaposlenik zaposlenik = new Zaposlenik(korisnickoIme.getText(),ime.getText(),prezime.getText(),sifra.getText());
		try
		{
			checkEmptyFields(zaposlenik);
		}
		catch (IOException e)
		{
			logger.error("Pogreška kod spremanja podataka!", e);
			e.printStackTrace();
			return;
		}
		BazaPodataka.spremiZaposlenika(zaposlenik);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uspješno spremanje zaposlenika!");
		alert.setHeaderText("Uspješno spremanje zaposlenika!");
		alert.setContentText("Uneseni podaci za zaposlenika su uspješno spremljeni.");
		alert.showAndWait();
		Stage stage = (Stage) spremiButton.getScene().getWindow();
		stage.close();
		ZaposleniciController.dodajNovogZaposlenika(zaposlenik);
	}
	private void checkEmptyFields(Zaposlenik kl) throws IOException
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Neuspjesno spremanje zaposlenika!");
		alert.setHeaderText("Potrebno je ispraviti sljedece pogreske:");
		int err=0;
		if(kl.getPrezime().isEmpty())
		{
			err=1;
			alert.setContentText("Morate unjeti prezime!\n");
		}
		if(kl.getIme().isEmpty())
		{
			err=1;
			alert.setContentText(alert.getContentText() + "Morate unjeti ime!\n");
		}
		if(kl.getSifraZaposlenika().isEmpty())
		{
			err=1;
			alert.setContentText(alert.getContentText() + "Morate unjeti sifru zaposlenika!\n");
		}
		if(kl.getKorisnickoIme().isEmpty())
		{
			err=1;
			alert.setContentText(alert.getContentText() + "Morate unjeti korisnicko ime!\n");
		}
		if(err==1)
		{
			alert.showAndWait();
			throw new IOException();
		}
	}
}
