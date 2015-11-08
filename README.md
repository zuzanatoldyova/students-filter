Zadání úkolu č. 2, Karotéka lidí
====================================

**Publication date:** 8. week of the semester 

**Submission deadline:** 12. week of the semester

Obecné informace
-------------------
Cílem tohoto úkolu je implementovat program, který bude schopen načíst lidi ze souborů do kolekce, dále jejich filtrování a řazení podle zadaných parametrů.

Níže najdete popis jednotlivých kroků, které potřebujete k úspěšné implementaci úkolu. Podrobnější informace získáte prostudováním kódu a přečtením JavaDocu. Maximální bodový zisk je **90** bodů.

- **60 bodů** za projití testů.
- **20 bodů** za správnou implementaci. Tu posoudí cvičící.
- **10 bodů** za čístou a elegantní implementaci (minimální duplikace kódu, dodržování konvencí).

Pokud přiložené testy neprojdou, bodový zisk posoudí cvičící individuálně.

### Struktura projektu

1. Balíček ```cz.muni.fi.pb162.people``` Obsahuje třídy, interface a výčtové typy (enum), které jsou součástí zadání.
  - **Nepřidávejte ani jakkoli nemodifikujte soubory v tomto balíčku.**
2. Package  ```cz.muni.fi.pb162.people.impl``` bude obsahovat Vaši implementaci.
  - **Cokoli mimo tento balíček bude ignorováno.**

### Konkrétní informace k zadání

Po otevření projektu nebuďte překvapeni, že nejde zkompilovat. Přiložené testy počítají s existencí tříd v balíčku ```impl```. Nenechte se zaskočit počtem tříd a výčtových typů, které jsou v balíčku ```people```, po chvíli se zorientujete. Celé zadání si několikrát přečtěte, až do konce.

Postup implementace

Krok 1: 
---------------------------
V první části máte za úkol získat data ze souborů. Konkrétně pouze správně rozparsovat načtená data. Nejdříve ze všeho doporučuji vytvořit všeny třídy, tak aby projekt šel zkompilovat. Do balíčku ```impl``` přidejte:

- třídu ```Person```
- třídu ```CSVReader``` rošiřující ```AbstractFileReader```
- třídu ```PeopleStorageImpl``` implementující ```PeopleStorage```
- enum ```EPersonRole```

### 1.2 - EPersonRole

Výčtový typ bude obsahovat 3 hodnoty: ```STUDENT, STAFF, PHD```.
Označuje jakou roli daný člověk zastává. Kartotéka obsahuje všechny lidi a je portřeba explicitně rozlišit jaké role může člověk vykonávat. Jeden člověk může mít více rolí naráz (např učitel a student).

### 1.2 - Person

Třída bude obsahovat 5 atributů. Čtyři z nich jednoduše vyčtete ze struktury přiložených souborů, nicméně jsou to: ```name, surname, login, uco```. Pouze ```uco``` bude typu ```Long```, zbývající atributy jsou řetězcové. Pátým atributem bude množina (```java.util.Set```) typu ```EPersonRole``` pojmenovaná ```roles```. Tento atribut znamená, že člověk může mít více rolí. Implementujte gettery a settery. Dva objekty typu ```Person``` se budou rovnat, pokud budou mít stejné ```uco```.

### 1.3 - CSVReader

Rodičovská třída obsahuje metodu pro načtení zadaného souboru a jeho čtení po řádcích. Každý řádek je potřeba rozparsovat a vytvořit z dat instanci třídy ```Person```. To má za úkol metoda ```readLine```, kterou musíte přepsat. Než začnete s implementací, dobře si prostudujte strukturu přiložených souborů (**staff.csv, students.csv, phd.csv**).

Metoda ```readLine``` bude také validovat jestli je načtený řádek v pořádku. Očekává se od Vás následující validace:

- každý řetězcový prvek (name, surname, login) musí obsahovat alespoň jedno písmeno.
- uco musí být složeno pouze z číslic od 0-9 a musí tam být minimálně 1 číslice.
- pokud není načtený řádek validní, metoda vrací null.

Hint: Při parsování načteného řádku můžete s úspěchem využít třídu ```java.util.regex.Pattern```.


Krok 2: 
------------------------------
V druhé části máte za úkol načtená data uložit do kartotéky a implementovat jejich filtrování. Základem celé kartotéky je atribut typu ```java.util.Map```. Mapa bude obsahovat lidi identifikované podle jejich uča (klíč - hodnota). Viz [dokumentace](http://docs.oracle.com/javase/7/docs/api/java/util/Map.html).

O načtení dat do mapy se starají dvě metody ```storePeople```. Jejich popis naleznete v rozhraní třídy.

Filtrování je nejdůležitější část tohoto úkolu. Dobře si projděte vzorový příklad použití filtru ve třídě ```PeopleApp```. Logickou úvahou dojdete k tomu, jak máte filtrování implementovat, nehledejte v tom zbytečné komplikace. Zde popíšu věci na které si dát pozor. 

- filtr vždy obsahuje **jeden** zdroj dat. To znamená, že určuje jakou roli člověk má. Viz ```EFilterSource```
- filtr obsahuje **0-1** typ řazení filtrovaných lidí. Tzn jakým způsobem má být seřazený výsledek filtrování.
- filtr obsahuje **0-N** typů podle kterých se mají data filtrovat. Každý typ se sestává z **právě jednoho** identifikátoru a **1-N** hodnot. Opět viz ukázka v ```PeopleApp```.
- mezi jednotlivými typy filtru je logická operace **AND**. Například chcete-li filtrovat podle jména a loginu. Výsledná kolekce lidí musí splňovat obě kritéria. Tedy pokud do filtru zadáte jedno učo a spoustu jmen, výsledkem bude maximálně jeden člověk. Pokud se jméno člověka s daným učem nebude shodovat ani s jedním jménem ve filtru, pak výsledkem bude prázdná množina.
- řetězcové hodnoty (jméno, příjmení, login) se filtrují i podle částečné shody. Podobně jak funguje operátor ```LIKE``` v SQL. Například typ filtru:

```java
new FilterType(
    EFilterType.NAME, 
    "Mich"
);
```
by měl vrátit jak **Mich**aelu, tak **Mich**ala.

Krok 3: 
-------------------------------------
Nyní už máte data načtená i v nich umíte hledat podle zadaného filtru. V kroku 2 bylo pouze zmíněno, že filtr může obsahovat parametr ```EFilterOrder```. Nyní si blíže popíšeme implementaci řazení (sorting).

Začněte nativním řezaním, to znamená, že třída ```Person``` bude implementovat rozhraní ```Comparable```. Řazení bude na základě uča.

Ve druhé části přidejte do balíčku ```impl``` třídu ```PersonComparator```. Jde o standartní komparátor implementující rozhraní ```java.util.Comparator```. Třída bude mít jediný konstruktor:
```java
public PersonComparator(EFilterOrder order) {
    this.order = order;
}
```

Pokud filter bude obsahovat řazení, použije se komparátor s příslušným typem řazení, jinak bude výsledek řazen defaultně podle uča.