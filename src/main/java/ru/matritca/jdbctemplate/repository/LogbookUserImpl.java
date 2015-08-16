package ru.matritca.jdbctemplate.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.matritca.jdbctemplate.domain.LogbookUser;

/**
 * Created by Василий on 16.08.2015.
 */
@Repository
public class LogbookUserImpl implements LogbookUsers
{
      private static final String curval = "select currval('logbook_seq')";
      private static final String nextval = "select nextval('logbook_seq')";

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public long create(LogbookUser logbookUser) {
        return 0;
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
