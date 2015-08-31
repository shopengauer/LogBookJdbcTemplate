package ru.matritca.jdbctemplate.logbookuser;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.matritca.jdbctemplate.DemoApplication;
import ru.matritca.jdbctemplate.domain.users.LogbookUser;
import ru.matritca.jdbctemplate.repository.users.logbookuser.LogbookUsersDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vasiliy on 24.08.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class LogbookUserTests {

    @Autowired
    private LogbookUsersDao jdbcLogbookUserDao;

    private LogbookUser insertLogbookUser1;
    private LogbookUser insertLogbookUser2;
    private LogbookUser insertLogbookUser3;

    String user1role1;
    String user1role2;

    String user2role1;
    String user2role2;

    @Before
    public void setUp() throws Exception {

        user1role1 = "Администратор";
        user1role2 = "Поверитель";

        user2role1 = "Редактор";
        user2role2 = "Председатель комиссии";


        String[] rolesArray1 = {user1role1,user1role2};
        List<String> roles1 = new ArrayList<>(Arrays.asList(rolesArray1));

        String[] rolesArray2 = {user2role1,user2role2};
        List<String> roles2 = new ArrayList<>(Arrays.asList(rolesArray2));

        insertLogbookUser1 = new LogbookUser("Wasul","Павлов","Василий",roles1);
        insertLogbookUser1.setOrganizationName("ООО Матрица");
        insertLogbookUser1.setDepartmentName("ПТО");
        insertLogbookUser1.setJobtitleName("Архитектор ПО");
        insertLogbookUser1.setPassword("123");
        insertLogbookUser1.setCertificate(new byte[]{1, 2, 3, 4, 5});

        insertLogbookUser2 = new LogbookUser("Danuer","Михайлов","Серафим",roles2);
        insertLogbookUser2.setOrganizationName("ООО 'Матрица'");
        insertLogbookUser2.setDepartmentName("ОТК");
        insertLogbookUser2.setJobtitleName("Начальние ОТК");
        insertLogbookUser2.setPassword("321");
        insertLogbookUser2.setCertificate(new byte[]{'a','2','3','4','5'});

        insertLogbookUser3 = new LogbookUser("Kuksha","Михайлов","Серафим",roles2);
        insertLogbookUser3.setOrganizationName("ООО 'Матрица'");
        insertLogbookUser3.setDepartmentName("КУРОЦАПА");
        insertLogbookUser3.setJobtitleName("Бабигуль ОТК");
        insertLogbookUser3.setPassword("321");
        insertLogbookUser3.setCertificate(new byte[]{'a','2','3','4','5'});


    }

    @Test
    public void insertLogbookUser() throws Exception {

         Assert.assertNotNull(jdbcLogbookUserDao);

         jdbcLogbookUserDao.addLogbookUser(insertLogbookUser1);

         jdbcLogbookUserDao.addLogbookUser(insertLogbookUser2);


         Assert.assertTrue(jdbcLogbookUserDao.isLogbookUserExists(insertLogbookUser1.getUsername()));
         Assert.assertTrue(jdbcLogbookUserDao.isLogbookUserExists(insertLogbookUser2.getUsername()));

         Assert.assertFalse(jdbcLogbookUserDao.isLogbookUserExists("GAGA"));
         Assert.assertFalse(jdbcLogbookUserDao.isLogbookUserExists("BABA"));


        Assert.assertEquals(0, jdbcLogbookUserDao.addLogbookUserIfNotExists(insertLogbookUser1));
        Assert.assertEquals(1, jdbcLogbookUserDao.addLogbookUserIfNotExists(insertLogbookUser3));




         LogbookUser findLogbookUser1 = jdbcLogbookUserDao.findLogbookUserByUsername(insertLogbookUser1.getUsername());
         LogbookUser findLogbookUser2 = jdbcLogbookUserDao.findLogbookUserByUsername(insertLogbookUser2.getUsername());





         // Проверка всех полей

        Assert.assertEquals(findLogbookUser1,insertLogbookUser1);


         // Проверка размера листа ролей
         Assert.assertEquals(insertLogbookUser1.getRoles().size(),findLogbookUser1.getRoles().size());
         Assert.assertEquals(insertLogbookUser2.getRoles().size(),findLogbookUser2.getRoles().size());

        Assert.assertTrue(findLogbookUser1.getRoles().contains(user1role1));
         Assert.assertTrue(findLogbookUser1.getRoles().contains(user1role2));

         Assert.assertTrue(findLogbookUser2.getRoles().contains(user2role1));
         Assert.assertTrue(findLogbookUser2.getRoles().contains(user2role2));




    }
}
