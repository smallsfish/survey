package com.hassdata.survey.dto;

import com.hassdata.survey.po.Questionnaire;

import java.io.Serializable;
import java.util.List;

public class DisplayQuestionnaireModel implements Serializable{
    private Questionnaire questionnaire;
    private List<DisplayQuestionTypeModel> displayQuestionTypeModels;

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(Questionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public List<DisplayQuestionTypeModel> getDisplayQuestionTypeModels() {
        return displayQuestionTypeModels;
    }

    public void setDisplayQuestionTypeModels(List<DisplayQuestionTypeModel> displayQuestionTypeModels) {
        this.displayQuestionTypeModels = displayQuestionTypeModels;
    }
}
