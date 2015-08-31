package ru.matritca.jdbctemplate.dictinary.department;


import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import ru.matritca.jdbctemplate.DemoApplication;
import ru.matritca.jdbctemplate.domain.users.Department;
import ru.matritca.jdbctemplate.repository.users.department.DepartmentDao;
import ru.matritca.jdbctemplate.repository.users.department.JdbcDepartmentDao;

import java.util.*;

/**
 * Created by Vasiliy on 31.08.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
@Transactional
//@Ignore
public class DepartmentTests2 {

    private Logger logger = LoggerFactory.getLogger(DepartmentTests.class);

    private static Department departmentForInsert1;
    private static Department departmentForInsert2;
    private static List<Department> insertDepartmentList;
    private static Set<Department> insertDepartmentSet;
    private static Set<Department> insertDuplicateDepartmentSet;

    @Autowired
    private DepartmentDao jdbcDepartmentDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeClass
    public static void setUpClass() throws Exception {
        departmentForInsert1 = new Department("ПТО");
        departmentForInsert2 = new Department("ОРПО");

        Department[] departmentsArray = {new Department("ПТО"),
                new Department("Цех"),new Department("Ремонт"),
                new Department("Администрация"),new Department("Бухгалтерия"),
                new Department("Охрана"),new Department("АСУ"),
                new Department("ОТК"),new Department("ПЭО")};

        Department[] duplicateDepartmentsArray = {new Department("ПТО"),
                new Department("Цех"),new Department("Ремонт"),
                new Department("Администрация"),new Department("Бухгалтерия"),
                new Department("Охрана"),new Department("АСУ"),
                new Department("ОТК"),new Department("ПЭО"),
                new Department("ОТК"),new Department("ПЭО")};

        insertDepartmentList =  new ArrayList<>(Arrays.asList(departmentsArray));
        insertDepartmentSet = new HashSet<>(Arrays.asList(departmentsArray));
        insertDuplicateDepartmentSet = new HashSet<>(Arrays.asList(duplicateDepartmentsArray));

    }

    @Before
    public void setUp() throws Exception {
        Assert.assertNotNull(jdbcDepartmentDao);
        Assert.assertNotNull(jdbcTemplate);
   }

    @Test
    @Rollback
    public void testAddDepartment() throws Exception {
       //Проверяем что в таблице нет записей
       Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
       // Добавляем отдел ПТО
       jdbcDepartmentDao.addDepartment(departmentForInsert1);
        //Проверяем что в таблице 1 запись
       Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
        // Добавляем отдел ОРПО
       jdbcDepartmentDao.addDepartment(departmentForInsert2);
        //Проверяем что в таблице 2 записи
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));

        // Находим отделы с заданными именами
        Department findDepartment1 = jdbcDepartmentDao.findDepartmentByName(departmentForInsert1.getDepartmentName()).get(0);
        Department findDepartment2 = jdbcDepartmentDao.findDepartmentByName(departmentForInsert2.getDepartmentName()).get(0);
        // И убеждаемся что имена совпадают
        Assert.assertEquals(departmentForInsert1.getDepartmentName(),findDepartment1.getDepartmentName());
        Assert.assertEquals(departmentForInsert2.getDepartmentName(),findDepartment2.getDepartmentName());
    }

    @Test
    @Rollback
    public void testUpdateDepartment() throws Exception {
        //Проверяем что в таблице нет записей
        Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
        // Добавляем отдел ПТО
        jdbcDepartmentDao.addDepartment(departmentForInsert1);
        //Проверяем что в таблице 1 запись
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));

        // Добавляем отдел ОРПО
        jdbcDepartmentDao.addDepartment(departmentForInsert2);
        //Проверяем что в таблице 2 записи
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));

        // Находим добавленный отдел
        Assert.assertFalse(jdbcDepartmentDao.findDepartmentByName(departmentForInsert1.getDepartmentName()).isEmpty());
        Department findDepartment = jdbcDepartmentDao.findDepartmentByName(departmentForInsert1.getDepartmentName()).get(0);
        // меняем название
        findDepartment.setDepartmentName("ОТК");
        // меняем имя отдела в таблице. flag: 1- отдел заменен 0 - отдел стаким именем уже существует
        int flag = jdbcDepartmentDao.updateDepartment(findDepartment);
        // проверяем что флаг равен единице и отдел заменен
        Assert.assertEquals(1, flag);
        // и что в таблице присутствует две записи
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));

        // попробуем поменять отдел на отдел с именем которое уже присутствует в таблице
        findDepartment.setDepartmentName("ОРПО");
        int flagExists = jdbcDepartmentDao.updateDepartment(findDepartment);
        // проверяем что флаг равен нулю
        Assert.assertEquals(0, flagExists);
        // и что в таблице присутствует две запись
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
    }

    @Test
    @Rollback
    public void testAddDepartmentIfNotExists() throws Exception {
        //Проверяем что в таблице нет записей
        Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
        // Добавляем отдел ПТО
        jdbcDepartmentDao.addDepartment(departmentForInsert1);
        //Проверяем что в таблице 1 запись
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));

        int flagExists = jdbcDepartmentDao.addDepartmentIfNotExists(departmentForInsert1);
        Assert.assertEquals(0, flagExists);
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));

        int flagNotExists = jdbcDepartmentDao.addDepartmentIfNotExists(departmentForInsert2);
        Assert.assertEquals(1, flagNotExists);
        Assert.assertEquals(2, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));

    }

    @Test
    @Rollback
    public void testIsDepartmentExists() throws Exception {

        //Проверяем что в таблице нет записей
        Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
        // Добавляем отдел ПТО
        jdbcDepartmentDao.addDepartment(departmentForInsert1);
        //Проверяем что в таблице 1 запись
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
         // Проверяем что отдел который мы добавили существует
        Assert.assertTrue(jdbcDepartmentDao.isDepartmentExists(departmentForInsert1));
        //// Проверяем что отдел который мы не добавили не существует
        Assert.assertFalse(jdbcDepartmentDao.isDepartmentExists(departmentForInsert2));

    }

    @Test
    @Rollback
    public void testAddSetOfDepartment() throws Exception {
        //Проверяем что в таблице нет записей
        Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
        // Добавляем Set отделов из девяти штук
        int[] count = jdbcDepartmentDao.addSetOfDepartment(insertDepartmentSet);
        // проверяем что метод вернул массив с информацией о том что добавлено 9 отделов
        Assert.assertEquals(count.length,9);
        // проверяем что в таблице 9 записей
        Assert.assertEquals(9, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));

    }


    @Test
    @Rollback
    public void testAddDuplicateSetOfDepartment() throws Exception {
        //Проверяем что в таблице нет записей
         Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
        // проверяем что не добавилось отделов с одинаковыми именами в Set отделов
        Assert.assertEquals(insertDuplicateDepartmentSet.size(),9);

        for (Department department : insertDuplicateDepartmentSet) {
            System.out.println(department.getDepartmentName());
        }
        // сохраняем отделы в базу
         int[] count = jdbcDepartmentDao.addSetOfDepartment(insertDuplicateDepartmentSet);
        // проверяем что метод вернул массив с информацией о том что добавлено 9 отделов
        Assert.assertEquals(count.length,9);
        // проверяем что в таблице 9 записей
        Assert.assertEquals(9,JdbcTestUtils.countRowsInTable(jdbcTemplate,"USERS.DEPARTMENT"));
    }

    @Test
    @Rollback
    public void testFindDepartmentByName() throws Exception {

        //Проверяем что в таблице нет записей
        Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
        // добавляем отдел и проверяем что он добавлен
        Assert.assertEquals(1, jdbcDepartmentDao.addDepartmentIfNotExists(departmentForInsert1));
        // Находим отдел по имени и сравниваем имена добавленного отдела и отдела который мы нашли
        Assert.assertEquals(departmentForInsert1.getDepartmentName(), jdbcDepartmentDao.findDepartmentByName(departmentForInsert1.getDepartmentName()).get(0).getDepartmentName());
     }

    @Test
    public void testFindDepartmentById() throws Exception {
        //Проверяем что в таблице нет записей
        Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
        // добавляем отдел и проверяем что он добавлен
        Assert.assertEquals(1, jdbcDepartmentDao.addDepartmentIfNotExists(departmentForInsert1));
        // Находим отдел по имени
        Department findDepartment = jdbcDepartmentDao.findDepartmentByName(departmentForInsert1.getDepartmentName()).get(0);

        // И далее находим этот же отдел по id
        Assert.assertFalse(jdbcDepartmentDao.findDepartmentById(findDepartment.getId()).isEmpty());
    }

    @Test
    public void testFindAllDepartments() throws Exception {
        //Проверяем что в таблице нет записей
        Assert.assertEquals(0, JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
        // проверяем что метод findAllDepartments() работает также как и  JdbcTestUtils.countRowsInTable()
        Assert.assertEquals(jdbcDepartmentDao.findAllDepartments().size(), JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));

        // Добавляем Set отделов из девяти штук
        int[] count = jdbcDepartmentDao.addSetOfDepartment(insertDepartmentSet);
        // проверяем что метод вернул массив с информацией о том что добавлено 9 отделов
        Assert.assertEquals(count.length,9);
        // проверяем что в таблице 9 записей
        Assert.assertEquals(jdbcDepartmentDao.findAllDepartments().size(), JdbcTestUtils.countRowsInTable(jdbcTemplate, "USERS.DEPARTMENT"));
    }




}
