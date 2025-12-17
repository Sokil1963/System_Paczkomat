// Kontroler/KontrolerAdmina.java
package Kontroler;

import Model.IModel;

public class KontrolerAdmina implements IKontrolerAdmina {

	private final IModel model;

	public KontrolerAdmina(IModel model) {
		this.model = model;
	}

	@Override
	public void ustalanieZadanDystrybucyjnych() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
