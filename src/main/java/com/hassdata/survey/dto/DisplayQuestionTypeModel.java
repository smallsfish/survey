package com.hassdata.survey.dto;

import com.hassdata.survey.po.QuestionType;

import java.io.Serializable;
import java.util.List;

public class DisplayQuestionTypeModel implements Serializable {
    private QuestionType questionType;
    private List<DisplayQuestionModel> displayQuestionModels;

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public List<DisplayQuestionModel> getDisplayQuestionModels() {
        return displayQuestionModels;
    }

    public void setDisplayQuestionModels(List<DisplayQuestionModel> displayQuestionModels) {
        this.displayQuestionModels = displayQuestionModels;
    }
}
