package com.hassdata.survey.dto;

import java.io.Serializable;
import java.util.List;

public class DataDTO implements Serializable {
    private String questionname;
    private List<OptionDTO> optionDTOS;

    public String getQuestionname() {
        return questionname;
    }

    public void setQuestionname(String questionname) {
        this.questionname = questionname;
    }

    public List<OptionDTO> getOptionDTOS() {
        return optionDTOS;
    }

    public void setOptionDTOS(List<OptionDTO> optionDTOS) {
        this.optionDTOS = optionDTOS;
    }
}
