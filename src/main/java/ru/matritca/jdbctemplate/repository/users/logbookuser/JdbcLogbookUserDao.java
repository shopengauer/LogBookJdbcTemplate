package ru.matritca.jdbctemplate.repository.users.logbookuser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.matritca.jdbctemplate.domain.users.*;
import ru.matritca.jdbctemplate.repository.users.department.DepartmentDao;
import ru.matritca.jdbctemplate.repository.users.department.JdbcDepartmentDao;
import ru.matritca.jdbctemplate.repository.users.jobtitle.JdbcJobtitleDao;
import ru.matritca.jdbctemplate.repository.users.jobtitle.JobtitleDao;
import ru.matritca.jdbctemplate.repository.users.logbookuserrole.JdbcLogbookUserRoleDictDao;
import ru.matritca.jdbctemplate.repository.users.logbookuserrole.LogbookUserRoleDictDao;
import ru.matritca.jdbctemplate.repository.users.organization.JdbcOrganizationDao;
import ru.matritca.jdbctemplate.repository.users.organization.OrganizationDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Василий on 16.08.2015.
 */
@Repository
public class JdbcLogbookUserDao implements LogbookUsersDao
{

    private Logger logger = LoggerFactory.getLogger(JdbcLogbookUserDao.class);

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    @Autowired
    private JobtitleDao jdbcJobtitleDao;
    @Autowired
    private DepartmentDao jdbcDepartmentDao;
    @Autowired
    private OrganizationDao jdbcOrganizationDao;
    @Autowired
    private LogbookUserRoleDictDao logbookUserRoleDictDao;


    private static final class LogbookUserMapper implements RowMapper<LogbookUser>{
        @Override
        public LogbookUser mapRow(ResultSet resultSet, int i) throws SQLException {
            LogbookUser logbookUser = new LogbookUser();
            logbookUser.setId(resultSet.getLong(1));
            logbookUser.setUsername(resultSet.getString(2));
            logbookUser.setFirstname(resultSet.getString(3));
            logbookUser.setLastname(resultSet.getString(4));
            logbookUser.setPassword(resultSet.getString(5));
            logbookUser.setCertificate(resultSet.getBytes(6));
            logbookUser.setOrganizationName(resultSet.getString(7));
            logbookUser.setJobtitleName(resultSet.getString(8));
            logbookUser.setDepartmentName(resultSet.getString(9));
            return logbookUser;
        }
    }


    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int addLogbookUser(LogbookUser logbookUser) {

        // Внешние ключи
        Long organizationId = null;
        Long jobtitleId = null;
        Long departmentId = null;

        // Проверяем задано ли название организации у LogbookUser
        if (logbookUser.getOrganizationName() != null) {
            try {
                //пытаемся определить id введенной организации если она уже есть в базе
                organizationId = jdbcOrganizationDao.findOrganizationIdByOrganizationName(logbookUser.getOrganizationName());
            } catch (EmptyResultDataAccessException exception) {
                // Если организации нет то создаем ее
                jdbcOrganizationDao.addOrganization(new Organization(logbookUser.getOrganizationName()));
                // И получаем ее id
                organizationId = jdbcOrganizationDao.findOrganizationIdByOrganizationName(logbookUser.getOrganizationName());
            }
        }
          // Аналогично поступаем с Department и Jobtitle
        if(logbookUser.getDepartmentName() != null){
            try {
                departmentId = jdbcDepartmentDao.findDepartmentIdByDepartmentName(logbookUser.getDepartmentName());
            }catch (EmptyResultDataAccessException exception){
                jdbcDepartmentDao.addDepartment(new Department(logbookUser.getDepartmentName()));
                departmentId = jdbcDepartmentDao.findDepartmentIdByDepartmentName(logbookUser.getDepartmentName());
            }
        }

        if(logbookUser.getJobtitleName() != null){
            try {
                jobtitleId = jdbcJobtitleDao.findJobtitleIdByJobtitleName(logbookUser.getJobtitleName());
            }catch (EmptyResultDataAccessException exception){
                jdbcJobtitleDao.addJobtitle(new Jobtitle(logbookUser.getJobtitleName()));
                jobtitleId = jdbcJobtitleDao.findJobtitleIdByJobtitleName(logbookUser.getJobtitleName());
            }
        }

        // Задаем sql запрос для создания LogbookUser
        String sql = "INSERT INTO USERS.LOGBOOK_USER (LOGBOOK_USER_ID,LOGBOOK_USERNAME,LOGBOOK_USER_FIRSTNAME," +
                "LOGBOOK_USER_LASTNAME,LOGBOOK_USER_PASSWORD,LOGBOOK_USER_CERTIFICATE,LOGBOOK_USER_ORGANIZATION_ID," +
                " LOGBOOK_USER_DEPARTMENT_ID,LOGBOOK_USER_JOBTITLE_ID)" +
                " VALUES (nextval('USERS_SEQUENCE'),:username,:firstname,:lastname,:password,:certificate," +
                ":organizationId,:departmentId,:jobtitleId)";

        SqlParameterSource parameterSource = new MapSqlParameterSource("username",logbookUser.getUsername())
                .addValue("firstname", logbookUser.getFirstname()).addValue("lastname", logbookUser.getLastname())
                .addValue("password", logbookUser.getPassword()).addValue("certificate",logbookUser.getCertificate())
                .addValue("organizationId", organizationId).addValue("departmentId", departmentId)
                .addValue("jobtitleId", jobtitleId);

        // Записываем LogbookUser в базу
        namedParameterJdbcTemplate.update(sql, parameterSource);


        // Теперь сохраняем роли логбукюзера в отдельную таблицу
        // Получаем айди сохраненного логбукюзера
        Long logbookUserId = getLogbookUserIdByUsername(logbookUser.getUsername());
        // Создаем запрос для сохранения ролей логбукюзера
        String sql2 = "insert into USERS.LOGBOOK_USER_ROLE (LOGBOOK_USER_ROLE_ENTRY_ID," +
                "LOGBOOK_USER_ID,LOGBOOK_USER_ROLE_ID) values (nextval('USERS_SEQUENCE')," +
                ":logbookUserId,:logbookUserRoleId)";

       // перебираем List со всеми ролями юзера
        for (String role : logbookUser.getRoles()) {
            Long logbookUserRoleId = null;
            try {
                // пытаемся найти id текущей роли в словаре ролей
                logbookUserRoleId = logbookUserRoleDictDao.findLogbookUserRoleIdByLogbookUserRoleName(role);
            } catch (EmptyResultDataAccessException e) {
                // если не находим то создаем ее
                logbookUserRoleDictDao.addLogbookUserRole(new LogbookUserRole(role));
                // запрашиваем айди созданной роли
                logbookUserRoleId = logbookUserRoleDictDao.findLogbookUserRoleIdByLogbookUserRoleName(role);
                // todo продумать другую логику невозможности создать юзера с несуществующей ролью
            }
            // Теперь мапим параметры
            SqlParameterSource parameterSource1 = new MapSqlParameterSource("logbookUserId", logbookUserId)
                    .addValue("logbookUserRoleId", logbookUserRoleId);
            // и сохраняем текущую роль юзера в базу
            namedParameterJdbcTemplate.update(sql2, parameterSource1);
        }


        return 0;
    }



