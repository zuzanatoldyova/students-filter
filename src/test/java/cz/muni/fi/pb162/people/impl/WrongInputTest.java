package cz.muni.fi.pb162.people.impl;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * @author tomasskopal.
 */
public class WrongInputTest extends StorageTestBase {

    @Test(expected = Exception.class)
    public void nullableFilter() {
        storage.getByFilter(null);
    }

    @Test(expected = Exception.class)
    public void nullableDataInput() {
        storage.storePeople(null);
    }


    @Test
    public void readLineWithWrongAttributes() {
        CSVReader r = new CSVReader();

        // surname
        Person person = r.readLine(" , Tomáš:xskopal2:374549", EPersonRole.STUDENT);
        assertNull(person);

        // name
        person = r.readLine("Skopal, :xskopal2:374549", EPersonRole.STUDENT);
        assertNull(person);

        // name - two spaces
        person = r.readLine("Skopal,  :xskopal2:374549", EPersonRole.STUDENT);
        assertNull(person);

        // login
        person = r.readLine("Skopal,  Tomáš: :374549", EPersonRole.STUDENT);
        assertNull(person);

        // uco
        person = r.readLine("Skopal,  Tomáš:xskopal2:", EPersonRole.STUDENT);
        assertNull(person);

        // uco - contains letter
        person = r.readLine("Skopal,  Tomáš:xskopal2:374549p", EPersonRole.STUDENT);
        assertNull(person);
    }
}
