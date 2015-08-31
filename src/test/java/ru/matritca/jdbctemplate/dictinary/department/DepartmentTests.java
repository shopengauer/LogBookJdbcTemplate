package ru.matritca.jdbctemplate.dictinary.department;

import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.matritca.jdbctemplate.DemoApplication;
import ru.matritca.jdbctemplate.domain.users.Department;
import ru.matritca.jdbctemplate.repository.users.department.DepartmentDao;

import java.util.*;

/**
 * Created by Vasiliy on 19.08.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
@Ignore
public class DepartmentTests {

    private Logger logger = LoggerFactory.getLogger(DepartmentTests.class);

    @Autowired
    private DepartmentDao departmentDao;


    private Department departmentForInsert1;
    private Department departmentForInsert2;
    private Department departmentForUpdate1;
    private Department departmentForUpdate2;

    private List<Department> insertDepartmentList;
    private Set<Department> insertDepartmentSet;



    @Before
    public void setUp() throws Exception {

       Department[] departmentsArray = {new Department("ПТО"),
               new Department("Цех"),new Department("Ремонт"),
               new Department("Администрация"),new Department("Бухгалтерия"),
               new Department("Охрана"),new Department("АСУ"),
               new Department("ОТК"),new Department("ПЭО")};

        insertDepartmentList =  new ArrayList<>(Arrays.asList(departmentsArray));
        insertDepartmentSet = new HashSet<>(Arrays.asList(departmentsArray));

        departmentForInsert1 = new Department("Отдел программной разработки");
        departmentForInsert2 = new Department("Отдел разработки печатных плат");
        departmentForUpdate1 = new Department("Отдел программной архитектуры");
        departmentForUpdate2 = new Department("Отдел SQL");

    }

    @Test
    public void departmentInsertTest() throws Exception {


        Assert.assertNotNull(departmentDao);
        departmentDao.deleteAllDepartments();
        Assert.assertEquals(0, departmentDao.findAllDepartments().size());

        // Check that departments does not equals null
        Assert.assertNotNull(departmentForInsert1);
        Assert.assertNotNull(departmentForInsert2);

        // Insert departments to database
        departmentDao.addDepartment(departmentForInsert1);
        departmentDao.addDepartment(departmentForInsert2);

        // Find before mentioned departments
        List<Department> findDepartmentByName1 = departmentDao.findDepartmentByName(departmentForInsert1.getDepartmentName());
        List<Department> findDepartmentByName2 = departmentDao.findDepartmentByName(departmentForInsert2.getDepartmentName());

        // Assert that selected departments had equals inserted departments

        Assert.assertEquals(departmentForInsert1,findDepartmentByName1.get(0));
        Assert.assertEquals(departmentForInsert2,findDepartmentByName2.get(0));

         // Проверка метода поиска id отдела по его имени
        long departmentId1 = departmentDao.findDepartmentIdByDepartmentName(departmentForInsert1.getDepartmentName()).get(0);
        long departmentId2 = departmentDao.findDepartmentIdByDepartmentName(departmentForInsert2.getDepartmentName()).get(0);
        Assert.assertEquals(departmentForInsert1, departmentDao.findDepartmentById(departmentId1));
        Assert.assertEquals(departmentForInsert2, departmentDao.findDepartmentById(departmentId2));

        // проверка метода проверки на существовании в таблице записы с аналогичным именем
        Assert.assertTrue(departmentDao.isDepartmentExists(departmentForInsert1));
        Assert.assertTrue(departmentDao.isDepartmentExists(departmentForInsert2));
         Assert.assertFalse(departmentDao.isDepartmentExists(new Department("Бахча")));
         Assert.assertFalse(departmentDao.isDepartmentExists(new Department("Пупиду")));

        List<Department> departmentForUpdate1 = departmentDao.findDepartmentByName(departmentForInsert1.getDepartmentName());
        departmentForUpdate1.get(0).setDepartmentName(this.departmentForUpdate1.getDepartmentName());
        Assert.assertEquals(1,departmentDao.updateDepartment(departmentForUpdate1.get(0)));

        List<Department> departmentForUpdate2 = departmentDao.findDepartmentByName(departmentForInsert2.getDepartmentName());
        Assert.assertEquals(0,departmentDao.updateDepartment(departmentForUpdate2.get(0)));

        //Assert.assertEquals(this.departmentForUpdate1,departmentDao.);

        // Delete this department
        departmentDao.deleteDepartmentByDepartmentName(departmentForInsert1.getDepartmentName());
        departmentDao.deleteDepartmentByDepartmentName(departmentForInsert2.getDepartmentName());

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
        List<Department> findEmptyList = departmentDao.findAllDepartments();
        Assert.assertTrue(findEmptyList.isEmpty());

        // Проверяем метод добавления отдела с проверкой на его существование
        Department depNotExists = new Department("Отдел обеспечения и комплектации");
        Assert.assertEquals(1,departmentDao.addDepartmentIfNotExists(depNotExists));
        Assert.assertEquals(0,departmentDao.addDepartmentIfNotExists(depNotExists));


    }

    @Test(expected = DuplicateKeyException.class)
    public void testDuplicateEntry() throws Exception {
        Assert.assertNotNull(departmentDao);
        departmentDao.deleteAllDepartments();
        Assert.assertEquals(0, departmentDao.findAllDepartments().size());

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


    @After
    public void tearDown() throws Exception {

        Assert.assertNotNull(departmentDao);
        departmentDao.deleteAllDepartments();
        Assert.assertEquals(0, departmentDao.findAllDepartments().size());

    }
}