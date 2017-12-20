package com.hassdata.survey.dto;

import com.hassdata.survey.po.Pictures;

import java.io.Serializable;
import java.util.List;

public class PictureIndexDTO implements Serializable {
    private String pictureTypeName;
    private List<Pictures> picturesList;

    public String getPictureTypeName() {
        return pictureTypeName;
    }

    public void setPictureTypeName(String pictureTypeName) {
        this.pictureTypeName = pictureTypeName;
    }

    public List<Pictures> getPicturesList() {
        return picturesList;
    }

    public void setPicturesList(List<Pictures> picturesList) {
        this.picturesList = picturesList;
    }
}
