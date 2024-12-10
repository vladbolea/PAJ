package com.luxoft.bankapp.domain;

public class Email {
    private final String from;
    private final String to;
    private final String subject;
    private final String content;
    private final Client client;

    public Email(Client client, String from, String to, String subject, String content) {
        this.client = client;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return "Email{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", client=" + client.getName() +
                '}';
    }
}
