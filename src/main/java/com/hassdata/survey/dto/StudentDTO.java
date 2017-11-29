package com.hassdata.survey.dto;

import java.io.Serializable;

public class StudentDTO implements Serializable {
    private String id;
    private Integer aid;
    private String studentname;
    private String sex;
    private Integer age;
    private String grade;
    private String classes;
    private Integer questionnairenumber;

    public Integer getQuestionnairenumber() {
        return questionnairenumber;
    }

    public void setQuestionnairenumber(Integer questionnairenumber) {
        this.questionnairenumber = questionnairenumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
