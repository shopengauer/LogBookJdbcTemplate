package ru.matritca.jdbctemplate;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.matritca.jdbctemplate.domain.users.LogbookUserRoleEnum;
import ru.matritca.jdbctemplate.repository.users.jobtitle.JobtitleDao;
import ru.matritca.jdbctemplate.repository.users.logbookuser.LogbookUsersDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class DemoApplicationTests {

	private Logger logger = LoggerFactory.getLogger(DemoApplicationTests.class);

	@Autowired
	private LogbookUsersDao logbookUser;
	@Autowired
	private JobtitleDao jobtitleDao;



	@Test
	public void testLogbookUserRoles() throws Exception {
  		Assert.assertEquals(LogbookUserRoleEnum.ADMINISTRATOR, LogbookUserRoleEnum.getLogbookUserRole(0));
		Assert.assertEquals(LogbookUserRoleEnum.WRITER, LogbookUserRoleEnum.getLogbookUserRole(1));
		Assert.assertEquals(LogbookUserRoleEnum.READER, LogbookUserRoleEnum.getLogbookUserRole(2));

        Assert.assertEquals(LogbookUserRoleEnum.getLabel(LogbookUserRoleEnum.ADMINISTRATOR), "Администратор");
        Assert.assertEquals(LogbookUserRoleEnum.getLabel(LogbookUserRoleEnum.WRITER), "Редактор");
        Assert.assertEquals(LogbookUserRoleEnum.getLabel(LogbookUserRoleEnum.READER), "Читатель");

		Assert.assertEquals(LogbookUserRoleEnum.ADMINISTRATOR.getIntValue(),0);
		Assert.assertEquals(LogbookUserRoleEnum.WRITER.getIntValue(),1);
		Assert.assertEquals(LogbookUserRoleEnum.READER.getIntValue(),2);

	}

	@Test
	public void sequenceTest() {

//		long currval = logbookUser.getCurrVal();
//		logger.warn("Current sequence value {}", currval);
		// long nextval = logbookUser.getNextVal();

		//logger.warn("Next sequence value {}",nextval);
		//System.out.println(nextval);

	}

	@Test
	public void insertJobtitleTest() throws Exception {

//		Assert.assertNotNull(jobtitleDao);
//		Jobtitle jobtitle1 = new Jobtitle();
//		jobtitle1.setJobtitleName("Начальник ПТО");
//		Jobtitle j = jobtitleDao.create(jobtitle1);
//        logger.info("Total objects inserted {} ",j.getId());
	}
}
