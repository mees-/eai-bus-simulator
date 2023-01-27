# Verslag Software quality enterprise application integration bus simulator

| Auteur           | studentnummer |
| ---------------- | ------------- |
| Mees van Dijk    | 393219        |
| Hylbren Rijnders | 372649        |

## Meaningful names comments, formatting

Over naamgeving wordt het volgende beschreven: Namen eten de intentie van iets
benoemen. Bijvoorbeeld bij een variabele die een bepaalde tijdunit vasthoud:

Door de hele applicatie heen zijn de functienamen wel goed benoemd.

Er zijn bijna geen comments behalve als iets voor java FX wordt gebruikt.

## functions

Als het gaat om functions dan schrijft Robert Martin in zijn boek dat functies
kort moeten zijn. Het liefst wil je de functie ver opsplitsen in andere
functies. Op die manier kun je functies bouwen uit andere functies en blijft je
code leesbaar.  
Een ander criterium van functies is dat ze één ding doen, en dat ding goed doen.
Dit heeft er mee te maken dat functies, net zoals objecten, een duidelijke,
enkele verantwoordelijkheid moeten hebben.  
Als laatste is het belangrijk dat een functie een duidelijk abstractieniveau
heeft. Een functie moet niet tegelijk ruwe data parsen en dan deze data in een
scherm weergeven. Het parsen is een ander abstractieniveau dan het weergeven.

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

## Error handling

## Boundaries (spaghetti code)

## Classes

```

```
