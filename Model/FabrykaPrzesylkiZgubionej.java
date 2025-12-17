// Model/FabrykaPrzesylkiZgubionej.java
package Model;

public class FabrykaPrzesylkiZgubionej implements IFabrykaPrzesylek {

	private IPrzesylka przesylka;
	private Kurier kurier;

	public FabrykaPrzesylkiZgubionej(IPrzesylka przesylka, Kurier kurier) {
		this.przesylka = przesylka;
		this.kurier = kurier;
	}

	@Override
	public IPrzesylka utworzPrzesylke(DanePrzesylki dane) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
