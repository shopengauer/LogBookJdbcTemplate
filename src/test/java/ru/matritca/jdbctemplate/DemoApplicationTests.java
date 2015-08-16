package ru.matritca.jdbctemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.matritca.jdbctemplate.repository.LogbookUserImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@WebAppConfiguration
public class DemoApplicationTests {


	@Autowired
	private LogbookUserImpl logbookUser;

	@Test
	public void sequenceTest() {

		long currval = logbookUser.getCurrVal();
		System.out.println(currval);
		long nextval = logbookUser.getNextVal();
		System.out.println(nextval);

	}

}
