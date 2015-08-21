package ru.matritca.jdbctemplate.repository.logbookuserrole;

import ru.matritca.jdbctemplate.domain.users.LogbookUserRole;

import java.util.List;

/**
 * Created by Vasiliy on 18.08.2015.
 */
public interface LogbookUserRoleDao {

    long create(LogbookUserRole logbookUserRole);
    LogbookUserRole findOneByLogbookUserRoleName(String LogbookUserRoleName);
    List<LogbookUserRole> findAllLogbookUserRole();
    int insertLogbookUserRoleList(List<LogbookUserRole> logbookUserRoleList);

}
