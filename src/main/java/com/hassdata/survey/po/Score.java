package com.hassdata.survey.po;

import java.io.Serializable;

public class Score implements Serializable{
    private Long id;
    private Integer questionnaireid;
    private Long questionid;
    private String options;
    private Long sid;
    private Integer uid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuestionnaireid() {
        return questionnaireid;
    }

    public void setQuestionnaireid(Integer questionnaireid) {
        this.questionnaireid = questionnaireid;
    }

    public Long getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Long questionid) {
        this.questionid = questionid;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}
