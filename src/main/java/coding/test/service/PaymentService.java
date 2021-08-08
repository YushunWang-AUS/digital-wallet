package coding.test.service;

import coding.test.controller.dao.WalletCreditTransactionDAO;
import coding.test.exception.FpException;
import coding.test.model.WalletCreditTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private WalletCreditTransactionDAO walletCreditTransactionDAO;

    public Boolean isDuplicatedTransaction(String transactionReference) throws FpException {
        Long transactionId = walletCreditTransactionDAO.findByTransactionReference(transactionReference);
        if(transactionId != null) {
            return true;
        }
        return false;
    }

    public Long createTransaction(WalletCreditTransactionDTO walletCreditTransactionDTO) {
        return walletCreditTransactionDAO.create(walletCreditTransactionDTO);
    }

}
