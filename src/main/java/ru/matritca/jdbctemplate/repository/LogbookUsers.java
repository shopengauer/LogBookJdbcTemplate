package ru.matritca.jdbctemplate.repository;

import ru.matritca.jdbctemplate.domain.LogbookUser;

/**
 * Created by Василий on 16.08.2015.
 */
public interface LogbookUsers {

    long create(LogbookUser logbookUser);
    long getCurrVal();
    long getNextVal();

}
