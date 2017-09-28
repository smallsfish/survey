package com.hassdata.survey.po;

import java.io.Serializable;

public class QuestionType implements Serializable {
    private String id;
    private String questionTypename;
    private String questionTypecolor;
    private Integer questionTypesort;
    private String questionnaireid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionTypename() {
        return questionTypename;
    }

    public void setQuestionTypename(String questionTypename) {
        this.questionTypename = questionTypename;
    }

    public String getQuestionTypecolor() {
        return questionTypecolor;
    }

    public void setQuestionTypecolor(String questionTypecolor) {
        this.questionTypecolor = questionTypecolor;
    }

    public Integer getQuestionTypesort() {
        return questionTypesort;
    }

    public void setQuestionTypesort(Integer questionTypesort) {
        this.questionTypesort = questionTypesort;
    }

    public String getQuestionnaireid() {
        return questionnaireid;
    }

    public void setQuestionnaireid(String questionnaireid) {
        this.questionnaireid = questionnaireid;
    }
}
