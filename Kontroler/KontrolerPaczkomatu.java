// Kontroler/KontrolerPaczkomatu.java
package Kontroler;

import Model.IModel;

public class KontrolerPaczkomatu implements IKontrolerPaczkomatu {

	private final IModel model;

	public KontrolerPaczkomatu(IModel model) {
		this.model = model;
	}

	@Override
	public void zgloszenieWydaniePrzesylki() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void zgloszeniePrzesylkiDoPrzewozu() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void wysylanieZadaniaZablokowaniaPaczkomatu() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	// dodatkowe pomocnicze z diagramu – mogą na razie tylko rzucać wyjątek
	public void zgloszenieNieprawidlowejPrzesylki() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void uwierzytelnianieKuriera() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void ustalanieZadanDystrybucyjnych() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void odbierzPrzesylkiZPaczkomatu() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
