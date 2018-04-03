package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Amalah
 * @param artikl artikli koji se nalaze u maloprodajnoj tvrtci
 */
public class MaloprodajnaTvrtka extends Tvrtka
{
	
	public static final int brojTvrtci=1;
	
	Integer id;
	private List<Artikl> artikl=new ArrayList<>();

	public MaloprodajnaTvrtka(String nazivTvrtke, String oibTvrtke, List<Artikl> artikl)
	{
		super(nazivTvrtke, oibTvrtke);
		this.artikl = artikl;
	}

	public List<Artikl> getArtikl()
	{
		return artikl;
	}

	public void setArtikl(List<Artikl> artikl)
	{
		this.artikl = artikl;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

}