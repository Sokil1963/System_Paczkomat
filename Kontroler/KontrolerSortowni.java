// Kontroler/KontrolerSortowni.java
package Kontroler;

import Model.IModel;

public class KontrolerSortowni implements IKontrolerSortowni {

	private final IModel model;

	public KontrolerSortowni(IModel model) {
		this.model = model;
	}

	@Override
	public void uwierzytelnianieKuriera() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void wydaniePrzesylekKurierowi() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void odebraniePrzesylkiOdKuriera() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void ustalanieZadanDystrybucyjnych() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
