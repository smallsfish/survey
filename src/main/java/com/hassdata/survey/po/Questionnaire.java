package com.hassdata.survey.po;

import java.io.Serializable;
import java.util.Date;

public class Questionnaire implements Serializable {
    private Integer id;
    private String questionnairename;
    private String questionnairecomp;
    private String questionnairefrom;
    private Date questionnairebegintime;
    private Date questionnaireendtime;
    private Date questionnairecreatetime;
    private String questionnaireexplain;
    private Integer aid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionnairename() {
        return questionnairename;
    }

    public void setQuestionnairename(String questionnairename) {
        this.questionnairename = questionnairename;
    }

    public String getQuestionnairecomp() {
        return questionnairecomp;
    }

    public void setQuestionnairecomp(String questionnairecomp) {
        this.questionnairecomp = questionnairecomp;
    }

    public String getQuestionnairefrom() {
        return questionnairefrom;
    }

    public void setQuestionnairefrom(String questionnairefrom) {
        this.questionnairefrom = questionnairefrom;
    }

    public Date getQuestionnairebegintime() {
        return questionnairebegintime;
    }

    public void setQuestionnairebegintime(Date questionnairebegintime) {
        this.questionnairebegintime = questionnairebegintime;
    }

    public Date getQuestionnaireendtime() {
        return questionnaireendtime;
    }

    public void setQuestionnaireendtime(Date questionnaireendtime) {
        this.questionnaireendtime = questionnaireendtime;
    }

    public Date getQuestionnairecreatetime() {
        return questionnairecreatetime;
    }

    public void setQuestionnairecreatetime(Date questionnairecreatetime) {
        this.questionnairecreatetime = questionnairecreatetime;
    }

    public String getQuestionnaireexplain() {
        return questionnaireexplain;
    }

    public void setQuestionnaireexplain(String questionnaireexplain) {
        this.questionnaireexplain = questionnaireexplain;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }
}
