package util;

import model.Category;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputReader {
    private final Scanner scanner = new Scanner(System.in);

    public String readString() {
        return scanner.nextLine();
    }

    public int readInt() {
        while (true) {
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again");
            }
        }
    }

    public BigDecimal readBigDecimal() {
        while (true) {
            String input = scanner.nextLine();

            try {
                BigDecimal value = new BigDecimal(input);

                if (value.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Amount must be greater than 0");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please try again");
            }
        }
    }

    public LocalDate readDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (true) {
            String input = scanner.nextLine();

            try {
                return LocalDate.parse(input.trim(), formatter);
            } catch (DateTimeException e) {
                System.out.println("Invalid date. Please use dd/MM/yyyy");
            }
        }
    }

    public Category readIncomeCategory() {
        System.out.println("""
                Select income category:
                1. Salary
                2. Freelance
                3. Investment
                4. Other Income
                """);
        while (true) {
            int choice = readInt();
            switch (choice) {
                case 1:
                    return Category.SALARY;
                case 2:
                    return Category.FREELANCE;
                case 3:
                    return Category.INVESTMENT;
                case 4:
                    return Category.OTHER_INCOME;
                default:
                    System.out.println("Please choose from 1 to 4.");
            }
        }
    }

    public Category readExpenseCategory() {
        System.out.println("""
                Select expense category:
                1. Food
                2. Transport
                3. Housing
                4. Entertainment
                5. Healthcare
                6. Education
                7. Shopping
                8. Other Expense
                """);
        while (true) {
            int choice = readInt();
            switch (choice) {
                case 1:
                    return Category.FOOD;
                case 2:
                    return Category.TRANSPORT;
                case 3:
                    return Category.HOUSING;
                case 4:
                    return Category.ENTERTAINMENT;
                case 5:
                    return Category.HEALTHCARE;
                case 6:
                    return Category.EDUCATION;
                case 7:
                    return Category.SHOPPING;
                case 8:
                    return Category.OTHER_EXPENSE;
                default:
                    System.out.println("Please choose from 1 to 8.");
            }
        }
    }
}
