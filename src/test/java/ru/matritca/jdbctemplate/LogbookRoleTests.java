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
import ru.matritca.jdbctemplate.domain.LogbookUserRole;
import ru.matritca.jdbctemplate.repository.logbookuserrole.LogbookUserRoleDao;

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
    private LogbookUserRoleDao logbookUserRoleDao;

    @Test
    public void insertLogbookUserRole() throws Exception {

        LogbookUserRole logbookUserRole = new LogbookUserRole();
        logbookUserRole.setLogbookUserRoleName("Комиссия");
        logbookUserRole.setLogbookUserRoleDesc("Член комиссии");
        logbookUserRoleDao.create(logbookUserRole);

        LogbookUserRole logbookUserRoleFind = logbookUserRoleDao.findOneByLogbookUserRoleName("Комиссия");
        System.out.println(logbookUserRoleFind);
       // Assert.assertEquals(logbookUserRole.getId(), logbookUserRoleFind.getId());
        Assert.assertEquals(logbookUserRole.getLogbookUserRoleName(), logbookUserRoleFind.getLogbookUserRoleName());
        Assert.assertEquals(logbookUserRole.getLogbookUserRoleDesc(), logbookUserRoleFind.getLogbookUserRoleDesc());

    }


    @Test(expected = DuplicateKeyException.class)
    public void reinsertSameLogbookUserRole() throws Exception {

        LogbookUserRole logbookUserRole = new LogbookUserRole();
        logbookUserRole.setLogbookUserRoleName("Ответственное лицо");
        logbookUserRole.setLogbookUserRoleDesc("Ответственное лицо за приемку системы");
        logbookUserRoleDao.create(logbookUserRole);
        logbookUserRoleDao.create(logbookUserRole);

    }

    @Test
    public void addNameLogbookUserRoleList() throws Exception {

    }

    @Test
    public void selectLogbookUserRoleList() throws Exception {

       List<LogbookUserRole> logbookUserRoles = logbookUserRoleDao.findAllLogbookUserRole();

        for (LogbookUserRole logbookUserRole : logbookUserRoles) {
            logger.info("Id: {}",logbookUserRole.getId());
            logger.info("Name: {}",logbookUserRole.getLogbookUserRoleName());
            logger.info("Desc: {}",logbookUserRole.getLogbookUserRoleDesc());
        }

    }
}
