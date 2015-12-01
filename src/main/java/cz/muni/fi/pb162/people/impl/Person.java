package cz.muni.fi.pb162.people.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Zuzana Toldyova <323119@mail.muni.cz>
 */
public class Person implements Comparable<Person> {

    private String name;
    private String surname;
    private String login;
    private long uco;
    private Set<PersonRole> roles = new HashSet();

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public long getUco() {
        return uco;
    }

    public Set<PersonRole> getRoles() {
        return roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setUco(long uco) {
        this.uco = uco;
    }

    public void setRoles(Set<PersonRole> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Person) {
            Person p = (Person) o;
            if (uco == p.uco) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (int) (this.uco ^ (this.uco >>> 32));
        return hash;
    }

    /**
     * Adds a role to the person.
     *
     * @param role role of the person
     */
    public void addRole(PersonRole role) {
        roles.add(role);
    }

    /**
     * Adds multiple roles to the person.
     *
     * @param roles roles of the person
     */
    public void addRoles(Collection<PersonRole> roles) {
        this.roles.addAll(roles);
    }

    @Override
    public String toString() {
        return "Person name: " + name + ", surname: " + surname + ", login: " + login
                + ", uco: " + uco + ", roles: " + roles;
    }

    @Override
    public int compareTo(Person t) {
        return (int) (this.uco - t.getUco());
    }

}
