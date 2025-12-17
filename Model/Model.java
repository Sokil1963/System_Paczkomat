// Model/Model.java
package Model;

public class Model implements IModel {

	private Inwentarz inwentarz;
	private IDAO dao;

	public Model(Inwentarz inwentarz, IDAO dao) {
		this.inwentarz = inwentarz;
		this.dao = dao;
	}

	@Override
	public int znajdzPrzesylkiDoWydania(int idKurier, int idSortowni) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public int znajdzPrzesylkiDoOdbioruZPaczkomatu(int idKurier, int idPaczkomatu) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public boolean sprawdzIdKuriera(int idKurier) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public boolean sprawdzUprawnieniaKurieraDoUrzadzenia(int idKurier, int idUrzadzenia) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void zarejestrujAutoryzacjeKuriera(int idKurier, int idUrzadzenia) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void aktualizujStatusPrzesylki(int idPrzesylki, StatusPrzesylki nowyStatus, String opis) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void nadajTagPrzesylki(int idPrzesylki, TagPrzesylki tag, String opis) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void zarejestrujNieprawidlowaPrzesylke(int idPrzesylki, String typBledu, int idPaczkomatu, int idKurier) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void zarejestrujAwariePaczkomatu(int idPaczkomatu, String typBledu, int idKurier) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void zablokujPaczkomat(int idPaczkomatu) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void zarejestrujZdarzenieHistorii(int idPrzesylki, String opisZdarzenia, String aktor) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
