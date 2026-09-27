package repository;

import java.util.List;
import java.util.Optional;

import model.Transaction;

public interface TransactionRepository {
    Transaction save(Transaction transaction);

    Optional<Transaction> findById(long id);

    List<Transaction> findAll();

    boolean update(Transaction transaction);

    boolean deleteById(long id);
}
