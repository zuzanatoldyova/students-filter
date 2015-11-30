package cz.muni.fi.pb162.people.impl;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author tomasskopal.
 */
public class CSVReaderTest extends StorageTestBase {

    @Test
    public void readLine() {
        CSVReader r = new CSVReader();
        Person person = r.parseLine("Skopal, Tomáš:xskopal2:374549", PersonRole.STUDENT);

        assertNotNull(person);
        assertEquals("Tomáš", person.getName());
        assertEquals("Skopal", person.getSurname());
        assertEquals("xskopal2", person.getLogin());
        assertEquals(Long.valueOf(374549), Long.valueOf(person.getUco()));
        assertEquals(1, person.getRoles().size());
        assertTrue(person.getRoles().contains(PersonRole.STUDENT));
    }

    @Test
    public void readFiles() {
        CSVReader r = new CSVReader();
        int numberOfAllPersons = r.readFile("files/all.csv", PersonRole.STUDENT).size();

        assertEquals(numberOfAllPersons, storage.getPeople().size());
    }

    @Test
    public void storePeople() {
        CSVReader r = new CSVReader();
        PeopleStorageImpl storage1 = new PeopleStorageImpl();

        storage1.storePeople("files/students.csv", PersonRole.STUDENT);
        int numberOfAllPersons = r.readFile("files/students.csv", PersonRole.STUDENT).size();

        assertEquals(numberOfAllPersons, storage1.getPeople().size());
    }

    @Test
    public void storePeopleWithRoles() {
        Person person = new Person();
        person.setName("Jan");
        person.setSurname("Novak");
        person.setUco(1111L);
        person.setLogin("login");
        person.setRoles(new HashSet<>(Arrays.asList(PersonRole.PHD)));

        Person person1 = new Person();
        person1.setName("Jan");
        person1.setSurname("Novak");
        person1.setUco(1111L);
        person1.setLogin("login");
        person1.setRoles(new HashSet<>(Arrays.asList(PersonRole.STAFF)));

        Set<Person> people = new HashSet<>(Arrays.asList(person));

        PeopleStorageImpl storage1 = new PeopleStorageImpl();
        storage1.storePeople(people);

        people.clear();
        people.add(person1);
        storage1.storePeople(people);

        assertEquals(1, storage1.getPeople().size());
        assertTrue(
                storage1.getPeople()
                .get(1111L)
                .getRoles()
                .containsAll(
                        Arrays.asList(PersonRole.PHD, PersonRole.STAFF)
                )
        );
    }
}
