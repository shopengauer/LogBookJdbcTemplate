package ru.matritca.jdbctemplate.domain.users;

/**
 * Created by Vasiliy on 17.08.2015.
 */
public class Department implements Comparable<Department>{

   private long id;
   private String departmentName;

    public Department() {
    }

    public Department(String departmentName) {
        this.departmentName = departmentName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if ((obj != null) && (obj instanceof Department)) {
            Department target = (Department)obj;
            isEqual = target.getDepartmentName().equals(this.getDepartmentName());
        }
        return isEqual;

    }

    @Override
    public int compareTo(Department o) {
       return o.getDepartmentName().compareTo(this.departmentName);
    }

    @Override
    public int hashCode() {
       return departmentName.hashCode();
    }
}
