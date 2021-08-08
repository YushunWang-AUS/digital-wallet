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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @NotBlank
    private String id;

    @NotBlank
    private String user_id;

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

    private TransactionType type;

    @NotBlank
    private String type_method;

    private AccountType debit_credit;

    private String currency;

    @NotNull
    @Min(value = 0)
    Long amount;  // Convert to Cents in set method

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getType_method() {
        return type_method;
    }

    public void setType_method(String type_method) {
        this.type_method = type_method;
    }

    public AccountType getDebit_credit() {
        return debit_credit;
    }

    public void setDebit_credit(AccountType debit_credit) {
        this.debit_credit = debit_credit;
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
    public void setType(String transactionType) {
        this.type = TransactionType.valueOf(transactionType.toUpperCase());
    }

    @JsonAnySetter
    public void setDebit_credit(String accountType) {
        this.debit_credit = AccountType.valueOf(accountType.toUpperCase());
    }

    @JsonAnySetter
    public void setAmount(BigDecimal amount) {
        this.amount = amount.multiply(new BigDecimal(100)).longValue();
    }
}
