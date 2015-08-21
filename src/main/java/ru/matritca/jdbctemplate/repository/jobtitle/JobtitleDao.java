package ru.matritca.jdbctemplate.repository.jobtitle;

import ru.matritca.jdbctemplate.domain.Department;
import ru.matritca.jdbctemplate.domain.Jobtitle;

import java.util.List;

/**
 * Created by Vasiliy on 17.08.2015.
 */
public interface JobtitleDao {

    int addJobtitle(Jobtitle jobtitle);
    int[] addListOfJobtitle(List<Jobtitle> jobtitleList);
    Jobtitle findJobtitleByName(String JobtitleName);
    Jobtitle findJobtitleById(long id);
    List<Jobtitle> findAllJobtitles();
    int deleteJobtitleByJobtitleName(String jobtitleName);
    int findJobtitleIdByJobtitleName(String jobtitleName);


}
