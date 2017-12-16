package com.hassdata.survey.controller.diplay;

import com.hassdata.survey.dto.DisplayQuestionTypeModel;
import com.hassdata.survey.dto.DisplayQuestionnaireModel;
import com.hassdata.survey.po.QuestionType;
import com.hassdata.survey.po.Questionnaire;
import com.hassdata.survey.service.OptionsService;
import com.hassdata.survey.service.QuestionService;
import com.hassdata.survey.service.QuestionTypeService;
import com.hassdata.survey.service.QuestionnaireService;
import com.hassdata.survey.util.QuestionnaireSort;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("display")
@Scope("prototype")
public class DisplayQuestionnaireController {

    private SimpleDateFormat format=new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH 时 mm 分 ss 秒");

    @Resource
    private QuestionService questionService;
    @Resource
    private QuestionnaireService questionnaireService;
    @Resource
    private QuestionTypeService questionTypeService;
    @Resource
    private OptionsService optionsService;
    @RequestMapping(value = "displayQuestionnaire",method = RequestMethod.GET)
    public String getDisplayQuestionnaire(String id, ModelMap map){
        Questionnaire questionnaire = questionnaireService.findByStringId(id);
        if(questionnaire==null){
            map.addAttribute("tips","该问卷已失效，或者不存在！，感谢您的参与！");
            return "display/questionnaire/tips";
        }
        Date nDate=new Date();
        Date bDate=questionnaire.getQuestionnairebegintime();
        Date eDate=questionnaire.getQuestionnaireendtime();
        if(bDate!=null && eDate!=null){
            //如果开始结束时间都不为空
            if(nDate.getTime()< bDate.getTime()){
                map.addAttribute("tips","问卷暂未开始，开始时间为："+format.format(bDate)+"   ，感谢您的积极参与！");
                return "display/questionnaire/tips";
            }
            if(nDate.getTime()>eDate.getTime()){
                //如果当前时间不在问卷有效期内，问卷不可填写
                map.addAttribute("tips","问卷已经结束啦，结束时间为："+format.format(eDate)+"    ,感谢您的积极参与！");
                return "display/questionnaire/tips";
            }
        }else if (bDate!=null && eDate==null){
            //开始时间不为空，结束时间为空
            if(nDate.getTime()<bDate.getTime()){
                //如果当前时间小于开始时间，问卷不可填写
                map.addAttribute("tips","问卷暂未开始，开始时间为："+format.format(bDate)+"   ，感谢您的积极参与！");
                return "display/questionnaire/tips";
            }
        }else if(bDate==null && eDate!=null){
            //开始时间为空，结束时间不为空
            if(nDate.getTime()>eDate.getTime()){
                //如果当前时间大于结束时间，问卷不可填写
                map.addAttribute("tips","问卷已经结束啦，结束时间为："+format.format(eDate)+"    ,感谢您的积极参与！");
                return "display/questionnaire/tips";
            }
        }
        DisplayQuestionnaireModel displayQuestionnaireModel=new DisplayQuestionnaireModel();
        displayQuestionnaireModel.setQuestionnaire(questionnaire);
        QuestionType questionType=new QuestionType();
        questionType.setQuestionnaireid(questionnaire.getId());
        List<QuestionType> questionTypes=questionTypeService.getAll(questionType);
        DisplayQuestionTypeModel displayQuestionTypeModel=null;
        List<DisplayQuestionTypeModel> displayQuestionTypeModels=new ArrayList<>();
        QuestionnaireSort questionnaireSort=new QuestionnaireSort();
        questionnaireSort.questionSort(questionTypes, displayQuestionTypeModels,questionService,optionsService);
        displayQuestionnaireModel.setDisplayQuestionTypeModels(displayQuestionTypeModels);
        map.addAttribute("displayQuestionnaireModel",displayQuestionnaireModel);
        map.addAttribute("questionnaireId",id);
        return "display/questionnaire/index";
    }
}
