package coding.test.controller;

import coding.test.exception.FpException;
import coding.test.model.*;
import coding.test.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController(value = "webHook")
public class WebHookController {

    private final Logger log = LoggerFactory.getLogger(WebHookController.class);

    @Autowired
    UserService userService;

    @Transactional
    @PostMapping("/payment/notification")
    public FpResponseDTO paymentNotification(@Valid @RequestBody PaymentNotificationDTO paymentNotificationDTO) throws FpException {

        TransactionDTO transactionDTO = paymentNotificationDTO.getTransactions();
        log.info(transactionDTO.toString());
        WalletAccountDTO walletAccountDTO = userService.getWalletAccount(transactionDTO.getUserReference());


        return FpResponseResultDTO.success();
    }

}
