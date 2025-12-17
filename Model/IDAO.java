// Model/IDAO.java
package Model;

import java.util.List;

public interface IDAO {

	String pobierzPrzesylke(int idPrzesylki);

	void zapiszPrzesylke(IPrzesylka przesylka);

	List<DanePrzesylki> pobierzListePrzesylekDoWydania(int idKurier, int idSortowni);

	List<DanePrzesylki> pobierzListePrzesylekDoOdbioru(int idKurier, int idPaczkomatu);

	DaneKuriera pobierzKurier(int idKurier);

	DanePaczkomatu pobierzPaczkomat(int idPaczkomatu);

	void zapiszHistorieZdarzenia(ZdarzenieHistorii z);

	void zapiszPaczkomat(Paczkomat p);
}
