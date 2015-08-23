package ru.matritca.jdbctemplate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.matritca.jdbctemplate.domain.users.Department;
import ru.matritca.jdbctemplate.domain.users.Jobtitle;
import ru.matritca.jdbctemplate.repository.users.jobtitle.JdbcJobtitleDao;
import ru.matritca.jdbctemplate.repository.users.jobtitle.JobtitleDao;

import java.util.List;

/**
 * Created by Vasiliy on 23.08.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class JobtitleTests {

    private Logger logger = LoggerFactory.getLogger(JobtitleTests.class);

    @Autowired
    private JobtitleDao jdbcJobtitleDao;

    private List<Jobtitle> insertJobtitleList;

    @Before
    public void setUp() throws Exception {



    }

    @Test
    public void testName() throws Exception {



    }
}
