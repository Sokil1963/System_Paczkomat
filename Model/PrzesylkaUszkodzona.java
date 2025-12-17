// Model/PrzesylkaUszkodzona.java
package Model;

public class PrzesylkaUszkodzona extends DekoratorPrzesylki {

	private String opisUszkodzenia;

	public PrzesylkaUszkodzona(IPrzesylka przesylka, String opisUszkodzenia) {
		super(przesylka);
		this.opisUszkodzenia = opisUszkodzenia;
	}

	@Override
	public String dajOpis() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
