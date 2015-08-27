package ru.matritca.jdbctemplate.repository.users.logbookuserrole;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import ru.matritca.jdbctemplate.domain.users.LogbookUserRole;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vasiliy on 18.08.2015.
 */
@Repository
public class JdbcLogbookUserRoleDictDao implements LogbookUserRoleDictDao {

    private Logger logger = LoggerFactory.getLogger(JdbcLogbookUserRoleDictDao.class);
    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;


    private static final class LogbookUserRoleMapper implements RowMapper<LogbookUserRole>{

        @Override
        public LogbookUserRole mapRow(ResultSet resultSet, int i) throws SQLException {
            LogbookUserRole logbookUserRole = new LogbookUserRole();
            logbookUserRole.setId(resultSet.getLong("LOGBOOK_USER_ROLE_ID"));
            logbookUserRole.setLogbookUserRoleName(resultSet.getString("LOGBOOK_USER_ROLE_NAME"));
            logbookUserRole.setLogbookUserRoleDesc(resultSet.getString("LOGBOOK_USER_ROLE_DESC"));
            return logbookUserRole;
        }
    }


    @Override
    public int addLogbookUserRole(LogbookUserRole logbookUserRole) {
        String sql = "insert into USERS.LOGBOOK_USER_ROLE_DICTIONARY(LOGBOOK_USER_ROLE_ID,LOGBOOK_USER_ROLE_NAME,LOGBOOK_USER_ROLE_DESC)" +
                "VALUES (nextval('USERS_SEQUENCE'),:logbookUserRoleName,:logbookUserRoleDesc)";
        SqlParameterSource parameterSource = new MapSqlParameterSource("logbookUserRoleName",logbookUserRole.getLogbookUserRoleName()).addValue("logbookUserRoleDesc",logbookUserRole.getLogbookUserRoleDesc());
        return namedParameterJdbcTemplate.update(sql,parameterSource);
    }

    @Override
    public int[] addListOfLogbookUserRole(List<LogbookUserRole> logbookUserRoleList) {
        String sql = "insert into USERS.LOGBOOK_USER_ROLE_DICTIONARY(LOGBOOK_USER_ROLE_ID,LOGBOOK_USER_ROLE_NAME,LOGBOOK_USER_ROLE_DESC)" +
                "VALUES (nextval('USERS_SEQUENCE'),:logbookUserRoleName,:logbookUserRoleDesc)";
       SqlParameterSource[] parameterSources = SqlParameterSourceUtils.createBatch(logbookUserRoleList.toArray());
        return namedParameterJdbcTemplate.batchUpdate(sql,parameterSources);
    }

    @Override
    public LogbookUserRole findLogbookUserRoleByName(String logbookUserRoleName) {
        String sql = "select * from USERS.LOGBOOK_USER_ROLE_DICTIONARY where LOGBOOK_USER_ROLE_NAME = :logbookUserRoleName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("logbookUserRoleName",logbookUserRoleName);
        return namedParameterJdbcTemplate.queryForObject(sql,parameterSource,new LogbookUserRoleMapper());
    }

    @Override
    public LogbookUserRole findLogbookUserRoleById(long id) {
        String sql = "select * from USERS.LOGBOOK_USER_ROLE_DICTIONARY where LOGBOOK_USER_ROLE_ID = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id",id);
        return namedParameterJdbcTemplate.queryForObject(sql,parameterSource,new LogbookUserRoleMapper());
    }

    @Override
    public List<LogbookUserRole> findAllLogbookUserRoles() {

        String sql = "select * from USERS.LOGBOOK_USER_ROLE_DICTIONARY";
        return namedParameterJdbcTemplate.query(sql, new LogbookUserRoleMapper());
    }

    @Override
    public int deleteLogbookUserRoleByLogbookUserRoleName(String logbookUserRoleName) {
        String sql = "delete USERS.LOGBOOK_USER_ROLE_DICTIONARY where LOGBOOK_USER_ROLE_NAME = :logbookUserRoleName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("logbookUserRoleName",logbookUserRoleName);
        return namedParameterJdbcTemplate.update(sql,parameterSource);
    }

    @Override
    public long findLogbookUserRoleIdByLogbookUserRoleName(String logbookUserRoleName) {
        String sql = "select LOGBOOK_USER_ROLE_ID from USERS.LOGBOOK_USER_ROLE_DICTIONARY where LOGBOOK_USER_ROLE_NAME = :logbookUserRoleName ";
        SqlParameterSource parameterSource = new MapSqlParameterSource("logbookUserRoleName",logbookUserRoleName);
        return namedParameterJdbcTemplate.queryForObject(sql,parameterSource,Long.class);
    }

    @Override
    public void deleteAllLogbookUserRoles() {
        String sql = "delete from USERS.LOGBOOK_USER_ROLE_DICTIONARY";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sql);
    }
}
