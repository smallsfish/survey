package com.hassdata.survey.po;

import java.io.Serializable;

public class QuestionType implements Serializable {
    private Integer id;
    private String questiontypename;
    private String questiontypecolor;
    private Integer questiontypesort;
    private Integer questionnaireid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestiontypename() {
        return questiontypename;
    }

    public void setQuestiontypename(String questiontypename) {
        this.questiontypename = questiontypename;
    }

    public String getQuestiontypecolor() {
        return questiontypecolor;
    }

    public void setQuestiontypecolor(String questiontypecolor) {
        this.questiontypecolor = questiontypecolor;
    }

    public Integer getQuestiontypesort() {
        return questiontypesort;
    }

    public void setQuestiontypesort(Integer questiontypesort) {
        this.questiontypesort = questiontypesort;
    }

    public Integer getQuestionnaireid() {
        return questionnaireid;
    }

    public void setQuestionnaireid(Integer questionnaireid) {
        this.questionnaireid = questionnaireid;
    }
}
