package coding.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

@Validated
public class UserDTO {

    @Id
    Long id;

    @NotBlank
    @Max(50)
    String firstName;

    @NotBlank
    @Max(50)
    String lastName;

    @Email
    String email;

}
