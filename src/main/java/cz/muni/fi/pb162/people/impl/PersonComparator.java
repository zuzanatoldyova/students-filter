package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.FilterOrder;
import java.util.Comparator;

/**
 *
 * @author Zuzana Toldyova <323119@mail.muni.cz>
 */
public class PersonComparator implements Comparator<Person> {

    private final FilterOrder order;

    /**
     * Creates new PersonComparator.
     *
     * @param order order in which people should be sorted.
     */
    public PersonComparator(FilterOrder order) {
        this.order = order;
    }

    @Override
    public int compare(Person t, Person t1) {
        switch (this.order) {
            case NAME:
                if (!t.getName().equals(t1.getName())) {
                    return t.getName().compareTo(t1.getName());
                }
                break;
            case SURNAME:
                if (!t.getSurname().equals(t1.getSurname())) {
                    return t.getSurname().compareTo(t1.getSurname());
                }
                break;
            case SURNAME_AND_NAME:
                if (!t.getSurname().equals(t1.getSurname())) {
                    return t.getSurname().compareTo(t1.getSurname());
                } else if (!t.getName().equals(t1.getName())) {
                    return t.getName().compareTo(t1.getName());
                }
                break;
            default:
                return t.compareTo(t1);
        }
        return t.compareTo(t1);
    }

}
