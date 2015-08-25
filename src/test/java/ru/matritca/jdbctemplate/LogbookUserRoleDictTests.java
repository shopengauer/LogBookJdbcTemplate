package ru.matritca.jdbctemplate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.matritca.jdbctemplate.domain.users.Jobtitle;
import ru.matritca.jdbctemplate.domain.users.LogbookUserRole;
import ru.matritca.jdbctemplate.repository.users.logbookuserrole.JdbcLogbookUserRoleDictDao;
import ru.matritca.jdbctemplate.repository.users.logbookuserrole.LogbookUserRoleDictDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vasiliy on 18.08.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class LogbookUserRoleDictTests { //todo

    private Logger logger = LoggerFactory.getLogger(LogbookUserRoleDictTests.class);



    private List<LogbookUserRole> insertLogbookUserRoleList;
    private LogbookUserRole logbookUserRoleForInsert1;
    private LogbookUserRole logbookUserRoleForInsert2;

    @Autowired
    private LogbookUserRoleDictDao logbookUserRoleDictDao;


    @Before
    public void setUp() throws Exception {

       LogbookUserRole[] logbookUserRoles =
               {new LogbookUserRole("Администратор","Добавление пользователей.Редактирование.Назначение прав"),
                new LogbookUserRole("Редактор","Редактирование и просмотр записей"),
                new LogbookUserRole("Читатель","Просмотр записей"),
                new LogbookUserRole("Член приемочной комиссии","Просмотр записей"),
                new LogbookUserRole("Председатель приемочной комиссии"),
                new LogbookUserRole("Поверитель"),
                new LogbookUserRole("Проверяющий"),
                new LogbookUserRole("Технический специалист")
        };

        insertLogbookUserRoleList = new ArrayList<>(Arrays.asList(logbookUserRoles));

        logbookUserRoleForInsert1 = new LogbookUserRole("Технический специалист","Тестовая запись");
        logbookUserRoleForInsert2 = new LogbookUserRole("Поверитель","Тестовая запись");

    }

    @Test(expected = DuplicateKeyException.class)
    public void logbookUserRoleInsertTest() throws Exception {

        Assert.assertNotNull(logbookUserRoleDictDao);


        // Check that job titles does not equals null
        Assert.assertNotNull(logbookUserRoleForInsert1);
        Assert.assertNotNull(logbookUserRoleForInsert2);

        // Insert departments to database
        logbookUserRoleDictDao.addLogbookUserRole(logbookUserRoleForInsert1);
        logbookUserRoleDictDao.addLogbookUserRole(logbookUserRoleForInsert2);

        // Find before mentioned job titles
        LogbookUserRole findLogbookUserRoleByName1 = logbookUserRoleDictDao.findLogbookUserRoleByName(logbookUserRoleForInsert1.getLogbookUserRoleName());
        LogbookUserRole findLogbookUserRoleByName2 = logbookUserRoleDictDao.findLogbookUserRoleByName(logbookUserRoleForInsert2.getLogbookUserRoleName());

        // Assert that selected job titles had equals inserted job titles
        Assert.assertEquals(logbookUserRoleForInsert1.getLogbookUserRoleName(), findLogbookUserRoleByName1.getLogbookUserRoleName());
        Assert.assertEquals(logbookUserRoleForInsert2.getLogbookUserRoleName(), findLogbookUserRoleByName2.getLogbookUserRoleName());

        // Delete this job title
        logbookUserRoleDictDao.deleteLogbookUserRoleByLogbookUserRoleName(logbookUserRoleForInsert1.getLogbookUserRoleName());
        logbookUserRoleDictDao.deleteLogbookUserRoleByLogbookUserRoleName(logbookUserRoleForInsert2.getLogbookUserRoleName());

        // Find all job titles and check that job titles were deleted
        List<LogbookUserRole> logbookUserRoleList = logbookUserRoleDictDao.findAllLogbookUserRoles();
        Assert.assertEquals(0, logbookUserRoleList.size());

        // Insert list of job titles and check that 8 departments were inserted
        logbookUserRoleDictDao.addListOfLogbookUserRole(insertLogbookUserRoleList);
        List<LogbookUserRole> findLogbookUserRoleList = logbookUserRoleDictDao.findAllLogbookUserRoles();
        Assert.assertEquals(8, findLogbookUserRoleList.size());

        // Delete all job titles and check that all job titles were deleted
        logbookUserRoleDictDao.deleteAllLogbookUserRoles();
        List<LogbookUserRole> findEmptytList = logbookUserRoleDictDao.findAllLogbookUserRoles();
        Assert.assertTrue(findEmptytList.isEmpty());

        // Try to insert duplicate entry
        logbookUserRoleDictDao.addLogbookUserRole(new LogbookUserRole("Тестовая роль","Тестовое описание"));
        logbookUserRoleDictDao.addLogbookUserRole(new LogbookUserRole("Тестовая роль"));


    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testInsertNullJobtitleName() throws Exception {
        // Try to insert NULL jobtitle
       LogbookUserRole insertlogbookUserRole = new LogbookUserRole(null,"Тестовое описание");
        logbookUserRoleDictDao.addLogbookUserRole(insertlogbookUserRole);

    }


    @Test(expected = DataIntegrityViolationException.class)
    public void testInsertJobtitleNameMuchChar() throws Exception {
        // Try to insert department with length of departmentName over 60 chars
        logbookUserRoleDictDao.addLogbookUserRole(new LogbookUserRole("1234567890asdfghjkl;'" +
                "zxcvbnm,./.,mnbvcxz';lkjhgfdsa][poiuytrewq" +
                "0987654321"));
    }




}
