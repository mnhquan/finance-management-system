package repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Category;
import model.Income;
import model.Expense;

import model.Transaction;
import util.DatabaseConnection;

public class PostgreSQLTransactionRepository implements TransactionRepository {

    @Override
    public Transaction save(Transaction transaction) {
        String sql = "INSERT INTO transactions (type, amount, description, date, category)"
                + " VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, transaction.getType().name());
            ps.setBigDecimal(2, transaction.getAmount());
            ps.setString(3, transaction.getDescription());
            ps.setDate(4, java.sql.Date.valueOf(transaction.getDate()));
            ps.setString(5, transaction.getCategory().name());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                transaction.setId(rs.getLong("id"));
            }

            return transaction;

        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Transaction> findById(long id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }
            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException("Data error: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Transaction> findAll() {
        String sql = "SELECT * FROM transactions";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();) {

            ResultSet rs = stmt.executeQuery(sql);

            List<Transaction> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }

            return list;

        } catch (SQLException e) {
            throw new RuntimeException("Data error: " + e.getMessage(), e);
        }

    }

    @Override
    public boolean deleteById(long id) {
        String sql = "DELETE FROM transactions WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setLong(1, id);

            int result = ps.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Data error: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean update(Transaction transaction) {
        String sql = "UPDATE transactions"
                + " SET type=?, amount=?, description=?, date=?, category=?"
                + " WHERE id =?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);) {

            ps.setString(1, transaction.getType().name());
            ps.setBigDecimal(2, transaction.getAmount());
            ps.setString(3, transaction.getDescription());
            ps.setDate(4, java.sql.Date.valueOf(transaction.getDate()));
            ps.setString(5, transaction.getCategory().name());

            ps.setLong(6, transaction.getId());

            int result = ps.executeUpdate();

            return result > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Data error: " + e.getMessage(), e);
        }
    }

    private Transaction mapRow(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String type = rs.getString("type");
        BigDecimal amount = rs.getBigDecimal("amount");
        String description = rs.getString("description");
        LocalDate date = rs.getDate("date").toLocalDate();
        Category category = Category.valueOf(rs.getString("category"));

        if (type.equals("INCOME")) {
            return new Income(id, amount, description, date, category);
        } else {
            return new Expense(id, amount, description, date, category);
        }
    }
}
