package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Expense extends Transaction {
    public Expense(long id, BigDecimal amount, String description, LocalDate date, Category category) {
        super(id, amount, description, date, category);
    }

    @Override
    public TransactionType getType() {
        return TransactionType.EXPENSE;
    }
}
