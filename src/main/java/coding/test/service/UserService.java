package coding.test.service;

import coding.test.controller.dao.UserDAO;
import coding.test.controller.dao.WalletAccountDAO;
import coding.test.exception.FpException;
import coding.test.model.UserDTO;
import coding.test.model.WalletAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    WalletAccountDAO walletAccountDAO;

    public WalletAccountDTO getWalletAccount(String userReference) throws FpException {
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


}
