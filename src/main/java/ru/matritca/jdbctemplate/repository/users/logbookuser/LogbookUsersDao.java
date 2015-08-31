package ru.matritca.jdbctemplate.repository.users.logbookuser;

import ru.matritca.jdbctemplate.domain.users.LogbookUser;

import java.util.List;

/**
 * Created by Василий on 16.08.2015.
 */

public interface LogbookUsersDao {

     int addLogbookUser(LogbookUser logbookUser);
     int addLogbookUserIfNotExists(LogbookUser logbookUser);
     boolean isLogbookUserExists(String username);
     int addLogbookUserRoles(LogbookUser logbookUser);
     void updateLogbookUser(LogbookUser logbookUser);

     long getLogbookUserIdByUsername(String username);
     LogbookUser findLogbookUserByUsername(String username);
     List<LogbookUser> findAllLogbookUsers();

}
