package com.hassdata.survey.po;

import java.io.Serializable;

public class Question implements Serializable {
    private String id;
    private String questionname;
    private String questionstyle;
    private String questiontypeid;
    private String questionnaireid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionname() {
        return questionname;
    }

    public void setQuestionname(String questionname) {
        this.questionname = questionname;
    }

    public String getQuestionstyle() {
        return questionstyle;
    }

    public void setQuestionstyle(String questionstyle) {
        this.questionstyle = questionstyle;
    }

    public String getQuestiontypeid() {
        return questiontypeid;
    }

    public void setQuestiontypeid(String questiontypeid) {
        this.questiontypeid = questiontypeid;
    }

    public String getQuestionnaireid() {
        return questionnaireid;
    }

    public void setQuestionnaireid(String questionnaireid) {
        this.questionnaireid = questionnaireid;
    }
}
