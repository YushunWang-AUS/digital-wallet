package coding.test.controller;

import coding.test.service.UserService;
import coding.test.exception.FpException;
import coding.test.model.PaymentNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController(value = "webHook")
public class WebHookController {

    private final Logger log = LoggerFactory.getLogger(WebHookController.class);

    @Autowired
    UserService userService;

    @PostMapping("/payment/notification")
    public String paymentNotification(@Valid @RequestBody PaymentNotificationDTO paymentNotificationDTO) throws FpException {
//        System.out.println("paymentNotification start");
        log.info("paymentNotification start");
        userService.getWalletAccount(paymentNotificationDTO.getUserId());
        return "Greetings from Spring Boot!";
    }

}
