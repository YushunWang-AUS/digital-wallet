package coding.test.controller;

import coding.test.model.FpResponseDTO;
import coding.test.model.FpResponseResultDTO;
import coding.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping(value = "user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @GetMapping("wallet-account/{userReference}")
    public FpResponseDTO getWalletAccount(@PathVariable String userReference) {
        Map result = userService.getWalletAccountDetails(userReference);
        return FpResponseResultDTO.success(result);
    }

    @Transactional(readOnly = true)
    @GetMapping("transactions/{userReference}")
    public FpResponseDTO getTransactions(@PathVariable String userReference) {
        List transactions = userService.findTransactionDetails(userReference);
        Map summary = userService.findTransactionSummary(userReference);
        summary.put("transactions", transactions);
        return FpResponseResultDTO.success(summary);
    }

}
