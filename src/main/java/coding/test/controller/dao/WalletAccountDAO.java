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
import org.springframework.stereotype.Repository;

@Repository
public class WalletAccountDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public WalletAccountDTO findByUserId(Long userId) {

        try {
            String sql = "SELECT id FROM wallet_account WHERE user_id=:userId AND enabled=true";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userId", userId);
            return jdbc.queryForObject(sql, params, new BeanPropertyRowMapper<>(WalletAccountDTO.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn(String.format("WalletAccount doesn't exist. userId:%s", userId));
            return null;
        }

    }

    public Long create(WalletAccountDTO walletAccount) {

        String sql = "INSERT INTO wallet_account (user_id, enabled, created_at) VALUES (:userId, true, NOW())";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", walletAccount.getUserId());

        Integer id = jdbc.update(sql, params, new GeneratedKeyHolder());
        log.info(String.format("WalletAccount created. id: %s, userId:%s", id, walletAccount.getUserId()));
        return id.longValue();

    }
}
