package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Transaction;

public class InMemoryTransactionRepository implements TransactionRepository {
    private List<Transaction> transactions = new ArrayList<>();
    private long nextId = 1;

    @Override
    public Transaction save(Transaction transaction) {
        transaction.setId(nextId);
        nextId++;
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public Optional<Transaction> findById(long id) {
        return transactions.stream().filter(t -> t.getId() == id).findFirst();
    }

    @Override
    public List<Transaction> findAll() {
        return new ArrayList<>(transactions);
    }

    @Override
    public boolean update(Transaction transaction) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == transaction.getId()) {
                transactions.set(i, transaction);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(long id) {
        return transactions.removeIf(t -> t.getId() == id);
    }
}
