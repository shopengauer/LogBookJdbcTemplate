package ru.matritca.jdbctemplate.domain.users;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Василий on 16.08.2015.
 */
public class LogbookUser implements Serializable,Comparable<LogbookUser>{

     private long id;
     private String username;
     private String firstname;
     private String lastname;
     private String password;
     private List<String> roles;
     private byte[] certificate;
     private String organizationName;
     private String departmentName;
     private String jobtitleName;



    public LogbookUser() {

    }

    public LogbookUser(String username, String lastname, String firstname, List<String> roles) {
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.roles = roles;
    }

    public LogbookUser(String username, String lastname, String firstname, List<String> roles,String organizationName, String departmentName, String jobtitleName) {
        this.username = username;
        this.lastname = lastname;
        this.firstname = firstname;
        this.roles = roles;
    }

    public LogbookUser(String username, String lastname, String firstname, String password, List<String> roles) {
        this(username,lastname,firstname,roles);
        this.password = password;

    }

    public LogbookUser(String username, String lastname, String firstname, String password, List<String> roles, String organizationName, String departmentName, String jobtitleName) {
        this(username,lastname,firstname,password,roles);
        this.organizationName = organizationName;
        this.departmentName = departmentName;
        this.jobtitleName = jobtitleName;
    }




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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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

    public byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    @Override
    public int compareTo(LogbookUser o) {
        return 0;
    }




    @Override
    public boolean equals(Object obj) {

        boolean isEqual = false;
        if ((obj != null) && (obj instanceof LogbookUser)) {
            LogbookUser user = (LogbookUser) obj;
            isEqual = this.username.equals(user.username) && this.firstname.equals(user.firstname)
                    && this.lastname.equals(user.lastname) && this.password.equals(user.password)
                    && Arrays.equals(this.certificate, user.certificate) && this.departmentName.equals(user.departmentName)
                    && this.jobtitleName.equals(user.jobtitleName) && this.organizationName.equals(user.organizationName)
                    && this.roles.containsAll(user.roles);
        }
        return isEqual;

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
