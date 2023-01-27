package infoborden;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import tijdtools.InfobordTijdFuncties;

/*
 * 
 * {
 * "tijd": 0,
 * "aankomsttijd": 0,
 * "lijnNaam": "string",
 * "busID": "string",
 * "bedrijf": "string",
 * "eindpunt": "string"
 * }
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public record JSONBericht(
	 int tijd,
	 int aankomsttijd,
	 String lijnNaam,
	 String busID,
	 String bedrijf,
	 String eindpunt)
{
	public String getInfoRegel() {
		// Code voor opdracht 3:
		// InfobordTijdFuncties tijdFuncties = new InfobordTijdFuncties();
		// String tijd = tijdFuncties.getFormattedTimeFromCounter(aankomsttijd);
		String tijd = "" + aankomsttijd;
		String regel = String.format("%8s - %5s - %12s", this.lijnNaam, this.eindpunt, tijd);
		return regel;
	}

}
