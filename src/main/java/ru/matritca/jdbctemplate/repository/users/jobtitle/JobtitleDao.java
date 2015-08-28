package ru.matritca.jdbctemplate.repository.users.jobtitle;

import ru.matritca.jdbctemplate.domain.users.Department;
import ru.matritca.jdbctemplate.domain.users.Jobtitle;

import java.util.List;

/**
 * Created by Vasiliy on 17.08.2015.
 */
public interface JobtitleDao {

    int addJobtitle(Jobtitle jobtitle);
    int addJobtitleIfNotExists(Jobtitle jobtitle);
    boolean isJobtitleExists(String jobtitleName);
    int[] addListOfJobtitle(List<Jobtitle> jobtitleList);
    Jobtitle findJobtitleByName(String jobtitleName);
    Jobtitle findJobtitleById(long id);
    List<Jobtitle> findAllJobtitles();
    int deleteJobtitleByJobtitleName(String jobtitleName);
    long findJobtitleIdByJobtitleName(String jobtitleName);
    void deleteAllJobtitles();

}
