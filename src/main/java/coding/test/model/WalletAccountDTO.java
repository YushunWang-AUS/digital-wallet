package coding.test.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Component
public class WalletAccountDTO {
    @NotNull
    Long id;

    @NotNull
    Long userId;

    @NotNull
    @CreatedDate
    LocalDateTime dateCreated;

    @NotNull
    Boolean enabled;
}
