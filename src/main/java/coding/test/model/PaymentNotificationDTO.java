package coding.test.model;

import lombok.ToString;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
