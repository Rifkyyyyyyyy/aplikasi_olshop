package  domain.model.balance;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

public final class BalanceModel {

    private final String userId;
    private final BigDecimal balance;
    private final Timestamp lastUpdated;
    private final Timestamp createdAt;

    // Constructor
    public BalanceModel( String userId, BigDecimal balance, Timestamp lastUpdated, Timestamp createdAt) {

        this.userId = userId;
        this.balance = balance;
        this.lastUpdated = lastUpdated;
        this.createdAt = createdAt;
    }


 
    public String getUserId() {
        return userId;
    }


    public BigDecimal getBalance() {
        return balance;
    }

   
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }


    public Timestamp getCreatedAt() {
        return createdAt;
    }

 


    @Override
    public int hashCode() {
        return Objects.hash( userId, balance, lastUpdated, createdAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceModel that = (BalanceModel) o;
        return 
               Objects.equals(userId, that.userId) &&
               Objects.equals(balance, that.balance) &&
               Objects.equals(lastUpdated, that.lastUpdated) &&
               Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public String toString() {
        return "BalanceModel{" +
               ", userId='" + userId + '\'' +
               ", balance=" + balance +
               ", lastUpdated=" + lastUpdated +
               ", createdAt=" + createdAt +
               '}';
    }
}
