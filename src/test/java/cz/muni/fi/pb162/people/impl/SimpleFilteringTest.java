package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author tomasskopal.
 */
public class SimpleFilteringTest extends StorageTestBase {

    private static final List<Person> peopleForTest = new ArrayList<>();
    private static Set<Person> all;
    private static Set<Person> phd;

    @BeforeClass
    public static void setUpBefore() {
        CSVReader reader = new CSVReader();
        all = reader.readFile("files/all.csv", EPersonRole.STUDENT);
        phd = reader.readFile("files/phd.csv", EPersonRole.PHD);

        int i = 0;
        for (Person p : all) {
            if (i % 100 == 0) {
                peopleForTest.add(p);
            }
            i++;
        }
    }

    /**
     * Empty filter means no filtering so all people should be returned.
     */
    @Test
    public void emptyFilter() {
        Filter filter = new Filter();
        Set<Person> people = storage.getByFilter(filter);
        assertEquals("All people should be returned by empty filter.", people.size(), all.size());
    }

    @Test
    public void filterByUco() {
        Person person = peopleForTest.get(0);
        FilterType filterType = new FilterType(EFilterType.UCO, person.getUco().toString());
        Filter filter = new Filter(filterType);
        Set<Person> people = storage.getByFilter(filter);

        assertEquals("Filter by one UCO must returns just one person.", people.size(), 1);
        assertTrue("Result contains specified person", people.contains(person));
    }

    @Test
    public void filterByName() {
        Person person = peopleForTest.get(0);
        FilterType filterType = new FilterType(EFilterType.NAME, person.getName());
        Filter filter = new Filter(filterType);
        Set<Person> people = storage.getByFilter(filter);

        assertTrue("Result contains specified person", people.contains(person));
        for (Person p : people) {
            assertEquals(p.getName(), person.getName());
        }

        int countFromAll = 0;
        for (Person p : all) {
            if (p.getName().equals(person.getName())) {
                countFromAll++;
            }
        }
        assertEquals(countFromAll, people.size());
    }

    @Test
    public void filterBySurname() {
        Person person = peopleForTest.get(0);
        FilterType filterType = new FilterType(EFilterType.SURNAME, person.getSurname());
        Filter filter = new Filter(filterType);
        Set<Person> people = storage.getByFilter(filter);

        assertTrue("Result contains specified person", people.contains(person));
        for (Person p : people) {
            assertEquals(p.getSurname(), person.getSurname());
        }

        int countFromAll = 0;
        for (Person p : all) {
            if (p.getSurname().equals(person.getSurname())) {
                countFromAll++;
            }
        }
        assertEquals(countFromAll, people.size());
    }

    @Test
    public void filterByLogin() {
        Person person = peopleForTest.get(0);
        FilterType filterType = new FilterType(EFilterType.LOGIN, person.getLogin());
        Filter filter = new Filter(filterType);
        Set<Person> people = storage.getByFilter(filter);

        assertTrue("Result contains specified person", people.contains(person));
        for (Person p : people) {
            assertEquals(p.getLogin(), person.getLogin());
        }

        int countFromAll = 0;
        for (Person p : all) {
            if (p.getLogin().equals(person.getLogin())) {
                countFromAll++;
            }
        }
        assertEquals(countFromAll, people.size());
    }

    @Test
    public void noOne() {
        Person person = peopleForTest.get(0);
        Person person1 = peopleForTest.get(1);
        Filter filter = new Filter(
                EFilterSource.SOURCE_ALL,
                new FilterType(EFilterType.UCO, person.getUco().toString()),
                new FilterType(EFilterType.SURNAME, person1.getSurname())
        );
        Set<Person> people = storage.getByFilter(filter);

        assertEquals(people.size(), 0);
    }

    @Test
    public void fullFilter() {
        Person person = peopleForTest.get(0);
        Filter filter = new Filter(
                EFilterSource.SOURCE_ALL,
                new FilterType(EFilterType.UCO, person.getUco().toString()),
                new FilterType(EFilterType.NAME, person.getName()),
                new FilterType(EFilterType.SURNAME, person.getSurname())
        );
        Set<Person> people = storage.getByFilter(filter);

        assertTrue(people.contains(person));
        assertEquals(people.size(), 1);
    }

    @Test
    public void likeOperation() {
        Person person = peopleForTest.get(0);
        Filter filter = new Filter(
                EFilterSource.SOURCE_ALL,
                new FilterType(EFilterType.NAME, person.getName().substring(0, 2))
        );
        System.out.println("Using person name for filtering. Name: " + person.getName().substring(0, 2));
        Set<Person> people = storage.getByFilter(filter);

        assertTrue(people.size() > 0);
        assertTrue(people.contains(person));
    }

    @Test
    public void onlyPhd() {
        Person person = phd.iterator().next();
        FilterType type = new FilterType(EFilterType.UCO, person.getUco().toString());

        // ----------- PHD - non empty --------------
        Filter filter = new Filter(
                EFilterSource.SOURCE_PHD,
                type
        );
        Set<Person> people = storage.getByFilter(filter);

        assertTrue(people.contains(person));
        assertEquals(people.size(), 1);

        // There is no test for student and staff because every phd is student and almost every is staff.
    }

}
