package ru.matritca.jdbctemplate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.matritca.jdbctemplate.domain.users.LogbookUserRole;
import ru.matritca.jdbctemplate.repository.users.logbookuserrole.JdbcLogbookUserRoleDictDao;

import java.util.List;

/**
 * Created by Vasiliy on 18.08.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class LogbookRoleTests {

    private Logger logger = LoggerFactory.getLogger(LogbookRoleTests.class);

    @Autowired
    private JdbcLogbookUserRoleDictDao logbookUserRoleDao;


}
