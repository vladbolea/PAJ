package com.luxoft.bankapp.domain;

import com.luxoft.bankapp.domain.Bank;

import java.util.*;
import java.util.stream.Collectors;

public class BankReportStreams {

    public int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    public int getNumberOfAccounts(Bank bank) {
        return bank.getClients().stream()
                .mapToInt(client -> client.getAccounts().size())
                .sum();
    }

    public SortedSet<Client> getClientsSorted(Bank bank) {
        return bank.getClients().stream()
                .sorted(Comparator.comparing(Client::getName))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public double getTotalSumInAccounts(Bank bank) {
        return bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public SortedSet<Account> getAccountsSortedBySum(Bank bank) {
        return bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .sorted(Comparator.comparingDouble(Account::getBalance))
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public double getBankCreditSum(Bank bank) {
        return bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .filter(account -> account instanceof CheckingAccount)
                .mapToDouble(account -> Math.max(0, ((CheckingAccount) account).getOverdraft()))
                .sum();
    }

    public Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {
        return bank.getClients().stream()
                .collect(Collectors.toMap(client -> client, Client::getAccounts));
    }

    public Map<String, List<Client>> getClientsByCity(Bank bank) {
        return bank.getClients().stream()
                .collect(Collectors.groupingBy(Client::getCity, TreeMap::new, Collectors.toList()));
    }
}
