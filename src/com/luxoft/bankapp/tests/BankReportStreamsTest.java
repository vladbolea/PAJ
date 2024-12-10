package com.luxoft.bankapp.tests;

import static org.junit.Assert.assertEquals;

import com.luxoft.bankapp.domain.*;
import org.junit.Test;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.BankService;

import java.util.*;

public class BankReportStreamsTest {

    private Bank createTestBank() {
        Bank bank = new Bank();
        BankService bankService = new BankService();

        Client client1 = new Client("Alice", Gender.FEMALE, "New York");
        Client client2 = new Client("Bob", Gender.MALE, "Los Angeles");
        Client client3 = new Client("Charlie", Gender.MALE, "New York");

        client1.addAccount(new SavingAccount(1, 1000));
        client1.addAccount(new CheckingAccount(2, 1000, 500));

        client2.addAccount(new SavingAccount(3, 2000));

        client3.addAccount(new SavingAccount(4, 1500));
        client3.addAccount(new CheckingAccount(5, 1000, 300));

        try {
            BankService.addClient(bank, client1);
            BankService.addClient(bank, client2);
            BankService.addClient(bank, client3);
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }

        return bank;
    }

    @Test
    public void testGetTotalSumInAccounts() {
        Bank bank = createTestBank();
        BankReportStreams report = new BankReportStreams();
        assertEquals(6500.0, report.getTotalSumInAccounts(bank), 0.01);
    }

    @Test
    public void testGetNumberOfClients() {
        Bank bank = createTestBank();
        BankReportStreams report = new BankReportStreams();
        assertEquals(3, report.getNumberOfClients(bank));
    }

    @Test
    public void testGetNumberOfAccounts() {
        Bank bank = createTestBank();
        BankReportStreams report = new BankReportStreams();
        assertEquals(5, report.getNumberOfAccounts(bank));
    }

    @Test
    public void testGetClientsSorted() {
        Bank bank = createTestBank();
        BankReportStreams report = new BankReportStreams();
        SortedSet<Client> sortedClients = report.getClientsSorted(bank);
        List<String> names = sortedClients.stream().map(Client::getName).toList();
        assertEquals(Arrays.asList("Alice", "Bob", "Charlie"), names);
    }



    @Test
    public void testGetAccountsSortedBySum() {
        Bank bank = createTestBank();
        BankReportStreams report = new BankReportStreams();
        SortedSet<Account> sortedAccounts = report.getAccountsSortedBySum(bank);
        List<Double> balances = sortedAccounts.stream()
                .map(Account::getBalance)
                .toList();

        assertEquals(Arrays.asList(1000.0, 1500.0, 2000.0), balances);
    }


    @Test
    public void testGetBankCreditSum() {
        Bank bank = createTestBank();
        BankReportStreams report = new BankReportStreams();
        assertEquals(800.0, report.getBankCreditSum(bank), 0.01);
    }

    @Test
    public void testGetCustomerAccounts() {
        Bank bank = createTestBank();
        BankReportStreams report = new BankReportStreams();
        Map<Client, Collection<Account>> customerAccounts = report.getCustomerAccounts(bank);

        assertEquals(2, customerAccounts.get(new Client("Alice", Gender.FEMALE, "New York")).size());
        assertEquals(1, customerAccounts.get(new Client("Bob", Gender.MALE, "Los Angeles")).size());
    }

    @Test
    public void testGetClientsByCity() {
        Bank bank = createTestBank();
        BankReportStreams report = new BankReportStreams();
        Map<String, List<Client>> clientsByCity = report.getClientsByCity(bank);

        assertEquals(2, clientsByCity.get("New York").size());
        assertEquals(1, clientsByCity.get("Los Angeles").size());
        assertEquals(Arrays.asList("Los Angeles", "New York"), new ArrayList<>(clientsByCity.keySet()));
    }
}
