package ru.matritca.jdbctemplate.repository.department;

import ru.matritca.jdbctemplate.domain.Department;

import java.util.List;

/**
 * Created by Vasiliy on 17.08.2015.
 */
public interface DepartmentDao {

    int addDepartment(Department department);
    int[] addListOfDepartment(List<Department> departmentList);
    Department findDepartmentByName(String departmentName);
    Department findDepartmentById(long id);
    List<Department> findAllDepartments();
    int deleteDepartmentByDepartmentName(String departmentName);
    int findDepartmentIdByDepartmentName(String departmentName);

}
