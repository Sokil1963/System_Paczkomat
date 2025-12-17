// Kontroler/KontrolerFixera.java
package Kontroler;

import Model.IModel;

public class KontrolerFixera implements IKontrolerFixera {

	private final IModel model;

	public KontrolerFixera(IModel model) {
		this.model = model;
	}

	@Override
	public void zgloszenieAwariiPaczkomatu() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
