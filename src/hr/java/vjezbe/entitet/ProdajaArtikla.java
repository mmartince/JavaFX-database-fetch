package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 
 * @author Amalah
 * @param artikl artikl koji se prodaje
 */
public class ProdajaArtikla extends Usluga implements Robna,Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7611248906420305366L;
	private Artikl artikl;

	public ProdajaArtikla(Klijent klijent, VrstaUsluge oznakaVrsteUsluge, String oznakaOpisaUsluge, LocalDate datumUsluge,
			BigDecimal cijenaUsluge, Boolean uslugaObavljena, Boolean uslugaNaplacena, Artikl artikl)
	{
		super(klijent, oznakaVrsteUsluge, oznakaOpisaUsluge, datumUsluge, cijenaUsluge, uslugaObavljena,
				uslugaNaplacena);
		this.artikl = artikl;
	}

	public Artikl getArtikl()
	{
		return artikl;
	}

	public void setArtikl(Artikl artikl)
	{
		this.artikl = artikl;
	}

	@Override
	public BigDecimal prodaja(int brArtikala)
	{
		this.setUslugaNaplacena(true);
		this.setUslugaObavljena(true);
		return this.getCijenaUsluge().multiply(new BigDecimal(brArtikala));
	}

}
