package coding.test.model;

public class PaymentNotificationDTO {

    private WalletCreditTransactionDTO transactions;

    public WalletCreditTransactionDTO getTransactions() {
        return transactions;
    }

    public void setTransactions(WalletCreditTransactionDTO transactions) {
        this.transactions = transactions;
    }
}
