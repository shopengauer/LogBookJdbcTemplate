package ru.matritca.jdbctemplate.repository.users.department;

import ru.matritca.jdbctemplate.domain.users.Department;

import java.util.List;
import java.util.Set;

/**
 * Created by Vasiliy on 17.08.2015.
 */
public interface DepartmentDao {

    int addDepartment(Department department);
    int addDepartmentIfNotExists(Department department);
    boolean isDepartmentExists(Department departmentName);
    int updateDepartment(Department department);
    int[] addListOfDepartment(List<Department> departmentList);
    int[] addSetOfDepartment(Set<Department> departmentList);
    List<Department> findDepartmentByName(String departmentName);
    List<Department> findDepartmentById(long id);
    List<Department> findAllDepartments();
    List<Long> findDepartmentIdByDepartmentName(String departmentName);
    int deleteDepartmentByDepartmentName(String departmentName);
    void deleteAllDepartments();


}
