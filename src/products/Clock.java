package products;

import java.util.Date;

/**
 * Generisches Interface zur Verwaltung der Zeit.
 * Port im Modell, ist den Model-Klassen bekannt. Adapter in Anwendungsschicht können es gemäß individuellem Bedarf implementieren.
 */
public interface Clock {
	
	public void passDay();
	
	public Date getCurrentDate();

	public Date getInitialDate();
	
}
