package ru.matritca.jdbctemplate.repository.users.jobtitle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.matritca.jdbctemplate.domain.users.Department;
import ru.matritca.jdbctemplate.domain.users.Jobtitle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
        String sql = "INSERT INTO USERS.JOBTITLE (JOBTITLE_ID,JOBTITLE_NAME) VALUES(NEXTVAL('USERS_SEQUENCE'),:jobTitleName)";
        SqlParameterSource parameterSource = new MapSqlParameterSource("jobTitleName", jobtitle.getJobtitleName());
        return namedParameterJdbcTemplate.update(sql,parameterSource);
    }

    @Override
    @Transactional
    public int addJobtitleIfNotExists(Jobtitle jobtitle) {
       if(this.isJobtitleExists(jobtitle.getJobtitleName()))
        return 0;
       return this.addJobtitle(jobtitle);
    }

    @Override
    public boolean isJobtitleExists(String jobtitleName) {
        String sql = "SELECT JOBTITLE_NAME FROM USERS.JOBTITLE WHERE JOBTITLE_NAME = :jobtitleName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("jobtitleName",jobtitleName);
        return !namedParameterJdbcTemplate.queryForList(sql,parameterSource,String.class).isEmpty();


    }

    @Override
    public int[] addListOfJobtitle(List<Jobtitle> jobtitleList) {

        String sql = "INSERT INTO USERS.JOBTITLE (JOBTITLE_ID,JOBTITLE_NAME) VALUES (NEXTVAL('USERS_SEQUENCE'),:jobtitleName)";
        SqlParameterSource[] parameterSource = SqlParameterSourceUtils.createBatch(jobtitleList.toArray());
        return namedParameterJdbcTemplate.batchUpdate(sql,parameterSource);
    }

    @Override
    public Jobtitle findJobtitleByName(String jobtitleName) {
        String sql = "SELECT * FROM USERS.JOBTITLE WHERE JOBTITLE_NAME = :jobtitleName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("jobtitleName",jobtitleName);
        return namedParameterJdbcTemplate.queryForObject(sql,parameterSource,new JobtitleMapper());
    }

    @Override
    public Jobtitle findJobtitleById(long id) {
        String sql = "SELECT * FROM USERS.JOBTITLE WHERE JOBTITLE_ID = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id",id);
        return namedParameterJdbcTemplate.queryForObject(sql,parameterSource,new JobtitleMapper());
    }

    @Override
    public List<Jobtitle> findAllJobtitles() {
        String sql = "SELECT * FROM USERS.JOBTITLE";
        return namedParameterJdbcTemplate.query(sql,new JobtitleMapper());
    }

    @Override
    public int deleteJobtitleByJobtitleName(String jobtitleName) {
        String sql = "DELETE FROM USERS.JOBTITLE WHERE JOBTITLE_NAME = :jobtitleName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("jobtitleName",jobtitleName);
        return namedParameterJdbcTemplate.update(sql,parameterSource);
    }

    @Override
    public long findJobtitleIdByJobtitleName(String jobtitleName) {
        String sql = "select jobtitle_id from users.jobtitle where jobtitle_name = :jobtitleName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("jobtitleName",jobtitleName);
        return namedParameterJdbcTemplate.queryForObject(sql,parameterSource,Long.class);
    }


    @Override
    public void deleteAllJobtitles() {
        String sql = "delete from users.jobtitle";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sql);

    }
}
