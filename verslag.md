# Verslag Software quality enterprise application integration bus simulator

| Auteur           | studentnummer |
| ---------------- | ------------- |
| Mees van Dijk    | 393219        |
| Hylbren Rijnders | 372649        |

## Meaningful names comments, formatting

Over naamgeving wordt het volgende beschreven: Namen eten de intentie van iets
benoemen. Bijvoorbeeld bij een variabele die een bepaalde tijdunit vasthoud moet
duidelijk maken wat de tijdunit is, zijn het secondes,minuten uren dagen weken
maanden jaren. Dit principe werkt ook door bij variabelen verder namen geven,
het is af te raden om op deze manier variabelen te benoemen:

```java
int a1;
int a2;
int a3;
int a4;
```

etc te gaan, geef in plaats daarvan duidelijke eenduidige namen aan variabelen.

```java
int elapsedDaysSince;
int minutesAfterReset;
int weeksSinceFailedExam;
int daysSinceLastMentalBreakdown;
```

Ook is het handig om bij functies geen gebruik tem aken van magic numbers, maar
deze zowaar mogelijk in een leesbare functie te veranderen, bijvoorbeeld als bij
een bepaald getal een status moet veranderen.

Ook is het belangerijk dat er geen dubbelzinne namen worden gebruikt. Ga
bbijvoorbeeld geen namen van structure gebruiken tenzij het object in de
variablee ook echt van dat type is. Ook is het niet handig om variabelenamen die
slechts 1 karakter van elkaar verschillen, ze moeten distinct zijn.

Ook is het ontzettend onhandig om zelfbedachte woorden te geven aan variabelen
die niet duidelijk aangeven wat een variabele is en doet.

Door de hele applicatie heen zijn de functienamen wel goed benoemd.

Er zijn bijna geen comments behalve als iets voor java FX wordt gebruikt.

## functions

Als het gaat om functions dan schrijft Robert Martin in zijn boek dat functies
kort moeten zijn. Het liefst wil je de functie ver opsplitsen in andere
functies. Op die manier kun je functies bouwen uit andere functies en blijft je
code leesbaar. Een ander criterium van functies is dat ze één ding doen, en dat
ding goed doen. Dit heeft er mee te maken dat functies, net zoals objecten, een
duidelijke, enkele verantwoordelijkheid moeten hebben. Als laatste is het
belangrijk dat een functie een duidelijk abstractieniveau heeft. Een functie
moet niet tegelijk ruwe data parsen en dan deze data in een scherm weergeven.
Het parsen is een ander abstractieniveau dan het weergeven.

### Voorbeelden

In de file `Dasboard.java` staat de volgende functie:

```java
public void start(Stage primaryStage) {
    // Create a pane and set its properties
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    pane.setHgap(5.5);
    pane.setVgap(5.5);
    pane.add(new Label("Halte:"), 0, 0);
    TextField halteInput = new TextField("A-Z");
    pane.add(halteInput, 1, 0);
    pane.add(new Label("Richting:"), 0, 1);
    TextField richting = new TextField("1 of -1");
    pane.add(richting, 1, 1);
    Button btBord = new Button("Start Bord");
    btBord.setOnAction(e -> {
      startBord(halteInput.getText(), richting.getText());
    });
    Button btStart = new Button("Start");
    btStart.setOnAction(e -> {
      startAlles();
    });
    Button btLogger = new Button("Start Logger");
    btLogger.setOnAction(e -> {
      thread(new ArrivaLogger(), false);
    });
    pane.add(btBord, 1, 5);
    pane.add(btStart, 2, 5);
    pane.add(btLogger, 3, 5);
    GridPane.setHalignment(btBord, HPos.LEFT);
    GridPane.setHalignment(btStart, HPos.LEFT);
    GridPane.setHalignment(btLogger, HPos.LEFT);
    Scene scene = new Scene(pane);
    primaryStage.setTitle("BusSimulatie control-Center");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
```

Deze functie is veel te lang en doet veel meer dan hij zou moeten. Deze functie
maakt knoppen en text inputs aan en moet ze ook nog positioneren. Daarnaast
voegt de functie ook nog functionaliteit toe aan die knoppen, wat op een ander
abstractieniveau ligt dan de layout.  
Een verbeterde versie zou zijn:

```java
public void start(Stage primaryStage) {
    PageElements elements = createPageElements();
    addOnClickHandlers(elements);
    Scene scene = new Scene(elements.pane);
    primaryStage.setTitle("BusSimulatie control-Center");
    primaryStage.setScene(scene);
    primaryStage.show();`
}

