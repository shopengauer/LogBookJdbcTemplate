package ru.matritca.jdbctemplate.domain.users;

import java.util.List;

/**
 * Created by Vasiliy on 28.08.2015.
 */
public class LogbookUser_v2 {


    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private List<String> roles;
    private byte[] certificate;
    private Organization organization;
    private Department department;
    private Jobtitle jobtitle;




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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public byte[] getCertificate() {
        return certificate;
    }

    public void setCertificate(byte[] certificate) {
        this.certificate = certificate;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Jobtitle getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(Jobtitle jobtitle) {
        this.jobtitle = jobtitle;
    }
}
