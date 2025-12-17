// Model/Przesylka.java
package Model;

public class Przesylka implements IPrzesylka {

	private int id;
	private StatusPrzesylki status;
	private String opis;
	private TagPrzesylki tag;
	private Kurier[] kurier;

	public Przesylka(int id, StatusPrzesylki status, String opis) {
		this.id = id;
		this.status = status;
		this.opis = opis;
	}

	@Override
	public int dajId() {
		return id;
	}

	@Override
	public StatusPrzesylki dajStatus() {
		return status;
	}

	@Override
	public TagPrzesylki dajTag() {
		return tag;
	}

	@Override
	public String dajOpis() {
		return opis;
	}
}
