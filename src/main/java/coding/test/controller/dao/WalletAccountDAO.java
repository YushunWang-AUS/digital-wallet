package coding.test.controller.dao;

import coding.test.model.WalletAccountDTO;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WalletAccountDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public WalletAccountDTO findByUserId(Long userId) {

//        try {
        String sql = "SELECT id FROM wallet_account WHERE user_id=:userId AND enabled=true";
        Map params = new HashMap<>();
        params.put("userId", userId);

        return jdbc.queryForObject(sql, new MapSqlParameterSource(params), WalletAccountDTO.class);
//        } catch (EmptyResultDataAccessException e) {
//            log.debug("No allocation overrides found for Product Option with id:${id} on ${date}")
//        }


    }
}
