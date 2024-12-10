package com.luxoft.bankapp.domain;

import java.util.LinkedList;
import java.util.List;

public class Queue {
    private final List<Email> emails;

    public Queue() {
        emails = new LinkedList<>();
    }

    public synchronized void add(Email email) {
        emails.add(email);
        notify();
    }

    public synchronized Email get() throws InterruptedException {
        while (emails.isEmpty()) {
            wait();
        }
        return emails.remove(0);
    }

    public synchronized boolean isEmpty() {
        return emails.isEmpty();
    }
}
