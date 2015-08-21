package ru.matritca.jdbctemplate.repository.logbookuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.matritca.jdbctemplate.domain.LogbookUser;

/**
 * Created by Василий on 16.08.2015.
 */
@Repository
public class LogbookUserImplDao implements LogbookUsersDao
{
      private static final String curval = "select currval('USERS_SEQUENCE')";
      private static final String nextval = "select nextval('USERS_SEQUENCE')";


    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public long create(LogbookUser logbookUser) {
        return 0;
    }


    @Override
    public void createSequence(String seqName) {

    }

    @Override
    public long getCurrVal() {
        return jdbcOperations.queryForObject(curval, Long.class);
     }

    @Override
    @Transactional(readOnly = true)
    public long getNextVal() {
        return jdbcOperations.queryForObject(nextval, Long.class);
    }
}
