package ru.matritca.jdbctemplate.dictinary.organization;

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
import ru.matritca.jdbctemplate.domain.users.Jobtitle;
import ru.matritca.jdbctemplate.domain.users.Organization;
import ru.matritca.jdbctemplate.repository.users.organization.OrganizationDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vasiliy on 24.08.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
@Ignore
public class OrganizationTests {


    private Logger logger = LoggerFactory.getLogger(OrganizationTests.class);



    private List<Organization> organizationList;
    private Organization organizationForInsert1;
    private Organization organizationForInsert2;

    @Autowired
    private OrganizationDao jdbcOrganizationDao;


    @Before
    public void setUp() throws Exception {

        Organization[] organizations =
                {new Organization("Тестовая организация 1","Тестовое описание 1"),
                        new Organization("Тестовая организация 2","Тестовое описание 2"),
                        new Organization("Тестовая организация 3","Тестовое описание 3"),
                        new Organization("Тестовая организация 4","Тестовое описание 4"),
                        new Organization("Тестовая организация 5","Тестовое описание 5"),
                        new Organization("Тестовая организация 6 без описания"),
                        new Organization("Тестовая организация 7 без описания"),
                        new Organization("Тестовая организация 8 без описания")
                };

        organizationList = new ArrayList<>(Arrays.asList(organizations));

        organizationForInsert1 = new Organization("ООО 'Матрица'","Тестовая запись");
        organizationForInsert2 = new Organization("ЗАО 'БЭЛС'","Тестовая запись");

    }

    @Test
    public void logbookUserRoleInsertTest() throws Exception {

        Assert.assertNotNull(jdbcOrganizationDao);

        // Clear the table
        jdbcOrganizationDao.deleteAllOrganizations();
        Assert.assertEquals(0,jdbcOrganizationDao.findAllOrganizations().size());

        // Check that organizations does not equals null
        Assert.assertNotNull(organizationForInsert1);
        Assert.assertNotNull(organizationForInsert2);

        // Insert organizations to database
        jdbcOrganizationDao.addOrganization(organizationForInsert1);
        jdbcOrganizationDao.addOrganization(organizationForInsert2);

        // Find before mentioned organizations
        Organization findOrganizationForInsert1 = jdbcOrganizationDao.findOrganizationByName(organizationForInsert1.getOrganizationName());
        Organization findOrganizationForInsert2 = jdbcOrganizationDao.findOrganizationByName(organizationForInsert2.getOrganizationName());

        // Assert that selected organizations had equals inserted organizations
        Assert.assertEquals(organizationForInsert1.getOrganizationName(), findOrganizationForInsert1.getOrganizationName());
        Assert.assertEquals(organizationForInsert2.getOrganizationName(), findOrganizationForInsert2.getOrganizationName());
        Assert.assertEquals(organizationForInsert1.getOrganizationDesc(), findOrganizationForInsert1.getOrganizationDesc());
        Assert.assertEquals(organizationForInsert2.getOrganizationDesc(), findOrganizationForInsert2.getOrganizationDesc());

        long organizationId1 = jdbcOrganizationDao.findOrganizationIdByOrganizationName(organizationForInsert1.getOrganizationName());
        long organizationId2 = jdbcOrganizationDao.findOrganizationIdByOrganizationName(organizationForInsert2.getOrganizationName());
        Assert.assertEquals(organizationForInsert1.getOrganizationName(), jdbcOrganizationDao.findOrganizationById(organizationId1).getOrganizationName());
        Assert.assertEquals(organizationForInsert2.getOrganizationName(), jdbcOrganizationDao.findOrganizationById(organizationId2).getOrganizationName());

        Assert.assertTrue(jdbcOrganizationDao.isOrganizationExists(organizationForInsert1.getOrganizationName()));
        Assert.assertTrue(jdbcOrganizationDao.isOrganizationExists(organizationForInsert2.getOrganizationName()));
        Assert.assertFalse(jdbcOrganizationDao.isOrganizationExists("НИИЧАВО"));
        Assert.assertFalse(jdbcOrganizationDao.isOrganizationExists("СВЯЗЬТЕЛ"));

        // Delete this organizations
        jdbcOrganizationDao.deleteOrganizationByOrganizationName(organizationForInsert1.getOrganizationName());
        jdbcOrganizationDao.deleteOrganizationByOrganizationName(organizationForInsert2.getOrganizationName());

//        // Find all Organizations and check that Organizations were deleted
        List<Organization> organizationList = jdbcOrganizationDao.findAllOrganizations();
        Assert.assertEquals(0, organizationList.size());

//        // Insert list of Organizations and check that 8 departments were inserted
        jdbcOrganizationDao.addListOfOrganization(this.organizationList);
        List<Organization> findOrganizationList = jdbcOrganizationDao.findAllOrganizations();
        Assert.assertEquals(8, findOrganizationList.size());

       // Delete all Organizations and check that all Organizations were deleted
        jdbcOrganizationDao.deleteAllOrganizations();
        List<Organization> findEmptyList = jdbcOrganizationDao.findAllOrganizations();
        Assert.assertEquals(0, findEmptyList.size());

        // Проверяем метод добавления организации с проверкой на ее существование
        Organization organizationNotExists = new Organization("Google");
        Assert.assertEquals(1,jdbcOrganizationDao.addOrganizationIfNotExists(organizationNotExists));
        Assert.assertEquals(0,jdbcOrganizationDao.addOrganizationIfNotExists(organizationNotExists));



    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testInsertNullJobtitleName() throws Exception {

        Assert.assertNotNull(jdbcOrganizationDao);
        jdbcOrganizationDao.deleteAllOrganizations();
        Assert.assertEquals(0, jdbcOrganizationDao.findAllOrganizations().size());

        // Try to insert NULL jobtitle
        Organization insertOrganization = new Organization(null,"Тестовое описание");
        jdbcOrganizationDao.addOrganization(insertOrganization);

    }


    @Test(expected = DataIntegrityViolationException.class)
    public void testInsertJobtitleNameMuchChar() throws Exception {
        Assert.assertNotNull(jdbcOrganizationDao);
        jdbcOrganizationDao.deleteAllOrganizations();
        Assert.assertEquals(0, jdbcOrganizationDao.findAllOrganizations().size());


        // Try to insert Organization with length of organizationName over 60 chars
        jdbcOrganizationDao.addOrganization(new Organization("1234567890asdfghjkl;'" +
                "zxcvbnm,./.,mnbvcxz';lkjhgfdsa][poiuytrewq" +
                "0987654321"));
    }


    @Test(expected = DuplicateKeyException.class)
    public void testDuplicateEntry() throws Exception {
        Assert.assertNotNull(jdbcOrganizationDao);
        jdbcOrganizationDao.deleteAllOrganizations();
        Assert.assertEquals(0, jdbcOrganizationDao.findAllOrganizations().size());

        // Try to insert duplicate entry
        jdbcOrganizationDao.addOrganization(new Organization("Тестовая организация", "Тестовое описание организации"));
        jdbcOrganizationDao.addOrganization(new Organization("Тестовая организация"));

    }

    @After
    public void tearDown() throws Exception {

        jdbcOrganizationDao.deleteAllOrganizations();
        Assert.assertEquals(0, jdbcOrganizationDao.findAllOrganizations().size());


    }
}
