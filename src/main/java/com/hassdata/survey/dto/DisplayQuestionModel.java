package com.hassdata.survey.dto;

import com.hassdata.survey.po.Options;
import com.hassdata.survey.po.Question;

import java.io.Serializable;
import java.util.List;

public class DisplayQuestionModel implements Serializable {
    private Question question;
    private List<Options> optionsList;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Options> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<Options> optionsList) {
        this.optionsList = optionsList;
    }
}
