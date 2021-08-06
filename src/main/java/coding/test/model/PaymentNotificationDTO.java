package coding.test.model;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
public class PaymentNotificationDTO {

    @NotNull
    Long userId;

    @NotNull
    @Min(value = 0)
    Long amount;  // Cents

}
