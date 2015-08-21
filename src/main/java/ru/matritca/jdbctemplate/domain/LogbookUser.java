package ru.matritca.jdbctemplate.domain;

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
     private long organizationId;
     private long departmentId;
     private long jobtitleId;

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

    public long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }

    public long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    public long getJobtitleId() {
        return jobtitleId;
    }

    public void setJobtitleId(long jobtitleId) {
        this.jobtitleId = jobtitleId;
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
