package coding.test.model;

import coding.test.enums.PaymentType;
import coding.test.enums.TransactionType;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    @NotNull
    @JsonProperty(value = "id")
    private String transactionReference;

    @NotNull
    @JsonProperty(value = "user_id")
    private String userReference;

    @NotNull
    @JsonProperty(value = "created_at")
    private LocalDateTime createdAt;

    @NotNull
    @JsonProperty(value = "updated_at")
    private LocalDateTime updatedAt;

    private String description;

    @NotNull
    @JsonProperty(value = "type")
    private PaymentType paymentType;

    @JsonProperty(value = "type_method")
    private String method;

    @NotNull
    @JsonProperty(value = "debit_credit")
    private TransactionType transactionType;

    private String currency;

    @NotNull
    @Min(value = 0)
    BigDecimal amount;  // Dollars, need to covert to cents when persistent

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

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @JsonAnySetter
    public void setPaymentType(String type) {
        this.paymentType = PaymentType.valueOf(type.toUpperCase());
    }

    @JsonAnySetter
    public void setTransactionType(String transactionType) {
        this.transactionType = TransactionType.valueOf(transactionType.toUpperCase());
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
                "transactionReference='" + transactionReference + '\'' +
                ", userReference='" + userReference + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", description='" + description + '\'' +
                ", paymentType=" + paymentType +
                ", method='" + method + '\'' +
                ", transactionType=" + transactionType +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                '}';
    }
}
