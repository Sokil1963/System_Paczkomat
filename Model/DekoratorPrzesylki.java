// Model/DekoratorPrzesylki.java
package Model;

public abstract class DekoratorPrzesylki implements IPrzesylka {

	protected IPrzesylka przesylka;

	public DekoratorPrzesylki(IPrzesylka przesylka) {
		this.przesylka = przesylka;
	}

	@Override
	public int dajId() {
		return przesylka.dajId();
	}

	@Override
	public StatusPrzesylki dajStatus() {
		return przesylka.dajStatus();
	}

	@Override
	public TagPrzesylki dajTag() {
		return przesylka.dajTag();
	}

	@Override
	public abstract String dajOpis();
}
