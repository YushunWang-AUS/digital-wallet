package coding.test.model;

import coding.test.enums.AccountType;
import coding.test.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WalletCreditTransactionDTO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @NotBlank
    @JsonProperty(value = "id")
    private String transactionReference;

    @NotBlank
    @JsonProperty(value = "user_id")
    private String userReference;

    private Long walletAccountId;

    @NotNull
    @JsonProperty(value = "created_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @NotNull
    @JsonProperty(value = "updated_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    private String description;

    @JsonProperty(value = "type")
    private TransactionType transactionType;

    @NotEmpty
    @JsonProperty(value = "type_method")
    private String method;

    @NotNull
    @JsonProperty(value = "debit_credit")
    private AccountType accountType;

    private String currency;

    @NotNull
    @Min(value = 0)
    Long amount;  // Dollars, need to covert to cents when persistent

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getUserReference() {
        return userReference;
    }

    public void setUserReference(String userReference) {
        this.userReference = userReference;
    }

    public Long getWalletAccountId() {
        return walletAccountId;
    }

    public void setWalletAccountId(Long walletAccountId) {
        this.walletAccountId = walletAccountId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getAmount() {
        return amount;
    }

    @JsonAnySetter
    public void setTransactionType(String transactionType) {
        this.transactionType = TransactionType.valueOf(transactionType.toUpperCase());
    }

    @JsonAnySetter
    public void setAccountType(String accountType) {
        this.accountType = AccountType.valueOf(accountType.toUpperCase());
    }

    @JsonAnySetter
    public void setAmount(BigDecimal amount) {
        this.amount = amount.multiply(new BigDecimal(100)).longValue();
    }

    @Override
    public String toString() {
        return "WalletCreditTransactionDTO{" +
                "transactionReference='" + transactionReference + '\'' +
                ", userReference='" + userReference + '\'' +
                ", walletAccountId=" + walletAccountId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", description='" + description + '\'' +
                ", transactionType=" + transactionType +
                ", method='" + method + '\'' +
                ", accountType=" + accountType +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }
}
