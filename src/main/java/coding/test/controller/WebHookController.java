package coding.test.controller;

import coding.test.model.PaymentNotificationDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class WebHookController {

    @PostMapping("/payment/notification")
    public String paymentNotification(@Valid @RequestBody PaymentNotificationDTO paymentNotificationDTO) {
        return "Greetings from Spring Boot!";
    }

}
