package ru.matritca.jdbctemplate.domain.users;

/**
 * Created by Vasiliy on 17.08.2015.
 */
public enum LogbookUserRoleEnum {

    ADMINISTRATOR(0,"Администратор"),
    WRITER(1,"Редактор"),
    READER(2,"Читатель");

    private  int intValue;
    private  String label;

    LogbookUserRoleEnum(int intValue, String label) {
          this.intValue = intValue;
          this.label = label;
    }

    public static String getLabel(LogbookUserRoleEnum logbookUserRoleEnum){
        return  logbookUserRoleEnum.getLabel();
    }

    public static LogbookUserRoleEnum getLogbookUserRole(int intValue){
        LogbookUserRoleEnum[] userRoles = LogbookUserRoleEnum.values();
        for (LogbookUserRoleEnum userRole : userRoles) {
           if(userRole.getIntValue() == intValue){
               return userRole;
           }
        }
       return  LogbookUserRoleEnum.ADMINISTRATOR;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
