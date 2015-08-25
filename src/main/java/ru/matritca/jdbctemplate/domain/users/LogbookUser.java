package ru.matritca.jdbctemplate.domain.users;

import java.io.Serializable;

/**
 * Created by Василий on 16.08.2015.
 */
public class LogbookUser implements Serializable,Comparable<LogbookUser>{

     private long id;
     private String username;
     private String lastname;
     private String firstname;
     private String password;
     private String role;
     private String organizationName;
     private String departmentName;
     private String jobtitleName;
     private byte[] certificate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobtitleName() {
        return jobtitleName;
    }

    public void setJobtitleName(String jobtitleName) {
        this.jobtitleName = jobtitleName;
    }

    @Override
    public int compareTo(LogbookUser o) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
