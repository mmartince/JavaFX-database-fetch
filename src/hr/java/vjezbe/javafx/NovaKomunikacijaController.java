package hr.java.vjezbe.javafx;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Komunikacija;
import hr.java.vjezbe.entitet.VrstaKomunikacije;
import hr.java.vjezbe.entitet.Zaposlenik;
import hr.java.vjezbe.niti.AlarmiNit;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class NovaKomunikacijaController
{
	public static AlarmiNit alarmiNit;
	public static Zaposlenik selectedZaposlenik;
		
		@FXML
		private TextField klijent;
		@FXML
		private TextField zaposlenik;
		@FXML
		private TextField sadrzaj;
		@FXML
		private ComboBox<VrstaKomunikacije> vrstaKomunikacije;
		@FXML
		private Button spremiButton;
		
		@FXML
		public void initialize()
		{
			vrstaKomunikacije.setValue(VrstaKomunikacije.ELEKTRONICKA);
			vrstaKomunikacije.getItems().setAll(VrstaKomunikacije.values());
			klijent.replaceSelection(alarmiNit.pAlarm.getKlijent().getIme() + " " + alarmiNit.pAlarm.getKlijent().getPrezime());
			zaposlenik.setText(selectedZaposlenik.getIme() + " " +selectedZaposlenik.getPrezime());
		}
		
		public void novaKomunkiacija() throws SQLException, IOException
		{
			Komunikacija komunikacija = new Komunikacija(alarmiNit.pAlarm.getKlijent(),selectedZaposlenik,vrstaKomunikacije.getValue(),sadrzaj.getText(),LocalDateTime.now());
			try
			{
				checkEmptyFields(komunikacija);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return;
			}
			BazaPodataka.spremiKomunikaciju(komunikacija);
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Uspješno spremanje informacije!");
			alert.setHeaderText("Uspješno spremanje informacije!");
			alert.setContentText("Uneseni podaci za informaciju su uspješno spremljeni.");
			alert.showAndWait();
			Stage stage = (Stage) spremiButton.getScene().getWindow();
			stage.close();
			KomunikacijaController.dodajNovuKomunikaciju(komunikacija);
			AlarmiNit.lock=0;
			notifyAll();
		}
		
		private void checkEmptyFields(Komunikacija kom) throws IOException
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Neuspjesno spremanje komunikacije!");
			alert.setHeaderText("Potrebno je ispraviti sljedece pogreske:");
			int err=0;
			if(kom.getSadrzajKomunikacije().isEmpty())
			{
				err=1;
				alert.setContentText("Morate unjeti sadrzaj!\n");
			}
			if(err==1)
			{
				alert.showAndWait();
				throw new IOException();
			}
		}
}
