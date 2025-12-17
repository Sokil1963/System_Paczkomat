// Kontroler/WydaniePrzesylekKurierowi.java
package Kontroler;

import Model.IModel;

public class WydaniePrzesylekKurierowi extends IStrategiaAktualizacjiStatusuPrzesylki {

	private int idKuriera;
	private int idSortowni;
	private String[] listaPrzesylek;

	public WydaniePrzesylekKurierowi(IModel model, int idKuriera) {
		super(model);
		this.idKuriera = idKuriera;
	}

	public void znajdzListePrzesylek() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void wyslijListeDoSortowni() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void aktywujPanelKontroliKuriera() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void obsluzWiadomoscOdKuriera() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void obsluzWiadomoscOdSortowni() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void aktualizujStatus(int idPrzesylki, int statusPrzesylki, String opis) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
