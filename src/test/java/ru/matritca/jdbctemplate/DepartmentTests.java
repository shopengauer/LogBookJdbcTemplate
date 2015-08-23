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
import ru.matritca.jdbctemplate.domain.users.Department;
import ru.matritca.jdbctemplate.repository.users.department.DepartmentDao;

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

    @Test(expected = DuplicateKeyException.class)
    public void departmentInsertTest() throws Exception {


        String depName1 = "Отдел программной разработки";
        String depName2 = "Отдел разработки печатных плат";

        Assert.assertNotNull(departmentDao);

        // Create two departments
        Department departmentForInsert1 = new Department();
        departmentForInsert1.setDepartmentName(depName1);
        Department departmentForInsert2 = new Department();
        departmentForInsert2.setDepartmentName(depName2);

        // Check that departments does not equals null
        Assert.assertNotNull(departmentForInsert1);
        Assert.assertNotNull(departmentForInsert2);

        // Insert departments to database
        departmentDao.addDepartment(departmentForInsert1);
        departmentDao.addDepartment(departmentForInsert2);

        // Find before mentioned departments
        Department findDepartmentByName1 = departmentDao.findDepartmentByName(depName1);
        Department findDepartmentByName2 = departmentDao.findDepartmentByName(depName2);

        // Assert that selected departments had equals inserted departments
        Assert.assertEquals(departmentForInsert1.getDepartmentName(), findDepartmentByName1.getDepartmentName());
        Assert.assertEquals(departmentForInsert2.getDepartmentName(), findDepartmentByName2.getDepartmentName());

        // Delete this department
        departmentDao.deleteDepartmentByDepartmentName(depName1);
        departmentDao.deleteDepartmentByDepartmentName(depName2);

        // Find all departments and check that departments were deleted
        List<Department> departmentList = departmentDao.findAllDepartments();
        Assert.assertEquals(0, departmentList.size());

        // Insert list of departments and check that 9 departments were inserted
        departmentDao.addListOfDepartment(insertDepartmentList);
        List<Department> findDepartmentList = departmentDao.findAllDepartments();
        Assert.assertEquals(9, findDepartmentList.size());

        logger.info("Id: {}", departmentDao.findDepartmentIdByDepartmentName("ПТО"));
        logger.info("Id: {}", departmentDao.findDepartmentIdByDepartmentName("Цех"));
        logger.info("Id: {}", departmentDao.findDepartmentIdByDepartmentName("Ремонт"));

        // Delete all departments and check that all departments were deleted
        departmentDao.deleteAllDepartments();
        List<Department> findEmptytList = departmentDao.findAllDepartments();
        Assert.assertTrue(findEmptytList.isEmpty());

        // Try to insert duplicate entry
        departmentDao.addDepartment(new Department("Маркетинг"));
        departmentDao.addDepartment(new Department("Маркетинг"));

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testInsertNullDepartmentName() throws Exception {
        // Try to insert NULL department
        Department insertDepartment = new Department(null);
        departmentDao.addDepartment(insertDepartment);

    }


    @Test(expected = DataIntegrityViolationException.class)
    public void testInsertDepartmentNameMuchChar() throws Exception {
        // Try to insert department with length of departmentName over 60 chars
        departmentDao.addDepartment(new Department("1234567890asdfghjkl;'" +
                "zxcvbnm,./.,mnbvcxz';lkjhgfdsa][poiuytrewq" +
                "0987654321"));
    }


}