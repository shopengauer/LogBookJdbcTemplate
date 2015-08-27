package ru.matritca.jdbctemplate.domain.users;

/**
 * Created by Vasiliy on 18.08.2015.
 */
public class LogbookUserRole {

    private long id;
    private String logbookUserRoleName;
    private String logbookUserRoleDesc;

    public LogbookUserRole() {
    }

    public LogbookUserRole(String logbookUserRoleName) {
        this.logbookUserRoleName = logbookUserRoleName;
    }

    public LogbookUserRole(String logbookUserRoleName, String logbookUserRoleDesc) {
        this.logbookUserRoleName = logbookUserRoleName;
        this.logbookUserRoleDesc = logbookUserRoleDesc;
    }

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

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = false;
        if ((obj != null) && (obj instanceof LogbookUserRole)) {
            LogbookUserRole target = (LogbookUserRole)obj;
            isEqual = target.getLogbookUserRoleName().equals(this.getLogbookUserRoleName());
        }
        return isEqual;

    }
}
