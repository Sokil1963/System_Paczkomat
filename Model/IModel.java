// Model/IModel.java
package Model;

public interface IModel {

    int znajdzPrzesylkiDoWydania(int idKurier, int idSortowni);

    int znajdzPrzesylkiDoOdbioruZPaczkomatu(int idKurier, int idPaczkomatu);

    boolean sprawdzIdKuriera(int idKurier);

    boolean sprawdzUprawnieniaKurieraDoUrzadzenia(int idKurier, int idUrzadzenia);

    void zarejestrujAutoryzacjeKuriera(int idKurier, int idUrzadzenia);

    void aktualizujStatusPrzesylki(int idPrzesylki, StatusPrzesylki nowyStatus, String opis);

    void nadajTagPrzesylki(int idPrzesylki, TagPrzesylki tag, String opis);

    void zarejestrujNieprawidlowaPrzesylke(int idPrzesylki, String typBledu, int idPaczkomatu, int idKurier);

    void zarejestrujAwariePaczkomatu(int idPaczkomatu, String typBledu, int idKurier);

    void zablokujPaczkomat(int idPaczkomatu);

    void zarejestrujZdarzenieHistorii(int idPrzesylki, String opisZdarzenia, String aktor);
}
