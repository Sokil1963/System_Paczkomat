// Kontroler/KontrolerKuriera.java
package Kontroler;

import Model.IModel;

public class KontrolerKuriera implements IKontrolKuriera {

	private final IModel model;

	public KontrolerKuriera(IModel model) {
		this.model = model;
	}

	@Override
	public void odbierzPrzesylkiZPaczkomatu() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void zgloszenieNieprawidlowejPrzesylki() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
