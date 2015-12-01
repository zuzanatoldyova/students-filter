package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.Filter;
import cz.muni.fi.pb162.people.FilterType;
import cz.muni.fi.pb162.people.FilterValue;
import cz.muni.fi.pb162.people.PeopleStorage;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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
        storePeople(people);
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
        if (filter.getFilterOrder() != null) {
            peopleSet = new TreeSet<>(new PersonComparator(filter.getFilterOrder()));
        } else {
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

        String parameter = "none";
        for (FilterValue v : filter.getFilterValues()) {
            FilterType type = v.getType();
            Iterator<Person> it = peopleSet.iterator();
            while (it.hasNext()) {
                Person person = it.next();
                switch (type) {
                    case UCO:
                        parameter = Long.toString(person.getUco());
                        break;
                    case NAME:
                        parameter = person.getName();
                        break;
                    case SURNAME:
                        parameter = person.getSurname();
                        break;
                    case LOGIN:
                        parameter = person.getLogin();
                        break;
                    default:
                        break;
                }
                int i = 0;
                for (String t : v.getValues()) {
                    if (parameter.contains(t)) {
                        break;
                    }
                    i++;
                    if (i == v.getValues().size()) {
                        it.remove();
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
