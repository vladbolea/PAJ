package com.luxoft.bankapp.reports;

import com.luxoft.bankapp.domain.Bank;
import com.luxoft.bankapp.domain.Client;
import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.CheckingAccount;

import java.util.*;
import java.util.stream.Collectors;

public class BankReport {

    public int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    public int getNumberOfAccounts(Bank bank) {
        return bank.getClients().stream()
                .mapToInt(client -> client.getAccounts().size())
                .sum();
    }

    public List<Client> getClientsSorted(Bank bank) {
        return bank.getClients().stream()
                .sorted(Comparator.comparing(Client::getName))
                .collect(Collectors.toList());
    }

    public double getTotalSumInAccounts(Bank bank) {
        return bank.getClients().stream()
                .flatMap(client -> client.getAccounts().stream())
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public SortedSet<Account> getAccountsSortedBySum(Bank bank) {
        SortedSet<Account> sortedAccounts = new TreeSet<>(Comparator.comparingDouble(Account::getBalance));
        bank.getClients().forEach(client -> sortedAccounts.addAll(client.getAccounts()));
        return sortedAccounts;
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
