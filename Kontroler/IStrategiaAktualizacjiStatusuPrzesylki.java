// Kontroler/IStrategiaAktualizacjiStatusuPrzesylki.java
package Kontroler;

import Model.IModel;

public abstract class IStrategiaAktualizacjiStatusuPrzesylki {

	protected IModel model;
	protected int idPrzesylki;

	public IStrategiaAktualizacjiStatusuPrzesylki(IModel model) {
		this.model = model;
	}

	public abstract void aktualizujStatus(int idPrzesylki, int statusPrzesylki, String opis);
}