    @Override
    public long getLogbookUserIdByUsername(String username) {
       String sql = "select LOGBOOK_USER_ID from USERS.LOGBOOK_USER where LOGBOOK_USERNAME = :username";
       SqlParameterSource parameterSource = new MapSqlParameterSource("username",username);
       return namedParameterJdbcTemplate.queryForObject(sql,parameterSource,Long.class);
    }

    @Override
    @Transactional
    public LogbookUser findLogbookUserByUsername(String username) {

        // Создаем запрос на получение записи Logbookuser по username при этом соединяем organization,department,jobtitle
        String sql = "SELECT l.LOGBOOK_USER_ID,l.LOGBOOK_USERNAME,l.LOGBOOK_USER_FIRSTNAME,l.LOGBOOK_USER_LASTNAME," +
                "l.LOGBOOK_USER_PASSWORD,l.LOGBOOK_USER_CERTIFICATE,o.ORGANIZATION_NAME,j.JOBTITLE_NAME," +
                "d.DEPARTMENT_NAME FROM USERS.LOGBOOK_USER AS l, USERS.ORGANIZATION AS o," +
                "USERS.JOBTITLE AS j, USERS.DEPARTMENT AS d WHERE l.LOGBOOK_USERNAME = :username " +
                "AND l.LOGBOOK_USER_ORGANIZATION_ID = o.ORGANIZATION_ID AND l.LOGBOOK_USER_DEPARTMENT_ID = d.DEPARTMENT_ID " +
                "AND l.LOGBOOK_USER_JOBTITLE_ID = j.JOBTITLE_ID";

        // Маппим параметр username на запрос
        SqlParameterSource parameterSource = new MapSqlParameterSource("username",username);

      //* List<LogbookUser> logbookUsers = namedParameterJdbcTemplate.query(sql, parameterSource,new LogbookUserMapper());

       // получаем logbookuser со всеми параметрами кроме roles
       LogbookUser logbookUser = namedParameterJdbcTemplate.queryForObject(sql,parameterSource,new LogbookUserMapper());

        // Создаем запрос на получение roles для полученного юзера
        String sql2 = "SELECT dic.LOGBOOK_USER_ROLE_NAME from USERS.LOGBOOK_USER_ROLE_DICTIONARY AS dic, " +
               "USERS.LOGBOOK_USER_ROLE AS r WHERE r.LOGBOOK_USER_ID = :logbookUserId AND " +
               "r.LOGBOOK_USER_ROLE_ID = dic.LOGBOOK_USER_ROLE_ID";

        // Маппим параметр id на запрос
        SqlParameterSource parameterSource1 = new MapSqlParameterSource("logbookUserId",logbookUser.getId());
        // Получаем лист ролей полученного юзера
        List<String> roleList = namedParameterJdbcTemplate.queryForList(sql2,parameterSource1,String.class);
        // и добавляем их юзеру
        logbookUser.setRoles(roleList);
        return logbookUser;
    }

    @Override
    public void updateLogbookUser(LogbookUser logbookUser) {//todo

    }

    @Override
    public List<LogbookUser> findAllLogbookUsers() { // todo
        return null;
    }
}
