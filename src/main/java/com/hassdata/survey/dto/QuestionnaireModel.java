package com.hassdata.survey.dto;

import java.util.Date;

public class QuestionnaireModel {
    private String id;
    private String questionnairename;
    private Date questionnairebegintime;
    private Date questionnaireendtime;
    private Date questionnairecreatetime;
    private String author;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionnairename() {
        return questionnairename;
    }

    public void setQuestionnairename(String questionnairename) {
        this.questionnairename = questionnairename;
    }

    public Date getQuestionnairebegintime() {
        return questionnairebegintime;
    }

    public void setQuestionnairebegintime(Date questionnairebegintime) {
        this.questionnairebegintime = questionnairebegintime;
    }

    public Date getQuestionnaireendtime() {
        return questionnaireendtime;
    }

    public void setQuestionnaireendtime(Date questionnaireendtime) {
        this.questionnaireendtime = questionnaireendtime;
    }

    public Date getQuestionnairecreatetime() {
        return questionnairecreatetime;
    }

    public void setQuestionnairecreatetime(Date questionnairecreatetime) {
        this.questionnairecreatetime = questionnairecreatetime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
