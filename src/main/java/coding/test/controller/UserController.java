package coding.test.controller;

import coding.test.model.FpResponseDTO;
import coding.test.model.FpResponseResultDTO;
import coding.test.model.WalletAccountDTO;
import coding.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping(value = "user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userReference}")
    public FpResponseDTO getWalletAccount(@PathVariable String userReference) {
        Map result = userService.getWalletAccountDetails(userReference);
        return FpResponseResultDTO.success(result);
    }

}
