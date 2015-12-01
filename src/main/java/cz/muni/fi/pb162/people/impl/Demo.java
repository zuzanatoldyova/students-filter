package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.Filter;
import cz.muni.fi.pb162.people.FilterOrder;
import cz.muni.fi.pb162.people.FilterSource;
import cz.muni.fi.pb162.people.FilterType;
import cz.muni.fi.pb162.people.FilterValue;
import cz.muni.fi.pb162.people.PeopleStorage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Zuzana Toldyova <323119@mail.muni.cz>
 */
public class Demo {

    protected static final String STUDENTS_CSV_FILE = "files/students.csv";
    protected static final String PHD_CSV_FILE = "files/phd.csv";
    protected static final String STAFF_CSV_FILE = "files/staff.csv";

    /**
     * lmoidc
     *
     * @param args mod
     */
    public static void main(String[] args) {

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
        System.out.println(storage1.getPeople());
        people.clear();
        people.add(person1);
        storage1.storePeople(people);

        System.out.println(storage1.getPeople());
        PeopleStorage storage = createStorage();
        System.out.println(storage.getPeople().get((long) 94));
        System.out.println("");
        Filter filter = new Filter(
                FilterSource.SOURCE_STUDENTS,
                FilterOrder.NAME
        // new FilterValue(FilterType.SURNAME, )
        // new FilterValue(FilterType.SURNAME, "Mich")
        );

        Set<Person> people3 = storage.getByFilter(filter);

        for (Person p : people3) {
            System.out.println(p);
        }
        System.out.println("Storage size " + people3.size());
    }

    /**
     * jku
     *
     * @return koj
     */
    public static PeopleStorage createStorage() {
        PeopleStorage storage = new PeopleStorageImpl();
        storage.storePeople(STUDENTS_CSV_FILE, PersonRole.STUDENT);
        storage.storePeople(PHD_CSV_FILE, PersonRole.PHD);
        storage.storePeople(STAFF_CSV_FILE, PersonRole.STAFF);
        return storage;
    }
}
