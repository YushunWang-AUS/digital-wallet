package coding.test.controller.dao;

import coding.test.model.WalletAccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class WalletAccountDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public WalletAccountDTO findByUserId(Long userId) {

        try {
            String sql = "SELECT id, user_id AS 'userId', created_at AS 'createdAt', enabled FROM wallet_account WHERE user_id=:userId AND enabled=true";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userId", userId);
            return jdbc.queryForObject(sql, params, new BeanPropertyRowMapper<>(WalletAccountDTO.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn(String.format("WalletAccount doesn't exist. userId:%s", userId));
            return null;
        }

    }

    public Map findByUserReference(String userReference) {

        try {
            String sql = "SELECT wc.id AS 'walletAccountId', user_id AS 'userId', created_at AS 'createdAt'," +
                    " user_reference AS 'userReference', u.first_name AS 'firstName', u.last_name AS 'lastName', u.email, u.username" +
                    " FROM wallet_account wc" +
                    " INNER JOIN user u ON wc.user_id = u.id AND wc.enabled=true" +
                    " WHERE user_reference=:userReference AND u.enabled=true";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userReference", userReference);
            return jdbc.queryForMap(sql, params);
        } catch (EmptyResultDataAccessException e) {
            log.warn(String.format("WalletAccount doesn't exist. userReference:%s", userReference));
            return null;
        }

    }

    public Long create(WalletAccountDTO walletAccount) {

        String sql = "INSERT INTO wallet_account (user_id, enabled, created_at) VALUES (:userId, true, NOW())";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", walletAccount.getUserId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder);
        Long id = keyHolder.getKey().longValue();
        log.info(String.format("WalletAccount created. id: %s, userId:%s", id, walletAccount.getUserId()));
        return id;

    }
}
