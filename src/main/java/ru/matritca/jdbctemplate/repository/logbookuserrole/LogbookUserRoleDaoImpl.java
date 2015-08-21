package ru.matritca.jdbctemplate.repository.logbookuserrole;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.matritca.jdbctemplate.domain.users.LogbookUserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vasiliy on 18.08.2015.
 */
@Repository
public class LogbookUserRoleDaoImpl implements LogbookUserRoleDao {

    private Logger logger = LoggerFactory.getLogger(LogbookUserRoleDaoImpl.class);

    @Autowired
    private JdbcOperations jdbcTemplate;

    private static final class LogbookUserRoleMapper implements RowMapper<LogbookUserRole>{

        @Override
        public LogbookUserRole mapRow(ResultSet resultSet, int i) throws SQLException {
            LogbookUserRole logbookUserRole = new LogbookUserRole();
            logbookUserRole.setId(resultSet.getLong(1));
            logbookUserRole.setLogbookUserRoleName(resultSet.getString(2));
            logbookUserRole.setLogbookUserRoleDesc(resultSet.getString(3));
            return logbookUserRole;
        }
    }



    @Override
    public long create(LogbookUserRole logbookUserRole) {

      String stmt = "INSERT INTO USERS.LOGBOOK_USER_ROLE (LOGBOOK_USER_ROLE_NAME,LOGBOOK_USER_ROLE_DESC) VALUES (?,?)";

      jdbcTemplate.update(stmt,logbookUserRole.getLogbookUserRoleName(),logbookUserRole.getLogbookUserRoleDesc());

        return 0;
    }

    @Override
    public LogbookUserRole findOneByLogbookUserRoleName(String logbookUserRoleName) {

      String stmt = "SELECT * FROM USERS.LOGBOOK_USER_ROLE WHERE LOGBOOK_USER_ROLE_NAME = ?";
      return jdbcTemplate.queryForObject(stmt, new Object[]{logbookUserRoleName}, new LogbookUserRoleMapper());
      }

    @Override
    public List<LogbookUserRole> findAllLogbookUserRole() {

        String stmt = "SELECT * FROM USERS.LOGBOOK_USER_ROLE";
        return jdbcTemplate.query(stmt,new LogbookUserRoleMapper());
    }

    @Override
    public int insertLogbookUserRoleList(List<LogbookUserRole> logbookUserRoleList) {
        return 0;
    }
}
