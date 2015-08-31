package ru.matritca.jdbctemplate.repository.users.logbookuser_v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.matritca.jdbctemplate.domain.users.LogbookUser_v2;
import ru.matritca.jdbctemplate.repository.users.department.DepartmentDao;
import ru.matritca.jdbctemplate.repository.users.jobtitle.JobtitleDao;
import ru.matritca.jdbctemplate.repository.users.logbookuserrole.LogbookUserRoleDictDao;
import ru.matritca.jdbctemplate.repository.users.organization.OrganizationDao;

/**
 * Created by Vasiliy on 28.08.2015.
 */
@Repository
public class JdbcLogbookUserDao2 implements LogbookUserDao2 {


    @Autowired
    private DepartmentDao jdbcDepartmentDao;
    @Autowired
    private JobtitleDao jdbcJobtitleDao;
    @Autowired
    private OrganizationDao jdbcOrganizationDao;
    @Autowired
    private LogbookUserRoleDictDao jdbcLogbookUserRoleDictDao;
    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;


    @Override
    public int addLogbookUserIfNotExists(LogbookUser_v2 logbookUser) {

        // Внешние ключи
        Long organizationId = null;
        Long jobtitleId = null;
        Long departmentId = null;

        return 0;
    }

    @Override
    public int addLogbookUser(LogbookUser_v2 logbookUser) {

        jdbcDepartmentDao.addDepartmentIfNotExists(logbookUser.getDepartment());

        Long organizationId = jdbcDepartmentDao.findDepartmentIdByDepartmentName(logbookUser.getDepartment().getDepartmentName()).get(0);


        return 0;
    }

    @Override
    public boolean isLogbookUserExists(String username) {
      String sql = "SELECT LOGBOOK_USERNAME FROM USERS.LOGBOOK_USER WHERE LOGBOOK_USERNAME = :username";
      SqlParameterSource parameterSource = new MapSqlParameterSource("username",username);
      return namedParameterJdbcTemplate.queryForList(sql,parameterSource,String.class).isEmpty();
    }
}
