// Kontroler/OdbierzPrzesylkiZPaczkomatu.java
package Kontroler;

import Model.IModel;

public class OdbierzPrzesylkiZPaczkomatu extends IStrategiaAktualizacjiStatusuPrzesylki {

	private IStrategiaAktualizacjiStatusuPrzesylki strategia;
	private int[] listaId;

	public OdbierzPrzesylkiZPaczkomatu(IModel model) {
		super(model);
	}

	public void ustawListePrzesylek(int[] lista) {
		this.listaId = lista;
	}

	@Override
	public void aktualizujStatus(int idPrzesylki, int statusPrzesylki, String opis) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void wybierzAkcjeKuriera(TypAkcji akcja) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void wykonajOdbior() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
