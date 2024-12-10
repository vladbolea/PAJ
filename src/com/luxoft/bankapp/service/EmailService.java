package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Email;
import com.luxoft.bankapp.domain.Queue;

public class EmailService {
    private final Queue emailQueue = new Queue();
    private volatile boolean running = true;

    public EmailService() {
        new Thread(() -> {
            while (running || !emailQueue.isEmpty()) {
                try {
                    Email email = emailQueue.get();
                    sendEmail(email);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Email service has stopped.");
        }).start();
    }

    public void sendNotificationEmail(Email email) {
        if (!running) {
            throw new IllegalStateException("EmailService is closed. Cannot send emails.");
        }
        emailQueue.add(email);
    }

    private void sendEmail(Email email) {
        System.out.println("Sending email to: " + email.getTo());
        System.out.println("Email content: " + email);
    }

    public void close() {
        running = false;
        synchronized (emailQueue) {
            emailQueue.notifyAll();
        }
    }
}
