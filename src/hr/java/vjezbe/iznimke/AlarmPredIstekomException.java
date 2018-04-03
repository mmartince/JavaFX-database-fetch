package hr.java.vjezbe.iznimke;

/**
 * 
 * @author Amalah
 * Iznimka koja se baca ukoliko je alarm pred isticanjem (60 sekundi)
 */
public class AlarmPredIstekomException extends Exception
{

	private static final long serialVersionUID = -1470200642369564508L;

	public AlarmPredIstekomException()
	{
		super();
	}

	public AlarmPredIstekomException(String poruka)
	{
		super(poruka);
	}

	public AlarmPredIstekomException(Throwable uzrok)
	{
		super(uzrok);
	}

	public AlarmPredIstekomException(String poruka, Throwable uzrok)
	{
		super(poruka, uzrok);
	}
}
