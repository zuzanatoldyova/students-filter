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
        Person person = r.readLine("Skopal, Tomáš:xskopal2:374549", EPersonRole.STUDENT);

        assertNotNull(person);
        assertEquals(person.getName(), "Tomáš");
        assertEquals(person.getSurname(), "Skopal");
        assertEquals(person.getLogin(), "xskopal2");
        assertEquals(person.getUco(), Long.valueOf(374549));
        assertEquals(person.getRoles().size(), 1);
        assertTrue(person.getRoles().contains(EPersonRole.STUDENT));
    }

    @Test
    public void readFiles() {
        CSVReader r = new CSVReader();
        int numberOfAllPersons = r.readFile("files/all.csv", EPersonRole.STUDENT).size();

        assertEquals(storage.getPeople().size(), numberOfAllPersons);
    }

    @Test
    public void storePeople() {
        CSVReader r = new CSVReader();
        PeopleStorageImpl storage1 = new PeopleStorageImpl();

        storage1.storePeople("files/students.csv", EPersonRole.STUDENT);
        int numberOfAllPersons = r.readFile("files/students.csv", EPersonRole.STUDENT).size();

        assertEquals(storage1.getPeople().size(), numberOfAllPersons);
    }

    @Test
    public void storePeopleWithRoles() {
        Person person = new Person();
        person.setName("Jan");
        person.setSurname("Novak");
        person.setUco(1111L);
        person.setLogin("login");
        person.setRoles(new HashSet<>(Arrays.asList(EPersonRole.PHD)));

        Person person1 = new Person();
        person1.setName("Jan");
        person1.setSurname("Novak");
        person1.setUco(1111L);
        person1.setLogin("login");
        person1.setRoles(new HashSet<>(Arrays.asList(EPersonRole.STAFF)));

        Set<Person> people = new HashSet<>(Arrays.asList(person));

        PeopleStorageImpl storage1 = new PeopleStorageImpl();
        storage1.storePeople(people);

        people.clear();
        people.add(person1);
        storage1.storePeople(people);

        assertEquals(storage1.getPeople().size(), 1);
        assertTrue(
                storage1.getPeople()
                .get(1111L)
                .getRoles()
                .containsAll(
                        Arrays.asList(EPersonRole.PHD, EPersonRole.STAFF)
                )
        );
    }
}
