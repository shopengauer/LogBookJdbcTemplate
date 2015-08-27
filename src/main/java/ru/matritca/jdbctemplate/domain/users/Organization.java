package ru.matritca.jdbctemplate.domain.users;

/**
 * Created by Vasiliy on 17.08.2015.
 */
public class Organization {

    private long id;
    private String organizationName;
    private String organizationDesc;

    public Organization() {
    }

    public Organization(String organizationName) {
        this.organizationName = organizationName;
    }

    public Organization(String organizationName, String organizationDescription) {
        this.organizationName = organizationName;
        this.organizationDesc = organizationDescription;
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

    public String getOrganizationDesc() {
        return organizationDesc;
    }

    public void setOrganizationDesc(String organizationDesc) {
        this.organizationDesc = organizationDesc;
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if ((obj != null) && (obj instanceof Organization)) {
            Organization target = (Organization)obj;
            isEqual = target.getOrganizationName().equals(this.getOrganizationName());
        }
        return isEqual;

    }

}
