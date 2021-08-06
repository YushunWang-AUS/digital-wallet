package coding.test.controller;

import coding.test.model.PaymentNotificationDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @GetMapping("/get/")
    public String paymentNotification(@Valid @RequestBody PaymentNotificationDTO paymentNotificationDTO) {
        return "Greetings from Spring Boot!";
    }

}
