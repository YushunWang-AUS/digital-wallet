package coding.test.controller.dao;

import coding.test.model.WalletCreditTransactionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class WalletCreditTransactionDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public Long findByTransactionReference(String transactionReference) {

        try {
            String sql = "SELECT id FROM wallet_credit_transaction WHERE transaction_reference=:transactionReference";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("transactionReference", transactionReference);
            return jdbc.queryForObject(sql, params, Long.class);
        } catch (EmptyResultDataAccessException e) {
            log.warn(String.format("Wallet Account Transaction doesn't exist. transactionReference:%s", transactionReference));
            return null;
        }

    }

    public Long create(WalletCreditTransactionDTO transactionDTO) {

        String sql = "INSERT INTO wallet_credit_transaction (transaction_reference, wallet_account_id, amount, currency, transaction_type, method, account_type, description, created_at, updated_at) " +
                "VALUES (:transactionReference, :walletAccountId, :amount, :currency, :transactionType, :method, :accountType, :description, :createdAt, :updatedAt)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("transactionReference", transactionDTO.getTransactionReference())
                .addValue("walletAccountId", transactionDTO.getWalletAccountId())
                .addValue("amount", transactionDTO.getAmount())
                .addValue("currency", transactionDTO.getCurrency())
                .addValue("transactionType", transactionDTO.getTransactionType().name())
                .addValue("method", transactionDTO.getMethod())
                .addValue("accountType", transactionDTO.getAccountType().name())
                .addValue("description", transactionDTO.getDescription())
                .addValue("createdAt", transactionDTO.getCreatedAt())
                .addValue("updatedAt", transactionDTO.getUpdatedAt());

        Integer id = jdbc.update(sql, params, new GeneratedKeyHolder());
        log.info(String.format("Wallet Credit Transaction created. id: %s, transactionReference:%s", id, transactionDTO.getTransactionReference()));
        return id.longValue();

    }
}
