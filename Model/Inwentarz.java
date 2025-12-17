// Model/Inwentarz.java
package Model;

import java.util.Collection;

public class Inwentarz {

	private Kurier[] kurier = null;
	private IDAO dao;
	private Paczkomat[] paczkomat = null;
	private Collection<ZdarzenieHistorii> zdarzeniaHistorii;

	public Inwentarz(IDAO dao, IFabrykaPrzesylek fabryka) {
		this.dao = dao;
	}

	public int[] znajdzPrzesylkiDoWydania(int idKurier, int idSortowni) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public int[] znajdzPrzesylkiDoOdbioruZPaczkomatu(int idKurier, int idPaczkomatu) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public boolean sprawdzIdKuriera(int idKurier) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public boolean sprawdzUprawnieniaDoUrzadzenia(int idKurier, int idUrzadzenia) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public IPrzesylka pobierzPrzesylke(int idPrzesylki) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void zapiszPrzesylke(IPrzesylka p) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void ustawStatusPrzesylki(int idPrzesylki, StatusPrzesylki status, String opis) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void ustawTagPrzesylki(int idPrzesylki, TagPrzesylki tag, String opis) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public Paczkomat pobierzPaczkomat(int idPaczkomatu) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void zapiszPaczkomat(Paczkomat p) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	public void zarejestrujZdarzenie(ZdarzenieHistorii z) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
