package coding.test.model;

import javax.validation.Valid;

public class PaymentNotificationDTO {

    @Valid private WalletCreditTransactionDTO transactions;

    public WalletCreditTransactionDTO getTransactions() {
        return transactions;
    }

    public void setTransactions(WalletCreditTransactionDTO transactions) {
        this.transactions = transactions;
    }
}
