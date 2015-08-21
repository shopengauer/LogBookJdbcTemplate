package ru.matritca.jdbctemplate.domain;

/**
 * Created by Vasiliy on 18.08.2015.
 */
public class LogbookUserRole {

    private long id;
    private String logbookUserRoleName;
    private String logbookUserRoleDesc;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogbookUserRoleName() {
        return logbookUserRoleName;
    }

    public void setLogbookUserRoleName(String logbookUserRoleName) {
        this.logbookUserRoleName = logbookUserRoleName;
    }

    public String getLogbookUserRoleDesc() {
        return logbookUserRoleDesc;
    }

    public void setLogbookUserRoleDesc(String logbookUserRoleDesc) {
        this.logbookUserRoleDesc = logbookUserRoleDesc;
    }
}
