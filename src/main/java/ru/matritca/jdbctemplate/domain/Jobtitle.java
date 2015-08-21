package ru.matritca.jdbctemplate.domain;

/**
 * Created by Vasiliy on 17.08.2015.
 */
public class Jobtitle {

    private long id;
    private String jobtitleName;

    public Jobtitle() {
    }

    public Jobtitle(String jobtitleName) {
        this.jobtitleName = jobtitleName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJobtitleName() {
        return jobtitleName;
    }

    public void setJobtitleName(String jobtitleName) {
        this.jobtitleName = jobtitleName;
    }
}
