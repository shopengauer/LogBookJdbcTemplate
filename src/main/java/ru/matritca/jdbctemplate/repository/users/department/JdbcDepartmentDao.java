package ru.matritca.jdbctemplate.repository.users.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;
import ru.matritca.jdbctemplate.domain.users.Department;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Vasiliy on 17.08.2015.
 */
@Repository
public class JdbcDepartmentDao implements DepartmentDao {


    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcTemplate;

    private static final class DepartmentMapper implements RowMapper<Department>{
        @Override
        public Department mapRow(ResultSet resultSet, int i) throws SQLException {
            Department department = new Department();
            department.setId(resultSet.getLong("DEPARTMENT_ID"));
            department.setDepartmentName(resultSet.getString("DEPARTMENT_NAME"));
            return department;
        }
    }


    @Override
    public int addDepartment(Department department) {
        String sql = "INSERT INTO USERS.DEPARTMENT (DEPARTMENT_ID,DEPARTMENT_NAME) VALUES (NEXTVAL('USERS_SEQUENCE'),:departmentName)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("departmentName",department.getDepartmentName());
        return namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public Department findDepartmentByName(String departmentName) {
        String sql = "SELECT * FROM USERS.DEPARTMENT WHERE DEPARTMENT_NAME=:departmentName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("departmentName",departmentName);
        return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, new DepartmentMapper());
    }

    @Override
    public Department findDepartmentById(long id) {
        String sql = "SELECT * FROM USERS.DEPARTMENT WHERE DEPARTMENT_ID = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id",id);
        return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, new DepartmentMapper());
    }


    @Override
    public int[] addListOfDepartment(final List<Department> departmentList) {
        String sql = "insert into USERS.DEPARTMENT (DEPARTMENT_ID,DEPARTMENT_NAME) values (NEXTVAL('USERS_SEQUENCE'),:departmentName)";
        SqlParameterSource[] parameterSource = SqlParameterSourceUtils.createBatch(departmentList.toArray());
        return namedParameterJdbcTemplate.batchUpdate(sql, parameterSource);
    }

    @Override
    public List<Department> findAllDepartments() {
        String sql = "select * from USERS.DEPARTMENT";
        return namedParameterJdbcTemplate.query(sql, new DepartmentMapper());
    }

    @Override
    public int deleteDepartmentByDepartmentName(String departmentName) {
        String sql = "DELETE FROM USERS.DEPARTMENT WHERE DEPARTMENT_NAME = :departmentName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("departmentName",departmentName);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }


    @Override
    public int findDepartmentIdByDepartmentName(String departmentName) {
        String sql = "select department_id from users.department where department_name = :departmentName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("departmentName",departmentName);
        namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class);
        return namedParameterJdbcTemplate.queryForObject(sql, parameterSource, Integer.class);
    }

    @Override
    public void deleteAllDepartments() {
        String sql = "delete from users.department";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sql);
      }
}
