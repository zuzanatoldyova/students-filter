package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.Filter;
import cz.muni.fi.pb162.people.FilterType;
import cz.muni.fi.pb162.people.FilterValue;
import cz.muni.fi.pb162.people.PeopleStorage;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Zuzana Toldyova <323119@mail.muni.cz>
 */
public class PeopleStorageImpl implements PeopleStorage {

    private final Map<Long, Person> storage = new HashMap();

    @Override
    public void storePeople(String file, PersonRole role) {
        CSVReader reader = new CSVReader();
        Set<Person> people = reader.readFile(file, role);
        for (Person person : people) {
            storage.put(person.getUco(), person);
        }

    }

    @Override
    public void storePeople(Collection<Person> people) {
        for (Person person : people) {
            if (storage.containsKey(person.getUco())) {
                Person updatePerson = storage.get(person.getUco());
                updatePerson.addRoles(person.getRoles());
                storage.put(person.getUco(), updatePerson);
            } else {
                storage.put(person.getUco(), person);
            }
        }
    }

    @Override
    public Set<Person> getByFilter(Filter filter) {
        Set<Person> peopleSet;
        switch (filter.getFilterOrder()) {
            case UCO:
                peopleSet = new TreeSet<>(new UcoComparator());
                break;
            case NAME:
                peopleSet = new TreeSet<>(new NameComparator());
                break;
            case SURNAME:
                peopleSet = new TreeSet<>(new SurnameComparator());
                break;
            case SURNAME_AND_NAME:
                peopleSet = new TreeSet<>(new SurnameNameComparator());
                break;
            default:
                peopleSet = new TreeSet<>();
        }

        Set<PersonRole> roles = new HashSet();
        switch (filter.getFilterSource()) {
            case SOURCE_STAFF:
                roles.add(PersonRole.STAFF);
                break;
            case SOURCE_STUDENTS:
                roles.add(PersonRole.STUDENT);
                break;
            case SOURCE_PHD:
                roles.add(PersonRole.PHD);
                break;
            case SOURCE_STUDENTS_AND_STAFF:
                roles.add(PersonRole.STUDENT);
                roles.add(PersonRole.STAFF);
                break;
            default:
                roles.add(PersonRole.PHD);
                roles.add(PersonRole.STAFF);
                roles.add(PersonRole.STUDENT);
                break;

        }
        for (Person person : storage.values()) {
            for (PersonRole role : roles) {
                if (person.getRoles().contains(role)) {
                    peopleSet.add(person);
                    break;
                }
            }

        }
        for (Person person : peopleSet) {
            for (FilterValue v : filter.getFilterValues()) {
                FilterType type = v.getType();
                for (String s : v.getValues()) {
                    switch (type) {
                        case UCO:
                            if (!Long.toString(person.getUco()).contains(s)) {
                                peopleSet.remove(person);
                            }
                            break;
                        case NAME:
                            if (!person.getName().contains(s)) {
                                peopleSet.remove(person);
                            }
                            break;
                        case SURNAME:
                            if (!person.getSurname().contains(s)) {
                                peopleSet.remove(person);
                            }
                            break;
                        case LOGIN:
                            if (!person.getLogin().contains(s)) {
                                peopleSet.remove(person);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }

        }
        return peopleSet;
    }

    @Override
    public Map<Long, Person> getPeople() {
        return storage;
    }

}
