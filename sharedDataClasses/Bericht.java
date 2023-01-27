package sharedDataClasses;

import java.util.ArrayList;

public record Bericht(
	String lijnNaam,
	String eindpunt,
	String bedrijf,
	String busID,
	int tijd,
	ArrayList<ETA> ETAs){}
