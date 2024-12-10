package com.luxoft.bankapp.domain;

import java.util.*;

public class Client implements Comparable<Client> {

    private String name;
    private Gender gender;
    private Set<Account> accounts = new HashSet<>();
    private String city;

    public Client(String name, Gender gender, String city) {
        this.name = name;
        this.gender = gender;
        this.city = city;
    }

    public void addAccount(final Account account) {
        accounts.add(account);
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    public String getClientGreeting() {
        if (gender != null) {
            return gender.getGreeting() + " " + name;
        } else {
            return name;
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return getClientGreeting();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Client client = (Client) object;
        return Objects.equals(name, client.name) &&
                gender == client.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender);
    }

    public int compareTo(Client other) {
        return this.name.compareTo(other.name);
    }
}
