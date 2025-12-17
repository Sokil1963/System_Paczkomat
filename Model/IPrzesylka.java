// Model/IPrzesylka.java
package Model;

public interface IPrzesylka {

	int dajId();

	StatusPrzesylki dajStatus();

	TagPrzesylki dajTag();

	String dajOpis();
}
