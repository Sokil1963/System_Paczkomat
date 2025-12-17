// Model/FabrykaPrzesylkiNieprawidlowej.java
package Model;

public class FabrykaPrzesylkiNieprawidlowej implements IFabrykaPrzesylek {

	private IPrzesylka przesylka;
	private Kurier kurier;

	public FabrykaPrzesylkiNieprawidlowej(IPrzesylka przesylka, Kurier kurier) {
		this.przesylka = przesylka;
		this.kurier = kurier;
	}

	@Override
	public IPrzesylka utworzPrzesylke(DanePrzesylki dane) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
