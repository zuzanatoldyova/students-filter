package cz.muni.fi.pb162.people.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Zuzana Toldyova <323119@mail.muni.cz>
 */
public class Demo {
    
    public static void main(String[] args){
        
        
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
    }
}
