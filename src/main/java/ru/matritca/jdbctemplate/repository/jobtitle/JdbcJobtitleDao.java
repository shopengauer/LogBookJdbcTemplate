package ru.matritca.jdbctemplate.repository.jobtitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.matritca.jdbctemplate.domain.users.Jobtitle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vasiliy on 17.08.2015.
 */
@Repository
public class JdbcJobtitleDao implements JobtitleDao {


    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    private static final class JobtitleMapper implements RowMapper<Jobtitle>{
        @Override
        public Jobtitle mapRow(ResultSet resultSet, int i) throws SQLException {
            Jobtitle jobtitle = new Jobtitle();
            jobtitle.setId(resultSet.getLong("JOBTITLE_ID"));
            jobtitle.setJobtitleName(resultSet.getString("JOBTITLE_NAME"));
            return jobtitle;
        }
    }


    @Override
    public int addJobtitle(Jobtitle jobtitle) {


        return 0;
    }

    @Override
    public int[] addListOfJobtitle(List<Jobtitle> jobtitleList) {
        return new int[0];
    }

    @Override
    public Jobtitle findJobtitleByName(String JobtitleName) {
        return null;
    }

    @Override
    public Jobtitle findJobtitleById(long id) {
        return null;
    }

    @Override
    public List<Jobtitle> findAllJobtitles() {
        return null;
    }

    @Override
    public int deleteJobtitleByJobtitleName(String jobtitleName) {
        return 0;
    }

    @Override
    public int findJobtitleIdByJobtitleName(String jobtitleName) {
        return 0;
    }
}
