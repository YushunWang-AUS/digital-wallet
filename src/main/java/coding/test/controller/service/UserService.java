package coding.test.controller.service;

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

    public WalletAccountDAO getWalletAccount(Long userId) throws FpException {
        UserDTO user = userDAO.find(userId);
        if (user == null) {
            throw new FpException(String.format("User doesn't exist. userId:%s", userId));
        }
        WalletAccountDTO walletAccount = walletAccountDAO.findByUserId(userId);
        if (walletAccount == null) {

        }

        return null;
    }


}
