# Mestint beadandó - Szállítási probléma A* keresővel Java nyelven

## A* kereső algoritmus

Az alkalmazás az **A*** kereső algoritmust alkalmazza, amelyet gyakran használnak útvonaltervezési problémák megoldására gráfokban vagy rácsokon. Az algoritmus célja egy kezdőponttól egy célpontig vezető optimális út megtalálása, figyelembe véve az **útköltségeket** és egy **heurisztikus** becslést a maradék költségekre.

### Működése

1. Kezdőpont és célpont kiválasztása: Meg kell határozni a kezdőpontot, ahol elkezdődik a keresés, valamint a célpontot, amelyre el kell jutni.
2. Nyitott és zárt halmazok inicializálása: Az A* algoritmus követi a nyitott-zárt halmaz (open-closed set) módszerét. A nyitott halmaz tartalmazza azokat a csomópontokat, amelyeket még feldolgozni kell, míg a zárt halmaz a már feldolgozott csomópontokat tartalmazza.
3. Kezdőpont hozzáadása a nyitott halmazhoz: A kezdőpontot hozzáadjuk a nyitott halmazhoz, és inicializáljuk a kezdeti költségeket és a heurisztikus becslést (általában a célponttól való távolságot) az adott pontig.
4. Ciklus, amíg a nyitott halmaz üres nem lesz vagy megtaláljuk a célpontot:
    - Az aktuális csomópont kiválasztása a nyitott halmazból, amelynek a legkisebb költsége és heurisztikus becslése van.
    - Ha az aktuális csomópont a célpont, akkor találtunk egy optimális utat, és a keresést befejezzük.
    - Ellenkező esetben feldolgozzuk az aktuális csomópontot:
        - Átmozgatjuk az aktuális csomópontot a zárt halmazba.
        - Megvizsgáljuk az aktuális csomópont összes szomszédját:
            - Ha egy szomszédos csomópont még nem szerepel a nyitott vagy zárt halmazban, hozzáadjuk a nyitott halmazhoz, és kiszámítjuk a költségeket és heurisztikus becslést.
            - Ha egy szomszédos csomópont már a nyitott vagy zárt halmazban van, akkor frissítjük a költségeket, ha a jelenlegi út olcsóbb.
5. Ha a nyitott halmaz üres lesz, és nem találtunk célpontot, akkor nincs lehetséges út a kezdőpontról a célpontig.

Az A* algoritmus nagyon hatékony, ha a heurisztikus becslés jó közelítést ad a valódi költségekre, és a gráf vagy rács mérete nem túl nagy.

## Az alkalmazásban felhasznált útvonalak

![Képernyőkép 2023-05-18 143934.png](Mestint%20beadando%CC%81%20-%20Sza%CC%81lli%CC%81ta%CC%81si%20proble%CC%81ma%20A%20kere%208bc36f169fdc40cdb2464ff0401e2078/Kpernykp_2023-05-18_143934.png)

A számokkal mérhető adatok nincsenek kiírva, ugyanis az alkalmazás számolja ki azokat az útvonalak helyzetéből adódóan. A helyszínek elhelyezkedését a hosszúsági és szélességi értékek adják meg. Az indítás után automatikusan létrehozásra kerülnek az adatok.

**Település létrehozásáról példa:**

```java
private final Settlement settlementA = new Settlement("A", 47.4979, 19.0402);
// További települések
```

**Szomszédsági beállítások:**

```java
public void setNeighbours() {
				settlementA.addNeighbour(settlementB);
        settlementA.addNeighbour(settlementC);
        settlementA.addNeighbour(settlementG);
				// További szomszédok
}
```

## A projekt leírása

A fejlesztés során **OpenJDK 17**-es verziót használtam. Fejlesztői környezet az IntelliJ IDEA.

### Input

A **Main.java** osztályban történik a kód indítását követően egy **while** ciklus segítségével az adatok bekérése. Azért szükséges a ciklus, ugyanis a program addig nem áll le, amig az ****exit**** kulcsszót nem kapja meg inputként. A beérkező adatot elküldi az **InputHandler** osztálynak, amely elvégzi a megfelelő intézkedéseket az adattal.

```java
public class InputHandler {

    public String inputReader() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public char[] handleInput(String input) {
        String[] letters = input.split(" ");
        char[] lettersChar = new char[2];

        if (letters.length == 2) {
            lettersChar[0] = letters[0].toCharArray()[0];
            lettersChar[1] = letters[1].toCharArray()[0];
            return lettersChar;
        } else {
            System.out.println("Nem megfelelő az input!");
            return null;
        }
    }
}
```

Amennyiben nem a megfelelő input érkezik, akkor lehetőség adódik az újabb megadására.

### Start és Cél

Ezt követően megkeresem az induló és a célállomásokat egy ún. **SettlementSearcher** osztály segítségével.

