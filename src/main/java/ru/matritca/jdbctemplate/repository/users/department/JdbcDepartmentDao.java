package ru.matritca.jdbctemplate.repository.users.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.matritca.jdbctemplate.domain.users.Department;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

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
    public int addDepartmentIfNotExists(Department department) {
       if(this.isDepartmentExists(department)){
         return 0;
       }
      return addDepartment(department);
    }

    @Override
    public List<Department> findDepartmentByName(String departmentName) {
        String sql = "SELECT * FROM USERS.DEPARTMENT WHERE DEPARTMENT_NAME=:departmentName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("departmentName",departmentName);
        return namedParameterJdbcTemplate.query(sql, parameterSource, new DepartmentMapper());
    }

    @Override
    public List<Department> findDepartmentById(long id) {
        String sql = "SELECT * FROM USERS.DEPARTMENT WHERE DEPARTMENT_ID = :id";
        SqlParameterSource parameterSource = new MapSqlParameterSource("id",id);
        return namedParameterJdbcTemplate.query(sql, parameterSource, new DepartmentMapper());
    }


    @Override
    public int[] addListOfDepartment(final List<Department> departmentList) {
        String sql = "INSERT INTO USERS.DEPARTMENT (DEPARTMENT_ID,DEPARTMENT_NAME) values (NEXTVAL('USERS_SEQUENCE'),:departmentName)";
        SqlParameterSource[] parameterSource = SqlParameterSourceUtils.createBatch(departmentList.toArray());
        return namedParameterJdbcTemplate.batchUpdate(sql, parameterSource);
    }

    @Override
    public int[] addSetOfDepartment(Set<Department> departmentSet) {
        String sql = "INSERT INTO USERS.DEPARTMENT (DEPARTMENT_ID,DEPARTMENT_NAME) values (NEXTVAL('USERS_SEQUENCE'),:departmentName)";
        SqlParameterSource[] parameterSource = SqlParameterSourceUtils.createBatch(departmentSet.toArray());
        return namedParameterJdbcTemplate.batchUpdate(sql, parameterSource);
    }

    @Override
    public List<Department> findAllDepartments() {
        String sql = "SELECT * FROM USERS.DEPARTMENT";
        return namedParameterJdbcTemplate.query(sql, new DepartmentMapper());
    }

    @Override
    public int deleteDepartmentByDepartmentName(String departmentName) {
        String sql = "DELETE FROM USERS.DEPARTMENT WHERE DEPARTMENT_NAME = :departmentName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("departmentName",departmentName);
        return namedParameterJdbcTemplate.update(sql, parameterSource);
    }


    @Override
    public List<Long> findDepartmentIdByDepartmentName(String departmentName) {
        String sql = "SELECT department_id FROM users.department WHERE department_name = :departmentName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("departmentName",departmentName);
        return namedParameterJdbcTemplate.queryForList(sql, parameterSource, Long.class);
    }

    @Override
    public void deleteAllDepartments() {
        String sql = "DELETE FROM users.department";
        namedParameterJdbcTemplate.getJdbcOperations().execute(sql);
      }

    @Override
    public boolean isDepartmentExists(Department department) {
        String sql = "SELECT * FROM USERS.DEPARTMENT WHERE DEPARTMENT_NAME = :departmentName";
        SqlParameterSource parameterSource = new MapSqlParameterSource("departmentName",department.getDepartmentName());
        return !namedParameterJdbcTemplate.query(sql, parameterSource, new DepartmentMapper()).isEmpty();
    }

    @Override
    @Transactional
    public int updateDepartment(Department department) {
       if(isDepartmentExists(department)){
          return 0;}
      String sql = "UPDATE USERS.DEPARTMENT SET DEPARTMENT_NAME = :departmentName" +
              " WHERE DEPARTMENT_ID = :departmentId";
        SqlParameterSource parameterSource = new MapSqlParameterSource("departmentName",department.getDepartmentName())
                .addValue("departmentId",department.getId());
        //namedParameterJdbcTemplate.update(sql,parameterSource);
        return namedParameterJdbcTemplate.update(sql,parameterSource);

    }
}
