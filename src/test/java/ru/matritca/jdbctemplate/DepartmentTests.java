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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.matritca.jdbctemplate.domain.users.Department;
import ru.matritca.jdbctemplate.repository.department.DepartmentDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vasiliy on 19.08.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class DepartmentTests {

    private Logger logger = LoggerFactory.getLogger(DepartmentTests.class);

    @Autowired
    private DepartmentDao departmentDao;

    private List<Department> insertDepartmentList;

    @Before
    public void setUp() throws Exception {

       Department[] departmentsArray = {new Department("ПТО"),
               new Department("Цех"),new Department("Ремонт"),
               new Department("Администрация"),new Department("Бухгалтерия"),
               new Department("Охрана"),new Department("АСУ"),
               new Department("ОТК"),new Department("ПЭО")};

        insertDepartmentList =  new ArrayList<>(Arrays.asList(departmentsArray));

    }

    @Test
    public void departmentInsertTest() throws Exception {
        String depName1 = "Отдел программной разработки";
        String depName2 = "Отдел разработки печатных плат";

        Assert.assertNotNull(departmentDao);

        Department departmentForInsert1 = new Department();
        departmentForInsert1.setDepartmentName(depName1);
        Department departmentForInsert2 = new Department();
        departmentForInsert2.setDepartmentName(depName2);

        Assert.assertNotNull(departmentForInsert1);
        Assert.assertNotNull(departmentForInsert2);

        departmentDao.addDepartment(departmentForInsert1);
        departmentDao.addDepartment(departmentForInsert2);

        Department findDepartmentByName1 = departmentDao.findDepartmentByName(depName1);
        Department findDepartmentByName2 = departmentDao.findDepartmentByName(depName2);

        Assert.assertEquals(departmentForInsert1.getDepartmentName(), findDepartmentByName1.getDepartmentName());
        Assert.assertEquals(departmentForInsert2.getDepartmentName(), findDepartmentByName2.getDepartmentName());

        departmentDao.deleteDepartmentByDepartmentName(depName1);
        departmentDao.deleteDepartmentByDepartmentName(depName2);

        List<Department> departmentList = departmentDao.findAllDepartments();
        Assert.assertEquals(0, departmentList.size());

        departmentDao.addListOfDepartment(insertDepartmentList);
        List<Department> findDepartmentList = departmentDao.findAllDepartments();
        Assert.assertEquals(9, findDepartmentList.size());

        logger.info("Id: {}", departmentDao.findDepartmentIdByDepartmentName("ПТО"));
        logger.info("Id: {}", departmentDao.findDepartmentIdByDepartmentName("Цех"));
        logger.info("Id: {}", departmentDao.findDepartmentIdByDepartmentName("Ремонт"));

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testInsertNullDepartmentName() throws Exception {

        Department insertDepartment = new Department(null);
        departmentDao.addDepartment(insertDepartment);

    }

//    @Test(expected = DuplicateKeyException.class)
//    public void testInsertDuplicateDepartmentName() throws Exception {
//        departmentDao.addDepartment(new Department("Маркетинг"));
//        departmentDao.addDepartment(new Department("Маркетинг"));
//
//    }



    @Test(expected = DataIntegrityViolationException.class)
    public void testInsertDepartmentNameMuchChar() throws Exception {
        departmentDao.addDepartment(new Department("Бухгалтерия dbfbdfb" +
                "dfbdfbsdfbfdbfbg nfgbdsfbadfbfdbdfbsfbsfgbsfgnfsg" +
                "fdbsdfbdbdfbsdfbsdfb dfxcxbhnhnxcvnvnynwfncvbcvzcv" +
                "cvbqweerttyuiop;lkjhgff"));
    }
}