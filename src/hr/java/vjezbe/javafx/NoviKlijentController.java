package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Klijent;
import hr.java.vjezbe.entitet.MaloprodajnaTvrtka;
import hr.java.vjezbe.entitet.Tvrtka;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NoviKlijentController
{

	@FXML
	private TextField prezime;
	@FXML
	private TextField ime;
	@FXML
	private TextField oib;
	@FXML
	private TextField brojTelefona;
	@FXML
	private TextField email;
	@FXML
	private DatePicker datumRodjenja;
	@FXML
	private Button spremiButton;

	public void noviKlijent() throws SQLException, IOException
	{
		Logger logger = LoggerFactory.getLogger(NoviKlijentController.class);
		Klijent klijent = new Klijent(oib.getText(), prezime.getText(), ime.getText(), brojTelefona.getText(), email.getText(), datumRodjenja.getValue());
		try
		{
			checkEmptyFields(klijent);
		}
		catch (IOException e)
		{
			logger.error("Pogreška kod spremanja podataka!", e);
			e.printStackTrace();
			return;
		}
		List<MaloprodajnaTvrtka> tvrtka = BazaPodataka.dohvatiTvrtke();
		BazaPodataka.spremiKlijenta(klijent,(Tvrtka) tvrtka.get(0));
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Uspješno spremanje klijenta!");
		alert.setHeaderText("Uspješno spremanje klijenta!");
		alert.setContentText("Uneseni podaci za klijenta su uspješno spremljeni.");
		alert.showAndWait();
		Stage stage = (Stage) spremiButton.getScene().getWindow();
		stage.close();
		KlijentiController.dodajNovogKlijenta(klijent);
	}
	private void checkEmptyFields(Klijent kl) throws IOException
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Neuspjesno spremanje klijenta!");
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
		if(kl.getOib().isEmpty() || kl.getOib().length()!=11)
		{
			err=1;
			if(kl.getOib().isEmpty()) alert.setContentText(alert.getContentText() + "Morate unjeti OIB!\n");
			if(kl.getOib().length()!=11) alert.setContentText(alert.getContentText() + "OIB mora imati 11 znakova!\n");
		}
		if(kl.getBrojTelefona().isEmpty())
		{
			err=1;
			alert.setContentText(alert.getContentText() + "Morate unjeti broj telefona!\n");
		}
		if(kl.getEmail().isEmpty())
		{
			err=1;
			alert.setContentText(alert.getContentText() + "Morate unjeti e-mail!\n");
		}
		if(kl.getDatumRodjenja()==null)
		{
			err=1;
			alert.setContentText(alert.getContentText() + "Morate unjeti datum rodjenja!");
		}
		if(err==1)
		{
			alert.showAndWait();
			throw new IOException();
		}
	}
}
