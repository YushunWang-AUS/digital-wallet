package coding.test.service;

import coding.test.controller.dao.UserDAO;
import coding.test.controller.dao.WalletAccountDAO;
import coding.test.controller.dao.WalletCreditTransactionDAO;
import coding.test.exception.FpException;
import coding.test.model.UserDTO;
import coding.test.model.WalletAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    WalletAccountDAO walletAccountDAO;

    @Autowired
    WalletCreditTransactionDAO walletCreditTransactionDAO;

    /**
     * Get Wallet Account. If it doesn't exist, it will create a new one here.
     *
     * @param userReference user_id in the API request
     * @return WalletAccountDTO: Wallet Account Details
     */
    public WalletAccountDTO getWalletAccount(String userReference) {
        UserDTO user = userDAO.findByUserReference(userReference);
        if (user == null) {
            throw new FpException(String.format("User doesn't exist. userReference:%s", userReference));
        }
        WalletAccountDTO walletAccount = walletAccountDAO.findByUserId(user.getId());
        if (walletAccount == null) {
            walletAccount = new WalletAccountDTO();
            walletAccount.setUserId(user.getId());
            Long id = walletAccountDAO.create(walletAccount);
            walletAccount.setId(id);
        }

        return walletAccount;
    }

    public Map getWalletAccountDetails(String userReference) {
        Map details = walletAccountDAO.findByUserReference(userReference);
        if (details == null) {
            throw new FpException(String.format("Wallet account doesn't exist. userReference:%s", userReference));
        }

        return details;
    }

    public List findTransactionDetails(String userReference) {
        List details = walletCreditTransactionDAO.findTransactionDetails(userReference);
        if (details == null) {
            throw new FpException(String.format("Available transactions doesn't exist. userReference:%s", userReference));
        }
        return details;
    }


    public Map findTransactionSummary(String userReference) {
        Map summary = walletCreditTransactionDAO.findTransactionSummary(userReference);
        if (summary == null) {
            throw new FpException(String.format("Available transaction summary doesn't exist. userReference:%s", userReference));
        }
        return summary;
    }
}
