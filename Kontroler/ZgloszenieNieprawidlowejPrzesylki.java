// Kontroler/ZgloszenieNieprawidlowejPrzesylki.java
package Kontroler;

import Model.IModel;

public class ZgloszenieNieprawidlowejPrzesylki extends IStrategiaAktualizacjiStatusuPrzesylki {

	private int idKuriera;
	private String typBledu;

	public ZgloszenieNieprawidlowejPrzesylki(IModel model) {
		super(model);
	}

	public void odbierzZgloszenieNieprawidlowejPrzesylki(String typBledu, int idPaczki) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public String wyslijKomunikatDoKuriera(String typBledu) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void aktualizujStatus(int idPrzesylki, int statusPrzesylki, String opis) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void wyslijZgloszenieNieprawidlowejPrzesylki() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
