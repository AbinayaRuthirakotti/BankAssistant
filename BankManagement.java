package com.jts.bank;

import java.util.*;

public class BankManagement {
    public static void main(String[] args) {
        BankService bankService = new BankService();
        List<Customer> customers = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true) {
            // Display menu
            System.out.println("***************************************");
            System.out.println("\n***** Welcome to Banking System *****");
            System.out.println();
            System.out.println("***************************************");
            System.out.println("0. Create new customer account.");
            System.out.println("1. Display all the accounts.");
            System.out.println("2. Search customer by account no.");
            System.out.println("3. Deposit amount.");
            System.out.println("4. Withdraw amount.");
            System.out.println("5. Exit.");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();

            switch (option) {
                case 0: // Create a new customer account
                    Customer newCustomer = bankService.createCustomer();
                    customers.add(newCustomer);
                    System.out.println("Customer created successfully!");
                    break;
                case 1: // Display all customer accounts
                    bankService.displayAccounts(customers);
                    break;
                case 2: // Search for a customer by account number
                    System.out.print("Enter account no: ");
                    String acctNo = sc.next();
                    Customer customer = bankService.searchCustomer(customers, acctNo);
                    if (customer != null) {
                        bankService.displayAccount(customer);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 3: // Deposit amount into account
                    System.out.print("Enter account no: ");
                    acctNo = sc.next();
                    customer = bankService.searchCustomer(customers, acctNo);
                    if (customer != null) {
                        System.out.print("Enter deposit amount: ");
                        long amount = sc.nextLong();
                        bankService.deposit(customer, amount);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 4: // Withdraw amount from account
                    System.out.print("Enter account no: ");
                    acctNo = sc.next();
                    customer = bankService.searchCustomer(customers, acctNo);
                    if (customer != null) {
                        System.out.print("Enter withdrawal amount: ");
                        long amount = sc.nextLong();
                        bankService.withdraw(customer, amount);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;
                case 5: // Exit the banking system
                    System.out.println("Exiting system.");
                    sc.close(); // Close the scanner
                    return; // Exit the program
                default: // Handle invalid menu choice
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
