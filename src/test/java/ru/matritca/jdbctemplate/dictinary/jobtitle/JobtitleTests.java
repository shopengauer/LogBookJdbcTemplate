package ru.matritca.jdbctemplate.dictinary.jobtitle;

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
import ru.matritca.jdbctemplate.domain.users.Jobtitle;
import ru.matritca.jdbctemplate.repository.users.jobtitle.JobtitleDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vasiliy on 23.08.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
@Ignore
public class JobtitleTests {

    private Logger logger = LoggerFactory.getLogger(JobtitleTests.class);

    @Autowired
    private JobtitleDao jdbcJobtitleDao;

    private List<Jobtitle> insertJobtitleList;
    private Jobtitle jobtitleForInsert1;
    private Jobtitle jobtitleForInsert2;



    @Before
    public void setUp() throws Exception {

      Jobtitle[] jobtitles = {new Jobtitle("Генеральный директор"),new Jobtitle("Начальник цеха"),
                              new Jobtitle("Начальник ПТО"),new Jobtitle("Главный инженер"),
                              new Jobtitle("Главный технолог"),new Jobtitle("Начальник ОТК"),
                              new Jobtitle("Слесарь-сборщик"),new Jobtitle("Электромонтер"),
                              new Jobtitle("Главный бухгалтер"),new Jobtitle("Архитектор ПО")
      };

      insertJobtitleList = new ArrayList<>(Arrays.asList(jobtitles));

      jobtitleForInsert1 = new Jobtitle("Охранник");
      jobtitleForInsert2 = new Jobtitle("Уборщик");

    }

    @Test
    public void jobtitleInsertTest() throws Exception {

        Assert.assertNotNull(jdbcJobtitleDao);
        jdbcJobtitleDao.deleteAllJobtitles();
        Assert.assertTrue(jdbcJobtitleDao.findAllJobtitles().isEmpty());

        // Check that job titles does not equals null
        Assert.assertNotNull(jobtitleForInsert1);
        Assert.assertNotNull(jobtitleForInsert2);

        // Insert job titles to database
        jdbcJobtitleDao.addJobtitle(jobtitleForInsert1);
        jdbcJobtitleDao.addJobtitle(jobtitleForInsert2);

        // Find before mentioned job titles
        Jobtitle findJobtitleByName1 = jdbcJobtitleDao.findJobtitleByName(jobtitleForInsert1.getJobtitleName());
        Jobtitle findJobtitleByName2 = jdbcJobtitleDao.findJobtitleByName(jobtitleForInsert2.getJobtitleName());

        // Assert that selected job titles had equals inserted job titles
        Assert.assertEquals(jobtitleForInsert1, findJobtitleByName1);
        Assert.assertEquals(jobtitleForInsert2, findJobtitleByName2);

        long jobtitleId1 = jdbcJobtitleDao.findJobtitleIdByJobtitleName(jobtitleForInsert1.getJobtitleName());
        long jobtitleId2 = jdbcJobtitleDao.findJobtitleIdByJobtitleName(jobtitleForInsert2.getJobtitleName());
        Assert.assertEquals(jobtitleForInsert1,jdbcJobtitleDao.findJobtitleById(jobtitleId1));
        Assert.assertEquals(jobtitleForInsert2,jdbcJobtitleDao.findJobtitleById(jobtitleId2));

        Assert.assertTrue(jdbcJobtitleDao.isJobtitleExists(jobtitleForInsert1.getJobtitleName()));
        Assert.assertTrue(jdbcJobtitleDao.isJobtitleExists(jobtitleForInsert2.getJobtitleName()));
        Assert.assertFalse(jdbcJobtitleDao.isJobtitleExists("Муравьед"));
        Assert.assertFalse(jdbcJobtitleDao.isJobtitleExists("Кукун"));


        // Delete this job title
        jdbcJobtitleDao.deleteJobtitleByJobtitleName(jobtitleForInsert1.getJobtitleName());
        jdbcJobtitleDao.deleteJobtitleByJobtitleName(jobtitleForInsert2.getJobtitleName());

        // Find all job titles and check that job titles were deleted
        List<Jobtitle> jobtitleList = jdbcJobtitleDao.findAllJobtitles();
        Assert.assertEquals(0, jobtitleList.size());

        // Insert list of job titles and check that 10 job titles were inserted
        jdbcJobtitleDao.addListOfJobtitle(insertJobtitleList);
        List<Jobtitle> findJobtitleList = jdbcJobtitleDao.findAllJobtitles();
        Assert.assertEquals(10, findJobtitleList.size());

        // Delete all job titles and check that all job titles were deleted
        jdbcJobtitleDao.deleteAllJobtitles();
        List<Jobtitle> findEmptytList = jdbcJobtitleDao.findAllJobtitles();
        Assert.assertTrue(findEmptytList.isEmpty());


        // Проверяем метод добавления должности с проверкой на ее существование
        Jobtitle jobNotExists = new Jobtitle("Архитектор ПО");
        Assert.assertEquals(1,jdbcJobtitleDao.addJobtitleIfNotExists(jobNotExists));
        Assert.assertEquals(0,jdbcJobtitleDao.addJobtitleIfNotExists(jobNotExists));



    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testInsertNullJobtitleName() throws Exception {
        Assert.assertNotNull(jdbcJobtitleDao);
        jdbcJobtitleDao.deleteAllJobtitles();
        Assert.assertTrue(jdbcJobtitleDao.findAllJobtitles().isEmpty());

        // Try to insert NULL jobtitle
        Jobtitle insertJobtitle = new Jobtitle(null);
        jdbcJobtitleDao.addJobtitle(insertJobtitle);

    }


    @Test(expected = DataIntegrityViolationException.class)
    public void testInsertJobtitleNameMuchChar() throws Exception {

        Assert.assertNotNull(jdbcJobtitleDao);
        jdbcJobtitleDao.deleteAllJobtitles();
        Assert.assertTrue(jdbcJobtitleDao.findAllJobtitles().isEmpty());
        // Try to insert department with length of departmentName over 60 chars
        jdbcJobtitleDao.addJobtitle(new Jobtitle("1234567890asdfghjkl;'" +
                "zxcvbnm,./.,mnbvcxz';lkjhgfdsa][poiuytrewq" +
                "0987654321"));
    }


    @Test(expected = DuplicateKeyException.class)
    public void testDuplicateEntry() throws Exception {
        Assert.assertNotNull(jdbcJobtitleDao);
        jdbcJobtitleDao.deleteAllJobtitles();
        Assert.assertTrue(jdbcJobtitleDao.findAllJobtitles().isEmpty());

        // Try to insert duplicate entry
        jdbcJobtitleDao.addJobtitle(new Jobtitle("Заведующий складом"));
        jdbcJobtitleDao.addJobtitle(new Jobtitle("Заведующий складом"));

    }

    @After
    public void tearDown() throws Exception {
        Assert.assertNotNull(jdbcJobtitleDao);
        jdbcJobtitleDao.deleteAllJobtitles();
        Assert.assertTrue(jdbcJobtitleDao.findAllJobtitles().isEmpty());

    }
}



