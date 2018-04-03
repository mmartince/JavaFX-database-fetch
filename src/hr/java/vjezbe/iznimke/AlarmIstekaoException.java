package hr.java.vjezbe.iznimke;

/**
 * 
 * @author Amalah
 * Iznimka koja se baca ukoliko je istekao alarm
 */
public class AlarmIstekaoException extends RuntimeException
{
	private static final long serialVersionUID = 5530202309897425093L;

	public AlarmIstekaoException()
	{

	}

	public AlarmIstekaoException(String poruka)
	{

	}

	public AlarmIstekaoException(Throwable uzrok)
	{

	}

	public AlarmIstekaoException(String poruka, Throwable uzrok)
	{

	}
}
