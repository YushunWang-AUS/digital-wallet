package coding.test.model;

import javax.validation.Valid;

public class PaymentNotificationDTO {

    @Valid private TransactionDTO transactions;

    public TransactionDTO getTransactions() {
        return transactions;
    }

    public void setTransactions(TransactionDTO transactions) {
        this.transactions = transactions;
    }
}
