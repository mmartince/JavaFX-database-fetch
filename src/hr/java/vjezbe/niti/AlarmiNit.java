package hr.java.vjezbe.niti;

import java.util.List;
import java.util.Optional;

import hr.java.vjezbe.baza.podataka.BazaPodataka;
import hr.java.vjezbe.entitet.Alarm;
import hr.java.vjezbe.javafx.Main;
import hr.java.vjezbe.javafx.NovaKomunikacijaController;
import hr.java.vjezbe.javafx.PocetniEkranController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AlarmiNit implements Runnable
{
	private static PocetniEkranController pocetni;
	public Alarm pAlarm;
	
	private static BorderPane root;
	private Stage primaryStage;
	public static int lock=0;
	
	public AlarmiNit()
	{
		
	}

	 @Override
	    public synchronized void run()
	    {
		 	Platform.runLater(new Runnable()
		 	{
	            @Override
	            public void run()
	            {
        	    	while(lock==1)
        	    	{
        	    		try
        	    		{
        	    			wait();
        	    		}
        	    		catch(InterruptedException ex)
        	    		{
        	    			ex.printStackTrace();
        	    		}
        	    	}
        	    	lock=1;
	            	List<Alarm> alarmi = BazaPodataka.dohvatiAlarme();
	            	try
	                {
	            	    ButtonType confirmButton = new ButtonType("Da",  ButtonBar.ButtonData.OK_DONE);
	            	    ButtonType cancelButton = new ButtonType("Ne",  ButtonBar.ButtonData.CANCEL_CLOSE); 
	            	  
	            	    Alert alert = new Alert(AlertType.CONFIRMATION,  "Alarm za korisnika " + alarmi.get(0).getKlijent().getPrezime() + " " 
	            	    + alarmi.get(0).getKlijent().getIme() + " s opisom '" + alarmi.get(0).getOpisAlarma()  + "' istječe "
	            	    +  Main.dateTimeFormatter.format(alarmi.get(0).getVrijemeAlarma()) + " Želite li unijeti podatke o komunikaciji?",
	            	    confirmButton, cancelButton);
	            	    alert.setTitle("Upozorenje"); 
	            	    alert.setHeaderText("Alarm pred istekom"); 
	            	    
	            	    Optional<ButtonType> response = alert.showAndWait(); 
	            	    
	            	    if (response.isPresent() && response.get() == confirmButton)
	            	    {
	            	    	pAlarm=alarmi.get(0);
	            	    	pocetni.prikaziEkranZaposlenika();
	            	    	
	            	    }
	            	    if(response.isPresent() && response.get() == cancelButton)
	            	    {
	            	    	lock=0;
	            	    	notifyAll();
	            	    }
	                }
	                catch (Exception e)
	                {
	                  e.printStackTrace();
	                }
	          }});
	     }
	 
	 public String getPalarm()
	 {
		 return pAlarm.getKlijent().getIme() + " " + pAlarm.getKlijent().getPrezime();
	 }
	 
	 public void otvoriZaposlenike()
	 {
		 try
		 {
			 primaryStage = new Stage();
			 root=(BorderPane)FXMLLoader.load(getClass().getResource( "../javafx/Zaposlenici.fxml"));
			 Scene scene = new Scene(root, 600, 400);
			 primaryStage.setScene(scene);
			 primaryStage.show();
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
	 }

		public static void init(PocetniEkranController pocetniEkranController)
		{
			pocetni = pocetniEkranController;
		}
		public void giveAlarm()
		{
			NovaKomunikacijaController.alarmiNit=this;
		}
		public void giveZaposlenik()
		{
			
		}
}
