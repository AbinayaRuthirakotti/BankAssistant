package com.jts.bank;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class BankService {
    private static final String TRANSACTION_CSV_PATH = "transaction.csv"; // Relative path for better portability
    private final Scanner sc = new Scanner(System.in);

    // Method to create a new customer account
    public Customer createCustomer() {
        Customer customer = new Customer();

        System.out.print("Enter account no: ");
        customer.setAcctNo(sc.next());

        System.out.print("Enter account type: ");
        customer.setAcctType(sc.next());

        System.out.print("Enter name: ");
        sc.nextLine(); // Consume the leftover newline
        customer.setName(sc.nextLine()); // Use nextLine to allow spaces in name

        System.out.print("Enter balance: ");
        customer.setBalance(sc.nextLong());

        return customer;
    }

    // Method to display all customer accounts
    public void displayAccounts(List<Customer> customers) {
        for (Customer customer : customers) {
            displayAccount(customer);
            logTransaction(customer, "Display", 0); // Log the "Display" action
        }
    }

    // Method to display a single customer's account
    public void displayAccount(Customer customer) {
        System.out.println("Name: " + customer.getName());
        System.out.println("Account No: " + customer.getAcctNo());
        System.out.println("Account Type: " + customer.getAcctType());
        System.out.println("Balance: " + customer.getBalance());
    }

    // Method to search a customer by account number
    public Customer searchCustomer(List<Customer> customers, String acctNo) {
        return customers.stream().filter(c -> c.getAcctNo().equals(acctNo)).findFirst().orElse(null);
    }

    // Method to deposit an amount to the customer's account
    public void deposit(Customer customer, long amount) {
        customer.setBalance(customer.getBalance() + amount);
        logTransaction(customer, "Deposit", amount);
    }

    // Method to withdraw an amount from the customer's account
    public void withdraw(Customer customer, long amount) {
        if (customer.getBalance() < amount) {
            System.out.println("Insufficient balance. Transaction failed.");
        } else {
            customer.setBalance(customer.getBalance() - amount);
            System.out.println("Balance after withdrawal: " + customer.getBalance());
            logTransaction(customer, "Withdraw", amount);
        }
    }

    // Method to log the transaction details into the CSV file
    private void logTransaction(Customer customer, String type, long amount) {
        try {
            // Create or append to the transaction CSV file
            File file = new File(TRANSACTION_CSV_PATH);
            boolean newFile = !file.exists(); // Check if the file already exists

            // Use BufferedWriter to write data
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                // Write the header if it's a new file or empty
                if (newFile || file.length() == 0) {
                    writer.write("AccountNo,TransactionType,Amount,Balance,Timestamp\n");
                }

                // Format the current timestamp for the transaction
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                // Write the transaction record to the file
                writer.write(customer.getAcctNo() + "," + type + "," + amount + "," + customer.getBalance() + "," + timestamp + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error logging transaction: " + e.getMessage());
        }
    }
}
