package coding.test.controller;

import coding.test.exception.FpException;
import coding.test.model.*;
import coding.test.service.PaymentService;
import coding.test.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("payment")
public class PaymentController {

    private final Logger log = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;

//    @Transactional
    @PostMapping("/notification")
    public FpResponseDTO paymentNotification(@Valid @RequestBody PaymentNotificationDTO paymentNotificationDTO) {

        TransactionDTO transactionDTO = paymentNotificationDTO.getTransactions();

        // Get Wallet Account. If it doesn't exist, it will create a new one here.
        WalletAccountDTO walletAccountDTO = userService.getWalletAccount(transactionDTO.getUser_id());

        // create walletCreditTransactionDTO.
        WalletCreditTransactionDTO walletCreditTransactionDTO = new WalletCreditTransactionDTO(transactionDTO, walletAccountDTO.getId());

        // Check Transaction Reference (TransactionId in API Request) Duplicated
        String transactionReference = walletCreditTransactionDTO.getTransactionReference();
        Boolean isDuplicatedTransaction = paymentService.isDuplicatedTransaction(transactionReference);
        if (isDuplicatedTransaction) {
            throw new FpException(String.format("Transaction [%s] already exists.", transactionReference));
        }

        // Create Transaction data in database
        paymentService.createTransaction(walletCreditTransactionDTO);

        return FpResponseResultDTO.success();
    }

}