public void static addOnClickHandlers(PageElements elements) {
    elements.btBord.setOnAction(e -> {
        startBord(halteInput.getText(), richting.getText());
    });
    elements.btStart.setOnAction(e -> {
        startAlles();
    });
    elements.btLogger.setOnAction(e -> {
        thread(new ArrivaLogger(), false);
    });
}

public static PageElements createPageElements() {
    // Create a pane and set its properties
    GridPane pane = new GridPane();
    pane.setAlignment(Pos.CENTER);
    pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
    pane.setHgap(5.5);
    pane.setVgap(5.5);
    pane.add(new Label("Halte:"), 0, 0);
    TextField halteInput = new TextField("A-Z");
    pane.add(halteInput, 1, 0);
    pane.add(new Label("Richting:"), 0, 1);
    TextField richting = new TextField("1 of -1");
    pane.add(richting, 1, 1);
    Button btBord = new Button("Start Bord");
    pane.add(btBord, 1, 5);
    pane.add(btStart, 2, 5);
    pane.add(btLogger, 3, 5);
    GridPane.setHalignment(btBord, HPos.LEFT);
    GridPane.setHalignment(btStart, HPos.LEFT);
    GridPane.setHalignment(btLogger, HPos.LEFT);

    return new PageElements(
        pane,
        halteInput,
        richting,
        btBord
    )
}
```

In deze verbeterde versie worden verantwoordelijkheden netter uitgesplitst over
verschillende functies en is de intentie van de functies duidelijker.

Een voorbeeld van een goede functie in de bestaande code-base is
`infoborden.InfoBord.updateBord()`. Deze functie ziet er als volgt uit:

```java
public void updateBord() {
    Runnable updater = new Runnable() {
        @Override
        public void run() {
            verwerkBericht();
        }
    };
    Platform.runLater(updater);
}
```

De functie heeft een duidelijke intentie, en doet alleen dat.

## Objects and data structures

In het hoofdstuk Objects and data structures worden de verschillen tussen
objecten en data structuren uitgelegd. Hier wordt besproken wat de voor en
nadelen van objecten en data structuren zijn.  
Objecten zijn slim en bevatten hun functionaliteit binnen zichzelf. Ze slaan
data privé op en hebben een nauw contract met de buitenwereld.  
Datastructuren zijn compleet open en bevatten niet hun eigen functionaliteit.
Buitenstaande code moet worden geschreven om de data structuren te interpreteren
en te manipuleren.

### Voorbeelden

Een goed voorbeeld van een Data structure is de ETA class in
`mockDatabaseLogger`.  
De class ziet er als volgt uit:

```java
public class ETA {
	String halteNaam;
	int richting;
	int aankomsttijd;

	ETA(String halteNaam, int richting, int aankomsttijd){
		this.halteNaam=halteNaam;
		this.richting=richting;
		this.aankomsttijd=aankomsttijd;
	}
}
```

Deze class heeft een aantal public properties en heeft geen functionaliteit, een
typisch voorbeeld van een data structuur.  
Hetzelfde geld voor de class `MockDatabaseLogger.Bericht`.  
Deze ziet er zo uit:

```java
package mockDatabaseLogger;

import java.util.ArrayList;

public class Bericht {
	String lijnNaam;
	String eindpunt;
	String bedrijf;
	String busID;
	int tijd;
	ArrayList<ETA> ETAs;

	Bericht(String lijnNaam, String bedrijf, String busID, int tijd){
		this.lijnNaam=lijnNaam;
		this.bedrijf=bedrijf;
		this.eindpunt="";
		this.busID=busID;
		this.tijd=tijd;
		this.ETAs=new ArrayList<ETA>();
	}
}
```

Ook deze class is een goed voorbeeld van een data sturcture.

Een voorbeeld van een slechte uitvoering van een data class is de
`infoborden.JSONBericht` class.  
Deze ziet er als volgt uit:

```java
public class JSONBericht {
	private int tijd;
	private int aankomsttijd;
	private String lijnNaam;
	private String busID;
	private String bedrijf;
	private String eindpunt;

	public JSONBericht(int tijd, int aankomsttijd, String lijnNaam, String busID, String bedrijf, String eindpunt) {
		super();
		this.tijd = tijd;
		this.aankomsttijd = aankomsttijd;
		this.lijnNaam = lijnNaam;
		this.busID = busID;
		this.bedrijf = bedrijf;
		this.eindpunt = eindpunt;
	}

	public JSONBericht() {

	}

