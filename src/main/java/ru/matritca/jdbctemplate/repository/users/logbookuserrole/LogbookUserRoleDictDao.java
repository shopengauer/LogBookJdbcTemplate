package ru.matritca.jdbctemplate.repository.users.logbookuserrole;

import ru.matritca.jdbctemplate.domain.users.Jobtitle;
import ru.matritca.jdbctemplate.domain.users.LogbookUserRole;
import ru.matritca.jdbctemplate.domain.users.Organization;

import java.util.List;

/**
 * Created by Vasiliy on 18.08.2015.
 */
public interface LogbookUserRoleDictDao {

    int addLogbookUserRole(LogbookUserRole logbookUserRole);
    int[] addListOfLogbookUserRole(List<LogbookUserRole> logbookUserRoleList);
    int addLogbookUserRoleIfNotExists(LogbookUserRole logbookUserRole);
    boolean isLogbookUserRoleExists(String logbookUserRoleName);
    LogbookUserRole findLogbookUserRoleByName(String logbookUserRoleName);
    LogbookUserRole findLogbookUserRoleById(long id);
    List<LogbookUserRole> findAllLogbookUserRoles();
    int deleteLogbookUserRoleByLogbookUserRoleName(String logbookUserRoleName);
    long findLogbookUserRoleIdByLogbookUserRoleName(String logbookUserRoleName);
    void deleteAllLogbookUserRoles();

}
