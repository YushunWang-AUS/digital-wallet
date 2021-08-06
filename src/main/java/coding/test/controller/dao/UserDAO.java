package coding.test.controller.dao;

import coding.test.model.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserDAO {

    private final Logger log = LoggerFactory.getLogger(UserDAO.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public UserDTO find(Long id) {

        try {
            String sql = "SELECT id FROM user WHERE id=:id AND enabled=true";
            Map<String, Object> params = new HashMap<>();
            params.put("id", id);
            return jdbc.queryForObject(sql, new MapSqlParameterSource(params), UserDTO.class);
        } catch (EmptyResultDataAccessException e) {
            log.debug("No allocation overrides found for Product Option with id:${id} on ${date}");
            return null;
        }

    }
}