	public int getTijd() {
		return tijd;
	}

	public void setTijd(int tijd) {
		this.tijd = tijd;
	}

	public int getAankomsttijd() {
		return aankomsttijd;
	}

	public void setAankomsttijd(int aankomsttijd) {
		this.aankomsttijd = aankomsttijd;
	}

	public String getLijnNaam() {
		return lijnNaam;
	}

	public void setLijnNaam(String lijnNaam) {
		this.lijnNaam = lijnNaam;
	}

	public String getBusID() {
		return busID;
	}

	public void setBusID(String busID) {
		this.busID = busID;
	}

	public String getBedrijf() {
		return bedrijf;
	}

	public void setBedrijf(String bedrijf) {
		this.bedrijf = bedrijf;
	}

	public String getEindpunt() {
		return eindpunt;
	}

	public void setEindpunt(String eindpunt) {
		this.eindpunt = eindpunt;
	}

	public String getInfoRegel() {
		// Code voor opdracht 3:
		// InfobordTijdFuncties tijdFuncties = new InfobordTijdFuncties();
		// String tijd = tijdFuncties.getFormattedTimeFromCounter(aankomsttijd);
		String tijd = "" + aankomsttijd;
		String regel = String.format("%8s - %5s - %12s", this.lijnNaam, this.eindpunt, tijd);
		return regel;
	}

	@Override
	public String toString() {
		return "JSONBericht [tijd=" + tijd + ", aankomsttijd=" + aankomsttijd + ", lijnNaam=" + lijnNaam + ", busID="
				+ busID + ", bedrijf=" + bedrijf + ", eindpunt=" + eindpunt + "]";
	}
}
```

Deze class heeft (vrijwel) geen functionaliteit maar biedt wel getters en
setters voor al zijn properties. Dit is een slechte vorm van object oriented
programmeren en lijkt op wat Robert Martin een `Bean` noemt. Een class met
private properties die door getters/setters public worden gemaakt waarbij de
getters/setters geen toegevoegde waarde hebben. Dit soort klassen worden vaak
ontworpen door Object-oriented puristen.

## Error handling

Alle code heeft error handling nodig, omdat er nou eenmaal dingen fout kunnen
gaan in een programma, zoals verkeerde input. Error handling kan een grote bron
van slechte code zijn, niet omdat de code die de error oplost slecht is, maar
omdat de error handling code overal door de normale code heen staat.

### Voorbeelden

Een voorbeeld waarbij zowel goede als verkeerde error-handling wordt gebruikt
zit in `infoBorden.QueueListener.onMessage()`. Deze functie ziet er als volgt
uit:

```java
public void onMessage(Message message) {
    try {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println("Consumer(" + consumerName + ")");
            berichten.nieuwBericht(text);
            infobord.updateBord();
        } else {
            System.out.println("Consumer(" + consumerName + "): Received: " + message);
        }
    } catch (JMSException e) {
        e.printStackTrace();
    }
}
```

Deze methode weet alleen om te gaan met messages van het type `TextMessage`. In
de method wordt een if statement gebruikt om te checken dat de message van dat
type is, als dat niet zo is wordt er geen mogelijkheid aan de calling functie
gegeven om de error af te handelen maar schrijft de functie gewoon naar
stdout.  
De goede vorm van error handling zit hem in de try-catch, hoewel hier ook niet
echt met de error om gegaan wordt, maar ook alleen gelogd wordt.

Een betere variant van deze methode is:

```java
public void onMessage(Message message) {
    try {
        TextMessage textMessage = (TextMessage) message;
        String text = textMessage.getText();
        System.out.println("Consumer(" + consumerName + ")");
        berichten.nieuwBericht(text);
        infobord.updateBord();
    } catch (JMSException e) {
        e.printStackTrace();
    }
    catch (ClassCastException e) {
        System.out.println("Consumer(" + consumerName + "): Received: " + message);
    }
}
```

Hier wordt de code uitgevoerd alsof er niks aan de hand is zonder if checks op
de data, als de data invalide is dan wordt de error gevangen en afgehandeld.

## Boundaries (spaghetti code)

Op veel plekken in deze code wordt met third-party libraries gewerkt. Vooral JMS
komt hier veel voor. Volgens Robert Martin is het beter om je code te schrijven
vanuit de intenties die je hebt, en dan een adapter te schrijven tussen je code
en de third-party library die er voor zorgt dat de intenties van je eigen code
worden vertaald naar de nodige interacties met de third-party code. Op deze
manier heb je maar een enkele plek om de code te veranderen als de interface van
de third-party code verandert.

### Voorbeelden

Op een aantal plekken zijn er wel duidelijke scheidingen tussen de intenties van
eigen code en de nodige interacties met third-party code. Een voorbeeld hiervan
is de `bussimulator.producer` class.  
Deze ziet er als volgt uit:

```java
public class Producer {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    // TODO hier de naam van de destination invullen
    private static String subject = "bus-updates-xml";

