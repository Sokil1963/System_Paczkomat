// Model/DAO.java
package Model;

import java.util.List;

public class DAO implements IDAO {

	private String polaczenie;

	public DAO() {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public String pobierzPrzesylke(int idPrzesylki) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void zapiszPrzesylke(IPrzesylka przesylka) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<DanePrzesylki> pobierzListePrzesylekDoWydania(int idKurier, int idSortowni) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public List<DanePrzesylki> pobierzListePrzesylekDoOdbioru(int idKurier, int idPaczkomatu) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public DaneKuriera pobierzKurier(int idKurier) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public DanePaczkomatu pobierzPaczkomat(int idPaczkomatu) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void zapiszHistorieZdarzenia(ZdarzenieHistorii z) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public void zapiszPaczkomat(Paczkomat p) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
