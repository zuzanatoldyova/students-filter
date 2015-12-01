package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.AbstractPersonReader;
import static java.lang.Long.parseLong;

/**
 *
 * @author Zuzana Toldyova <323119@mail.muni.cz>
 */
public class CSVReader extends AbstractPersonReader {

    @Override
    protected Person parseLine(String line, PersonRole type) {
        Person newPerson = new Person();
        String[] split = line.split("[,:]");
        if (split.length != 4) {
            return null;
        }
        for (String s : split) {
            if (s.trim().isEmpty()) {
                return null;
            }
        }

        newPerson.setSurname(split[0]);
        newPerson.setName(split[1].trim());
        newPerson.setLogin(split[2]);

        try {
            newPerson.setUco(parseLong(split[3]));
        } catch (NumberFormatException ex) {
            return null;
        }

        newPerson.addRole(type);

        return newPerson;

    }

}
