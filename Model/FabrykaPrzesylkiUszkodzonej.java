// Model/FabrykaPrzesylkiUszkodzonej.java
package Model;

public class FabrykaPrzesylkiUszkodzonej implements IFabrykaPrzesylek {

	private IPrzesylka przesylka;
	private Kurier kurier;

	public FabrykaPrzesylkiUszkodzonej(IPrzesylka przesylka, Kurier kurier) {
		this.przesylka = przesylka;
		this.kurier = kurier;
	}

	@Override
	public IPrzesylka utworzPrzesylke(DanePrzesylki dane) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