    private Session session;
    private Connection connection;
    private MessageProducer producer;

    public Producer() {
    }

    public void sendBericht(String bericht) {
        try {
            createConnection();
            sendTextMessage(bericht);
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void createConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination dest = session.createQueue(subject);
        producer = session.createProducer(dest);

    }

    private void sendTextMessage(String themessage) throws JMSException {
        TextMessage message = session.createTextMessage(themessage);
        producer.send(message);
    }
}
```

Hier zie je een adapter die een simpele intentie van eigen code (`sendBericht`)
vertaalt naar de nodige stappen van de JMS code. De gebruikers van deze class
hoeven niet te weten hoe een bericht versturen werkt in JMS, ze hoeven alleen de
`sendBericht` methode aan te roepen met een string.

## Classes

Een class moet, net zoals een methode, klein zijn. Ook is het voor classes
belangrijk dat ze maar 1 verantwoordelijkheid hebben. Het single-responsibility
principle. Dat houdt in dat een class maar 1 reden moet hebben om te veranderen.
Een ander belangrijk concept bij classes is cohesion. Cohesion geeft aan hoe
sterk de methoden en properties van een class verbonden zijn. Je wil classen met
zo hoog mogelijke cohesion, omdat dan alle methoden en properties blijkbaar
samen werken naar een enkel doel.

### Voorbeelden

De bus class in `bussimulator.Bus` is erg groot. Deze class is een goede
kandidaat om opgesplitst te worden. De grootte van de class maakt hem ook
moeilijk om te begrijpen.  
De bus class ziet er als volgt uit:

```java
public class Bus {

	private Bedrijven bedrijf;
	private Lijnen lijn;
	private int halteNummer;
	private int totVolgendeHalte;
	private int richting;
	private boolean bijHalte;
	private String busID;

	Bus(Lijnen lijn, Bedrijven bedrijf, int richting) {
		this.lijn = lijn;
		this.bedrijf = bedrijf;
		this.richting = richting;
		this.halteNummer = -1;
		this.totVolgendeHalte = 0;
		this.bijHalte = false;
		this.busID = "Niet gestart";
	}

	public void setbusID(int starttijd) {
		this.busID = starttijd + lijn.name() + richting;
	}

	public void naarVolgendeHalte() {
		Positie volgendeHalte = lijn.getHalte(halteNummer + richting).getPositie();
		totVolgendeHalte = lijn.getHalte(halteNummer).afstand(volgendeHalte);
	}

