package com.hassdata.survey.po;

import java.io.Serializable;

public class Question implements Serializable {
    private Long id;
    private String questionname;
    private Integer questionstyle;
    private Integer questiontypeid;
    private Integer questionnaireid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionname() {
        return questionname;
    }

    public void setQuestionname(String questionname) {
        this.questionname = questionname;
    }

    public Integer getQuestionstyle() {
        return questionstyle;
    }

    public void setQuestionstyle(Integer questionstyle) {
        this.questionstyle = questionstyle;
    }

    public Integer getQuestiontypeid() {
        return questiontypeid;
    }

    public void setQuestiontypeid(Integer questiontypeid) {
        this.questiontypeid = questiontypeid;
    }

    public Integer getQuestionnaireid() {
        return questionnaireid;
    }

    public void setQuestionnaireid(Integer questionnaireid) {
        this.questionnaireid = questionnaireid;
    }
}
