// Model/PrzesylkaZagubiona.java
package Model;

import java.time.LocalDateTime;

public class PrzesylkaZagubiona extends DekoratorPrzesylki {

	private LocalDateTime dataZgubienia;

	public PrzesylkaZagubiona(IPrzesylka przesylka, LocalDateTime dataZgubienia) {
		super(przesylka);
		this.dataZgubienia = dataZgubienia;
	}

	@Override
	public String dajOpis() {
		throw new UnsupportedOperationException("Not implemented yet");
	}
}
