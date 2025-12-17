// Kontroler/AktualizacjaStatusuPrzesylki.java
package Kontroler;

import Model.IModel;

public class AktualizacjaStatusuPrzesylki {

	private int idPrzesylki;
	private IModel model;

	public AktualizacjaStatusuPrzesylki(IModel model) {
		this.model = model;
	}

	public void aktualizujStatus(int idPrzesylki, int statusPrzesylki, String opis) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void wyborOpcji() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
