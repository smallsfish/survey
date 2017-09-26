package com.hassdata.survey.po;

import java.io.Serializable;

public class Options implements Serializable {
    private Long id;
    private String optionsname;
    private Long questionid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionsname() {
        return optionsname;
    }

    public void setOptionsname(String optionsname) {
        this.optionsname = optionsname;
    }

    public Long getQuestionid() {
        return questionid;
    }

    public void setQuestionid(Long questionid) {
        this.questionid = questionid;
    }
}
