package ru.matritca.jdbctemplate.repository.logbookuser;

import org.springframework.stereotype.Repository;
import ru.matritca.jdbctemplate.domain.LogbookUser;

/**
 * Created by Василий on 16.08.2015.
 */

public interface LogbookUsersDao {

    long create(LogbookUser logbookUser);
    long getCurrVal();
    long getNextVal();
    void createSequence(String seqName);

}
