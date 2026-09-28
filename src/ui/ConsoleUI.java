package ui;

import util.InputReader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import exception.TransactionNotFoundException;
import model.Category;
import model.Transaction;
import model.TransactionType;
import service.TransactionService;

public class ConsoleUI {
    private final InputReader inputReader = new InputReader();
    private TransactionService service;

    public ConsoleUI(TransactionService service) {
        this.service = service;
    }

    public void start() {
        boolean running = true;

        while (running) {
            showMenu();
            switch (readMenuChoice()) {
                case 1:
                    addIncome();
                    break;
                case 2:
                    addExpense();
                    break;
                case 3:
                    viewTransaction();
                    break;
                case 4:
                    updateTransaction();
                    break;
                case 5:
                    deleteTransaction();
                    break;
                case 6:
                    searchTransaction();
                    break;
                case 7:
                    showStatistic();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try it again");
            }
        }
        System.out.println("Thanks for using our Personal Finance Management System");
    }

    private void showMenu() {
        Menu.displayMenu();
    }

    private int readMenuChoice() {
        while (true) {
            System.out.println("Choose an option: ");
            int choice = inputReader.readInt();

            if (choice >= 1 && choice <= 8) {
                return choice;
            }

            System.out.println("Please choose an option from 1 to 8");
        }
    }

    private void addIncome() {
        System.out.println("====== ADD INCOME ======\n");
        System.out.println("Enter amount: ");
        BigDecimal amount = inputReader.readBigDecimal();
        System.out.println("Enter description: ");
        String description = inputReader.readString();
        System.out.println("Enter date (dd/MM/yyyy): ");
        LocalDate date = inputReader.readDate();
        System.out.println("Enter category: ");
        Category category = inputReader.readIncomeCategory();

        Transaction saved = service.addIncome(amount, description, date, category);
        System.out.println("Added successfully! ID = " + saved.getId());
    }

    private void addExpense() {
        System.out.println("====== ADD EXPENSE ======\n");
        System.out.println("Enter amount: ");
        BigDecimal amount = inputReader.readBigDecimal();
        System.out.println("Enter description: ");
        String description = inputReader.readString();
        System.out.println("Enter date (dd/MM/yyyy): ");
        LocalDate date = inputReader.readDate();
        System.out.println("Enter category: ");
        Category category = inputReader.readExpenseCategory();

        Transaction saved = service.addExpense(amount, description, date, category);
        System.out.println("Added successfully! ID = " + saved.getId());
    }

    private void viewTransaction() {
        System.out.println("====== VIEW TRANSACTIONS ======\n");
        List<Transaction> transactions = service.getAll();

        if (transactions.isEmpty()) {
            System.out.println("Empty transactions");
            return;
        }

        System.out.printf("%-5s | %-8s | %-12s | %-15s | %-12s | %s%n",
                "ID", "Type", "Amount", "Category", "Date", "Description");
        System.out.println("-----------------------------------------------------------------------");
        for (Transaction t : transactions) {
            System.out.printf("%-5d | %-8s | %-12.2f | %-15s | %-12s | %s%n",
                    t.getId(),
                    t.getType(),
                    t.getAmount(),
                    t.getCategory(),
                    t.getDate(),
                    t.getDescription());
        }
        System.out.println();
    }

    private void updateTransaction() {
        System.out.println("====== UPDATE TRANSACTION ======\n");
        System.out.println("Enter ID: ");
        long inputId = inputReader.readLong();

        Transaction transaction;
        try {
            transaction = service.getById(inputId);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Enter amount: ");
        BigDecimal amount = inputReader.readBigDecimal();
        System.out.println("Enter description: ");
        String description = inputReader.readString();
        System.out.println("Enter date (dd/MM/yyyy): ");
        LocalDate date = inputReader.readDate();
        System.out.println("Enter category: ");
        Category category;
        if (transaction.getType() == TransactionType.INCOME) {
            category = inputReader.readIncomeCategory();
        } else {
            category = inputReader.readExpenseCategory();
        }

        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setDate(date);
        transaction.setCategory(category);

        service.update(transaction);
        System.out.println("Updated successfully!");
    }

    private void deleteTransaction() {
        System.out.println("====== DELETE TRANSACTION ======\n");
        System.out.println("Enter ID: ");
        long inputId = inputReader.readLong();

        System.out.println("Are you sure?");
        System.out.println("1. Yes - 2. No");
        String decision = inputReader.readString();

        if (decision.equals("1")) {
            try {
                service.delete(inputId);
            } catch (TransactionNotFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
        } else {
            return;
        }

        System.out.println("Deleted successfully");
    }

    private void searchTransaction() {
        System.out.println("====== SEARCH TRANSACTION ======\n");
        System.out.println("Enter ID: ");
        long inputId = inputReader.readInt();

        Transaction transaction;
        try {
            transaction = service.getById(inputId);
        } catch (TransactionNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.printf("%-5d | %-8s | %-12.2f | %-15s | %-12s | %s%n",
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getDate(),
                transaction.getDescription());

    }

    private void showStatistic() {
        System.out.println("====== SHOW STATISTICS ======\n");
        System.out.println("1. Total income\n"
                + "2. Total expense\n"
                + "3. Balance\n");

        switch (readStatisticChoice()) {
            case 1:
                System.out.println("Your total income: " + service.getTotalIncome());
                break;
            case 2:
                System.out.println("Your total expense: " + service.getTotalExpense());
                break;
            case 3:
                System.out.println("Your balance: " + service.getBalance());
                break;
            default:
                System.out.println("Invalid option. Please try again");
                break;
        }
    }

    private int readStatisticChoice() {
        while (true) {
            System.out.println("Choose an option:");
            int choice = inputReader.readInt();
            if (choice >= 1 && choice <= 3) {
                return choice;
            }
            System.out.println("Invalid choice. Please try again");
        }
    }
}
