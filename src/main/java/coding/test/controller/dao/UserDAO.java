package coding.test.controller.dao;

import coding.test.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public UserDTO find(Long id) {

        try {
            String sql = "SELECT id, user_reference AS 'userReference', first_name AS 'firstName', last_name AS 'lastName', email FROM user WHERE id=:id AND enabled=true";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("id", id);
            return jdbc.queryForObject(sql, params, new BeanPropertyRowMapper<>(UserDTO.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn(String.format("User doesn't exist. userId:%s", id));
            return null;
        }

    }

    public UserDTO findByUserReference(String userReference) {

        try {
            final String sql = "SELECT id, user_reference AS 'userReference', first_name AS 'firstName', last_name AS 'lastName', email " +
                    "FROM user " +
                    "WHERE user_reference=:userReference AND enabled=true";
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("userReference", userReference);
            return jdbc.queryForObject(sql, params, new BeanPropertyRowMapper<>(UserDTO.class));
        } catch (EmptyResultDataAccessException e) {
            log.warn(String.format("User doesn't exist. userReference:%s", userReference));
            return null;
        }

    }
}