```java
List<Settlement> settlements = settlementSearcher.settlementList(letters[0], letters[1]);
```

Itt meghívásra kerül a **ProgramDatabase** osztály eleme, a már fentebb említett szomszédságot beállító metódus. Miután megtörtént a beállítás, egy listába eltárolom a két állomást.

```java
public class SettlementSearcher {
    public List<Settlement> settlementList(char start, char destination) {
        ProgramDatabase programDatabase = new ProgramDatabase();
        programDatabase.setNeighbours();

        List<Settlement> startAndDestination = new ArrayList<>();

        startAndDestination.add(programDatabase.findSettlementByName(String.valueOf(start).toUpperCase()));
        startAndDestination.add(programDatabase.findSettlementByName(String.valueOf(destination).toUpperCase()));

        return startAndDestination;
    }
}
```

### A* kereső

A következő lépés már az útvonalkeresés.

```java
List<Settlement> route = aStar(settlements.get(0), settlements.get(1));
```

Az **aStar** függvényben történik a keresés végrehajtása.

```java
public static List<Settlement> aStar(Settlement start, Settlement destination) {
        PriorityQueue<Route> openList = new PriorityQueue<>((a, b) -> Double.compare(a.getCost() + a.getRoute().get(a.getRoute().size() - 1).getHeuristics(),
                b.getCost() + b.getRoute().get(b.getRoute().size() - 1).getHeuristics()));
        Set<Settlement> locked = new HashSet<>();

        Route startRoute = new Route(Collections.singletonList(start), 0);
        openList.add(startRoute);

        while (!openList.isEmpty()) {
            Route actualRoute = openList.poll();
            Settlement actualSettlement = actualRoute.getRoute().get(actualRoute.getRoute().size() - 1);

            if (actualSettlement.equals(destination)) {
                return actualRoute.getRoute();
            }

            if (locked.contains(actualSettlement)) {
                continue;
            }

            locked.add(actualSettlement);

            for (Settlement neighbour : actualSettlement.getNeighbours()) {
                if (locked.contains(neighbour)) {
                    continue;
                }

                double cost = actualRoute.getCost() + distance(actualSettlement, neighbour);
                List<Settlement> ut = new ArrayList<>(actualRoute.getRoute());
                ut.add(neighbour);
                double heuristics = distance(neighbour, destination);
                Route newRoute = new Route(ut, cost);
                newRoute.getRoute().get(newRoute.getRoute().size() - 1).setHeuristics(heuristics);

                openList.add(newRoute);
            }
        }

        return null; // Ha nem található útvonal
    }
```

Létrehozok egy listát, amely a nyitott elemeket tartalmazza. Ez egy olyan lista, amely prioritás szerint működik. Itt egy számítás történik, amely segítségével a legnagyobb prioritást élvező elem fog bekerülni a listába.

Emellett létrehozok egy halmazt, amely a zárt, tehát azokat az elemeket tartalmazza, amikre már ráléptünk.

Hozzáadom a kezdőállomást a nyitott listához. Ebből a listából mindig kiszedésre kerül az aktuális útvonal. Amennyiben a célállomással megegyezik az aktuális útvonal akkor visszaadásra kerül az útvonal. Ha a zártak között van az aktuális útvonal akkor a ciklus elejére lépünk, különben hozzáadjuk a zártakhoz.

Ezt követően megnézzük, hogy a szomszédok közül szerepel-e már valamelyik a zártak között, mert ha igen, akkor tovább lépés történik. Viszont amikor nem szerepel, akkor történik a szomszédos település távolságának a kiszámítása. Ezt a **distance** függvény végzi el.

```java
public static double distance(Settlement settlement1, Settlement settlement2) {

        // Euklideszi távolság használata
        double latDiff = settlement1.getLat() - settlement2.getLat();
        double lonDiff = settlement1.getLon() - settlement2.getLon();
        return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
    }
```

Az Euklideszi távolságot alkalmazom, mely segítségével megkapjuk a kereső algoritmus sikeres működéséhez szükséges adatot.

Végül pedig az adott útvonal heurisztikája beállításra kerül.

Miután mindenen végig ment a program, de nem találja az útvonalat, akkor ****null**** értékkel tér vissza.

### Settlement és Route

Mind a kettő egy-egy külön osztályban van létrehozva, amelyek **Gettereket**, **Settereket** tartalmaznak a változókhoz.

**Settlement**

```java
@Getter
@Setter
@NoArgsConstructor
public class Settlement {
    private String name;
    private double lat;
    private double lon;
    private double heuristics;
    private List<Settlement> neighbours;

    public Settlement(String name, double lat, double lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.neighbours = new ArrayList<>();
    }

    public void addNeighbour(Settlement neighbour) {
        neighbours.add(neighbour);
    }
}
```

**Route**

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private List<Settlement> route;
    private double cost;
}
```