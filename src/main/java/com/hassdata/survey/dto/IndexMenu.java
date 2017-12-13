package com.hassdata.survey.dto;

import java.io.Serializable;
import java.util.List;

public class IndexMenu implements Serializable {
    private String name;
    private List<MenuUrl> menuUrlList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuUrl> getMenuUrlList() {
        return menuUrlList;
    }

    public void setMenuUrlList(List<MenuUrl> menuUrlList) {
        this.menuUrlList = menuUrlList;
    }
}
