package coding.test.model;

import coding.test.enums.AccountType;
import coding.test.enums.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class WalletCreditTransactionDTO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public WalletCreditTransactionDTO(TransactionDTO transactionDTO, Long walletAccountId) {
        this.walletAccountId = walletAccountId;
        this.transactionReference = transactionDTO.getId();
        this.userReference = transactionDTO.getUser_id();
        this.updatedAt = transactionDTO.getUpdatedAt();
        this.createdAt = transactionDTO.getCreatedAt();
        this.description = transactionDTO.getDescription();
        this.method = transactionDTO.getType_method();
        this.transactionType = transactionDTO.getType();
        this.accountType = transactionDTO.getDebit_credit();
        this.currency = transactionDTO.getCurrency();
        this.amount = transactionDTO.getAmount();
    }

    @NotBlank
    private String transactionReference;

    @NotBlank
    private String userReference;

    private Long walletAccountId;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    private String description;

    private TransactionType transactionType;

    @NotEmpty
    private String method;

    @NotNull
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
}
