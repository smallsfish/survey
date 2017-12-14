package com.hassdata.survey.dto;

import java.io.Serializable;
import java.util.List;

public class QuestionCharts implements Serializable{
    private String name;
    private List<OptionInfo> optionInfos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OptionInfo> getOptionInfos() {
        return optionInfos;
    }

    public void setOptionInfos(List<OptionInfo> optionInfos) {
        this.optionInfos = optionInfos;
    }
}
