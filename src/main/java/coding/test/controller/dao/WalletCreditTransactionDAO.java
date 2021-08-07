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

import java.util.List;
import java.util.Map;

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

    public List findTransactionDetails(String userReference) {

        try {
            String sql = "SELECT wct.id, wct.transaction_reference, wct.amount/100, wct.account_type AS 'accountType', " +
                    "wct.transaction_type AS 'transactionType', wct.created_at AS 'createdAt', wct.updated_at AS 'updatedAt'," +
                    " wct.currency, wct.description FROM wallet_credit_transaction wct" +
                    " INNER JOIN wallet_account wa on wct.wallet_account_id = wa.id AND wa.enabled=true" +
                    " INNER JOIN user u on wa.user_id = u.id AND u.enabled=true" +
                    " WHERE u.user_reference=:userReference";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userReference", userReference);

            return jdbc.queryForList(sql, params);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Map findTransactionSummary(String userReference) {

        try {
            String sql = "SELECT wcts.user_id, wcts.user_reference, wcts.total_credit/100 AS 'totalCredit', wcts.total_debit/100 AS 'totalDebit'," +
                    " wcts.balance AS 'transactionType'" +
                    " FROM view_wallet_credit_transaction_summary wcts" +
                    " WHERE wcts.user_reference=:userReference";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userReference", userReference);

            return jdbc.queryForMap(sql, params);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
