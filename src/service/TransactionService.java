package service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import exception.TransactionNotFoundException;
import model.Category;
import model.Expense;
import model.Income;
import model.Transaction;
import repository.TransactionRepository;

public class TransactionService {
    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction addIncome(BigDecimal amount, String description, LocalDate date, Category category) {
        Income income = new Income(0, amount, description, date, category);
        return repository.save(income);
    }

    public Transaction addExpense(BigDecimal amount, String descripton, LocalDate date, Category category) {
        Expense expense = new Expense(0, amount, descripton, date, category);
        return repository.save(expense);
    }

    public List<Transaction> getAll() {
        return repository.findAll();
    }

    public Transaction getById(long id) {
        return repository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
    }

    public Transaction update(Transaction transaction) {
        getById(transaction.getId());
        repository.update(transaction);
        return transaction;
    }

    public void delete(long id) {
        getById(id);
        repository.deleteById(id);
    }
}
