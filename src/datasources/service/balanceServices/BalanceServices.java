package datasources.service.balanceServices;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;

import domain.model.balance.BalanceModel;

public class BalanceServices {
    private final Connection connection;

    public BalanceServices(Connection connection) {
        if (connection == null) {
            throw new IllegalArgumentException("Connection cannot be null");
        }
        this.connection = connection;
    }

    public CompletableFuture<Boolean> updateBalance(BalanceModel data) {
        return CompletableFuture.supplyAsync(() -> {
            if (connection == null) {
                throw new IllegalStateException("Connection is not available");
            }
    
            String sql = "UPDATE users_balance SET balance = balance + ?, last_updated = ? WHERE user_id = ?";
    
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setBigDecimal(1, data.getBalance()); 
                Timestamp lastUpdated = data.getLastUpdated() != null ? data.getLastUpdated() : new Timestamp(System.currentTimeMillis());
                stmt.setTimestamp(2, lastUpdated);
                stmt.setString(3, data.getUserId()); 
    
                int affectedRows = stmt.executeUpdate();
                return affectedRows > 0; 
    
            } catch (SQLException e) {
                return false; 
            }
        });
    }
    

    public CompletableFuture<BalanceModel> getBalanceByUserId(String id) {
        return CompletableFuture.supplyAsync(() -> {
            if (connection == null) {
                throw new IllegalStateException("Connection is not available");
            }
    
            String sql = "SELECT user_id, balance, last_updated, created_at FROM users_balance WHERE user_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String userId = rs.getString("user_id");
                        BigDecimal balance = rs.getBigDecimal("balance");
                        Timestamp lastUpdated = rs.getTimestamp("last_updated");
                        Timestamp createdAt = rs.getTimestamp("created_at");
    
                        return new BalanceModel(userId, balance, lastUpdated, createdAt);
                    } else {
                      
                        return insertDummyData(id);
                    }
                }
            } catch (SQLException e) {
                return null;
            }
        });
    }
    
    private BalanceModel insertDummyData(String id) {
        String insertSql = "INSERT INTO users_balance (user_id, balance, last_updated, created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
            BigDecimal initialBalance = BigDecimal.ZERO;
            Timestamp now = new Timestamp(System.currentTimeMillis());
    
            insertStmt.setString(1, id);
            insertStmt.setBigDecimal(2, initialBalance);
            insertStmt.setTimestamp(3, now);
            insertStmt.setTimestamp(4, now);
            insertStmt.executeUpdate();

            return new BalanceModel(id, initialBalance, now, now);
        } catch (SQLException e) {
           
            return null;
        }
    }
    
    public CompletableFuture<Boolean> withdraw(String userId, BigDecimal amount) {
        return CompletableFuture.supplyAsync(() -> {
            if (connection == null) {
                throw new IllegalStateException("Connection is not available");
            }
    
            BalanceModel currentBalance = getBalanceByUserId(userId).join();
            if (currentBalance == null) {
                return false;
            }
    
            BigDecimal balance = currentBalance.getBalance();
    
            if (balance.compareTo(amount) < 0) {
                return false;
            }
    
            BigDecimal newBalance = balance.subtract(amount);
            String sql = "UPDATE users_balance SET balance = ?, last_updated = ? WHERE user_id = ?";
    
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                Timestamp lastUpdated = new Timestamp(System.currentTimeMillis());
                stmt.setBigDecimal(1, newBalance);
                stmt.setTimestamp(2, lastUpdated);
                stmt.setString(3, userId);
    
                int affectedRows = stmt.executeUpdate();
                return affectedRows > 0;
    
            } catch (SQLException e) {
                return false;
            }
        });
    }
    
}