	public boolean halteBereikt() {
		halteNummer += richting;
		bijHalte = true;
		if ((halteNummer >= lijn.getLengte() - 1) || (halteNummer == 0)) {
			System.out.printf("Bus %s heeft eindpunt (halte %s, richting %d) bereikt.%n",
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer) * richting);
			return true;
		} else {
			System.out.printf("Bus %s heeft halte %s, richting %d bereikt.%n",
					lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer) * richting);
			naarVolgendeHalte();
		}
		return false;
	}

	public void start() {
		halteNummer = (richting == 1) ? 0 : lijn.getLengte() - 1;
		System.out.printf("Bus %s is vertrokken van halte %s in richting %d.%n",
				lijn.name(), lijn.getHalte(halteNummer), lijn.getRichting(halteNummer) * richting);
		naarVolgendeHalte();
	}

	public boolean move() {
		boolean eindpuntBereikt = false;
		bijHalte = false;
		if (halteNummer == -1) {
			start();
		} else {
			totVolgendeHalte--;
			if (totVolgendeHalte == 0) {
				eindpuntBereikt = halteBereikt();
			}
		}
		return eindpuntBereikt;
	}

	public void sendETAs(int nu) {
		int i = 0;
		Bericht bericht = new Bericht(lijn.name(), bedrijf.name(), busID, nu);
		if (bijHalte) {
			ETA eta = new ETA(lijn.getHalte(halteNummer).name(), lijn.getRichting(halteNummer) * richting, 0);
			bericht.ETAs.add(eta);
		}
		Positie eerstVolgende = lijn.getHalte(halteNummer + richting).getPositie();
		int tijdNaarHalte = totVolgendeHalte + nu;
		for (i = halteNummer + richting; !(i >= lijn.getLengte()) && !(i < 0); i = i + richting) {
			tijdNaarHalte += lijn.getHalte(i).afstand(eerstVolgende);
			ETA eta = new ETA(lijn.getHalte(i).name(), lijn.getRichting(i) * richting, tijdNaarHalte);
			// System.out.println(bericht.lijnNaam + " naar halte" + eta.halteNaam + " t=" +
			// tijdNaarHalte);
			bericht.ETAs.add(eta);
			eerstVolgende = lijn.getHalte(i).getPositie();
		}
		bericht.eindpunt = lijn.getHalte(i - richting).name();
		sendBericht(bericht);
	}

	public void sendLastETA(int nu) {
		Bericht bericht = new Bericht(lijn.name(), bedrijf.name(), busID, nu);
		String eindpunt = lijn.getHalte(halteNummer).name();
		ETA eta = new ETA(eindpunt, lijn.getRichting(halteNummer) * richting, 0);
		bericht.ETAs.add(eta);
		bericht.eindpunt = eindpunt;
		sendBericht(bericht);
	}

	public void sendBericht(Bericht bericht) {
		XStream xstream = new XStream();
		xstream.alias("bus", Bus.class);
		xstream.alias("bericht", Bericht.class);
		xstream.alias("eta", ETA.class);
		xstream.alias("lijn", Lijnen.class);
		xstream.alias("bedrijf", Bedrijven.class);
		xstream.alias("halte", Halte.class);
		xstream.alias("positie", Positie.class);

		String xml = xstream.toXML(bericht);
		Producer producer = new Producer();
		producer.sendBericht(xml);
	}
}
```

De bus is op dit moment verantwoordelijk voor zijn eigen informatie, zijn
positie en het versturen van een nieuw ETA bericht. Dit zijn dus meerdere
verantwoordelijkheden. Een betere manier zou zijn om deze drie
verantwoordelijkheden op te splitsen in meerdere classes.  
Dan zou je waarschijnlijk een erg simpele bus class krijgen die alleen zijn
lijnnummer en bedrijf weet. Daarnaast zou je een class hebben die de positie van
een bus kan bijhouden. Als laatste een class die de positie van de bus kan
versturen.

## Verbeter voorstellen

### Serialiseren en versturen

In de `bussimulator.Bus` class zit een methode genaamd `sendBericht`. Deze
methode neemt een bericht en verstuurt deze. Echter is het versturen niet het
enige wat de methode doet, de methode serialiseert het bericht ook naar XML.  
De methode ziet er op het moment als volgt uit:

```java
public void sendBericht(Bericht bericht) {
    XStream xstream = new XStream();
    xstream.alias("bus", Bus.class);
    xstream.alias("bericht", Bericht.class);
    xstream.alias("eta", ETA.class);
    xstream.alias("lijn", Lijnen.class);
    xstream.alias("bedrijf", Bedrijven.class);
    xstream.alias("halte", Halte.class);
    xstream.alias("positie", Positie.class);

    String xml = xstream.toXML(bericht);
    Producer producer = new Producer();
    producer.sendBericht(xml);
}
```

Een verbeterde versie van deze code zou zijn:

```java

public String serializeMessage(Bericht bericht) {
    XStream xstream = new XStream();
    xstream.alias("bus", Bus.class);
    xstream.alias("bericht", Bericht.class);
    xstream.alias("eta", ETA.class);
    xstream.alias("lijn", Lijnen.class);
    xstream.alias("bedrijf", Bedrijven.class);
    xstream.alias("halte", Halte.class);
    xstream.alias("positie", Positie.class);

    return xstream.toXML(bericht);
}

public void sendMessage(Bericht bericht) {
    String messageString = serializeMessage(bericht);
    Producer producer = new Producer();
    producer.sendBericht(messageString);
}
```

Hierbij heb je de verantwoordelijkheid voor het serialiseren in een andere
methode gewaarborgd.

### Command-query separation

In de `bussimulator.Runner` class heb je een methode genaamd `startBussen()`.
Deze methode voert zowel een actie uit als dat hij een waarde returnt. Dit is
een schending van het command-query principe.  
De methode ziet er als volgt uit:

```java
private static int startBussen(int tijd){
    for (Bus bus : busStart.get(tijd)){
        actieveBussen.add(bus);
    }
    busStart.remove(tijd);
    return (!busStart.isEmpty()) ? Collections.min(busStart.keySet()) : -1;
}
```
