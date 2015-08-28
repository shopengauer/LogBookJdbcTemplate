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


    }

    @Test
    public void insertLogbookUser() throws Exception {

         Assert.assertNotNull(jdbcLogbookUserDao);

         jdbcLogbookUserDao.addLogbookUser(insertLogbookUser1);

//        try {
//            jdbcLogbookUserDao.addLogbookUser(insertLogbookUser1);
//        }catch (DuplicateKeyException e){
//            System.out.println(e.toString());
//        }
        jdbcLogbookUserDao.addLogbookUser(insertLogbookUser2);

         LogbookUser findLogbookUser1 = jdbcLogbookUserDao.findLogbookUserByUsername(insertLogbookUser1.getUsername());
         LogbookUser findLogbookUser2 = jdbcLogbookUserDao.findLogbookUserByUsername(insertLogbookUser2.getUsername());

         // Проверка всех полей

        Assert.assertEquals(findLogbookUser1,insertLogbookUser1);
//         Assert.assertEquals(findLogbookUser1.getUsername(),insertLogbookUser1.getUsername());
//         Assert.assertEquals(findLogbookUser2.getUsername(),insertLogbookUser2.getUsername());
//
//         Assert.assertEquals(findLogbookUser1.getFirstname(),insertLogbookUser1.getFirstname());
//         Assert.assertEquals(findLogbookUser2.getFirstname(),insertLogbookUser2.getFirstname());
//
//         Assert.assertEquals(findLogbookUser1.getLastname(),insertLogbookUser1.getLastname());
//         Assert.assertEquals(findLogbookUser2.getLastname(),insertLogbookUser2.getLastname());
//
//          Assert.assertEquals(findLogbookUser1.getPassword(),insertLogbookUser1.getPassword());
//         Assert.assertEquals(findLogbookUser2.getPassword(),insertLogbookUser2.getPassword());
//
//           Assert.assertEquals(findLogbookUser1.getCertificate(),insertLogbookUser1.getCertificate());
//         Assert.assertEquals(findLogbookUser2.getCertificate(),insertLogbookUser2.getCertificate());
//
//          Assert.assertEquals(findLogbookUser1.getDepartmentName(),insertLogbookUser1.getDepartmentName());
//         Assert.assertEquals(findLogbookUser2.getDepartmentName(),insertLogbookUser2.getDepartmentName());





         // Проверка размера листа ролей
         Assert.assertEquals(insertLogbookUser1.getRoles().size(),findLogbookUser1.getRoles().size());
         Assert.assertEquals(insertLogbookUser2.getRoles().size(),findLogbookUser2.getRoles().size());

        Assert.assertTrue(findLogbookUser1.getRoles().contains(user1role1));
         Assert.assertTrue(findLogbookUser1.getRoles().contains(user1role2));

         Assert.assertTrue(findLogbookUser2.getRoles().contains(user2role1));
         Assert.assertTrue(findLogbookUser2.getRoles().contains(user2role2));




    }
}
