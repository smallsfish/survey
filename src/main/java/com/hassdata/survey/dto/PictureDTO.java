package com.hassdata.survey.dto;

public class PictureDTO {
    private Integer aid;
    private Long id;
    private String pictureurl;
    private String picturetitle;
    private String picturetype;
    private String status;
    private String operator;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

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

    public String getPicturetype() {
        return picturetype;
    }

    public void setPicturetype(String picturetype) {
        this.picturetype = picturetype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
