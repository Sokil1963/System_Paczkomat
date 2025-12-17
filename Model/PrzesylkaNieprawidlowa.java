// Model/PrzesylkaNieprawidlowa.java
package Model;

public class PrzesylkaNieprawidlowa extends DekoratorPrzesylki {

	private String typBledu;

	public PrzesylkaNieprawidlowa(IPrzesylka przesylka, String typBledu) {
		super(przesylka);
		this.typBledu = typBledu;
	}

	@Override
	public String dajOpis() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
