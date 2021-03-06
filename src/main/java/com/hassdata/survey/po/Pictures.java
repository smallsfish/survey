package com.hassdata.survey.po;

import java.io.Serializable;

public class Pictures implements Serializable {
    private Long id;
    private String pictureurl;
    private String picturetitle;
    private Integer picturetype;
    private Boolean status;
    private Integer operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPictureurl() {
        return pictureurl;
    }

    public void setPictureurl(String pictureurl) {
        this.pictureurl = pictureurl;
    }

    public String getPicturetitle() {
        return picturetitle;
    }

    public void setPicturetitle(String picturetitle) {
        this.picturetitle = picturetitle;
    }

    public Integer getPicturetype() {
        return picturetype;
    }

    public void setPicturetype(Integer picturetype) {
        this.picturetype = picturetype;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }
}
