package cz.muni.fi.pb162.people.impl;

import java.util.Comparator;

/**
 *
 * @author Zuzana Toldyova <323119@mail.muni.cz>
 */
public class UcoComparator implements Comparator<Person>{

    @Override
    public int compare(Person t, Person t1) {
        return (int) (t.getUco() - t1.getUco());
    }
}
