package ru.matritca.jdbctemplate.domain.users;

/**
 * Created by Vasiliy on 17.08.2015.
 */
public class Organization {

    private long id;
    private String organizationName;
    private String organizationDescription;

    public Organization() {
    }

    public Organization(String organizationName) {
        this.organizationName = organizationName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationDescription() {
        return organizationDescription;
    }

    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }
}
