package com.hassdata.survey.dto;

import java.io.Serializable;
import java.util.List;

public class NewsIndexDTO implements Serializable{
    private String typeName;
    private List<NewsDTO> newsDTOList;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<NewsDTO> getNewsDTOList() {
        return newsDTOList;
    }

    public void setNewsDTOList(List<NewsDTO> newsDTOList) {
        this.newsDTOList = newsDTOList;
    }
}
