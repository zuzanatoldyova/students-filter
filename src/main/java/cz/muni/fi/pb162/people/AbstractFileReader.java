package cz.muni.fi.pb162.people;

import cz.muni.fi.pb162.people.impl.Person;
import cz.muni.fi.pb162.people.impl.EPersonRole;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tomasskopal.
 */
public abstract class AbstractFileReader {

    /**
     * Read given file, parse it into Person objects. These objects are returned as a set.
     *
     * @param pathToFile - path to file
     * @param type - specifies role of loaded people (data in file do not contains it)
     * @return set of successfully loaded people
     */
    public Set<Person> readFile(String pathToFile, EPersonRole type) {

        String line;
        Set<Person> result = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {

            while ((line = br.readLine()) != null) {

                Person p = readLine(line, type);
                result.add(p);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Parse line.
     *
     * @param line - contains data about person. See for example phd.csv for data structure
     * @param type - person role
     * @return instance of Person class filled with values from input parameters or null if line has wrong format.
     *         Format is described in readme.
     */
    protected abstract Person readLine(String line, EPersonRole type);
}
