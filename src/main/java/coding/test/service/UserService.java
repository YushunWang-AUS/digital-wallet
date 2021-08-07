package coding.test.service;

import coding.test.controller.dao.UserDAO;
import coding.test.controller.dao.WalletAccountDAO;
import coding.test.exception.FpException;
import coding.test.model.UserDTO;
import coding.test.model.WalletAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    WalletAccountDAO walletAccountDAO;

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
            walletAccountDAO.create(walletAccount);
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


}
