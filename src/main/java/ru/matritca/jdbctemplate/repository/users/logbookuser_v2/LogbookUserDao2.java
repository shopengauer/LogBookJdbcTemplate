package ru.matritca.jdbctemplate.repository.users.logbookuser_v2;

import ru.matritca.jdbctemplate.domain.users.LogbookUser;
import ru.matritca.jdbctemplate.domain.users.LogbookUser_v2;

/**
 * Created by Vasiliy on 28.08.2015.
 */
public interface LogbookUserDao2 {

    int addLogbookUserIfNotExists(LogbookUser_v2 logbookUser);
    int addLogbookUser(LogbookUser_v2 logbookUser);
    boolean isLogbookUserExists(String username);
}
